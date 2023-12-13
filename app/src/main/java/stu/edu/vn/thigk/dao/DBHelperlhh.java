package stu.edu.vn.thigk.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import stu.edu.vn.thigk.model.HangHoa;
import stu.edu.vn.thigk.model.LoaiHangHoa;

public class DBHelperlhh extends SQLiteOpenHelper {
    private static final String DB_Name = "dbloaihanghoa.sqlite";
    private static final int DB_Version = 1;

    public static class TABLE implements BaseColumns {
        public static final String TABLE_NAME = "loaiHanghoa";
        public static final String COLUMN_M = "MALOAI";
        public static final String COLUMN_Ten = "TENLOAI";

    }


    public static final String SQL_Create_Table = "CREATE TABLE IF NOT EXISTS " + DBHelper.TABLE.TABLE_NAME + " (" +
            DBHelper.TABLE.COLUMN_M + " VARCHAR(50), " +
            DBHelper.TABLE.COLUMN_Ten + " VARCHAR(50)) " ;



    public  static  final String Drop_table = "DROP TABLE IF EXISTS " + DBHelper.TABLE.TABLE_NAME;


    public void QueryData(String sql)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public DBHelperlhh(Context context) {
        super(context, DB_Name, null, DB_Version);
    }


    public void insertloaiHanghoa(LoaiHangHoa hh) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.TABLE.COLUMN_M, hh.getMaloai());
        values.put(DBHelper.TABLE.COLUMN_Ten, hh.getTenloai());

        long newRow = db.insert(DBHelper.TABLE.TABLE_NAME, null, values);
    }

    public List<LoaiHangHoa> getAllHanghoa() {
        List<LoaiHangHoa> listloaiHangHoa = new ArrayList<>();
        String[] projection = {
                DBHelper.TABLE.COLUMN_M,
                DBHelper.TABLE.COLUMN_Ten
        };
        Cursor cursor = getReadableDatabase().query(
                DBHelper.TABLE.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            LoaiHangHoa hangHoa = new LoaiHangHoa();
            hangHoa.setMaloai(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TABLE.COLUMN_M)));
            hangHoa.setTenloai(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TABLE.COLUMN_Ten)));

            listloaiHangHoa.add(hangHoa);
        }

        cursor.close();
        return listloaiHangHoa;


    }





    public Cursor getData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public boolean a;


    Context context;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldver ,int newver) {

    }

    public void deleteHanghoa(String Ma)
    {
        String selection = DBHelper.TABLE.COLUMN_M + " = ?";
        String[] selectionArgs ={Ma};
        int deleteRows =getWritableDatabase().delete(DBHelper.TABLE.TABLE_NAME, selection, selectionArgs);

    }

    public void updateHanghoa(LoaiHangHoa hh) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.TABLE.COLUMN_Ten, hh.getTenloai());

        String selection = DBHelper.TABLE.COLUMN_M + " = ?";
        String[] selectionArgs = {hh.getMaloai()};

        int updatedRows = db.update(DBHelper.TABLE.TABLE_NAME, values, selection, selectionArgs);

    }

}
