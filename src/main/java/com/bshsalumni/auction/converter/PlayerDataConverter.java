package com.bshsalumni.auction.converter;

import com.bshsalumni.auction.model.PlayerData;
import com.bshsalumni.auction.pojo.PlayerDataPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerDataConverter {

    @Autowired
    private ModelMapper mapper;

    public PlayerDataPojo modelToPojo(PlayerData model) {
        return mapper.map(model, PlayerDataPojo.class);
    }
}
