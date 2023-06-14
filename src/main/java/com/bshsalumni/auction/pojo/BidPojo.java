package com.bshsalumni.auction.pojo;
/*

 * Date : 05/03/23

 * Author : SWASTIK PREETAM DASH

 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BidPojo {

    private Integer id;
    private Integer teamId;
    private Integer bidAt;
    private Boolean isWinningBid;

}
