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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo", nullable = false)
    private String logo;

    @Column(name = "captain_id", nullable = false)
    private Integer captainId;

    @Column(name = "wallet_remaining", nullable = false)
    private Integer walletRemaining;
}
