package com.example.sce.screen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import com.example.sce.R;
import com.example.sce.common.CommonConstant;
import com.example.sce.common.CommonConstant;
import com.example.sce.db.user.UserDao;
import com.example.sce.helper.PreferenceManager;
import com.example.sce.model.User;

public class OTPVerification extends AppCompatActivity {

    private EditText etOtp;
    private Button btnVerifyOtp;
    private User user;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        Bundle extras = getIntent().getExtras();
        otp = extras.getString("OTP");

        etOtp = findViewById(R.id.etOtp);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);

        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });
    }


    private void verifyOtp() {
        String enteredOtp = etOtp.getText().toString().trim();

        if (TextUtils.isEmpty(enteredOtp)) {
            etOtp.setError("OTP is required");
            etOtp.requestFocus();
            return;
        }

        if (enteredOtp.equals(otp)) {
            Toast.makeText(OTPVerification.this, "OTP Verified", Toast.LENGTH_SHORT).show();
            UserDao userDao = new UserDao(getApplicationContext());
            long userID = userDao.register(user);

            PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
            preferenceManager.saveUserId(String.valueOf(userID));
            preferenceManager.saveUserType(CommonConstant.SIGNING_USER);

            startActivity(new Intent(OTPVerification.this, UserNavigation.class));
        } else {
            etOtp.setText("");
            Toast.makeText(OTPVerification.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
    }
}
