package fpt.edu.projectlibmanna.ui.thuthu;

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

import fpt.edu.projectlibmanna.DAO.ThuThuDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.model.ThuThu;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class ThuThuChiTietActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private Button btnDelete, btnUpdate;
    private TextView tvMaThuThu, tvHoTen, tvMatKhau;
    private ThuThuDao thuThuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_thu_chi_tiet);
        initView();
        setTextActivity();
        thuThuDao = new ThuThuDao(this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteThuThu();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateThuThu();
            }
        });
    }

    private void updateThuThu() {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_sua_thuthu, null);

        Button btnCancle = viewDialog.findViewById(R.id.btnBackScreenMain);
        Button btnUpdate = viewDialog.findViewById(R.id.btnUpdateThuThu);
        TextInputLayout tilTenDangNhap = viewDialog.findViewById(R.id.tilTenDangNhapSua);
        TextInputLayout tilHoVaTen = viewDialog.findViewById(R.id.tilHoVaTenSua);
        TextInputLayout tilMatKhau = viewDialog.findViewById(R.id.tilMatKhauSua);
        setTextDialog(tilTenDangNhap, tilHoVaTen, tilMatKhau);
        AlertDialog.Builder builder = new AlertDialog.Builder(ThuThuChiTietActivity.this);
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
                String tenDangNhap = getText(tilTenDangNhap);
                String hoVaTen = getText(tilHoVaTen);
                String matKhau = getText(tilMatKhau);
                if (!tenDangNhap.isEmpty() && !hoVaTen.isEmpty() && !matKhau.isEmpty()) {
                    if (thuThuDao.updateThuThu(new ThuThu(tenDangNhap, hoVaTen, matKhau))) {
                        setTextAfterUpdate(tenDangNhap, hoVaTen, matKhau);
                        dialog.dismiss();
                        notifiUpdate(tenDangNhap);
                    }
                } else {
                    ToastCustom.error(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin");
                }
            }
        });
    }

    private void setTextAfterUpdate(String tenDangNhap, String hoVaTen, String matKhau) {
        tvMaThuThu.setText(tenDangNhap);
        tvHoTen.setText(hoVaTen);
        tvMatKhau.setText(matKhau);
    }

    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
    }

    private void notifiUpdate(String tenDangNhap) {
        MyNotification.checkSDK(ThuThuChiTietActivity.this);
        MyNotification.getNotification(ThuThuChiTietActivity.this, "Cập nhật thủ thư có ID: " + tenDangNhap + " thành công");
        ToastCustom.successful(getApplicationContext(), "Cập nhật thành công");
    }

    private void deleteThuThu() {
        Intent intent = getIntent();
        String maThuThu = intent.getStringExtra("EXTRA_MATHUTHU");
        if(maThuThu.equals("admin")){
            ToastCustom.successful(ThuThuChiTietActivity.this, "Đây là tài khoản của bạn");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ThuThuChiTietActivity.this);
        builder.setMessage("Bạn có muốn xoá thủ thư " + thuThuDao.getByMaThuThu(maThuThu).getHoTen() + "?");
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (thuThuDao.deleteThuThu(maThuThu)) {
                    notifiDelete();
                    onBackPressed();
                } else {
                    ToastCustom.error(getApplicationContext(), "Xoá không thành công!Thủ thư này đã tồn tại trong phiếu mượn");
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
        ToastCustom.successful(getApplicationContext(), "Xoá thành công");
        MyNotification.checkSDK(ThuThuChiTietActivity.this);
        MyNotification.getNotification(ThuThuChiTietActivity.this, "Xoá thủ thư thành công");
    }


    private void initView() {
        btnBack = findViewById(R.id.imgbBack);
        tvMaThuThu = findViewById(R.id.tv_info_maThuThu);
        tvHoTen = findViewById(R.id.tv_info_tenThuThu);
        tvMatKhau = findViewById(R.id.tv_info_matKhau);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    private void setTextActivity() {
        Intent intent = getIntent();
        tvMaThuThu.setText(intent.getStringExtra("EXTRA_MATHUTHU"));
        tvHoTen.setText(intent.getStringExtra("EXTRA_TENTHUTU"));
        tvMatKhau.setText(intent.getStringExtra("EXTRA_MATKHAU"));
    }

    private void setTextDialog(TextInputLayout tilTenDangNhap, TextInputLayout tilHoVaTen, TextInputLayout tilMatKhau) {
        Objects.requireNonNull(tilTenDangNhap.getEditText()).setText(tvMaThuThu.getText());
        Objects.requireNonNull(tilHoVaTen.getEditText()).setText(tvHoTen.getText());
        Objects.requireNonNull(tilMatKhau.getEditText()).setText(tvMatKhau.getText());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}