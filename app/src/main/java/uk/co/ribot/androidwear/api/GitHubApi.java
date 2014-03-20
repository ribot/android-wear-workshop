package uk.co.ribot.androidwear.api;

import retrofit.RestAdapter;

public class GitHubApi {
    public static GitHubService get() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("https://api.github.com")
                .build();

        return restAdapter.create(GitHubService.class);
    }
}
