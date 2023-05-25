package com.bshsalumni.auction.repo;

import com.bshsalumni.auction.model.TeamPlayerMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamPlayerMapRepo extends JpaRepository<TeamPlayerMap, Integer> {
    List<TeamPlayerMap> findAllByTeamId(int teamId);
}
