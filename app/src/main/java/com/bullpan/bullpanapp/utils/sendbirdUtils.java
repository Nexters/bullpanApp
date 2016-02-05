package com.bullpan.bullpanapp.utils;

import android.content.Context;
import android.provider.Settings;

import java.security.MessageDigest;

/**
 * Created by daehyun on 16. 2. 5..
 */
public class SendbirdUtils {
    public static String appId = "4A91A28A-898A-4730-86CC-F4B0245C15A4" ;
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
}
