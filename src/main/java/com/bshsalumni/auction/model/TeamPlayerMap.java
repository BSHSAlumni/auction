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
@Table(name = "team_player_map")
public class TeamPlayerMap {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @Column(name = "player_id", nullable = false)
    private Integer playerId;
}
