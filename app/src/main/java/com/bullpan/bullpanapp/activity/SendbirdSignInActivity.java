package com.bullpan.bullpanapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bullpan.bullpanapp.Constants;
import com.bullpan.bullpanapp.R;
import com.bullpan.bullpanapp.utils.SendbirdUtils;
import com.sendbird.android.SendBird;

public class SendbirdSignInActivity extends AppCompatActivity {
    private EditText mUserNameInput;
    private RelativeLayout mProfileSection;
    private ImageView mProfileImage;
    private Button mStartButton;
    private TextView mErrorLabel;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendbird_sign_in);
        mUserNameInput = (EditText) findViewById(R.id.user_name_input);
        mProfileSection = (RelativeLayout) findViewById(R.id.profile_section);
        mProfileImage = (ImageView) findViewById(R.id.img_profile);
        mStartButton = (Button) findViewById(R.id.btn_start);
        mErrorLabel = (TextView) findViewById(R.id.error_label);

        mSharedPreferences = getApplicationContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        editor = mSharedPreferences.edit();

        mUserNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count < 3) {
                    mErrorLabel.setText("닉네임은 3자리 이상 15자 이하로 지정해주세요.");
                    mErrorLabel.setVisibility(View.VISIBLE);
                    mStartButton.setTextColor(getResources().getColor(R.color.colorGray));
                } else {
                    mErrorLabel.setVisibility(View.INVISIBLE);
                    mStartButton.setTextColor(getResources().getColor(R.color.colorRedAccent));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mStartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = mUserNameInput.getText().toString();
                if( checkUserAuth(username) ) {
                    editor.putString(Constants.KEY_USER_NAME, username);
                    editor.putBoolean(Constants.IS_USER_EXISTS, true);
                    editor.commit();
                    startActivity(new Intent(SendbirdSignInActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(SendbirdSignInActivity.this,"이미 존재하는 닉네임 입니다.",Toast.LENGTH_SHORT).show();
                }

            }


        });
    }
    private boolean checkUserAuth(String username) {
        String  appKey = SendbirdUtils.appKey;
        //중복체크 어케하징;

       return true;
    }
}
