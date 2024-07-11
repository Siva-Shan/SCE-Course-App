package com.example.sce.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sce.R;
import com.example.sce.common.CommonConstant;
import com.example.sce.db.user.UserDao;
import com.example.sce.helper.PreferenceManager;

public class Login extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin, btnGuestLogin;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        signup = findViewById(R.id.signupbtn);
        btnLogin = findViewById(R.id.btnLogin);
        btnGuestLogin = findViewById(R.id.btnGuestLogin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

        btnGuestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, UserNavigation.class));
                PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
                preferenceManager.saveUserType(CommonConstant.GUEST_USER);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                UserDao userDao = new UserDao(getApplicationContext());
                PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());

                //TODO
               // etUsername.setText("nirojan.yoga@gmail.com");
              //  etPassword.setText("123456");

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {

                    if (username.equals(CommonConstant.ADMIN_EMAIL) && password.equals(CommonConstant.ADMIN_PASSWORD)) {
                        preferenceManager.saveUserType(CommonConstant.ADMIN_USER);
                        Intent intent1=(new Intent(Login.this, AdminNavigation.class));
                        intent1.putExtra("username",username);
                        Login.this.startActivity(intent1);
                        //finish();
                    }
                    else if (userDao.loginUser(username, password)) {
                        preferenceManager.saveUserId(String.valueOf(userDao.getUserID(username)));
                        preferenceManager.saveUserType(CommonConstant.SIGNING_USER);

                        startActivity(new Intent(Login.this, UserNavigation.class));
                      //  finish();
                    }
                    else {
                        Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
