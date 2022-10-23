package fpt.edu.projectlibmanna.ui.thanhvien;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.projectlibmanna.DAO.ThanhVienDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.model.ThanhVien;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class ThanhVienChiTietActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnDelete, btnUpdate;
    private TextView tvMaThanhVien, tvHoTen, tvNamSinh;
    private ThanhVienDao thanhVienDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_vien_chi_tiet);
        initView();
        setDataActivity();
        thanhVienDao = new ThanhVienDao(this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteThanhVien();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateThanhVien();
            }
        });
    }

    private void updateThanhVien() {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_sua_thanhvien, null);
        Button btnCancle = viewDialog.findViewById(R.id.btnBackScreenMain);
        Button btnUpdate = viewDialog.findViewById(R.id.btnUpdateThanhVien);
        TextInputLayout tilMaThanhVien = viewDialog.findViewById(R.id.tilMaThanhVien);
        TextInputLayout tilTenThanhVien = viewDialog.findViewById(R.id.tilHoVaTenSua);
        TextInputLayout tilNamSinh = viewDialog.findViewById(R.id.tilNamSinhSua);
        setDataDialog(tilMaThanhVien, tilTenThanhVien, tilNamSinh);
        AlertDialog.Builder builder = new AlertDialog.Builder(ThanhVienChiTietActivity.this);
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoVaTen = getText(tilTenThanhVien);
                String namSinh = getText(tilNamSinh);
                if (!hoVaTen.isEmpty() && !namSinh.isEmpty()) {
                    String maThanhVien = getMaThanhVien();
                    ThanhVien thanhVien = thanhVienDao.getByMaThanhVien(maThanhVien);
                    thanhVien.setHoTen(hoVaTen);
                    thanhVien.setNamSinh(namSinh);
                    if (thanhVienDao.updateThanhVien(thanhVien)) {
                        dialog.dismiss();
                        notifiUpdate(maThanhVien);
                        setDataAfterUdapte(hoVaTen, namSinh, maThanhVien);
                    }
                } else {
                    ToastCustom.error(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin");
                }
            }
        });
    }

    private String getMaThanhVien() {
        Intent intent = getIntent();
        return intent.getStringExtra("EXTRA_MATHANHVIEN");
    }

    @NonNull
    private String getText(TextInputLayout tilTenThanhVien) {
        return Objects.requireNonNull(tilTenThanhVien.getEditText()).getText().toString();
    }

    private void setDataAfterUdapte(String hoVaTen, String namSinh, String maThanhVien) {
        tvMaThanhVien.setText(maThanhVien);
        tvHoTen.setText(hoVaTen);
        tvNamSinh.setText(namSinh);
    }

    private void notifiUpdate(String maThanhVien) {
        MyNotification.checkSDK(ThanhVienChiTietActivity.this);
        MyNotification.getNotification(ThanhVienChiTietActivity.this, "Cập thành viên có mã TV0"+ maThanhVien + " thành công");
        ToastCustom.successful(getApplicationContext(), "Cập nhật thành công");
    }

    private void deleteThanhVien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThanhVienChiTietActivity.this);
        builder.setMessage("Bạn có muốn xoá thành viên " + thanhVienDao.getByMaThanhVien(getMaThanhVien()).getHoTen() + "?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (thanhVienDao.deleteThanhVien(getMaThanhVien())) {
                    notifiDelete(getMaThanhVien());
                    onBackPressed();
                }else {
                    ToastCustom.error(getApplicationContext(), "Xoá không thành công!Thành viên này đã tồn tại trong phiếu mượn");
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

    private void notifiDelete(String maThanhVien) {
        MyNotification.checkSDK(ThanhVienChiTietActivity.this);
        MyNotification.getNotification(ThanhVienChiTietActivity.this, "Xoá thành viên thành công");
        ToastCustom.successful(getApplicationContext(), "Xoá thành công");
    }


    private void initView() {
        btnBack = findViewById(R.id.imgButomBack);
        tvMaThanhVien = findViewById(R.id.tv_info_maThanhVien);
        tvHoTen = findViewById(R.id.tv_info_tenThanhVien);
        tvNamSinh = findViewById(R.id.tv_info_namsinh);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
    }


    private void setDataActivity() {
        Intent intent = getIntent();
        tvMaThanhVien.setText(intent.getStringExtra("EXTRA_MATHANHVIEN"));
        tvHoTen.setText(intent.getStringExtra("EXTRA_TENTHANHVIEN"));
        tvNamSinh.setText(intent.getStringExtra("EXTRA_NAMSINH"));
    }

    private void setDataDialog(TextInputLayout tilMaThanhVien, TextInputLayout tilTenThanhVien, TextInputLayout tilNamSinh) {
        Objects.requireNonNull(tilMaThanhVien.getEditText()).setText(tvMaThanhVien.getText());
        Objects.requireNonNull(tilTenThanhVien.getEditText()).setText(tvHoTen.getText());
        Objects.requireNonNull(tilNamSinh.getEditText()).setText(tvNamSinh.getText());

    }


}