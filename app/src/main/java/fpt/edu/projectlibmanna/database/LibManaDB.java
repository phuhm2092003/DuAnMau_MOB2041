package fpt.edu.projectlibmanna.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LibManaDB extends SQLiteOpenHelper {
    private static final String NAME_DB = "LibMana";
    private static final int VERSION_DB = 1;
    public static final String CREATE_TABLE_THU_THU = "CREATE TABLE ThuThu (" +
            "maThuThu TEXT PRIMARY KEY," +
            "hoTen    TEXT NOT NULL," +
            "matKhau  TEXT NOT NULL)";

    public static final String CREATE_TABLE_THANH_VIEN = "CREATE TABLE ThanhVien (" +
            "maThanhVien INTEGER PRIMARY KEY AUTOINCREMENT," +
            "hoTen       TEXT NOT NULL," +
            "namSinh     TEXT NOT NULL)";

    public static final String CREATE_TABLE_LOAI_SACH = "CREATE TABLE LoaiSach (" +
            "maLoai  INTEGER PRIMARY KEY AUTOINCREMENT," +
            "tenLoai TEXT    NOT NULL)";

    public static final String CREATE_TABLE_SACH = "CREATE TABLE Sach (" +
            "maSach  INTEGER PRIMARY KEY AUTOINCREMENT," +
            "tenSach TEXT    NOT NULL," +
            "giaThue INTEGER NOT NULL," +
            "maLoai  INTEGER REFERENCES LoaiSach (maLoai))";

    public static final String CREATE_TABLE_PHIEU_MUON = "CREATE TABLE PhieuMuon (" +
            "maPhieuMuon INTEGER  PRIMARY KEY AUTOINCREMENT," +
            "maThuThu    TEXT     REFERENCES ThuThu (maThuThu)," +
            "maThanhVien INTEGER  REFERENCES ThanhVien (maThanhVien)," +
            "maSach      INTEGER  REFERENCES Sach (maSach)," +
            "tienThue    INTEGER NOT NULL," +
            "traSach     INTEGER  NOT NULL," +
            "ngay        DATE     NOT NULL)";
    public LibManaDB(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TẠO BẢNG THỦ THƯ
        sqLiteDatabase.execSQL(CREATE_TABLE_THU_THU);
        // TẠO BẢNG THÀNH VIÊN
        sqLiteDatabase.execSQL(CREATE_TABLE_THANH_VIEN);
        // TẠO BẢNG LOẠI SÁCH
        sqLiteDatabase.execSQL(CREATE_TABLE_LOAI_SACH);
        // TẠO BẢNG SÁCH
        sqLiteDatabase.execSQL(CREATE_TABLE_SACH);
        // TẠO BẢNG PHIẾU MƯỢN
        sqLiteDatabase.execSQL(CREATE_TABLE_PHIEU_MUON);
        // DATA SYSTEM
        sqLiteDatabase.execSQL(Data_SQL.INSERT_THUTHU);
        sqLiteDatabase.execSQL(Data_SQL.INSERT_THANHVIEN);
        sqLiteDatabase.execSQL(Data_SQL.INSERT_LOAISACH);
        sqLiteDatabase.execSQL(Data_SQL.INSERT_SACH);
        sqLiteDatabase.execSQL(Data_SQL.INSERT_PHIEUMUON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTableThuThu = "DROP TABLE IF EXISTS ThuThu";
        sqLiteDatabase.execSQL(dropTableThuThu);
        String dropTableThanhVien = "DROP TABLE IF EXISTS ThanhVien";
        sqLiteDatabase.execSQL(dropTableThanhVien);
        String dropTableLoaiSach = "DROP TABLE IF EXISTS LoaiSach";
        sqLiteDatabase.execSQL(dropTableLoaiSach);
        String dropTableSach = "DROP TABLE IF EXISTS Sach";
        sqLiteDatabase.execSQL(dropTableSach);
        String dropTablePhieuMuon = "DROP TABLE IF EXISTS PhieuMuon";
        sqLiteDatabase.execSQL(dropTablePhieuMuon);

        onCreate(sqLiteDatabase);

    }
}
