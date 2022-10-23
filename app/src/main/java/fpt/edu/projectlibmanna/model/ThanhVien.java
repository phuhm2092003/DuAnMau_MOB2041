package fpt.edu.projectlibmanna.model;

import androidx.annotation.NonNull;

public class ThanhVien {
    private int maThanhVien;
    private String hoTen;
    private String namSinh;

    public ThanhVien() {
    }

    public ThanhVien(int maThanhVien, String hoTen, String namSinh) {
        this.maThanhVien = maThanhVien;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }

    public ThanhVien(String hoTen, String namSinh) {
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    @NonNull
    @Override
    public String toString() {
        return "ThanhVien{" +
                "maThanhVien=" + maThanhVien +
                ", hoTen='" + hoTen + '\'' +
                ", namSinh='" + namSinh + '\'' +
                '}';
    }
}
