package uk.co.ribot.androidwear;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import hugo.weaving.DebugLog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.ribot.androidwear.api.GitHubApi;
import uk.co.ribot.androidwear.model.Issue;
import uk.co.ribot.androidwear.util.LoginUtils;

public class GitHubService extends IntentService {
    private static final String ACTION_CLOSE = "uk.co.ribot.androidwear.action.CLOSE";
    private static final String ISSUE_STRING_EXTRA = "uk.co.ribot.androidwear.extra.ISSUE_STRING_EXTRA";

    @DebugLog
    public static Intent getActionCloseIntent(Context context, Issue issue) {
        String issueString = new Gson().toJson(issue);

        Intent intent = new Intent(context, GitHubService.class);
        intent.setAction(ACTION_CLOSE);
        intent.putExtra(ISSUE_STRING_EXTRA, issueString);
        return intent;
    }

    public GitHubService() {
        super("GitHubService");
    }

    @Override @DebugLog
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CLOSE.equals(action)) {
                final String issueString = intent.getStringExtra(ISSUE_STRING_EXTRA);
                handleActionClose(issueString);
            }
        }
    }

    @DebugLog
    private void handleActionClose(String issueString) {
        Issue issue = new Gson().fromJson(issueString, Issue.class);
        issue.state = "closed";

        GitHubApi.getApi().patchIssue(LoginUtils.getLoginToken(this), "ribot", "android-wear-workshop", issue.id, issue, new Callback<Issue>() {
            @Override
            public void success(Issue issue, Response response) {
                Log.d("AW", "Closed " + issue);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("AW", "Error closing issue:  " + error.getResponse().getStatus());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getReason());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getBody().toString());
            }
        });
    }
}
