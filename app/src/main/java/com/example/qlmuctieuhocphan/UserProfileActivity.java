package com.example.qlmuctieuhocphan;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    Database db = new Database(this);

    private EditText user_fullname;
    private EditText user_username;
    private EditText user_password;
    private Button btnSaveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user_fullname = findViewById(R.id.user_fullname);
        user_username = findViewById(R.id.user_username);
        user_password = findViewById(R.id.user_password);
        btnSaveUser = findViewById(R.id.btnSaveUser);

        getUserInfo();

        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInfo();
            }
        });
    }

    public void getUserInfo() {
        Cursor cursor = db.getData("users");
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                user_fullname.setText(cursor.getString(1));
                user_username.setText(cursor.getString(2));
                user_password.setText(cursor.getString(3));
            }
        }
    }

    public boolean validateUserInfo(String fullname, String username, String password) {
        if (fullname.length() < 3 || fullname.length() > 50) {
            Toast.makeText(getApplicationContext(), "Họ tên phải từ 3 đến 50 ký tự!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (username.matches("^[a-zA-Z0-9._]{6,32}$") == false) {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập phải từ 6 đến 32 ký tự, không chứa các ký tự đặc biệt!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6 || password.length() > 32) {
            Toast.makeText(getApplicationContext(), "Mật khẩu phải từ 6 đến 32 ký tự!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void updateUserInfo() {
        String id = "1";
        String fullname = user_fullname.getText().toString().trim();
        String username = user_username.getText().toString().trim();
        String password = user_password.getText().toString().trim();

        if (!fullname.isEmpty() || !username.isEmpty() || !password.isEmpty()) {
            if (validateUserInfo(fullname, username, password) == true) {
                boolean user = db.updateUser(id, fullname, username, password);
                if (user == true) {
                    Toast.makeText(getApplicationContext(), "Cập nhật thông tin đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cập nhật thông tin đăng nhập Thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin người dùng!", Toast.LENGTH_SHORT).show();
        }
    }
}