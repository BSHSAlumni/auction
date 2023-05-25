package com.bshsalumni.auction.service;
/*

 * Date : 25/05/23

 * Author : SWASTIK PREETAM DASH

 */

import com.bshsalumni.auction.converter.TeamConverter;
import com.bshsalumni.auction.model.Team;
import com.bshsalumni.auction.model.TeamPlayerMap;
import com.bshsalumni.auction.pojo.PlayerDataPojo;
import com.bshsalumni.auction.pojo.TeamPojo;
import com.bshsalumni.auction.repo.TeamPlayerMapRepo;
import com.bshsalumni.auction.repo.TeamRepo;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public boolean init(List<TeamPojo> teamsList, int totalWallet) {

        if (teamsList == null)
            return false;

        repo.deleteAll();
        teamPlayerMap.deleteAll();

        for (TeamPojo team : teamsList) {
            team.setWalletRemaining(totalWallet);
            repo.save(converter.pojoToModel(team));
        }

        return true;
    }

    public ObjectNode getTeamsData() {
        ObjectNode data = JsonNodeFactory.instance.objectNode();
        int count = 1;
        for (Team team : repo.findAll()) {
            ObjectNode teamData = JsonNodeFactory.instance.objectNode();

            TeamPojo pojo = converter.modelToPojo(team);
            teamData.put("id", pojo.getId());
            teamData.put("name", pojo.getName());
            teamData.put("logo", pojo.getLogo());
            teamData.put("captainId", pojo.getCaptainId());
            teamData.put("wallet", pojo.getWalletRemaining());

            data.set("team_" + (count++), teamData);
        }
        return data;
    }

    public ObjectNode getTeam(int teamId) {
        Optional<Team> teamOpt = repo.findById(teamId);

        if (teamOpt.isEmpty())
            return null;

        List<Integer> playerIds = teamPlayerMap.findAllByTeamId(teamId).stream().map(TeamPlayerMap::getPlayerId).toList();
        log.info("{}", playerIds);
        List<PlayerDataPojo> players = playerService.getPlayers(playerIds);


        ObjectNode data = JsonNodeFactory.instance.objectNode();
        ObjectNode teamData = JsonNodeFactory.instance.objectNode();
        ObjectNode playersData = JsonNodeFactory.instance.objectNode();

        TeamPojo pojo = converter.modelToPojo(teamOpt.get());
        teamData.put("Name", pojo.getName());
        teamData.put("logo", pojo.getLogo());
        teamData.put("captainId", pojo.getCaptainId());
        teamData.put("wallet", pojo.getWalletRemaining());
        teamData.put("players", playerIds.size());

        data.set("team", teamData);

        players.forEach(playerDataPojo -> {
            playersData.put("name", playerDataPojo.getName());
            playersData.put("image", playerDataPojo.getImage());
            playersData.put("price", playerDataPojo.getPrice());
        });

        data.set("players", playersData);

        return data;
    }

    public void sellPlayer(Integer playerDataId, Integer teamId, Integer price) {
        log.info("selling player {} to {} at {}",playerDataId, teamId, price);

        int playerId = playerService.sellPlayer(playerDataId, price);

        TeamPlayerMap map = new TeamPlayerMap();
        map.setPlayerId(playerId);
        map.setTeamId(teamId);

        teamPlayerMap.save(map);

        Team team = repo.findById(teamId).orElseThrow();
        team.setWalletRemaining(team.getWalletRemaining() - price);

        repo.save(team);
    }
}
