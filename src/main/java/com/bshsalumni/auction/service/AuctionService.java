package com.bshsalumni.auction.service;
/*

 * Date : 01/03/23

 * Author : SWASTIK PREETAM DASH

 */

import com.bshsalumni.auction.common.Constants;
import com.bshsalumni.auction.config.CustomConfig;
import com.bshsalumni.auction.converter.AuctionConverter;
import com.bshsalumni.auction.model.BiddingHistory;
import com.bshsalumni.auction.pojo.AuctionedPlayerPojo;
import com.bshsalumni.auction.pojo.SetMetaData;
import com.bshsalumni.auction.repo.BiddingHistoryRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class AuctionService {

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private BiddingHistoryRepo biddingHistoryRepo;

    @Autowired
    private AuctionConverter auctionConverter;

    @Autowired
    private CustomConfig customConfig;

    @Autowired
    private TempDbService tempDbService;

    private List<SetMetaData> sets = null;

    public ObjectNode initAuction() {

        if (sets != null && sets.size() != 0)
            return JsonNodeFactory.instance.objectNode().put("error", "Auction already started...");

        log.info("Fetching teams data...");
        ObjectNode teamsData = teamService.getTeamsData();

        log.info("Fetching sets data...");
        ArrayNode setData = getSetMetaData().deepCopy();

        ObjectNode data = JsonNodeFactory.instance.objectNode();
        data.set("teamsData", teamsData);
        data.set("setsData", setData);

        return data;
    }

    public ObjectNode getNextSet() {

        if (sets.size() == 0) return JsonNodeFactory.instance.objectNode().put("error", "No more sets to view..");

        if (tempDbService.hasPlayersRemainingInPreviousSet())
            return JsonNodeFactory.instance.objectNode().put("error", "Players pending from previous set..");

        SetMetaData metaData = sets.remove(0);

        return getSetAndWriteToDb(metaData);
    }

    public ObjectNode getNextPlayer() {
        ObjectNode playerToBeAuctioned = tempDbService.nextPlayerFromDb();
        playerToBeAuctioned.put("category", playerService.getPlayers(Collections.singletonList(playerToBeAuctioned.get("id").asInt())).get(0).getCategory());

        if (playerToBeAuctioned == null)
            return JsonNodeFactory.instance.objectNode().put("error", "No more players in this set..");

        playerToBeAuctioned.put("remaining", tempDbService.remainingPlayersInSet());

        return playerToBeAuctioned;
    }

    public ObjectNode getTeam(int teamId) {
        return teamService.getTeam(teamId);
    }

    public ObjectNode getAllTeams() {
        return teamService.getTeamsData();
    }

    public ObjectNode sellPlayer(AuctionedPlayerPojo auctionedPlayerPojo) {
        if (auctionedPlayerPojo.getIsSold()) {
            teamService.sellPlayer(auctionedPlayerPojo.getPlayerId(), auctionedPlayerPojo.getTeamId(), auctionedPlayerPojo.getPrice());

            auctionedPlayerPojo.getBiddingHistory().forEach(bidPojo -> {

                BiddingHistory bidModel = new BiddingHistory();
                bidModel.setPlayerId(auctionedPlayerPojo.getPlayerId());
                bidModel.setTeamId(bidPojo.getTeamId());
                bidModel.setBidAt(bidPojo.getBidAt());
                bidModel.setIsWinningBid(bidPojo.getIsWinningBid());

                biddingHistoryRepo.save(bidModel);

            });
        }
        tempDbService.deletePlayer(auctionedPlayerPojo.getPlayerId());
        return getAllTeams();
    }

    private ArrayNode getSetMetaData() {
        sets = new ArrayList<>();

        try {
            HashMap<String, Long> params = new HashMap<>();
            String url = Constants.PLAYER_SERVICE_BASE_URL + Constants.PLAYER_SERVICE_SET_BASE_URL + Constants.PLAYER_SERVICE_SET_METADATA_URL;

            log.info("Request sent to {}", url);
            ResponseEntity<JsonNode> response = new RestTemplate().getForEntity(url, JsonNode.class, params);
            log.info("Response received : {}", response.getBody());

            sets = auctionConverter.jsonNodeToSetMetaDataPojo(response.getBody());

            Collections.shuffle(sets);

            if (response.hasBody() && response.getBody() != null) return response.getBody().deepCopy();
        } catch (Exception ex) {
            log.error("Failed to receive valid response : {}", ex.getMessage());
        }
        return JsonNodeFactory.instance.arrayNode();
    }

    private ObjectNode getSetAndWriteToDb(SetMetaData set) {
        try {

            String url = String.format("%s%s%s?%s=%s&%s=%s", Constants.PLAYER_SERVICE_BASE_URL, Constants.PLAYER_SERVICE_SET_BASE_URL, Constants.PLAYER_SERVICE_SET_DETAILS_URL, "type", set.getType(), "priority", set.getPriority());
            log.info("Request sent to {}", url);
            ResponseEntity<JsonNode> response = new RestTemplate().getForEntity(url, JsonNode.class, new HashMap<>());
            JsonNode body = response.getBody();

            if (body == null)
                throw new RuntimeException();

            body = playerService.checkAndSavePlayers(body, set.getType().substring(0,set.getType().indexOf("(")));

            tempDbService.writeSet(body);

            return body.deepCopy();
        } catch (Exception ex) {
            log.error("Failed to receive valid response -> {}", ex.getMessage());
        }
        return JsonNodeFactory.instance.objectNode();
    }

    public void deleteEverything() {
        sets = null;
        teamService.init();
        playerService.init();
        tempDbService.init();
    }
}
