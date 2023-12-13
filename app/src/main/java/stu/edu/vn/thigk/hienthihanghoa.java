package stu.edu.vn.thigk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import stu.edu.vn.thigk.adapter.AdapterHanghoa;
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
                Intent intent=new Intent(hienthihanghoa.this, nhaphanghoa.class);
                startActivityForResult(intent,requestcode);
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

            if(data.hasExtra("tra")){
                HangHoa s= (HangHoa) data.getSerializableExtra("tra");
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
}