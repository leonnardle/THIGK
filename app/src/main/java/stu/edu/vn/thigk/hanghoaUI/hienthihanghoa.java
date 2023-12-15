package stu.edu.vn.thigk.hanghoaUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import stu.edu.vn.thigk.MainActivity;
import stu.edu.vn.thigk.R;
import stu.edu.vn.thigk.adapter.AdapterHanghoa;
import stu.edu.vn.thigk.chonmenu;
import stu.edu.vn.thigk.dao.DBHelper;
import stu.edu.vn.thigk.dao.DBHelperlhh;
import stu.edu.vn.thigk.model.HangHoa;
import stu.edu.vn.thigk.model.LoaiHangHoa;

public class hienthihanghoa extends AppCompatActivity {

    private static final int REQUEST_CODE_ACTIVITY2 =115 ;
    FloatingActionButton fa;
    AdapterHanghoa adapter;
    ListView listView;
    DBHelper helper;
    DBHelperlhh helplhh;
    List<HangHoa> listHanghoa=new ArrayList<>();
    HangHoa chon;
    int requestcode=113,resultcode=115;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthihanghoa);
        addControls();
        addEvent();
        helper=new DBHelper(hienthihanghoa.this);
        helplhh=new DBHelperlhh(hienthihanghoa.this);
       /* helper.QueryData(DBHelper.Drop_table);
        helper.QueryData(DBHelper.SQL_Create_Table);*/

        hienthiHanghoa();
        chon=null;
    }

    private void addEvent() {
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(hienthihanghoa.this, nhaphanghoa.class);
                startActivityForResult(intent,requestcode);*/
                Intent intent=new Intent(hienthihanghoa.this, chonmenu.class);
               startActivityForResult(intent,REQUEST_CODE_ACTIVITY2);
            }
        });

    }

    private void addControls() {
        fa=findViewById(R.id.faThem);
        listView = findViewById(R.id.lvQlhh);
        registerForContextMenu(listView);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.lvQlhh){
            getMenuInflater().inflate(R.menu.context_menu,menu);
        }
    }
    // chon nut sua va xoa
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index=info.position;
        if(item.getItemId()==R.id.btnsua)
        {
            Intent intent=new Intent(hienthihanghoa.this, nhaphanghoa.class);
            chon=adapter.getItem(index);
            intent.putExtra("chon",chon);
            startActivityForResult(intent,resultcode);
        }
        else if(item.getItemId()==R.id.btnxoa){
            chon=adapter.getItem(index);
            helper.deleteHanghoa(chon.getMaHang());
            hienthiHanghoa();
        }
        return super.onContextItemSelected(item);
    }

    private  void capnhathanghoa(Intent intent)
    {
        if (intent.hasExtra("trahh")) {
            chon= (HangHoa) intent.getSerializableExtra("trahh");
            if (helper.isManvExists(chon.getMaHang())) {
                helper.updateHanghoa(chon);
            }
            hienthiHanghoa();
        }
    }
    private void xoahanghoa(Intent intent)
    {
        if (intent.hasExtra("trahh") ) {
            chon = (HangHoa) intent.getSerializableExtra("trahh");
            helper.insertHanghoa(chon);
            hienthiHanghoa();
        }
    }
    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode==this.resultcode) {
                capnhathanghoa(data);
            }
            else if (this.resultcode==requestCode){
               xoahanghoa(data);
            }

        }

    private void hienthiHanghoa() {
        listHanghoa=helper.getAllHanghoa();
        if(listHanghoa.size()>=0){
            adapter=new AdapterHanghoa(hienthihanghoa.this,R.layout.item_hanghoa,listHanghoa,helper);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(hienthihanghoa.this, MainActivity.class);
        startActivity(intent);
    }

}