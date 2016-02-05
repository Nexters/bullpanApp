package com.bullpan.bullpanapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bullpan.bullpanapp.Constants;
import com.bullpan.bullpanapp.R;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private boolean isFirst;
    private boolean isUserExists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSharedPreferences = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        isFirst = mSharedPreferences.getBoolean(Constants.IS_FIRST_INSTALL, true);
        isUserExists = mSharedPreferences.getBoolean(Constants.IS_USER_EXISTS, false);

//        Log.d(TAG, getId());
        Handler handler = new Handler();
/*** not using sendbird user model***/
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isFirst) {
//                    //회원정보가 없으면 StartActvitiy로 이동
//                    gotoStartActivity();
//                } else {
//                    //회원정보가 있으면 로그인 작업
//
//
//                    //작업완료후 메인액티비티로 이동
//                    goToMainActivity();
//
//                    //로그인 실패시 로그인 액티비티로 이동하기
////                    goToLoginActivity();
//                }
//            }
//        }, 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isUserExists) {
                    //회원정보가 없으면 StartAcitivity로이동
                    gotoSendbirdStartActivity();
                } else {
                    //회원정보가 있으면 Main으로 바로이동
                    goToMainActivity();
                }
            }
        },2000);
    }

    private void goToLoginActivity() {
        Intent intent =new Intent(SplashActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void gotoStartActivity() {
        Intent intent =new Intent(SplashActivity.this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void gotoSendbirdStartActivity() {
        Intent intent =new Intent(SplashActivity.this, SendbirdStartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
