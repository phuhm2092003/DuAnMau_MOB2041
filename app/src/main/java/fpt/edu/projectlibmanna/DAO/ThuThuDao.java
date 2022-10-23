package fpt.edu.projectlibmanna.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpt.edu.projectlibmanna.database.LibManaDB;
import fpt.edu.projectlibmanna.model.ThuThu;

public class ThuThuDao {
    LibManaDB libManaDB;

    public ThuThuDao(Context context) {
        this.libManaDB = new LibManaDB(context);
    }

    @SuppressLint("Range")
    public ArrayList<ThuThu> get(String sql, String... selectionAgrs) {
        /*
         * LẤY DỮ LIỆU THEO CÂU TRUY VẤN (sql) HOẶC THAM SỐ LỰA CHỌN (selectionAgrs)
         * */
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = libManaDB.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionAgrs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaThuThu(cursor.getString(cursor.getColumnIndex("maThuThu")));
                thuThu.setHoTen(cursor.getString(cursor.getColumnIndex("hoTen")));
                thuThu.setMatKhau(cursor.getString(cursor.getColumnIndex("matKhau")));
                list.add(thuThu);
                Log.i("TAG", thuThu.toString());
            } while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<ThuThu> getAll() {
        /*
         * LẤY TẤT CẢ DỮ LIỆU
         * */
        String sqlGetAll = "SELECT * FROM ThuThu";
        return get(sqlGetAll);
    }

    public boolean insertThuThu(ThuThu object) {
        /*
         * THÊM MỚI 1 THỦ THƯ
         * 1. CẦN 1 HÀM KHỞI TẠO CÓ 3 THAM SỐ (maThuThu, hoTen, matKhau);
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maThuThu", object.getMaThuThu());
        values.put("hoTen", object.getHoTen());
        values.put("matKhau", object.getMatKhau());
        long checkInsert = sqLiteDatabase.insert("ThuThu", null, values);
        return checkInsert != -1;
    }

    public boolean updateThuThu(ThuThu object) {
        /*
         * CẬP NHẬT THÔNG TIN THỦ THƯ (maThuThu)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoTen", object.getHoTen());
        values.put("matKhau", object.getMatKhau());
        int checkUpdate = sqLiteDatabase.update("ThuThu", values, "maThuThu=?", new String[]{object.getMaThuThu()});
        return checkUpdate > 0;
    }

    public boolean deleteThuThu(String maThuThu) {
        /*
         * XOÁ THỦ THƯ (maThuThu)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE maThuThu=?", new String[]{maThuThu});
        if (cursor.getCount() != 0){
            return false;
        }
        int checkDelete = sqLiteDatabase.delete("ThuThu", "maThuThu=?", new String[]{maThuThu});
        return checkDelete > 0;
    }

    public ThuThu getByMaThuThu(String maThuThu) {
        /*
         * LẤY RA 1 ĐỐI TƯỢNG THEO (maThuThu)
         * */
        String sqlGetByMaThuThu = "SELECT * FROM ThuThu WHERE maThuThu=?";
        ArrayList<ThuThu> list = get(sqlGetByMaThuThu, maThuThu);
        return list.get(0);
    }

    public boolean checkLogin(String tenDangNhap, String matKhau) {
        /*
         * KIỂM TRA ĐĂNG NHẬP
         * NẾU TÊN ĐĂNG NHẬP (maThuThu) VÀ MẬT KHẨU (matKhau) ĐÚNG THÌ list.size() != 0
         * --> ĐĂNG NHẬP THÀNH CÔNG
         * */
        String sqlCheckLogin = "SELECT * FROM ThuThu WHERE maThuThu=? AND matKhau=?";
        ArrayList<ThuThu> list = get(sqlCheckLogin, tenDangNhap, matKhau);
        return list.size() != 0;
    }

}
