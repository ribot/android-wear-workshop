package uk.co.ribot.androidwear;

import android.app.Activity;
import android.os.Bundle;
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
import uk.co.ribot.androidwear.model.Issue;
import uk.co.ribot.androidwear.util.NotificationUtils;

import java.util.List;

public class MainActivity extends Activity {
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

    @OnClick(R.id.get_issues)
    public void onGetIssuesClicked(View view) {
        getIssues();
    }

    private void getIssues() {
        mGetIssuesButton.setEnabled(false);
        mMessageTextView.setText(R.string.loading);

        GitHubApi.get().getIssues("ribot", "android-wear-workshop", networkCallback);
    }

    private Callback<List<Issue>> networkCallback = new Callback<List<Issue>>() {
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
            mMessageTextView.setText("Error: " + error);
            mGetIssuesButton.setEnabled(true);
        }
    };
}
