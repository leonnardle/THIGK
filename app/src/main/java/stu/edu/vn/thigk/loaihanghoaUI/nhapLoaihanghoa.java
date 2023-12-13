package stu.edu.vn.thigk.loaihanghoaUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import stu.edu.vn.thigk.R;
import stu.edu.vn.thigk.model.LoaiHangHoa;

public class nhapLoaihanghoa extends AppCompatActivity {

    public static LoaiHangHoa loaiHangHoa;
    EditText maloai,tenloai;
    Button btnluu;

    LoaiHangHoa lhh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_loaihanghoa);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               xulyluu();
            }
        });
    }

    private void addControl() {
        lhh=null;
        maloai=findViewById(R.id.edit_maloai);
        tenloai=findViewById(R.id.edit_tenloai);
        btnluu=findViewById(R.id.btnLuuLoaihh);
    }
    private  void xulyluu(){
        String ma=maloai.getText().toString();
        String ten=tenloai.getText().toString();
        if(lhh==null){
            lhh=new LoaiHangHoa();
        }
        lhh.setMaloai(ma);
        lhh.setTenloai(ten);


        finish();
    }
}