package com.bshsalumni.auction.service;
/*

 * Date : 25/05/23

 * Author : SWASTIK PREETAM DASH

 */

import com.bshsalumni.auction.common.Constants;
import com.bshsalumni.auction.config.CustomConfig;
import com.bshsalumni.auction.converter.TeamConverter;
import com.bshsalumni.auction.model.Team;
import com.bshsalumni.auction.model.TeamPlayerMap;
import com.bshsalumni.auction.pojo.PlayerDataPojo;
import com.bshsalumni.auction.pojo.TeamPojo;
import com.bshsalumni.auction.repo.TeamPlayerMapRepo;
import com.bshsalumni.auction.repo.TeamRepo;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TeamService {

    @Autowired
    private TeamRepo repo;

    @Autowired
    private TeamPlayerMapRepo teamPlayerMap;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamConverter converter;

    @Autowired
    private CustomConfig customConfig;

    @Autowired
    private JavaMailSender javaMailSender;

    public void init() {
//        repo.deleteAll();
        teamPlayerMap.deleteAll();
    }

    public ObjectNode getTeamsData() {
        ObjectNode data = JsonNodeFactory.instance.objectNode();
        int count = 1;

        log.info("Total teams found : {}", repo.count());

        for (Team team : repo.findAll()) {
            ObjectNode teamData = JsonNodeFactory.instance.objectNode();

            TeamPojo pojo = converter.modelToPojo(team);
            teamData.put("id", pojo.getId());
            teamData.put("name", pojo.getName());
            teamData.put("logo", pojo.getLogo());
            teamData.put("captainId", pojo.getCaptainId());
            teamData.put("wallet", pojo.getWalletRemaining());
            teamData.put("playersInTeam", teamPlayerMap.countByTeamId(pojo.getId()));

            data.set("team_" + (count++), teamData);
        }
        return data;
    }

    public ObjectNode getTeam(int teamId) {
        Optional<Team> teamOpt = repo.findById(teamId);

        if (teamOpt.isEmpty()) return null;

        List<Integer> playerIds = teamPlayerMap.findAllByTeamId(teamId).stream().map(TeamPlayerMap::getPlayerId).toList();
        log.info("player ids for the team {} are {}", teamId, playerIds);
        List<PlayerDataPojo> players = playerService.getPlayers(playerIds);


        ObjectNode data = JsonNodeFactory.instance.objectNode();
        ObjectNode teamData = JsonNodeFactory.instance.objectNode();
        ObjectNode captainData = JsonNodeFactory.instance.objectNode();
        ArrayNode playersData = JsonNodeFactory.instance.arrayNode();

        TeamPojo pojo = converter.modelToPojo(teamOpt.get());
        teamData.put("Name", pojo.getName());
        teamData.put("logo", pojo.getLogo());
        teamData.put("wallet", pojo.getWalletRemaining());
        teamData.put("players", playerIds.size());
        data.set("team", teamData);

        captainData.put("captainName", pojo.getCaptainName());
        captainData.put("captainPassOutYear", pojo.getCaptainPassOutYear());
        captainData.put("captainImage", pojo.getCaptainImage());
        data.set("captain", captainData);

        List<ObjectNode> playerObjectNode = new ArrayList<>();
        players.forEach(playerDataPojo -> {
            ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
            objectNode.put("name", playerDataPojo.getName());
            objectNode.put("image", playerDataPojo.getImage());
            objectNode.put("price", playerDataPojo.getPrice());
            objectNode.put("category", playerDataPojo.getCategory());
            playerObjectNode.add(objectNode);
        });

        playersData.addAll(playerObjectNode);
        data.set("players", playersData);

        return data;
    }

    public void sellPlayer(Integer playerDataId, Integer teamId, Integer price) {
        log.info("selling player {} to {} at {}", playerDataId, teamId, price);

        PlayerDataPojo player = playerService.sellPlayer(playerDataId, price);

        if (player == null)
            return;

        TeamPlayerMap map = new TeamPlayerMap();
        map.setPlayerId(player.getId());
        map.setTeamId(teamId);

        teamPlayerMap.save(map);

        Team team = repo.findById(teamId).orElseThrow();
        team.setWalletRemaining(team.getWalletRemaining() - price);

        repo.save(team);

//        CompletableFuture.supplyAsync(() -> {
//            sendNotificationToNewTeamEntry(team.getName(), team.getLogo(), player.getName(), player.getEmail(), price);
//            return true;
//        });
    }

    private void sendNotificationToNewTeamEntry(String teamName, String logo, String playerName, String email, Integer price) {

        log.info("Trying to send email to player...");

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(customConfig.getEmailSender());
            mailMessage.setTo(email);
            mailMessage.setText(MessageFormat.format(Constants.NEW_TEAM_MESSAGE, playerName, price, teamName));
            mailMessage.setSubject(MessageFormat.format(Constants.NEW_TEAM_SUBJECT, teamName));

            javaMailSender.send(mailMessage);

            log.info("Mail sent successfully...");
        } catch (Exception e) {
            log.info("Could not send mail to player {} as {}", playerName, e.getMessage());
        }
    }
}
