package uk.co.ribot.androidwear.util;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginUtils {
    private static final String PREFS_NAME = "login.prefs";
    private static final String TOKEN_KEY = "token";

    public static void setLoginToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public static String getLoginToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, null);
    }

    public static boolean isLoggedIn(Context context) {
        return (getLoginToken(context) != null);
    }
}
