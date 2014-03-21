package uk.co.ribot.androidwear.api;

import retrofit.Callback;
import retrofit.http.*;
import uk.co.ribot.androidwear.model.Issue;

import java.util.List;

public interface GitHubApiService {
    @GET("/repos/{owner}/{repoName}/issues")
    void getIssues(@Path("owner") String owner,
                   @Path("repoName") String repoName,
                   Callback<List<Issue>> callback);

    @PATCH("/repos/{owner}/{repoName}/issues/{number}")
    void patchIssue(@Query("access_token") String accessToken,
                    @Path("owner") String owner,
                    @Path("repoName") String repoName,
                    @Path("number") int number,
                    @Body Issue issue,
                    Callback<Issue> callback);
}
