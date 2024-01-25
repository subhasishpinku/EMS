package com.pmit.ems.ui;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
public class SharedPrefManagerLogin {
    private static final String SHARED_PREF_NAME = "EmsPmit";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_TOKEN = "keytoken";
    private static final String KEY_USERID = "keyuserid";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_DEPTNAME = "keydeptname";
    private static final String KEY_USERCODE = "keyusercode";
    private static final String KEY_USERIMAGE = "keyimage";
    private static final String KEY_USERPHONE = "keyphone";
    private static SharedPrefManagerLogin mInstance;
    private static Context mCtx;
    private SharedPrefManagerLogin(Context context) {
        mCtx = context;
    }
    public static synchronized SharedPrefManagerLogin getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerLogin(context);
        }
        return mInstance;
    }
    public void CustomerID(UserLoginData user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.putString(KEY_USERID, user.getUser_id());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_DEPTNAME, user.getDept_name());
        editor.putString(KEY_USERCODE, user.getUser_code());
        editor.putString(KEY_USERIMAGE, user.getUser_image());
        editor.putString(KEY_USERPHONE, user.getPhoneNumber());
        editor.apply();
    }
    public UserLoginData getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserLoginData(
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_TOKEN, null),
                sharedPreferences.getString(KEY_USERID, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_DEPTNAME, null),
                sharedPreferences.getString(KEY_USERCODE, null),
                sharedPreferences.getString(KEY_USERIMAGE, null),
                sharedPreferences.getString(KEY_USERPHONE, null)
        );
    }

    public void logout() {
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
