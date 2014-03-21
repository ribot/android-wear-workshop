package uk.co.ribot.androidwear.api;

import uk.co.ribot.androidwear.BuildConfig;

public class TokenRequest {
    public String client_id;
    public String client_secret;
    public String code;
    public String accept;

    public TokenRequest(String code) {
        this.client_id = BuildConfig.API_CLIENT_ID;
        this.client_secret = BuildConfig.API_CLIENT_SECRET;
        this.code = code;
        this.accept = "json";
    }
}

