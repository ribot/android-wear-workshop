package uk.co.ribot.androidwear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.ribot.androidwear.api.GitHubApi;
import uk.co.ribot.androidwear.api.GitHubApiService;
import uk.co.ribot.androidwear.model.Comment;
import uk.co.ribot.androidwear.model.Issue;
import uk.co.ribot.androidwear.util.LoginUtils;
import uk.co.ribot.androidwear.util.NotificationUtils;

import java.util.List;

public class MainActivity extends Activity {
    private final GitHubApiService mGitHubApi = GitHubApi.getApi();
    private List<Issue> mIssues;

    @InjectView(R.id.message) TextView mMessageTextView;
    @InjectView(R.id.get_issues) Button mGetIssuesButton;
    @InjectView(R.id.sign_in) Button mSigninButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);

        getIssues();
    }

    @SuppressWarnings("MagicConstant")
    @Override
    public void onResume() {
        super.onResume();

        boolean signedIn = LoginUtils.isLoggedIn(this);
        mSigninButton.setVisibility(showIf(!signedIn));
    }

    @OnClick(R.id.sign_in)
    public void onSignInClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.get_issues)
    public void onGetIssuesClicked(View view) {
        getIssues();
    }

    private void getIssues() {
        mGetIssuesButton.setEnabled(false);
        mMessageTextView.setText(R.string.loading);

        mGitHubApi.getIssues("ribot", "android-wear-workshop", issuesCallback);
    }

    private void getComment(final Issue issue) {
        mGitHubApi.getComments("ribot", "android-wear-workshop", issue.id, new Callback<List<Comment>>() {
            @Override
            public void success(List<Comment> comments, Response response) {
                NotificationUtils.notify(MainActivity.this, issue, comments);
                mGetIssuesButton.setEnabled(true);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("AndroidWear", "Comment api error: " + error);

                mMessageTextView.setText("Error: " + error);
                mGetIssuesButton.setEnabled(true);
            }
        });
    }

    private Callback<List<Issue>> issuesCallback = new Callback<List<Issue>>() {
        @Override
        public void success(List<Issue> issues, Response response) {
            if (mIssues != null) {
                for (Issue issue : issues) {
                    if (!mIssues.contains(issue)) {
                        getComment(issue);
                    }
                }
            }

            mIssues = issues;

            mMessageTextView.setText("" + issues.size());
            mGetIssuesButton.setEnabled(true);
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("AndroidWear", "Issues api error: " + error);

            mMessageTextView.setText("Error: " + error);
            mGetIssuesButton.setEnabled(true);
        }
    };

    private int showIf(boolean test) {
        return (test) ? View.VISIBLE : View.GONE;
    }
}
