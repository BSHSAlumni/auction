package com.bshsalumni.auction.repo;

import com.bshsalumni.auction.model.TempSetPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempSetPlayerRepo extends JpaRepository<TempSetPlayer,Integer>{
    TempSetPlayer findFirstByOrderByOrdered();
}
