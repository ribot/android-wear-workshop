package uk.co.ribot.androidwear;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.ribot.androidwear.api.GitHubApi;
import uk.co.ribot.androidwear.api.TokenRequest;
import uk.co.ribot.androidwear.model.AccessToken;
import uk.co.ribot.androidwear.util.LoginUtils;

public class LoginActivity extends Activity {
    public static final String CODE_EXTRA = "uk.co.ribot.androidwear.LoginActivity.CODE_EXTRA";

    private static final String CALLBACK_URL = "http://uk.co.ribot.androidwear.loginactivity";
    private static final String LOGIN_URL_FORMAT = "https://github.com/login/oauth/authorize?scope=public_repo&client_id=%s";

    @InjectView(R.id.login_webview) WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);

        webView.setWebViewClient(webClient);
        webView.loadUrl(String.format(LOGIN_URL_FORMAT, BuildConfig.API_CLIENT_ID));
    }

    private final WebViewClient webClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(CALLBACK_URL)) {
                String code = url.split("code=")[1];
                getAccessToken(code);
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private void getAccessToken(String code) {
        GitHubApi.getAuth().getAccessToken(new TokenRequest(code), accessTokenCallback);
    }

    private Callback<AccessToken> accessTokenCallback = new Callback<AccessToken>() {
        @Override
        public void success(AccessToken accessToken, Response response) {
            LoginUtils.setLoginToken(LoginActivity.this, accessToken.token);

            setResult(RESULT_OK);
            finish();
        }

        @Override
        public void failure(RetrofitError error) {
            // TODO: Show login error message
            Log.e("AndroidWear", "Login api error: " + error);
        }
    };
}
