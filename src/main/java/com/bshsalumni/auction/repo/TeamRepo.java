package com.bshsalumni.auction.repo;

import com.bshsalumni.auction.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepo extends JpaRepository<Team,Integer> {
}
