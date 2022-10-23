package fpt.edu.projectlibmanna.ui.phieumuon;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
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

public class ThemPhieuMuonActivity extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    Toolbar toolbar;
    Spinner spThanhVien, spSach;
    ThanhVienDao thanhVienDao;
    EditText edtTienThue, edtNgayMuon;
    SachDao sachDao;
    RadioGroup radioGroup;
    PhieuMuonDao phieuMuonDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phieu_muon);
        initView();
        setSupportActionBar(toolbar);
        initDao();
        fillSpinnerThanhVien();
        fillSpinnerSach();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        edtNgayMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

    }

    private void initDao() {
        phieuMuonDao = new PhieuMuonDao(this);
        thanhVienDao = new ThanhVienDao(this);
        sachDao = new SachDao(this);
    }

    private void initView() {
        spThanhVien = findViewById(R.id.spThanhVien);
        spSach = findViewById(R.id.spSach);
        edtTienThue = findViewById(R.id.edtTienThue);
        edtNgayMuon = findViewById(R.id.edtNgayMuon);
        toolbar = findViewById(R.id.tbThemPhieuMuon);
        radioGroup = findViewById(R.id.groupTrangThai);
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ThemPhieuMuonActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                edtNgayMuon.setText(sdf.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void fillSpinnerSach() {
        SpinnerSachAdapter adapterSach = new SpinnerSachAdapter(ThemPhieuMuonActivity.this, sachDao.getAll());
        spSach.setAdapter(adapterSach);
    }

    private void fillSpinnerThanhVien() {

        SpinerThanhVienAdapter adapterThanhVien = new SpinerThanhVienAdapter(ThemPhieuMuonActivity.this, thanhVienDao.getAll());
        spThanhVien.setAdapter(adapterThanhVien);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phieumuon, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addPhieuMuon) {
            Intent intent = getIntent();
            String maThuThu = intent.getStringExtra("EXTRA_MATHUTHU");
            ThanhVien thanhVien = (ThanhVien) spThanhVien.getSelectedItem();
            int maThanhVien = thanhVien.getMaThanhVien();
            Sach sach = (Sach) spSach.getSelectedItem();
            int maSach = sach.getMaSach();
            int tienThue = sach.getGiaThue();
            int trangThai = getTrangThai();
            Date ngay = null;
            try {
                ngay = sdf.parse(String.valueOf(edtNgayMuon.getText()));
                if (phieuMuonDao.insertPhieuMuon(new PhieuMuon(maThuThu, maThanhVien, maSach, tienThue, trangThai, ngay))) {
                    notifiAdd();
                } else {
                    ToastCustom.error(getApplicationContext(), "Lỗi");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                ToastCustom.error(getApplicationContext(), "Lỗi định dạng ngày tháng");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void notifiAdd() {
        ToastCustom.successful(getApplicationContext(), "Thêm thành công");
        MyNotification.checkSDK(ThemPhieuMuonActivity.this);
        MyNotification.getNotification(ThemPhieuMuonActivity.this, "Thêm phiếu mượn thành công");
    }

    private int getTrangThai() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.rbChuaTra) {
            return 0;
        }
        return 1;
    }
}