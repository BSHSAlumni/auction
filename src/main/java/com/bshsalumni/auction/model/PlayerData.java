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
@Table(name = "player_data")
public class PlayerData {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "data__id", nullable = false)
    private Integer dataId;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "is_sold", nullable = false)
    private Boolean isSold;

    @Column(name = "price", nullable = false)
    private Integer price;
}
