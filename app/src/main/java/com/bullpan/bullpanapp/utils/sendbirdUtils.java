package com.bullpan.bullpanapp.utils;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.view.inputmethod.InputMethodManager;

import com.bullpan.bullpanapp.Constants;


import java.security.MessageDigest;
import java.text.DecimalFormat;

/**
 * Created by daehyun on 16. 2. 5..
 */
public class SendbirdUtils {

    public static String appKey = "B40E07CB-B07E-4E77-9AED-D8229C769EF7" ;
    public static String apiToken ="576cc43932b392d4083ecbde4a7d00675c842a05";
    public static String urlBase = "23999.";
    public static String getUsername(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String username = pref.getString(Constants.KEY_USER_NAME, null);
        return username;
    }

    public static String generateDeviceUUID(Context context) {
        String serial = android.os.Build.SERIAL;
        String androidID = Settings.Secure.ANDROID_ID;
        String deviceUUID = serial + androidID;

        MessageDigest digest;
        byte[] result;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            result = digest.digest(deviceUUID.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02X", b));
        }

        return sb.toString();
    }

    public static Bundle makeSendBirdArgs(String appKey, String uuid, String nickname, String channelUrl) {
        Bundle args = new Bundle();
        args.putString("appKey", appKey);
        args.putString("uuid", uuid);
        args.putString("nickname", nickname);
        args.putString("channelUrl", channelUrl);
        return args;
    }
    public static Bundle makeSendBirdArgs(String appKey, String uuid, String nickname) {
        Bundle args = new Bundle();
        args.putString("appKey", appKey);
        args.putString("uuid", uuid);
        args.putString("nickname", nickname);
        return args;
    }

    public static String readableFileSize(long size) {
        if (size <= 0) return "0KB";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
    public static void hideKeyboard(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

}
