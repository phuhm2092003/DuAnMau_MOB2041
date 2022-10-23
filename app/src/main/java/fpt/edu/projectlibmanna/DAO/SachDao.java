package fpt.edu.projectlibmanna.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpt.edu.projectlibmanna.database.LibManaDB;
import fpt.edu.projectlibmanna.model.Sach;

public class SachDao {
    LibManaDB libManaDB;

    public SachDao(Context context) {
        this.libManaDB = new LibManaDB(context);
    }

    @SuppressLint("Range")
    public ArrayList<Sach> get(String sql, String... selectionAgrs) {
        /*
         * LẤY DỮ LIỆU THEO CÂU TRUY VẤN (sql) HOẶC THAM SỐ LỰA CHỌN (selectionAgrs)
         * */
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionAgrs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(cursor.getColumnIndex("maSach")));
                sach.setTenSach(cursor.getString(cursor.getColumnIndex("tenSach")));
                sach.setGiaThue(cursor.getInt(cursor.getColumnIndex("giaThue")));
                sach.setMaLoai(cursor.getInt(cursor.getColumnIndex("maLoai")));
                list.add(sach);
                Log.i("TAG", sach.toString());
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<Sach> getAll() {
        /*
         * LẤY TẤT CẢ DỮ LIỆU
         * */
        String sqlGetAll = "SELECT * FROM Sach";
        return get(sqlGetAll);
    }

    public boolean insertSach(Sach object) {
        /*
         * THÊM 1 SÁCH MỚI
         * 1. CẦN 1 HÀM KHỞI TẠO CÓ 3 THAM SỐ (tenSach, giaThue, maLoai)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", object.getTenSach());
        values.put("giaThue", object.getGiaThue());
        values.put("maLoai", object.getMaLoai());
        long checkInsert = sqLiteDatabase.insert("Sach", null, values);
        return checkInsert != -1;
    }

    public boolean updateSach(Sach object) {
        /*
         * CẬP NHẬT THÔNG TIN SÁCH (maSach)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", object.getTenSach());
        values.put("giaThue", object.getGiaThue());
        values.put("maLoai", object.getMaLoai());
        int checkUpadte = sqLiteDatabase.update("Sach", values, "maSach=?", new String[]{String.valueOf(object.getMaSach())});
        return checkUpadte > 0;
    }

    public boolean deleteSach(String maSach) {
        /*
         * XOÁ SÁCH (maSach)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE maSach=?", new String[]{maSach});
        if (cursor.getCount() != 0){
            return false;
        }
        int checkDelete = sqLiteDatabase.delete("Sach", "maSach=?", new String[]{maSach});
        return checkDelete > 0;
    }

    public Sach getByMaSach(String maSach) {
        /*
         * LẤY RA 1 ĐỐI TƯỢNG THEO (maSach)
         * */
        String sqlGetByMaSach = "SELECT * FROM Sach WHERE maSach=?";
        ArrayList<Sach> list = get(sqlGetByMaSach, maSach);
        return list.get(0);
    }
}
