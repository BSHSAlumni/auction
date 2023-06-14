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

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = RestMappingConstants.AUCTION_BASE_MAPPING)
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping(value = RestMappingConstants.INIT_AUCTION)
    public ResponseEntity<Object> initAuction() {
        return new ResponseEntity<>(auctionService.initAuction(), HttpStatus.OK);
    }

    @GetMapping(value = RestMappingConstants.GET_NEXT_SET)
    public ResponseEntity<Object> getNextSet() {
        return new ResponseEntity<>(auctionService.getNextSet(), HttpStatus.OK);
    }

    @GetMapping(value = RestMappingConstants.GET_NEXT_PLAYER)
    public ResponseEntity<Object> getNextPlayer() {
        Object res = auctionService.getNextPlayer();
        if (res != null)
            return new ResponseEntity<>(auctionService.getNextPlayer(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = RestMappingConstants.GET_TEAM)
    public ResponseEntity<Object> getTeam(@RequestParam(name = "teamId") int teamId) {
        return new ResponseEntity<>(auctionService.getTeam(teamId), HttpStatus.OK);
    }

    @GetMapping(value = RestMappingConstants.GET_ALL_TEAMS)
    public ResponseEntity<Object> getAllTeam() {
        return new ResponseEntity<>(auctionService.getAllTeams(), HttpStatus.OK);
    }

    @PostMapping(value = RestMappingConstants.SELL_PLAYER)
    public ResponseEntity<Object> sellPlayer(@RequestBody AuctionedPlayerPojo auctionedPlayerPojo) {
        return new ResponseEntity<>(auctionService.sellPlayer(auctionedPlayerPojo), HttpStatus.OK);
    }
}
