package com.bshsalumni.auction.controller;
/*

 * Date : 01/03/23

 * Author : SWASTIK PREETAM DASH

 */

import com.bshsalumni.auction.common.RestMappingConstants;
import com.bshsalumni.auction.pojo.AuctionedPlayerPojo;
import com.bshsalumni.auction.pojo.TeamPojo;
import com.bshsalumni.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RestMappingConstants.AUCTION_BASE_MAPPING)
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping(value = RestMappingConstants.INIT_AUCTION)
    public ResponseEntity<Object> initAuction(@RequestBody List<TeamPojo> listOfTeams) {
        return new ResponseEntity<>(auctionService.initAuction(listOfTeams), HttpStatus.OK);
    }

    @GetMapping(value = RestMappingConstants.GET_SET)
    public ResponseEntity<Object> getSet() {
        return new ResponseEntity<>(auctionService.getSet(), HttpStatus.OK);
    }

    @GetMapping(value = RestMappingConstants.GET_TEAM)
    public ResponseEntity<Object> getTeam(@RequestParam(name = "teamId") int teamId) {
        return new ResponseEntity<>(auctionService.getTeam(teamId), HttpStatus.OK);
    }


    @PostMapping(value = RestMappingConstants.SELL_PLAYER)
    public ResponseEntity<Object> sellPlayer(@RequestBody AuctionedPlayerPojo auctionedPlayerPojo) {
        return new ResponseEntity<>(auctionService.sellPlayer(auctionedPlayerPojo), HttpStatus.OK);
    }

    @GetMapping(value = RestMappingConstants.END_AUCTION)
    public ResponseEntity<Object> endAuction() {
        return new ResponseEntity<>(auctionService.getSetMetaData(), HttpStatus.OK);
    }
}
