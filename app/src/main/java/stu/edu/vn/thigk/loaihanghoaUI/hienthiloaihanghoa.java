package stu.edu.vn.thigk.loaihanghoaUI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import stu.edu.vn.thigk.R;
import stu.edu.vn.thigk.adapter.AdapterHanghoa;
import stu.edu.vn.thigk.adapter.Adapterloaihanghoa;
import stu.edu.vn.thigk.chonmenu;
import stu.edu.vn.thigk.dao.DBHelper;
import stu.edu.vn.thigk.dao.DBHelperlhh;
import stu.edu.vn.thigk.hanghoaUI.hienthihanghoa;
import stu.edu.vn.thigk.model.HangHoa;
import stu.edu.vn.thigk.model.LoaiHangHoa;

public class hienthiloaihanghoa extends AppCompatActivity {

    FloatingActionButton fa;
    Adapterloaihanghoa adapter;
    ListView listView;
    DBHelperlhh helper;
    List<LoaiHangHoa> listloaiHanghoa=new ArrayList<>();
    LoaiHangHoa chon;
    int requestcode=113,resultcode=115;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthiloaihanghoa);
        addControl();
        addEvent();
        helper=new DBHelperlhh(hienthiloaihanghoa.this);
/*        helper.QueryData(DBHelper.Drop_table);
        helper.QueryData(DBHelper.SQL_Create_Table);*/

        hienthiloaiHanghoa();
        chon=null;
    }

    private void hienthiloaiHanghoa() {
        listloaiHanghoa=helper.getAllloaiHanghoa();
        if(listloaiHanghoa.size()>0){
            adapter=new Adapterloaihanghoa(hienthiloaihanghoa.this,R.layout.item_loaihanghoa,listloaiHanghoa,helper);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    private void addEvent() {
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(hienthiloaihanghoa.this, chonmenu.class);
                startActivityForResult(intent,requestcode);
            }
        });
    }

    private void addControl() {
        fa=findViewById(R.id.faThemloai);
        listView = findViewById(R.id.lvQlloaihh);
        registerForContextMenu(listView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.hasExtra("tralhh")){
           /* if(data.hasExtra("tra")){
                HangHoa s= (HangHoa) data.getSerializableExtra("tra");
                helper.insertHanghoa(s);
                hienthiHanghoa();
            }*/
            if(data.hasExtra("tralhh")){
                LoaiHangHoa s= (LoaiHangHoa) data.getSerializableExtra("tralhh");
                helper.insertloaiHanghoa(s);
                hienthiloaiHanghoa();
            }
        }
    }
}