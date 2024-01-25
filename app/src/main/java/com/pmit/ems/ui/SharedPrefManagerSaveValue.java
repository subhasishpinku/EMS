package com.pmit.ems.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManagerSaveValue {
    private static final String SHARED_PREF_NAME = "EmsPmit";
    private static final String KEY_SAVE_VALUE = "keyphone";
    private static SharedPrefManagerSaveValue mInstance;
    private static Context mCtx;
    private SharedPrefManagerSaveValue(Context context) {
        mCtx = context;
    }
    public static synchronized SharedPrefManagerSaveValue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerSaveValue(context);
        }
        return mInstance;
    }
    public void UserPhones(UserPhone user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SAVE_VALUE, user.getPhonenumber());
        editor.apply();
    }
    public UserPhone getPhone() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserPhone(
                sharedPreferences.getString(KEY_SAVE_VALUE, null)
        );
    }
}
