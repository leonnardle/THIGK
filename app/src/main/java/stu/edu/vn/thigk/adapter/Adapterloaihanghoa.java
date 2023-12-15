package stu.edu.vn.thigk.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import stu.edu.vn.thigk.R;
import stu.edu.vn.thigk.dao.DBHelper;
import stu.edu.vn.thigk.dao.DBHelperlhh;
import stu.edu.vn.thigk.model.HangHoa;
import stu.edu.vn.thigk.model.LoaiHangHoa;

public class Adapterloaihanghoa extends ArrayAdapter<LoaiHangHoa> {
    Activity context;
    int res;
    List<LoaiHangHoa> obj;

    DBHelperlhh helper;

    public Adapterloaihanghoa(Activity context, int res, List<LoaiHangHoa> list, DBHelperlhh helper) {
        super(context, res, list);
        this.context = context;
        this.res = res;
        this.obj = list;
        this.helper = helper;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.res, null);

        TextView maloai = item.findViewById(R.id.tv_maloai);
        TextView tenloai = item.findViewById(R.id.tv_tenloai);

        final LoaiHangHoa hh = obj.get(position);
        maloai.setText(hh.getMaloai());
        tenloai.setText(hh.getTenloai());
        return item;
    }
}
