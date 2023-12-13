package stu.edu.vn.thigk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import stu.edu.vn.thigk.dao.DBHelper;
import stu.edu.vn.thigk.model.HangHoa;
import stu.edu.vn.thigk.model.LoaiHangHoa;

public class nhaphanghoa extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    List<LoaiHangHoa> listloaihanghoa = new ArrayList<>();
    List<String> listTenloai = new ArrayList<>();

    EditText txt_mahang, txt_tenhang, txt_gia, txt_dungtich;
    Spinner SpinnerLoaihanghoa;
    TextView tv_path;
    Button btn_browse, btn_luu, btn_themlhh;
    ArrayAdapter<String> adapter;
    HangHoa chon;
    int requestcode = 113, resultcode = 115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhaphanghoa);
        addControl();
        addEvent();
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
        btn_themlhh = findViewById(R.id.btnThemlhh);
        LoaiHangHoa l1 = new LoaiHangHoa("3", "3");
        LoaiHangHoa l2 = new LoaiHangHoa("2", "2");


        listloaihanghoa.add(l1);
        listloaihanghoa.add(l2);
        for (LoaiHangHoa loai : listloaihanghoa) {
            listTenloai.add(loai.getTenloai());
        }
        adapter = new ArrayAdapter<>(nhaphanghoa.this, android.R.layout.simple_list_item_1, listTenloai);

        chon = null;

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
                openFileChooser();
            }
        });
    }

    private void xulyluu() {
        String selectedLoaiHangHoa = SpinnerLoaihanghoa.getSelectedItem().toString();
        String path = tv_path.getText().toString();
        //LoaiHangHoa lhh=new LoaiHangHoa("1",autoCompleteTextViewLHH.getText().toString());
        if (chon == null) {
            chon = new HangHoa();
        }
        chon.setMaHang(txt_mahang.getText().toString());
        chon.setTenHang(txt_tenhang.getText().toString());
        chon.setLoaiHangHoa(selectedLoaiHangHoa);
        chon.setGia(Double.parseDouble(txt_gia.getText().toString()));
        chon.setDungtich(Double.parseDouble(txt_dungtich.getText().toString()));
        chon.setHinhanh(path);
        Intent intent = getIntent();
        intent.putExtra("tra", chon);
        setResult(requestcode, intent);
        finish();

    }
    private void openFileChooser() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
        }

        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            // Làm bất cứ xử lý gì với đường dẫn hình ảnh được chọn ở đây.
            // Ví dụ: hiển thị hình ảnh trong ImageView hoặc lưu đường dẫn vào biến.
            tv_path.setText(selectedImageUri.toString());
        }
    }




}