package com.bullpan.bullpanapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private boolean isFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSharedPreferences = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        isFirst = mSharedPreferences.getBoolean(Constants.IS_FIRST_INSTALL, true);

//        Log.d(TAG, getId());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirst) {
                    //회원정보가 없으면 StartActvitiy로 이동
                    gotoStartActivity();
                } else {
                    //회원정보가 있으면 로그인 작업


                    //작업완료후 메인액티비티로 이동
                    goToMainActivity();

                    //로그인 실패시 로그인 액티비티로 이동하기
                    goToLoginActivity();
                }
            }
        }, 2000);
    }

    private void goToLoginActivity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }

    private void gotoStartActivity() {
        startActivity(new Intent(SplashActivity.this, StartActivity.class));
    }

    private void goToMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }


}
