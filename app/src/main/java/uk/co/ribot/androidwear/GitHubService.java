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

public class GitHubService extends IntentService {
    private static final String ACTION_CLOSE = "uk.co.ribot.androidwear.action.CLOSE";
    private static final String ACTION_COMMENT = "uk.co.ribot.androidwear.action.COMMENT";
    private static final String ISSUE_STRING_EXTRA = "uk.co.ribot.androidwear.extra.ISSUE_STRING_EXTRA";
    private static final String COMMENT_STRING_EXTRA = "uk.co.ribot.androidwear.extra.COMMENT_STRING_EXTRA";

    public static Intent getActionCloseIntent(Context context, Issue issue) {
        String issueString = new Gson().toJson(issue);

        Intent intent = new Intent(context, GitHubService.class);
        intent.setAction(ACTION_CLOSE);
        intent.putExtra(ISSUE_STRING_EXTRA, issueString);
        return intent;
    }

    public static Intent getActionCommentIntent(Context context, Issue issue, Comment comment) {
        Gson gson = new Gson();
        String issueString = gson.toJson(issue);
        String commentString = gson.toJson(comment);

        Intent intent = new Intent(context, GitHubService.class);
        intent.setAction(ACTION_COMMENT);
        intent.putExtra(ISSUE_STRING_EXTRA, issueString);
        intent.putExtra(COMMENT_STRING_EXTRA, commentString);
        return intent;
    }

    public GitHubService() {
        super("GitHubService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CLOSE.equals(action)) {
                handleActionClose(getIssueFromIntent(intent));
            } else if (ACTION_COMMENT.equals(action)) {
                handleActionComment(getIssueFromIntent(intent), getCommentFromIntent(intent));
            }
        }
    }

    private Issue getIssueFromIntent(Intent intent) {
        return new Gson().fromJson(intent.getStringExtra(ISSUE_STRING_EXTRA), Issue.class);
    }

    private Comment getCommentFromIntent(Intent intent) {
        return new Gson().fromJson(intent.getStringExtra(COMMENT_STRING_EXTRA), Comment.class);
    }

    private void handleActionClose(final Issue issue) {
        issue.state = "closed";

        GitHubApi.getApi().patchIssue(LoginUtils.getLoginToken(this), "ribot", "android-wear-workshop", issue.id, issue, new Callback<Issue>() {
            @Override
            public void success(Issue issue, Response response) {
                NotificationUtils.dismiss(GitHubService.this, issue);
            }

            @Override
            public void failure(RetrofitError error) {
                //TODO Have notification ask you to retry
                issue.state = "open";
                NotificationUtils.notify(GitHubService.this, issue);
                Log.d("AW", "Error closing issue:  " + error.getResponse().getStatus());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getReason());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getBody().toString());
            }
        });
    }

    private void handleActionComment(final Issue issue, Comment comment) {
        GitHubApi.getApi().postComment(LoginUtils.getLoginToken(this), "ribot", "android-wear-workshop", issue.id, comment, new Callback<Comment>() {
            @Override
            public void success(Comment comment, Response response) {
                Log.d("AW", "COMPKLLETEEETED COMMENTING!!!!!");
                NotificationUtils.dismiss(GitHubService.this, issue);
            }

            @Override
            public void failure(RetrofitError error) {
                //TODO Have notification ask you to retry
                issue.state = "open";
                NotificationUtils.notify(GitHubService.this, issue);
                Log.d("AW", "Error closing issue:  " + error.getResponse().getStatus());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getReason());
                Log.d("AW", "Error closing issue:  " + error.getResponse().getBody().toString());
            }
        });
    }
}
