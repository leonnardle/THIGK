package stu.edu.vn.thigk.loaihanghoaUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import stu.edu.vn.thigk.R;
import stu.edu.vn.thigk.chonmenu;
import stu.edu.vn.thigk.hanghoaUI.nhaphanghoa;
import stu.edu.vn.thigk.model.HangHoa;
import stu.edu.vn.thigk.model.LoaiHangHoa;

public class nhapLoaihanghoa extends AppCompatActivity {

    public static LoaiHangHoa loaiHangHoa;
    EditText maloai,tenloai;
    Button btnluu;

    int requestcode=113;
    LoaiHangHoa lhh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_loaihanghoa);
        addControl();
        getintentData();
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
        Intent intent=new Intent();
        intent.putExtra("tralhh",lhh);
        setResult(requestcode,intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(nhapLoaihanghoa.this, hienthiloaihanghoa.class);
        startActivity(intent);
    }
    private void getintentData() {
        Intent intent=getIntent();
        if(intent.hasExtra("chon")){
            lhh= (LoaiHangHoa) intent.getSerializableExtra("chon");
            if(lhh!=null) {
                maloai.setFocusable(false);
                maloai.setText(lhh.getMaloai());
                tenloai.setText(lhh.getTenloai());

            }
        }
    }
}