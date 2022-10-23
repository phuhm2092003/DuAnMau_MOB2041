package fpt.edu.projectlibmanna.model;

import androidx.annotation.NonNull;

public class ThuThu {
    private String maThuThu;
    private String hoTen;
    private String matKhau;

    public ThuThu() {
    }

    public ThuThu(String maThuThu, String hoTen, String matKhau) {
        this.maThuThu = maThuThu;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @NonNull
    @Override
    public String toString() {
        return "ThuThu{" +
                "maThuThu='" + maThuThu + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}
