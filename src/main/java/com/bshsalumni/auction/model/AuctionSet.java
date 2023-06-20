package com.bshsalumni.auction.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "auction_set")
@ToString
public class AuctionSet {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "set_name", nullable = false)
    private String setName;

    @Column(name = "total", nullable = false)
    private Integer total;

    @Column(name = "auctioned", nullable = false)
    private Integer auctioned;

    @Column(name = "ordered", nullable = false)
    private Integer ordered;

    @Column(name = "priority", nullable = false)
    private Integer priority;
}
