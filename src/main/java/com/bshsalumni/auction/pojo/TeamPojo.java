package com.bshsalumni.auction.pojo;
/*

 * Date : 05/03/23

 * Author : SWASTIK PREETAM DASH

 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamPojo {
    private Integer id;

    private String name;

    private String logo;

    private Integer captainId;

    private Integer walletRemaining;

    private String captainName;

    private String captainPassOutYear;

    private String captainImage;

}
