package com.bshsalumni.auction.model;
/*

 * Date : 02/03/23

 * Author : SWASTIK PREETAM DASH

 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "player_data")
@ToString
public class PlayerData {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "data__id", nullable = false)
    private Integer dataId;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "is_sold", nullable = false)
    private Boolean isSold;

    @Column(name = "at_price", nullable = false)
    private Integer price;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "pass_out_year", nullable = false)
    private Integer passOutYear;
}
