package com.bshsalumni.auction.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*

 * Date : 02/03/23

 * Author : SWASTIK PREETAM DASH

 */
@Getter
@Setter
@Entity
@Table(name = "team_data")
public class Team {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "captain_id")
    private Integer captainId;

    @Column(name = "wallet_remaining", nullable = false)
    private Integer walletRemaining;

    @Column(name = "captain_name", nullable = false)
    private String captainName;

    @Column(name = "captain_pass_out_year", nullable = false)
    private String captainPassOutYear;

    @Column(name = "captain_image", nullable = false)
    private String captainImage;
}
