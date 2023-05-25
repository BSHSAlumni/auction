package com.bshsalumni.auction.repo;

import com.bshsalumni.auction.model.BiddingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiddingHistoryRepo extends JpaRepository<BiddingHistory, Integer> {
}
