package uk.co.ribot.androidwear.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import uk.co.ribot.androidwear.model.AccessToken;

public interface GitHubAuthService {
    @POST("/login/oauth/access_token")
    void getAccessToken(@Body TokenRequest clientId,
                        Callback<AccessToken> callback);
}
