package stu.edu.vn.thigk.loaihanghoaUI;

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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import stu.edu.vn.thigk.R;
import stu.edu.vn.thigk.about;
import stu.edu.vn.thigk.adapter.Adapterloaihanghoa;
import stu.edu.vn.thigk.dao.DBHelper;
import stu.edu.vn.thigk.dao.DBHelperlhh;
import stu.edu.vn.thigk.hanghoaUI.hienthihanghoa;
import stu.edu.vn.thigk.model.LoaiHangHoa;

public class hienthiloaihanghoa extends AppCompatActivity {

    FloatingActionButton fa;
    Adapterloaihanghoa adapter;
    ListView listView;
    DBHelperlhh helper;
    DBHelper helper2;

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
        helper2=new DBHelper(hienthiloaihanghoa.this);
/*        helper.QueryData(DBHelper.Drop_table);
        helper.QueryData(DBHelper.SQL_Create_Table);*/
        hienthiloaiHanghoa();
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
            Intent aboutIntent = new Intent(hienthiloaihanghoa.this, about.class);
            startActivity(aboutIntent);
            return true;
        }
        else
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void hienthiloaiHanghoa() {
        listloaiHanghoa=helper.getAllloaiHanghoa();
        if(listloaiHanghoa.size()>=0){
            adapter=new Adapterloaihanghoa(hienthiloaihanghoa.this,R.layout.item_loaihanghoa,listloaiHanghoa,helper);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    private void addEvent() {
        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(hienthiloaihanghoa.this, nhapLoaihanghoa.class);
                startActivityForResult(intent,requestcode);
            }
        });
    }


    private void addControl() {
        fa=findViewById(R.id.faThemloai);
        listView = findViewById(R.id.lvQlloaihh);
        registerForContextMenu(listView);
        Toolbar toolbar = findViewById(R.id.toolbarLoaihanghoa);
        setSupportActionBar(toolbar);
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.lvQlloaihh){
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
            Intent intent=new Intent(hienthiloaihanghoa.this, nhapLoaihanghoa.class);
            chon=adapter.getItem(index);
            intent.putExtra("chon",chon);
            startActivityForResult(intent,requestcode);
        }
        else if(item.getItemId()==R.id.btnxoa){
            xulyxoa(index);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==this.requestcode) {
            if (data.hasExtra("tralhh")) {
                LoaiHangHoa s = (LoaiHangHoa) data.getSerializableExtra("tralhh");
                if (helper.isMaloaiExists(s.getMaloai())) {
                    helper.updateHanghoa(s);
                    hienthiloaiHanghoa();
                } else {
                    helper.insertloaiHanghoa(s);
                    hienthiloaiHanghoa();
                }
            } else {
                hienthiloaiHanghoa();
            }
        }
        else {
            hienthiloaiHanghoa();
        }
    }
    private void xulyxoa(int index) {
        chon = adapter.getItem(index);
        AlertDialog.Builder dialog = new AlertDialog.Builder(hienthiloaihanghoa.this);
        dialog.setTitle("Xác nhận xóa");
        dialog.setMessage("Bạn có muốn xóa không?");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(helper2.isPhanloaiEqual(chon.getTenloai())) {
                    Toast.makeText(hienthiloaihanghoa.this,"ban khong the xoa",Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();
                }
                else{
                    helper.deleteHanghoa(chon.getMaloai());
                    hienthiloaiHanghoa();
                }

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(hienthiloaihanghoa.this, hienthihanghoa.class);
        startActivity(intent);
    }
}