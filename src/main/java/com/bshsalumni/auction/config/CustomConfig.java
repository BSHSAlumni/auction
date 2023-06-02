package com.bshsalumni.auction.config;
/*

 * Date : 25/05/23

 * Author : SWASTIK PREETAM DASH

 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {


    @Value("${custom-config.fresh-auction}")
    private Boolean isFresh;
    @Value("${custom-config.total-wallet}")
    private Integer totalWallet;
    @Value("${spring.mail.username}") private String emailSender;

    public boolean isFresh() {
        return isFresh;
    }

    public int getTotalWallet() {
        return totalWallet;
    }

    public String getEmailSender() {
        return emailSender;
    }
}
