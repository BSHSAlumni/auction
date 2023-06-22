package com.bshsalumni.auction.pojo;
/*

 * Date : 02/03/23

 * Author : SWASTIK PREETAM DASH

 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDataPojo {

    private Integer id;
    private String name;
    private Integer dataId;
    private String image;
    private Boolean isSold;
    private Integer price;
    private String email;
    private String category;
    private Integer passOutYear;
}
