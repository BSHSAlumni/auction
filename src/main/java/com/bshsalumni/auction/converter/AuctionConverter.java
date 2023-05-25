package com.bshsalumni.auction.converter;
/*

 * Date : 02/03/23

 * Author : SWASTIK PREETAM DASH

 */

import aj.org.objectweb.asm.TypeReference;
import com.bshsalumni.auction.pojo.SetMetaData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AuctionConverter {

    @Autowired
    private ModelMapper modelMapper;

    public List<SetMetaData> jsonNodeToSetMetaDataPojo(JsonNode node){
        ArrayNode arrayNode = (ArrayNode) node;

        List<SetMetaData> metaDataList = new ArrayList<>();
        arrayNode.forEach(element -> {
            SetMetaData metaData = new SetMetaData();
            metaData.setType(element.get("type").asText());
            metaData.setCount(element.get("count").asInt());
            metaData.setPriority(element.get("priority").asInt());
            metaDataList.add(metaData);
        });

        return metaDataList;
    }

}
