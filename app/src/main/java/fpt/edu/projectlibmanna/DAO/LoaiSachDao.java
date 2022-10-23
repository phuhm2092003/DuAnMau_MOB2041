package fpt.edu.projectlibmanna.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpt.edu.projectlibmanna.database.LibManaDB;
import fpt.edu.projectlibmanna.model.LoaiSach;

public class LoaiSachDao {
    LibManaDB libManaDB;

    public LoaiSachDao(Context context) {
        this.libManaDB = new LibManaDB(context);
    }

    @SuppressLint("Range")
    public ArrayList<LoaiSach> get(String sql, String... selectionAgrs) {
        /*
         * LẤY DỮ LIỆU THEO CÂU TRUY VẤN (sql) HOẶC THAM SỐ LỰA CHỌN (selectionAgrs)
         * */
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionAgrs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLoai(cursor.getInt(cursor.getColumnIndex("maLoai")));
                loaiSach.setTenLoai(cursor.getString(cursor.getColumnIndex("tenLoai")));
                list.add(loaiSach);
                Log.i("TAG", loaiSach.toString());
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<LoaiSach> getAll() {
        /*
         * LẤY TẤT CẢ DỮ LIỆU
         * */
        String sqlGetAll = "SELECT * FROM LoaiSach";
        return get(sqlGetAll);
    }

    public boolean insertLoaiSach(LoaiSach object) {
        /*
         * THÊM MỚI 1 LOẠI SÁCH
         * 1. CẦN 1 HÀM KHỞI TẠO CÓ 1 THAM SỐ (tenLoai)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", object.getTenLoai());
        long checkInsert = sqLiteDatabase.insert("LoaiSach", null, values);
        return checkInsert != -1;
    }

    public boolean updateLoaiSach(LoaiSach object) {
        /*
         * CẬP NHẬT THÔNG TIN LOẠI SÁCH (maLoai)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", object.getTenLoai());
        int  checkUpdate = sqLiteDatabase.update("LoaiSach", values, "maLoai=?", new String[]{String.valueOf(object.getMaLoai())});
        return checkUpdate > 0;
    }

    public boolean deleteLoaiSach(String maLoai) {
        /*
         * XOÁ THÀNH VIÊN (maLoai)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maLoai=?", new String[]{maLoai});
        if (cursor.getCount() != 0){
            return false;
        }
        int checkDelete = sqLiteDatabase.delete("LoaiSach", "maLoai=?", new String[]{maLoai});
        return checkDelete > 0;
    }

    public LoaiSach getByMaLoaiSach(String maLoai) {
        /*
         * LẤY RA 1 ĐỐI TƯỢNG THEO (maLoai)
         * */
        String sqlGetByMaLoaiSach = "SELECT * FROM LoaiSach WHERE maLoai=?;";
        ArrayList<LoaiSach> list = get(sqlGetByMaLoaiSach, maLoai);
        return list.get(0);
    }


}
