package com.bshsalumni.auction.service;

import com.bshsalumni.auction.converter.PlayerDataConverter;
import com.bshsalumni.auction.model.PlayerData;
import com.bshsalumni.auction.pojo.PlayerDataPojo;
import com.bshsalumni.auction.repo.PlayerDataRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlayerService {

    @Autowired
    private PlayerDataRepo playerDataRepo;

    @Autowired
    private PlayerDataConverter converter;

    public List<PlayerDataPojo> getPlayers(List<Integer> playerIds) {
        List<PlayerData> playerData = playerDataRepo.findAllById(playerIds);

        log.info("{}", playerData);
        return playerData.stream().map(model -> converter.modelToPojo(model)).collect(Collectors.toList());
    }

    public JsonNode checkAndSavePlayers(JsonNode body) {
        ArrayNode players = body.get("players").deepCopy();
        ObjectNode metaData = body.get("metadata").deepCopy();
        ArrayNode newPlayers = JsonNodeFactory.instance.arrayNode();

        for (int i = 0; i < players.size(); i++) {
            ObjectNode innerNode = players.get(i).deepCopy();

            int dataId = innerNode.get("id").asInt();

            Optional<PlayerData> previous = playerDataRepo.findByDataId(dataId);

            if (previous.isPresent()) {
                if (previous.get().getIsSold())
                    continue;
            } else {
                PlayerData playerData = new PlayerData();
                playerData.setDataId(dataId);
                playerData.setName(innerNode.get("name").asText());
                playerData.setPrice(innerNode.get("base").asInt());
                playerData.setPrice(innerNode.get("base").asInt());
                playerData.setEmail(innerNode.get("email").asText());
                playerData.setIsSold(false);
                if (innerNode.has("image"))
                    playerData.setImage(innerNode.get("image").asText());
                else
                    playerData.setImage("https://drive.google.com/file/d/1uKrHs5Hy0kwgv0OdtsO8LrtF9rYBpjyZ/view?usp=sharing");

                playerDataRepo.save(playerData);
            }


            newPlayers.add(innerNode);
        }

        metaData.put("count", newPlayers.size());
        ObjectNode bodyNew = JsonNodeFactory.instance.objectNode();
        bodyNew.set("metadata", metaData);
        bodyNew.set("players", newPlayers);

        return bodyNew;
    }

    public PlayerDataPojo sellPlayer(Integer playerId, Integer price) {
        PlayerData playerData = playerDataRepo.findByDataId(playerId).orElseThrow();

        if (playerData.getIsSold())
            return null;

        playerData.setPrice(price);
        playerData.setIsSold(true);

        return converter.modelToPojo(playerDataRepo.save(playerData));
    }

    public void init() {
        playerDataRepo.deleteAll();
    }
}
