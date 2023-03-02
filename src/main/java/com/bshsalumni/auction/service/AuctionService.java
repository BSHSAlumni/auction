package com.bshsalumni.auction.service;
/*

 * Date : 01/03/23

 * Author : SWASTIK PREETAM DASH

 */

import com.bshsalumni.auction.common.Constants;
import com.bshsalumni.auction.converter.AuctionConverter;
import com.bshsalumni.auction.pojo.SetMetaData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class AuctionService {

    @Autowired
    private AuctionConverter auctionConverter;

    private List<SetMetaData> metaDataList;

    public JsonNode getSetMetaData() {

        metaDataList = new ArrayList<>();

        HashMap<String, Long> params = new HashMap<>();
        try {
            String url = Constants.PLAYER_SERVICE_BASE_URL + Constants.PLAYER_SERVICE_SET_BASE_URL + Constants.PLAYER_SERVICE_SET_METADATA_URL;
            log.info("Request sent to {}", url);
            ResponseEntity<JsonNode> response
                    = new RestTemplate().getForEntity(
                    url,
                    JsonNode.class, params);

            log.info("Response received : {}", response.getBody());

            metaDataList = auctionConverter.jsonNodeToSetMetaDataPojo(response.getBody());

            return response.getBody();
        } catch (Exception ex) {
            log.error("Failed to receive valid response : {}", ex.getMessage());
        }

        return null;
    }

    public JsonNode getSet() {

        if (metaDataList.size() < 0)
            return null;

        HashMap<String, String> params = new HashMap<>();


        try {
            String url = String.format(
                    "%s%s%s?%s=%s&%s=%s",
                    Constants.PLAYER_SERVICE_BASE_URL, Constants.PLAYER_SERVICE_SET_BASE_URL, Constants.PLAYER_SERVICE_SET_DETAILS_URL,
                    "type", metaDataList.get(0).getType(),
                    "priority", metaDataList.get(0).getPriority()
            );
            log.info("Request sent to {}", url);
            ResponseEntity<JsonNode> response
                    = new RestTemplate().getForEntity(
                    url,
                    JsonNode.class, params);

            log.info("Response received : {}", response.getBody());
            metaDataList.remove(0);
            return response.getBody();
        } catch (Exception ex) {
            log.error("Failed to receive valid response -> {}", ex.getMessage());
        }

        return null;
    }
}
