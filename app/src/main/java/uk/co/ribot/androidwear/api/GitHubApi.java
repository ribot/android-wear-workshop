package uk.co.ribot.androidwear.api;

import retrofit.RestAdapter;

public class GitHubApi {
    public static GitHubApiService getApi() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("https://api.github.com")
                .build();

        return restAdapter.create(GitHubApiService.class);
    }

    public static GitHubAuthService getAuth() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("https://github.com")
                .build();

        return restAdapter.create(GitHubAuthService.class);
    }
}
