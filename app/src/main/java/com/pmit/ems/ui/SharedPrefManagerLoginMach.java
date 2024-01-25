package com.pmit.ems.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.pmit.ems.model.UserLoginMach;

public class SharedPrefManagerLoginMach {
    private static final String SHARED_PREF_NAME = "EmsPmit";
    private static final String KEY_USERPHONE = "keyphone";
    private static SharedPrefManagerLoginMach mInstance;
    private static Context mCtx;
    private SharedPrefManagerLoginMach(Context context) {
        mCtx = context;
    }
    public static synchronized SharedPrefManagerLoginMach getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerLoginMach(context);
        }
        return mInstance;
    }
    public void UserMachID(UserLoginMach user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERPHONE, user.getUserPhone());
        editor.apply();
    }
    public UserLoginMach getUserMach() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserLoginMach(
                sharedPreferences.getString(KEY_USERPHONE, null)
        );
    }
    public void logoutMach() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(mCtx, SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
        // mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
