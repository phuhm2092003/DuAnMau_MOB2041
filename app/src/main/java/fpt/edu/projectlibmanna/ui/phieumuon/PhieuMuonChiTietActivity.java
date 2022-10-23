package fpt.edu.projectlibmanna.ui.phieumuon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

import fpt.edu.projectlibmanna.DAO.PhieuMuonDao;
import fpt.edu.projectlibmanna.DAO.SachDao;
import fpt.edu.projectlibmanna.DAO.ThanhVienDao;
import fpt.edu.projectlibmanna.DAO.ThuThuDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.model.PhieuMuon;
import fpt.edu.projectlibmanna.model.Sach;
import fpt.edu.projectlibmanna.model.ThanhVien;
import fpt.edu.projectlibmanna.model.ThuThu;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class PhieuMuonChiTietActivity extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    TextView tvMaPhieuMuon, tvTenThuThu, tvTenThanhVien, tvTenSach, tvGiaThue, tvNgayMuon, tvTrangThai;
    ImageButton imgbBack;
    Button btnDelete,btnUpdate;
    PhieuMuonDao phieuMuonDao;
    ThuThuDao thuThuDao;
    ThanhVienDao thanhVienDao;
    SachDao sachDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_muon_chi_tiet);
        initView();
        initDao();
        setDataActivity();

        imgbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String maPhieuMuon = intent.getStringExtra("EXTRA_MAPHIEUMUON");
                AlertDialog.Builder builder = new AlertDialog.Builder(PhieuMuonChiTietActivity.this);
                builder.setMessage("Bạn có muốn xoá phiếu mượn PM0" + phieuMuonDao.getByMaPhieuMuon(maPhieuMuon).getMaPhieuMuon() + "?");
                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (phieuMuonDao.deletePhieuMuon(maPhieuMuon)) {
                            notifiDelete();
                            onBackPressed();
                        }
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String maPhieuMuon = intent.getStringExtra("EXTRA_MAPHIEUMUON");
                Intent intentUpadate = new Intent(PhieuMuonChiTietActivity.this, CapNhatPhieuMuonActivity.class);
                intentUpadate.putExtra("EXTRA_MAPHIEUMUON", maPhieuMuon);
                startActivity(intentUpadate);
            }
        });
    }

    private void notifiDelete() {
        MyNotification.checkSDK(PhieuMuonChiTietActivity.this);
        MyNotification.getNotification(PhieuMuonChiTietActivity.this, "Xoá phiếu mượn thành công");
        ToastCustom.successful(getApplicationContext(), "Xoá thành công");
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void setDataActivity() {
        Intent intent = getIntent();
        String maPhieuMuon = intent.getStringExtra("EXTRA_MAPHIEUMUON");
        PhieuMuon phieuMuon = phieuMuonDao.getByMaPhieuMuon(maPhieuMuon);
        ThuThu thuthu = thuThuDao.getByMaThuThu(String.valueOf(phieuMuon.getMaThuThu()));
        ThanhVien thanhVien = thanhVienDao.getByMaThanhVien(String.valueOf(phieuMuon.getMaThanhVien()));
        Sach sach = sachDao.getByMaSach(String.valueOf(phieuMuon.getMaSach()));
        tvMaPhieuMuon.setText(String.valueOf(phieuMuon.getMaPhieuMuon()));
        tvTenThuThu.setText(thuthu.getHoTen());
        tvTenThanhVien.setText(thanhVien.getHoTen());
        tvTenSach.setText(sach.getTenSach());
        tvGiaThue.setText(String.valueOf(sach.getGiaThue()));
        tvNgayMuon.setText(sdf.format(phieuMuon.getNgay()));
        int trangThai = phieuMuon.getTraSach();
        if(trangThai == 0){
            tvTrangThai.setText("Chưa trả");
            tvTrangThai.setBackground(getResources().getDrawable(R.drawable.bgr_tv_trang_thai_chua_tra));
            tvTrangThai.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
        }else {
            tvTrangThai.setText("Đã trả");
            tvTrangThai.setBackground(getResources().getDrawable(R.drawable.bgr_tv_trang_thai_da_tra));
            tvTrangThai.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
        }
    }

    private void initDao() {
        phieuMuonDao = new PhieuMuonDao(this);
        thuThuDao = new ThuThuDao(this);
        thanhVienDao = new ThanhVienDao(this);
        sachDao = new SachDao(this);
    }

    private void initView() {
        tvMaPhieuMuon = findViewById(R.id.tvMaPhieuMuon);
        tvTenThuThu = findViewById(R.id.tvTenThuThu);
        tvTenThanhVien = findViewById(R.id.tvTenThanhVien);
        tvTenSach = findViewById(R.id.tvTenSach);
        tvGiaThue = findViewById(R.id.tvGiaThue);
        tvNgayMuon = findViewById(R.id.tvNgayMuon);
        tvTrangThai = findViewById(R.id.tvTrangThai);
        imgbBack = findViewById(R.id.imgbBack);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataActivity();
    }
}