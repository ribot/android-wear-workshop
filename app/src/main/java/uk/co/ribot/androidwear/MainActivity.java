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
import uk.co.ribot.androidwear.api.GitHubAuthService;
import uk.co.ribot.androidwear.api.TokenRequest;
import uk.co.ribot.androidwear.model.AccessToken;
import uk.co.ribot.androidwear.model.Issue;
import uk.co.ribot.androidwear.util.NotificationUtils;

import java.util.List;

public class MainActivity extends Activity {
    private static final int LOGIN_REQUEST = 1000;

    private final GitHubApiService mGitHubApi = GitHubApi.getApi();
    private final GitHubAuthService mGitHubAuth = GitHubApi.getAuth();
    private List<Issue> mIssues;

    @InjectView(R.id.message) TextView mMessageTextView;
    @InjectView(R.id.get_issues) Button mGetIssuesButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);

        getIssues();
    }

    ///
    // Sign In
    ///

    @OnClick(R.id.sign_in)
    public void onSignInClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LOGIN_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_REQUEST && resultCode == RESULT_OK) {
            String code = data.getStringExtra(LoginActivity.CODE_EXTRA);
            mGitHubAuth.getAccessToken(new TokenRequest(code), accessTokenCallback);
        }
    }

    private Callback<AccessToken> accessTokenCallback = new Callback<AccessToken>() {
        @Override
        public void success(AccessToken accessToken, Response response) {
            Log.d("AndroidWear", "accessToken: " + accessToken.token);
        }

        @Override
        public void failure(RetrofitError error) {
            // TODO: Show login error message
            Log.e("AndroidWear", "Login api error: " + error);
        }
    };

    ///
    // Issues
    ///

    private void getIssues() {
        mGetIssuesButton.setEnabled(false);
        mMessageTextView.setText(R.string.loading);

        mGitHubApi.getIssues("ribot", "android-wear-workshop", issuesCallback);
    }

    private Callback<List<Issue>> issuesCallback = new Callback<List<Issue>>() {
        @Override
        public void success(List<Issue> issues, Response response) {
            if (mIssues != null) {
                for (Issue issue : issues) {
                    if (!mIssues.contains(issue)) {
                        NotificationUtils.notify(MainActivity.this, issue);
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
}
