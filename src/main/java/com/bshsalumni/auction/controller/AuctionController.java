package com.bshsalumni.auction.controller;
/*

 * Date : 01/03/23

 * Author : SWASTIK PREETAM DASH

 */

import com.bshsalumni.auction.common.RestMappingConstants;
import com.bshsalumni.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = RestMappingConstants.AUCTION_BASE_MAPPING)
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping(value = RestMappingConstants.INIT_AUCTION)
    public ResponseEntity<Object> initAuction() {
        return new ResponseEntity<>(auctionService.getSetMetaData(), HttpStatus.OK);
    }

    @GetMapping(value = RestMappingConstants.GET_SET)
    public ResponseEntity<Object> getSet() {
        return new ResponseEntity<>(auctionService.getSet(), HttpStatus.OK);
    }

    @PostMapping(value = RestMappingConstants.SELL_PLAYER)
    public ResponseEntity<Object> sellPlayer() {
        return new ResponseEntity<>(auctionService.getSetMetaData(), HttpStatus.OK);
    }

    @GetMapping(value = RestMappingConstants.END_AUCTION)
    public ResponseEntity<Object> endAuction() {
        return new ResponseEntity<>(auctionService.getSetMetaData(), HttpStatus.OK);
    }
}
