package fpt.edu.projectlibmanna.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class PhieuMuon {
    // phiếu mượn
    private int maPhieuMuon;
    private String maThuThu;
    private int maThanhVien;
    private int maSach;
    private int tienThue;
    private int traSach;
    private Date ngay;

    public PhieuMuon() {
    }

    public PhieuMuon(String maThuThu, int maThanhVien, int maSach, int tienThue, int traSach, Date ngay) {
        this.maThuThu = maThuThu;
        this.maThanhVien = maThanhVien;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.ngay = ngay;
    }

    public PhieuMuon(int maPhieuMuon, String maThuThu, int maThanhVien, int maSach, int tienThue, int traSach, Date ngay) {
        this.maPhieuMuon = maPhieuMuon;
        this.maThuThu = maThuThu;
        this.maThanhVien = maThanhVien;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.ngay = ngay;
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    @NonNull
    @Override
    public String toString() {
        return "PhieuMuon{" +
                "maPhieuMuon=" + maPhieuMuon +
                ", maThuThu='" + maThuThu + '\'' +
                ", maThanhVien=" + maThanhVien +
                ", maSach=" + maSach +
                ", tienThue=" + tienThue +
                ", traSach=" + traSach +
                ", ngay=" + ngay +
                '}';
    }
}
