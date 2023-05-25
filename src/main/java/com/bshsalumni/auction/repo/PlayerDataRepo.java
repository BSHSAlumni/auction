package com.bshsalumni.auction.repo;

import com.bshsalumni.auction.model.PlayerData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerDataRepo extends JpaRepository<PlayerData, Integer> {
    boolean existsByDataId(int dataId);

    Optional<PlayerData> findByDataId(int dataId);
}
