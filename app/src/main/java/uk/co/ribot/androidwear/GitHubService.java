package uk.co.ribot.androidwear;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.gson.Gson;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.ribot.androidwear.api.GitHubApi;
import uk.co.ribot.androidwear.model.Comment;
import uk.co.ribot.androidwear.model.Issue;
import uk.co.ribot.androidwear.util.LoginUtils;
import uk.co.ribot.androidwear.util.NotificationUtils;
import hugo.weaving.DebugLog;
import android.os.Bundle;

public class GitHubService extends IntentService {
    public static final String EXTRA_COMMENT = "uk.co.ribot.androidwear.extra.EXTRA_COMMENT";

    private static final String ACTION_CLOSE = "uk.co.ribot.androidwear.action.CLOSE";
    private static final String ACTION_COMMENT = "uk.co.ribot.androidwear.action.COMMENT";
    private static final String EXTRA_ISSUE_STRING = "uk.co.ribot.androidwear.extra.EXTRA_ISSUE_STRING";
    
    public static Intent getActionCloseIntent(Context context, Issue issue) {
        return getActionIntent(context, issue, ACTION_CLOSE);
    }

    public static Intent getActionCommentIntent(Context context, Issue issue) {
        return getActionIntent(context, issue, ACTION_COMMENT);
    }

    public GitHubService() {
        super("GitHubService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CLOSE.equals(action)) {
                Issue issue = getIssueFromIntent(intent);
                handleActionClose(issue);
            } else if (ACTION_COMMENT.equals(action)) {
                handleActionComment(getIssueFromIntent(intent), getCommentFromIntent(intent));
            }
        }
    }

    private static Intent getActionIntent(Context context, Issue issue, String action) {
        String issueString = new Gson().toJson(issue);

        Intent intent = new Intent(context, GitHubService.class);
        intent.setAction(action);
        intent.putExtra(EXTRA_ISSUE_STRING, issueString);
        return intent;
    }

    @DebugLog
    private Issue getIssueFromIntent(Intent intent) {
        return new Gson().fromJson(intent.getStringExtra(EXTRA_ISSUE_STRING), Issue.class);
    }

    private Comment getCommentFromIntent(Intent intent) {
        return new Comment(intent.getStringExtra(EXTRA_COMMENT));
    }

    private void handleActionClose(final Issue issue) {
        issue.state = "closed";
        NotificationUtils.dismiss(GitHubService.this, issue);
        GitHubApi.getApi().patchIssue(LoginUtils.getLoginToken(this), "ribot", "android-wear-workshop", issue.id, issue, new Callback<Issue>() {
            @Override
            public void success(Issue issue, Response response) {
                
            }

            @Override
            public void failure(RetrofitError error) {
                //TODO Have notification ask you to retry
                Log.d("AW", "Error closing issue:  " + error.getResponse().getStatus());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getReason());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getBody().toString());
            }
        });
    }

    private void handleActionComment(final Issue issue, Comment comment) {
        NotificationUtils.dismiss(GitHubService.this, issue);
        GitHubApi.getApi().postComment(LoginUtils.getLoginToken(this), "ribot", "android-wear-workshop", issue.id, comment, new Callback<Comment>() {
            @Override
            public void success(Comment comment, Response response) {
                
            }

            @Override
            public void failure(RetrofitError error) {
                //TODO Have notification ask you to retry
                Log.d("AW", "Error closing issue:  " + error.getResponse().getStatus());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getReason());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getBody().toString());
            }
        });
    }
}
