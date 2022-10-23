package fpt.edu.projectlibmanna.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;

import fpt.edu.projectlibmanna.XDate.MyDate;
import fpt.edu.projectlibmanna.database.LibManaDB;
import fpt.edu.projectlibmanna.model.PhieuMuon;
import fpt.edu.projectlibmanna.model.Sach;
import fpt.edu.projectlibmanna.model.Top10;


public class PhieuMuonDao {
    LibManaDB libManaDB;
    public PhieuMuonDao(Context context) {
        this.libManaDB = new LibManaDB(context);
    }

    @SuppressLint("Range")
    public ArrayList<PhieuMuon> get(String sql, String... selectionAgrs) {
        /*
         * LẤY DỮ LIỆU THEO CÂU TRUY VẤN (sql) HOẶC THAM SỐ LỰA CHỌN (selectionAgrs)
         * */
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = libManaDB.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionAgrs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaPhieuMuon(cursor.getInt(cursor.getColumnIndex("maPhieuMuon")));
                phieuMuon.setMaThuThu(cursor.getString(cursor.getColumnIndex("maThuThu")));
                phieuMuon.setMaThanhVien(cursor.getInt(cursor.getColumnIndex("maThanhVien")));
                phieuMuon.setMaSach(cursor.getInt(cursor.getColumnIndex("maSach")));
                phieuMuon.setTienThue(cursor.getInt(cursor.getColumnIndex("tienThue")));
                phieuMuon.setTraSach(cursor.getInt(cursor.getColumnIndex("traSach")));
                try {
                    // CHUYỂN DỮ LIỆU ĐỌC ĐƯỢC VỀ KIỀU DỮ LIỆU DATE
                    phieuMuon.setNgay(MyDate.toDate(cursor.getString(cursor.getColumnIndex("ngay"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                list.add(phieuMuon);
                Log.i("TAG", phieuMuon.toString());
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<PhieuMuon> getAll() {
        /*s
         * LẤY TẤT CẢ DỮ LIỆU
         * */
        String sqlGetAll = "SELECT * FROM PhieuMuon";
        return get(sqlGetAll);
    }

    public boolean insertPhieuMuon(PhieuMuon object) {
        /*
         * THÊM MỚI 1 PHIẾU MƯỢN MỚI
         * 1. CẦN 1 HÀM KHỞI TẠO CÓ 6 THAM SỐ (maThuThu, maThanhVien, maSach, tienThue, traSach, ngay);
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maThuThu", object.getMaThuThu());
        values.put("maThanhVien", object.getMaThanhVien());
        values.put("maSach", object.getMaSach());
        values.put("tienThue", object.getTienThue());
        values.put("traSach", object.getTraSach());
        values.put("ngay", MyDate.toString(object.getNgay()));
        long checkInsert = sqLiteDatabase.insert("PhieuMuon", null, values);
        return checkInsert != -1;
    }

    public boolean updatePhieuMuon(PhieuMuon object) {
        /*
         * CẬP NHẬT THÔNG TIN PHIẾU MƯỢN (maPhieuMuon)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maThuThu", object.getMaThuThu());
        values.put("maThanhVien", object.getMaThanhVien());
        values.put("maSach", object.getMaSach());
        values.put("tienThue", object.getTienThue());
        values.put("traSach", object.getTraSach());
        values.put("ngay", MyDate.toString(object.getNgay()));
        int checkUpdate = sqLiteDatabase.update("PhieuMuon", values, "maPhieuMuon=?", new String[]{String.valueOf(object.getMaPhieuMuon())});
        return checkUpdate > 0;
    }

    public boolean deletePhieuMuon(String maPhieuMuon) {
        /*
         * XOÁ PHIẾU MƯỢN(maPhieuMuon)
         * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getWritableDatabase();
        int checkDelete = sqLiteDatabase.delete("PhieuMuon", "maPhieuMuon=?", new String[]{maPhieuMuon});
        return checkDelete > 0;
    }

    public PhieuMuon getByMaPhieuMuon(String maPhieuMuon){
        /*
         * LẤY RA 1 ĐỐI TƯỢNG THEO (maPhieuMuon)
         * */
        String sqlGetByMaPhieuMuon = "SELECT * FROM PhieuMuon WHERE maPhieuMuon=?;";
        ArrayList<PhieuMuon> list = get(sqlGetByMaPhieuMuon, maPhieuMuon);
        return list.get(0);
    }

    @SuppressLint("Range")
    public ArrayList<Top10> getTop(Context context){
        /*
        * LẤY TOP 10 ĐẦU SÁCH ĐƯỢC MƯỢN NHIỀU NHẤT
        * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getReadableDatabase();
        String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        ArrayList<Top10> list = new ArrayList<>();
        SachDao sachDao = new SachDao(context);
        @SuppressLint("Recycle")
        Cursor cursor = sqLiteDatabase.rawQuery(sqlTop, null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    Top10 top10 = new Top10();
                    Sach sach = sachDao.getByMaSach(cursor.getString(cursor.getColumnIndex("maSach")));
                    top10.setTenSach(sach.getTenSach());
                    top10.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
                    list.add(top10);
                }while (cursor.moveToNext());
            }
        return list;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        /*
        * LẤY DOANH THU CỦA THƯ VIỆN
        * */
        SQLiteDatabase sqLiteDatabase = libManaDB.getReadableDatabase();
        String sqlDoanhThu = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        ArrayList<Integer> list = new ArrayList<>();
        @SuppressLint("Recycle")
        Cursor cursor = sqLiteDatabase.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                try{
                    list.add(cursor.getInt(cursor.getColumnIndex("doanhThu")));
                }catch (Exception e){
                    list.add(0);
                }
            }while (cursor.moveToNext());
        }
        return list.get(0);
    }



}
