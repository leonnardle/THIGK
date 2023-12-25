package stu.edu.vn.thigk.hanghoaUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import stu.edu.vn.thigk.MainActivity;
import stu.edu.vn.thigk.R;
import stu.edu.vn.thigk.about;
import stu.edu.vn.thigk.adapter.AdapterHanghoa;
import stu.edu.vn.thigk.dao.DBHelper;
import stu.edu.vn.thigk.dao.DBHelperlhh;
import stu.edu.vn.thigk.loaihanghoaUI.hienthiloaihanghoa;
import stu.edu.vn.thigk.model.HangHoa;

public class hienthihanghoa extends AppCompatActivity {

    private static final int REQUEST_CODE_ACTIVITY2 =115 ;
    FloatingActionButton fa;
    AdapterHanghoa adapter;
    ListView listView;
    DBHelper helper;
    DBHelperlhh helplhh;
    List<HangHoa> listHanghoa=new ArrayList<>();
    HangHoa chon;
    int requestUpdate=114,resultcode=115;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthihanghoa);
        addControls();
        addEvent();
        helper=new DBHelper(hienthihanghoa.this);
        helplhh=new DBHelperlhh(hienthihanghoa.this);
      /*  helper.QueryData(DBHelper.Drop_table);
        helper.QueryData(DBHelper.SQL_Create_Table);*/
        hienthiHanghoa();
        chon=null;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId() == R.id.about)
        {
            Intent aboutIntent = new Intent(hienthihanghoa.this, about.class);
            startActivity(aboutIntent);
            return true;
        }
        else if(item.getItemId() == R.id.loaihanghoa)
        {
            Intent aboutIntent = new Intent(hienthihanghoa.this, hienthiloaihanghoa.class);
            startActivity(aboutIntent);
            return true;
        }
        else
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void addEvent() {
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(hienthihanghoa.this, nhaphanghoa.class);
                startActivityForResult(intent,requestcode);*/
                Intent intent=new Intent(hienthihanghoa.this, nhaphanghoa.class);
               startActivityForResult(intent,REQUEST_CODE_ACTIVITY2);
            }
        });

    }

    private void addControls() {
        fa=findViewById(R.id.faThem);
        listView = findViewById(R.id.lvQlhh);
        registerForContextMenu(listView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            startActivityForResult(intent,REQUEST_CODE_ACTIVITY2);
        }
        else if(item.getItemId()==R.id.btnxoa){
           xulyxoa(index);
        }
        return super.onContextItemSelected(item);
    }

    private  void capnhathanghoa(Intent intent)
    {
        if (intent.hasExtra("trahh")) {
            if (helper.isManvExists(chon.getMaHang())) {
                helper.updateHanghoa(chon);
                hienthiHanghoa();
            }
        }
    }
    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==this.resultcode&&data!=null) {
                chon=(HangHoa) data.getSerializableExtra("trahh");
                if(helper.isManvExists(chon.getMaHang())&&chon!=null)
                {
                    capnhathanghoa(data);
                }
                else {
                    helper.insertHanghoa(chon);
                    hienthiHanghoa();
                }

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
    private void xulyxoa(int index) {
        chon = adapter.getItem(index);
        AlertDialog.Builder dialog = new AlertDialog.Builder(hienthihanghoa.this);
        dialog.setTitle("Xác nhận xóa");
        dialog.setMessage("Bạn có muốn xóa không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                helper.deleteHanghoa(chon.getMaHang());
                hienthiHanghoa();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
}