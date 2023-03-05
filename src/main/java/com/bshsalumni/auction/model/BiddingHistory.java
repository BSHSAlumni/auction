package com.bshsalumni.auction.model;
/*

 * Date : 02/03/23

 * Author : SWASTIK PREETAM DASH

 */

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "bidding_history")
public class BiddingHistory {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @Column(name = "player_id", nullable = false)
    private Integer playerId;

    @Column(name = "bid_at", nullable = false)
    private Integer bidAt;

    @Column(name = "is_winning_bid", nullable = false)
    private Boolean isWinningBid;

}
