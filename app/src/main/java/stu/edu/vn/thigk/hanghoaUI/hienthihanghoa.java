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

import stu.edu.vn.thigk.R;
import stu.edu.vn.thigk.adapter.AdapterHanghoa;
import stu.edu.vn.thigk.chonmenu;
import stu.edu.vn.thigk.dao.DBHelper;
import stu.edu.vn.thigk.model.HangHoa;

public class hienthihanghoa extends AppCompatActivity {

    FloatingActionButton fa;
    AdapterHanghoa adapter;
    ListView listView;
    DBHelper helper;
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
        helper.QueryData(DBHelper.Drop_table);
        helper.QueryData(DBHelper.SQL_Create_Table);

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
               startActivityForResult(intent,requestcode);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==this.requestcode&&data!=null){

           /* if(data.hasExtra("tra")){
                HangHoa s= (HangHoa) data.getSerializableExtra("tra");
                helper.insertHanghoa(s);
                hienthiHanghoa();
            }*/
            if(data.hasExtra("tra3")){
                HangHoa s= (HangHoa) data.getSerializableExtra("tra3");
                helper.insertHanghoa(s);
                hienthiHanghoa();
            }
        }
    }

    private void hienthiHanghoa() {
        listHanghoa=helper.getAllHanghoa();
        if(listHanghoa.size()>0){
            adapter=new AdapterHanghoa(hienthihanghoa.this,R.layout.item_hanghoa,listHanghoa,helper);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    // chon nut sua va xoa
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index=info.position;
        if(item.getItemId()==R.id.btnsua)
        {
            Intent intent=new Intent(hienthihanghoa.this,suahanghoa.class);
            chon=adapter.getItem(index);
            intent.putExtra("chon",chon);
            startActivityForResult(intent,requestcode);
        }
       /* else if(item.getItemId()==R.id.btnxoa){
            chon=adapter.getItem(index);
            helper.deleteSach(chon.getMa()+"");
            hienthisach();
        }*/
        return super.onContextItemSelected(item);
    }

}