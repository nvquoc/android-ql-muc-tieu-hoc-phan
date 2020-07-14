package com.example.qlmuctieuhocphan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Database db = new Database(this);

    private Button btnLogin;
    private EditText usernameLogin;
    private EditText passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameLogin = findViewById(R.id.userFullName);
        passwordLogin = findViewById(R.id.passwordLogin);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameLogin.getText().toString();
                String password = passwordLogin.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    boolean user = db.checkLogin(username, password);

                    if (user == true) {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }
                        }, 1000);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sai tên tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}