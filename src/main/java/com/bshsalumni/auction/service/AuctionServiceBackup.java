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
import com.bshsalumni.auction.pojo.TeamPojo;
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

import java.util.*;

@Service
@Slf4j
public class AuctionServiceBackup {

    @Autowired
    private BiddingHistoryRepo biddingHistoryRepo;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private AuctionConverter auctionConverter;

    @Autowired
    private CustomConfig customConfig;

    private List<SetMetaData> metaDataList;
    private List<ObjectNode> players;
    private String currentSet;

    public JsonNode initAuction(List<TeamPojo> teamsList) {
        if (customConfig.isFresh()) {
            log.info("Setting up teams...");
            teamService.init();
            playerService.init();
        }

        log.info("Fetching teams data...");
        ObjectNode teamsData = teamService.getTeamsData();

        log.info("Fetching sets data...");
        ArrayNode setData = getSetMetaData().deepCopy();

        ObjectNode data = JsonNodeFactory.instance.objectNode();
        data.set("teamsData", teamsData);
        data.set("setsData", setData);

        return data;
    }

    private JsonNode getSetMetaData() {

        log.info("Metadata : {}", metaDataList);

        metaDataList = new ArrayList<>();

        HashMap<String, Long> params = new HashMap<>();
        try {
            String url = Constants.PLAYER_SERVICE_BASE_URL + Constants.PLAYER_SERVICE_SET_BASE_URL + Constants.PLAYER_SERVICE_SET_METADATA_URL;
            log.info("Request sent to {}", url);
            ResponseEntity<JsonNode> response = new RestTemplate().getForEntity(url, JsonNode.class, params);

            log.info("Response received : {}", response.getBody());

            metaDataList = auctionConverter.jsonNodeToSetMetaDataPojo(response.getBody());

            return response.getBody();
        } catch (Exception ex) {
            log.error("Failed to receive valid response : {}", ex.getMessage());
        }

        return null;
    }

    public JsonNode getNextSet() {

        if (metaDataList.size() == 0) return null;

        if (players != null && players.size() > 0)
            return null;

        Collections.shuffle(metaDataList);

        HashMap<String, String> params = new HashMap<>();
        players = new ArrayList<>();


        try {
            String url = String.format("%s%s%s?%s=%s&%s=%s", Constants.PLAYER_SERVICE_BASE_URL, Constants.PLAYER_SERVICE_SET_BASE_URL, Constants.PLAYER_SERVICE_SET_DETAILS_URL, "type", metaDataList.get(0).getType(), "priority", metaDataList.get(0).getPriority());
            log.info("Request sent to {}", url);
            ResponseEntity<JsonNode> response = new RestTemplate().getForEntity(url, JsonNode.class, params);
            JsonNode body = response.getBody();

            if (body == null)
                return null;

            body = playerService.checkAndSavePlayers(body);
            currentSet = body.get("metadata").get("type").asText();
            parsePlayers(body);

            log.info("Response received (after filtering old values): {}", body);
            metaDataList.remove(0);

            ObjectNode node = body.deepCopy();
            node.set("teamsData", getAllTeams());

            return node;
        } catch (Exception ex) {
            log.error("Failed to receive valid response -> {}", ex.getMessage());
        }

        return null;
    }

    private void parsePlayers(JsonNode body) {
        players = new ArrayList<>();
        ArrayNode arrayNode = body.get("players").deepCopy();
        log.info("updating players...");

        arrayNode.forEach(node -> {
            players.add(node.deepCopy());
        });
    }

    public Object getTeam(int teamId) {
        return teamService.getTeam(teamId);
    }

    public boolean sellPlayer(AuctionedPlayerPojo auctionedPlayerPojo) {

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
        return true;
    }

    public ObjectNode getAllTeams() {
        return teamService.getTeamsData();
    }

    public Object getNextPlayer() {

        log.info("players {}", players);

        while (players == null || players.size() == 0) {
            JsonNode body = getNextSet();
            if (body == null)
                return null;
        }

        Collections.shuffle(players);

        ObjectNode nextPlayer = players.remove(0);
        nextPlayer.put("set", currentSet);

        return nextPlayer;
    }
}
