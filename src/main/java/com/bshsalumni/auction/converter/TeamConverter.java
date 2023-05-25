package com.bshsalumni.auction.converter;
/*

 * Date : 05/03/23

 * Author : SWASTIK PREETAM DASH

 */

import com.bshsalumni.auction.model.Team;
import com.bshsalumni.auction.pojo.TeamPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamConverter {

    @Autowired
    private ModelMapper mapper;

    public Team pojoToModel(TeamPojo team) {
        return mapper.map(team, Team.class);
    }

    public TeamPojo modelToPojo(Team team) {
        return mapper.map(team, TeamPojo.class);
    }
}
