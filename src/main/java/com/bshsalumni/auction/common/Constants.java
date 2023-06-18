package com.bshsalumni.auction.common;

public class Constants {

    private Constants() {
    }

    //GLOBAL
    public static final Double VERSION = 1.0;
    public static final String MDC_TRACKER = "mdcTracker";
    public static final String PLAYER_SERVICE_BASE_SERVER = "http://localhost";
    public static final String PLAYER_SERVICE_BASE_PORT = "7676";
    public static final String PLAYER_SERVICE_BASE_URL = PLAYER_SERVICE_BASE_SERVER + ":" + PLAYER_SERVICE_BASE_PORT;
    public static final String PLAYER_SERVICE_PLAYER_BASE_URL = "/player";
    public static final String PLAYER_SERVICE_SET_BASE_URL = "/set";
    public static final String PLAYER_SERVICE_SET_METADATA_URL = "/metadata";
    public static final String PLAYER_SERVICE_SET_DETAILS_URL = "/details";
    public static final String NEW_TEAM_SUBJECT = "Welcome to Team {0}";
    public static final String NEW_TEAM_MESSAGE = "Hi {0},\n" +
            "You have been bought, after a tough bidding war between the teams, at a price of {1} points by Team {2}. Looking forward to seeing you in action.";
}
