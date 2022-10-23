package fpt.edu.projectlibmanna.ui.phieumuon;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fpt.edu.projectlibmanna.DAO.PhieuMuonDao;
import fpt.edu.projectlibmanna.DAO.SachDao;
import fpt.edu.projectlibmanna.DAO.ThanhVienDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.adpater.SpinerThanhVienAdapter;
import fpt.edu.projectlibmanna.adpater.SpinnerSachAdapter;
import fpt.edu.projectlibmanna.model.PhieuMuon;
import fpt.edu.projectlibmanna.model.Sach;
import fpt.edu.projectlibmanna.model.ThanhVien;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class CapNhatPhieuMuonActivity extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    Toolbar toolbar;
    Spinner spThanhVien, spSach;
    ThanhVienDao thanhVienDao;
    EditText edtTienThue, edtNgayMuon, edtMaPhieuMuon;
    SachDao sachDao;
    RadioGroup radioGroup;
    PhieuMuonDao phieuMuonDao;
    RadioButton rdoChuaTra, rdoDatra;
    Button btnUpadte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_phieu_muon);
        initView();
        initDao();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
            }
        });
        fillSpinnerThanhVien();
        fillSpinnerSach();
        Intent intent = getIntent();
        String maPhieuMuon = intent.getStringExtra("EXTRA_MAPHIEUMUON");
        PhieuMuon phieuMuon = phieuMuonDao.getByMaPhieuMuon(maPhieuMuon);
        setDataAcivity(phieuMuon);

        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Sach sach = (Sach) spSach.getSelectedItem();
                edtTienThue.setText(String.valueOf(sach.getGiaThue()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnUpadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThanhVien thanhVien = (ThanhVien) spThanhVien.getSelectedItem();
                int maThanhVien = thanhVien.getMaThanhVien();
                Sach sach = (Sach) spSach.getSelectedItem();
                int maSach = sach.getMaSach();
                int tienThue = sach.getGiaThue();
                int trangThai = getTrangThai();
                Date ngay = null;
                try {
                    ngay = sdf.parse(String.valueOf(edtNgayMuon.getText()));
                    if (phieuMuonDao.updatePhieuMuon(new PhieuMuon(phieuMuon.getMaPhieuMuon(), phieuMuon.getMaThuThu(), maThanhVien, maSach, tienThue, trangThai, ngay))) {
                        notifiUpdate(phieuMuon);
                        onBackPressed();
                    } else {
                        ToastCustom.error(getApplicationContext(), "Lỗi");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    ToastCustom.error(getApplicationContext(), "Lỗi định dạng ngày tháng");
                }
            }
        });
        edtNgayMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

    }

    private void notifiUpdate(PhieuMuon phieuMuon) {
        ToastCustom.successful(getApplicationContext(), "Cập nhật thành công");
        MyNotification.checkSDK(CapNhatPhieuMuonActivity.this);
        MyNotification.getNotification(CapNhatPhieuMuonActivity.this, "Cập nhật phiếu mượn PM0"+ phieuMuon.getMaPhieuMuon()+" thành công");
    }

    private void setDataAcivity(PhieuMuon phieuMuon) {
        edtMaPhieuMuon.setText(String.valueOf(phieuMuon.getMaPhieuMuon()));
        String tenThanhVien = thanhVienDao.getByMaThanhVien(String.valueOf(phieuMuon.getMaThanhVien())).getHoTen();
        for (int i = 0 ; i < thanhVienDao.getAll().size(); i ++){
            if(thanhVienDao.getAll().get(i).getHoTen().equals(tenThanhVien)){
                spThanhVien.setSelection(i);
                break;
            }
        }
        String tenSach = sachDao.getByMaSach(String.valueOf(phieuMuon.getMaSach())).getTenSach();
        for (int i = 0 ; i < sachDao.getAll().size(); i ++){
            if(sachDao.getAll().get(i).getTenSach().equals(tenSach)){
                spSach.setSelection(i);
                break;
            }
        }
        edtTienThue.setText(String.valueOf(sachDao.getByMaSach(String.valueOf(phieuMuon.getMaSach())).getGiaThue()));
        edtNgayMuon.setText(sdf.format(phieuMuon.getNgay()));
        int trangThai = phieuMuon.getTraSach();
        if(trangThai == 0){
           rdoChuaTra.setChecked(true);
        }else {
            rdoDatra.setChecked(true);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.tbCapNhatPhieuMuon);
        edtMaPhieuMuon = findViewById(R.id.edtMaPhieuMuon);
        spThanhVien = findViewById(R.id.spThanhVien);
        spSach = findViewById(R.id.spSach);
        edtTienThue = findViewById(R.id.edtTienThue);
        edtNgayMuon = findViewById(R.id.edtNgayMuon);

        radioGroup = findViewById(R.id.groupTrangThai);
        rdoChuaTra = findViewById(R.id.rbChuaTra);
        rdoDatra = findViewById(R.id.rbDaTra);
        btnUpadte = findViewById(R.id.btnUpdate);
    }
    private void initDao() {
        phieuMuonDao = new PhieuMuonDao(this);
        thanhVienDao = new ThanhVienDao(this);
        sachDao = new SachDao(this);
    }

    private void fillSpinnerSach() {
        SpinnerSachAdapter adapterSach = new SpinnerSachAdapter(CapNhatPhieuMuonActivity.this, sachDao.getAll());
        spSach.setAdapter(adapterSach);
    }

    private void fillSpinnerThanhVien() {
        SpinerThanhVienAdapter adapterThanhVien = new SpinerThanhVienAdapter(CapNhatPhieuMuonActivity.this, thanhVienDao.getAll());
        spThanhVien.setAdapter(adapterThanhVien);
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CapNhatPhieuMuonActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                edtNgayMuon.setText(sdf.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private int getTrangThai() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.rbChuaTra) {
            return 0;
        }
        return 1;
    }
}