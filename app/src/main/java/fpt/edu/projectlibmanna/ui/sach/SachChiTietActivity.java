package fpt.edu.projectlibmanna.ui.sach;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.projectlibmanna.DAO.LoaiSachDao;
import fpt.edu.projectlibmanna.DAO.SachDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.adpater.SpinerLoaiSachAdapter;
import fpt.edu.projectlibmanna.model.LoaiSach;
import fpt.edu.projectlibmanna.model.Sach;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class SachChiTietActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView tvMaSach, tvTenSach, tvGiaThue, tvMaLoai, tvTenLoai;
    private Button btnDelete, btnUpdate;
    private SachDao sachDao;
    private LoaiSachDao loaiSachDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach_chi_tiet);
        initView();
        initDao();
        setTextActivity();
        btnBack.setOnClickListener(view -> onBackPressed());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSach();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_sua_sach, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(SachChiTietActivity.this);
                Button btnCancel = viewDialog.findViewById(R.id.btnBack);
                Button btnUpdate = viewDialog.findViewById(R.id.btnUpdate);
                TextInputLayout tilMaSach = viewDialog.findViewById(R.id.tilMaSach);
                TextInputLayout tilTenSach = viewDialog.findViewById(R.id.tilTenSach);
                TextInputLayout tilGiaThue = viewDialog.findViewById(R.id.tilGiaThue);
                Spinner spinner = viewDialog.findViewById(R.id.spinnerLoaiSach);
                fillSpinner(spinner);
                setDataDialog(tilMaSach, tilTenSach, tilGiaThue, spinner);
                builder.setView(viewDialog);
                AlertDialog dialog = builder.create();
                dialog.show();
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int maSach = getMaSach();
                        String tenSach = getText(tilTenSach);
                        String tienThue = getText(tilGiaThue);
                        int maLoai = getMaLoai(spinner);
                        if (!tenSach.isEmpty() && !tienThue.isEmpty()) {
                            if (sachDao.updateSach(new Sach(maSach, tenSach, Integer.parseInt(tienThue), maLoai))) {
                                setTextAfterUpdate(maSach, tenSach, tienThue, maLoai);
                                dialog.dismiss();
                                notifiUpdate(maSach);
                            }
                        } else {
                            ToastCustom.error(SachChiTietActivity.this, "Vui lòng nhập đầy đủ thông tin");
                        }
                    }
                });
            }
        });

    }

    private void setTextAfterUpdate(int maSach, String tenSach, String tienThue, int maLoai) {
        tvMaSach.setText(String.valueOf(maSach));
        tvTenSach.setText(tenSach);
        tvGiaThue.setText(tienThue);
        tvMaLoai.setText(String.valueOf(maLoai));
        tvTenLoai.setText(loaiSachDao.getByMaLoaiSach(String.valueOf(maLoai)).getTenLoai());
    }

    private int getMaLoai(Spinner spinner) {
        LoaiSach loaiSach = (LoaiSach) spinner.getSelectedItem();
        return loaiSach.getMaLoai();
    }

    private int getMaSach() {
        Intent intent = getIntent();
        return Integer.parseInt(intent.getStringExtra("EXTRA_MASACH"));
    }

    @NonNull
    private String getText(TextInputLayout tilTenSach) {
        return Objects.requireNonNull(tilTenSach.getEditText()).getText().toString();
    }

    private void notifiUpdate(int maSach) {
        MyNotification.checkSDK(SachChiTietActivity.this);
        MyNotification.getNotification(SachChiTietActivity.this, "Cập nhật sách có mã sách S0" + maSach + " thành công ");
        ToastCustom.successful(SachChiTietActivity.this, "Cập nhật thành công!");
    }

    private void setDataDialog(TextInputLayout tilMaSach, TextInputLayout tilTenSach, TextInputLayout tilGiaThue, Spinner spinner) {
        Objects.requireNonNull(tilMaSach.getEditText()).setText(tvMaSach.getText());
        Objects.requireNonNull(tilTenSach.getEditText()).setText(tvTenSach.getText());
        Objects.requireNonNull(tilGiaThue.getEditText()).setText(tvGiaThue.getText());
        String tenLoai = loaiSachDao.getByMaLoaiSach(tvMaLoai.getText().toString()).getTenLoai();
        int index = 0;
        for (int i = 0; i < loaiSachDao.getAll().size(); i++) {
            if (loaiSachDao.getAll().get(i).getTenLoai().equals(tenLoai)) {
                index = i;
                break;
            }
        }
        spinner.setSelection(index);
    }

    private void fillSpinner(Spinner spinner) {
        SpinerLoaiSachAdapter arrayAdapter = new SpinerLoaiSachAdapter(SachChiTietActivity.this, loaiSachDao.getAll());
        spinner.setAdapter(arrayAdapter);
    }


    private void setTextActivity() {
        Intent intent = getIntent();
        tvMaSach.setText(intent.getStringExtra("EXTRA_MASACH"));
        tvTenSach.setText(intent.getStringExtra("EXTRA_TENSACH"));
        tvGiaThue.setText(intent.getStringExtra("EXTRA_GIATHUE"));
        tvMaLoai.setText(intent.getStringExtra("EXTRA_MALOAI"));
        tvTenLoai.setText(loaiSachDao.getByMaLoaiSach(intent.getStringExtra("EXTRA_MALOAI")).getTenLoai());
    }

    private void deleteSach() {
        Intent intent = getIntent();
        String maSach = intent.getStringExtra("EXTRA_MASACH");
        AlertDialog.Builder builder = new AlertDialog.Builder(SachChiTietActivity.this);
        builder.setMessage("Bạn có muốn xoá sách " + sachDao.getByMaSach(maSach).getTenSach() + "?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (sachDao.deleteSach(maSach)) {
                    notifiDelete();
                    onBackPressed();
                } else {
                    ToastCustom.error(getApplicationContext(), "Xoá không thành công!Sachs này đã tồn tại trong phiếu mượn");
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

    private void notifiDelete() {
        MyNotification.checkSDK(SachChiTietActivity.this);
        MyNotification.getNotification(SachChiTietActivity.this, "Xoá sách thành công");
        ToastCustom.successful(getApplicationContext(), "Xoá thành công");
    }

    private void initDao() {
        sachDao = new SachDao(this);
        loaiSachDao = new LoaiSachDao(this);
    }

    private void initView() {
        tvMaSach = findViewById(R.id.tv_info_maSach);
        tvTenSach = findViewById(R.id.tv_info_tenSach);
        tvGiaThue = findViewById(R.id.tv_info_giathue);
        tvMaLoai = findViewById(R.id.tv_info_maLoai);
        tvTenLoai = findViewById(R.id.tv_info_tenLoaiSach);
        btnBack = findViewById(R.id.imgbBack);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
}