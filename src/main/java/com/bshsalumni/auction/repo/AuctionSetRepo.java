package com.bshsalumni.auction.repo;

import com.bshsalumni.auction.model.AuctionSet;
import com.bshsalumni.auction.pojo.SetMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionSetRepo extends JpaRepository<AuctionSet, Integer> {
    AuctionSet findFirstByOrderByOrdered();
}
