package com.bshsalumni.auction.service;
/*

 * Date : 13/06/23

 * Author : SWASTIK PREETAM DASH

 */

import com.bshsalumni.auction.model.TempSetPlayer;
import com.bshsalumni.auction.repo.TempSetPlayerRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class TempDbService {

    @Autowired
    private TempSetPlayerRepo repo;


    public ObjectNode nextPlayerFromDb() {
        if (repo.count() == 0)
            return null;

        TempSetPlayer setPlayer = repo.findFirstByOrderByOrdered();

        ObjectNode node = JsonNodeFactory.instance.objectNode();

        node.put("id", setPlayer.getId());
        node.put("name", setPlayer.getName());
        node.put("phoneNumber", setPlayer.getPhoneNumber());
        node.put("email", setPlayer.getEmail());
        node.put("passOutYear", setPlayer.getPassOutYear());
        node.put("battingType", setPlayer.getBattingType());
        node.put("battingHand", setPlayer.getBattingHand());
        node.put("bowlingType", setPlayer.getBowlingType());
        node.put("bowlingHand", setPlayer.getBowlingHand());
        node.put("isWicketKeeper", setPlayer.getIsWicketKeeper());
        node.put("base", setPlayer.getBase());
        node.put("image", setPlayer.getImage());

        return node;
    }

    public int remainingPlayersInSet() {
        return (int) (repo.count() - 1);
    }

    public void writeSet(JsonNode body) {
        ArrayNode nodes = body.get("players").deepCopy();

        List<TempSetPlayer> list = new ArrayList<>();

        nodes.forEach(node -> {
            TempSetPlayer tempSetPlayer = new TempSetPlayer();

            tempSetPlayer.setId(node.get("id").asInt());
            tempSetPlayer.setName(node.get("name").asText());
            tempSetPlayer.setPhoneNumber(node.get("phoneNumber").asText());
            tempSetPlayer.setEmail(node.get("email").asText());
            tempSetPlayer.setPassOutYear(node.get("passOutYear").asInt());
            tempSetPlayer.setBattingType(node.get("battingType").asText());
            tempSetPlayer.setBattingHand(node.get("battingHand").asText());
            tempSetPlayer.setBowlingType(node.get("bowlingType").asText());
            tempSetPlayer.setBowlingHand(node.get("bowlingHand").asText());
            tempSetPlayer.setIsWicketKeeper(node.get("isWicketKeeper").asBoolean());
            tempSetPlayer.setBase(node.get("base").asInt());
            tempSetPlayer.setImage(node.get("image").asText());

            list.add(tempSetPlayer);
        });

        Collections.shuffle(list);

        int c = 1;
        for(TempSetPlayer tempSetPlayer : list){
            tempSetPlayer.setOrdered(c++);
            repo.save(tempSetPlayer);
        }
    }

    public boolean hasPlayersRemainingInPreviousSet() {
        return repo.count() != 0;
    }

    public void deletePlayer(Integer playerId) {
        repo.deleteById(playerId);
    }
}
