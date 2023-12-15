package stu.edu.vn.thigk.hanghoaUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stu.edu.vn.thigk.R;
import stu.edu.vn.thigk.SelectImage;
import stu.edu.vn.thigk.chonmenu;
import stu.edu.vn.thigk.dao.DBHelper;
import stu.edu.vn.thigk.dao.DBHelperlhh;
import stu.edu.vn.thigk.loaihanghoaUI.nhapLoaihanghoa;
import stu.edu.vn.thigk.model.HangHoa;
import stu.edu.vn.thigk.model.LoaiHangHoa;

public class nhaphanghoa extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST = 1;

    List<LoaiHangHoa> listlhh = new ArrayList<>();

    List<String> listTenloai = new ArrayList<>();

    EditText txt_mahang, txt_tenhang, txt_gia, txt_dungtich;
    Spinner SpinnerLoaihanghoa;
    TextView tv_path;
    Button btn_browse, btn_luu, btn_themlhh;
    ArrayAdapter<String> adapter;
    DBHelperlhh helperlhh;
    DBHelper helper;
    HangHoa chon;
    int requestcode = 114, resultcode = 115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhaphanghoa);
        addControl();
        addEvent();
        getintentData();
    }



    private void addControl() {
        txt_mahang = findViewById(R.id.edit_mahang);
        txt_tenhang = findViewById(R.id.edit_tenhang);
        SpinnerLoaihanghoa = findViewById(R.id.spinnerLoaiHangHoa);
        txt_gia = findViewById(R.id.edit_gia);
        txt_dungtich = findViewById(R.id.edit_dungtich);
        tv_path = findViewById(R.id.tvPath);
        btn_browse = findViewById(R.id.btnbrowse);
        btn_luu = findViewById(R.id.btnLuu);
        helperlhh=new DBHelperlhh(nhaphanghoa.this);
        helper= new DBHelper(nhaphanghoa.this);
       listlhh.addAll(helperlhh.getAllloaiHanghoa());
        for (LoaiHangHoa l: listlhh) {
            listTenloai.add(l.getTenloai());

        }
        chon = null;
        // add lhh
        adapter = new ArrayAdapter<>(nhaphanghoa.this, android.R.layout.simple_list_item_1, listTenloai);
        SpinnerLoaihanghoa.setAdapter(adapter);

    }
    private void addEvent() {
        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyluu();
            }
        });
        btn_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= SelectImage.openFileChooser();
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
    }

    private HangHoa setChon(HangHoa hh)
    {
        int dem=SpinnerLoaihanghoa.getCount();
        String path = tv_path.getText().toString();
        if (chon == null) {
            chon = new HangHoa();
        }
        chon.setMaHang(txt_mahang.getText().toString());
        chon.setTenHang(txt_tenhang.getText().toString());
        if(dem<=0)
        {
            chon.setLoaiHangHoa("");
        }
        else {
            String selectedLoaiHangHoa = SpinnerLoaihanghoa.getSelectedItem().toString();
            chon.setLoaiHangHoa(selectedLoaiHangHoa);
        }
        chon.setGia(Double.parseDouble(txt_gia.getText().toString()));
        chon.setDungtich(Double.parseDouble(txt_dungtich.getText().toString()));
        if(path.equals("vui lòng chọn một hình ảnh"))
            chon.setHinhanh("");
        else
            chon.setHinhanh(path);
        return  chon;
    }
    private void xulyluu() {
        if(helper.isManvExists(chon.getMaHang()))
        {
            Toast.makeText(nhaphanghoa.this, "Mã hàng đã tồn tại. Vui lòng chọn mã hàng khác.", Toast.LENGTH_SHORT).show();
            txt_mahang.setText("");
        }
        else {

            //LoaiHangHoa lhh=new LoaiHangHoa("1",autoCompleteTextViewLHH.getText().toString());
           chon=setChon(chon);
            Intent intent = new Intent();
            intent.putExtra("trahh", chon);
            setResult(resultcode, intent);
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(nhaphanghoa.this, chonmenu.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Làm bất cứ xử lý gì với đường dẫn hình ảnh được chọn ở đây.
            // Ví dụ: hiển thị hình ảnh trong ImageView hoặc lưu đường dẫn vào biến.
            tv_path.setText(selectedImageUri.toString());
        }
    }
    private void getintentData() {
        Intent intent=getIntent();
        if(intent.hasExtra("chon")){
            chon= (HangHoa) intent.getSerializableExtra("chon");
            if(chon!=null) {
                txt_mahang.setFocusable(false);
                txt_mahang.setText(chon.getMaHang());
                txt_tenhang.setText(chon.getTenHang());
                txt_gia.setText(chon.getGia() + "");
                txt_dungtich.setText(chon.getDungtich() + "");
                tv_path.setText(chon.getHinhanh());
            }
        }
    }
}