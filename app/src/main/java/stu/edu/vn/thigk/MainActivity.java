package stu.edu.vn.thigk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import stu.edu.vn.thigk.hanghoaUI.hienthihanghoa;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 3;
    EditText username, password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvent();
    }

    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    private void addControls() {
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPass);
        btnLogin = findViewById(R.id.btnLogin);
    }

    public void Login() {
        String name = username.getText().toString();
        String pass = password.getText().toString();
        if (name.equals("Admin") && pass.equals("123456")) {
            Intent intent = new Intent(MainActivity.this, hienthihanghoa.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "sai tài khoản hoặc mật khẩu,vui lòng kiểm tra lại ", Toast.LENGTH_LONG).show();
        }
    }
}
