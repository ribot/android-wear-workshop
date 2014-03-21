package uk.co.ribot.androidwear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.ButterKnife;
import butterknife.InjectView;

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
                returnCode(code);
                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private void returnCode(String code) {
        Intent data = new Intent();
        data.putExtra(CODE_EXTRA, code);
        setResult(RESULT_OK, data);
        finish();
    }
}
