package uk.co.ribot.androidwear.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import uk.co.ribot.androidwear.model.Issue;

import java.util.List;

public interface GitHubService {
    @GET("/repos/{owner}/{repoName}/issues")
    void getIssues(@Path("owner") String owner, @Path("repoName") String repoName, Callback<List<Issue>> callback);
}
