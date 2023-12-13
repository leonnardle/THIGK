package stu.edu.vn.thigk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import stu.edu.vn.thigk.hanghoaUI.hienthihanghoa;
import stu.edu.vn.thigk.hanghoaUI.nhaphanghoa;
import stu.edu.vn.thigk.model.HangHoa;

public class chonmenu extends AppCompatActivity {

    private static final int REQUEST_CODE_ACTIVITY3 = 113;
    Button btnchonlhh, btnchonhanghoa;
    int requestcode = 113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chonmenu);
        addconTrol();
        addEvent();
    }

    private void addEvent() {
        btnchonhanghoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chonmenu.this, nhaphanghoa.class);

                startActivityForResult(intent, requestcode);
            }
        });
    }

    private void addconTrol() {
        btnchonhanghoa = findViewById(R.id.btn_open_hanghoa);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra nếu kết quả trả về từ Activity 3
        if (requestCode == REQUEST_CODE_ACTIVITY3) {
            // Xử lý dữ liệu từ Activity 3 (nếu có)
                HangHoa hh = (HangHoa) data.getSerializableExtra("tra3");
                // Xử lý dữ liệu nhận được
                // Trả dữ liệu về cho Activity 1 (MainActivity)
                Intent resultIntent = new Intent();
                resultIntent.putExtra("tra3", hh);
                setResult(113, resultIntent);
                finish();

        }
    }
}