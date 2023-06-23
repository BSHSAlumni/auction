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
    public static final String NEW_TEAM_SUBJECT = "Team {0} welcomes you";
    public static final String NEW_TEAM_MESSAGE = "Hello {0}" +
            "\n" +
            "We are happy to inform you that {1} purchased you at the auction for a price of {2} points. While other teams made a valiant effort to sign you, it was the {3} who were able to secure your services.\n" +
            "\n" +
            "You are welcomed to the team by Captain {4}. We are excited to see you in action. Wish you luck...";
}
