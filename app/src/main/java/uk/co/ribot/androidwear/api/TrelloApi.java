package uk.co.ribot.androidwear.api;

import retrofit.RestAdapter;

public class TrelloApi {
    public static TrelloService get() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("https://api.trello.com/1/")
                .build();

        return restAdapter.create(TrelloService.class);
    }
}
