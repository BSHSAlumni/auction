package com.bshsalumni.auction.config;
/*

 * Date : 25/05/23

 * Author : SWASTIK PREETAM DASH

 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomConfig {


    @Value("${custom-config.fresh-auction}")
    private Boolean isFresh;
    @Value("${custom-config.total-wallet}")
    private Integer totalWallet;

    public boolean isFresh() {
        return isFresh;
    }

    public int getTotalWallet() {
        return totalWallet;
    }
}
