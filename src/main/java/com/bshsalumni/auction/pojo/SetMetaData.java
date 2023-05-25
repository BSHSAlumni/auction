package com.bshsalumni.auction.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/*

 * Date : 02/03/23

 * Author : SWASTIK PREETAM DASH

 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetMetaData {
    private String type;
    private Integer count;
    private Integer priority;
}
