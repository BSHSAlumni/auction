package com.bshsalumni.auction.pojo;
/*

 * Date : 25/05/23

 * Author : SWASTIK PREETAM DASH

 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuctionedPlayerPojo {
    private Integer playerId;
    private Boolean isSold;
    private Integer teamId;
    private Integer price;
    private List<BidPojo> biddingHistory;
}
