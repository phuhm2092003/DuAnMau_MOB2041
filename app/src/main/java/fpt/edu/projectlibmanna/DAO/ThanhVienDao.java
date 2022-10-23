package fpt.edu.projectlibmanna.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpt.edu.projectlibmanna.database.LibManaDB;
import fpt.edu.projectlibmanna.model.ThanhVien;

public class ThanhVienDao {
    LibManaDB libManaDB;

    public ThanhVienDao(Context context) {
        this.libManaDB = new LibManaDB(context);
    }

    @SuppressLint("Range")
    public ArrayList<ThanhVien> get(String sql, String... selectionAgrs) {
        /*
         * LẤY DỮ LIỆU THEO CÂU TRUY VẤN (sql) HOẶC THAM SỐ LỰA CHỌN (selectionAgrs)
         * */
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = libManaDB.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionAgrs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setMaThanhVien(cursor.getInt(cursor.getColumnIndex("maThanhVien")));
                thanhVien.setHoTen(cursor.getString(cursor.getColumnIndex("hoTen")));
                thanhVien.setNamSinh(cursor.getString(cursor.getColumnIndex("namSinh")));
                list.add(thanhVien);
                Log.i("TAG", thanhVien.toString());
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<ThanhVien> getAll() {
        /*s
         * LẤY TẤT CẢ DỮ LIỆU
         * */
        String sqlGetAll = "SELECT * FROM ThanhVien";
        return get(sqlGetAll);
    }

    public boolean insertThanhVien(ThanhVien object) {
        /*
         * THÊM MỚI 1 THÀNH VIÊN
         * 1. CẦN 1 HÀM KHỞI TẠO CÓ 2 THAM SỐ (hoTen, namSinh)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", object.getHoTen());
        values.put("namSinh", object.getNamSinh());
        long checkInsert = sqLiteDatabase.insert("ThanhVien", null, values);
        return checkInsert != -1;
    }

    public boolean updateThanhVien(ThanhVien object) {
        /*
         * CẬP NHẬT THÔNG TIN THÀNH VIÊN (maThanhVien)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", object.getHoTen());
        values.put("namSinh", object.getNamSinh());
        int checkUpdate = sqLiteDatabase.update("ThanhVien", values, "maThanhVien=?", new String[]{String.valueOf(object.getMaThanhVien())});
        return checkUpdate > 0;
    }

    public boolean deleteThanhVien(String maThanhVien) {
        /*
         * XOÁ THÀNH VIÊN (maThanhVien)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE maThanhVien=?", new String[]{maThanhVien});
        if (cursor.getCount() != 0){
            return false;
        }
        int checkDelete = sqLiteDatabase.delete("ThanhVien", "maThanhVien=?", new String[]{maThanhVien});
        return checkDelete > 0;
    }

    public ThanhVien getByMaThanhVien(String maThanhVien) {
        /*
         * LẤY RA 1 ĐỐI TƯỢNG THEO (maThanhVien)
         * */
        String sqlGetByMaThanhVien = "SELECT * FROM ThanhVien WHERE maThanhVien=?";
        ArrayList<ThanhVien> list = get(sqlGetByMaThanhVien, maThanhVien);
        return list.get(0);
    }


}
