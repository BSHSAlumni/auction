package com.bshsalumni.auction.model;
/*

 * Date : 13/06/23

 * Author : SWASTIK PREETAM DASH

 */

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "temp_player_set")
public class TempSetPlayer {

    @Id
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "pass_out_year")
    private Integer passOutYear;
    @Column(name = "batting_type")
    private String battingType;
    @Column(name = "batting_hand")
    private String battingHand;
    @Column(name = "bowling_type")
    private String bowlingType;
    @Column(name = "bowling_hand")
    private String bowlingHand;
    @Column(name = "is_wicket_keeper")
    private Boolean isWicketKeeper;
    @Column(name = "base")
    private Integer base;
    @Column(name = "image")
    private String image;

    @Column(name = "ordered")
    private Integer ordered;
}
