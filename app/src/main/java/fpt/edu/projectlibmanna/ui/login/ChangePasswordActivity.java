package fpt.edu.projectlibmanna.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.projectlibmanna.DAO.ThuThuDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.model.ThuThu;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class ChangePasswordActivity extends AppCompatActivity {
    private Button btnBack, btnChangePassword;
    private TextInputLayout tilPasswordOld, tilPasswordNew, tilPasswordRepeat;
    private ThuThuDao thuThuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        changeTextEditText();
        thuThuDao = new ThuThuDao(this);
        btnBack.setOnClickListener(view -> onBackPressed());
        btnChangePassword.setOnClickListener(view -> changePassword());
    }

    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        tilPasswordOld = findViewById(R.id.tilPasswordOld);
        tilPasswordNew = findViewById(R.id.tilPasswordNew);
        tilPasswordRepeat = findViewById(R.id.tilPasswordRepeat);
    }

    private void changePassword() {
        String passwordOld = getText(tilPasswordOld);
        String passwordNew = getText(tilPasswordNew);
        String passwordRepeat = getText(tilPasswordRepeat);
        checkBlank(passwordOld, tilPasswordOld);
        checkBlank(passwordNew, tilPasswordNew);
        checkBlank(passwordRepeat, tilPasswordRepeat);
        if (!passwordOld.isEmpty() && !passwordNew.isEmpty() && !passwordRepeat.isEmpty()) {
            ThuThu thuthu = getObject();
            setErEnablePassOld(passwordOld, thuthu);
            setErEnablePassRepeat(passwordNew, passwordRepeat);
            if (isPasswordOld(passwordOld, thuthu) && isPasswordRepeat(passwordNew, passwordRepeat)) {
                thuthu.setMatKhau(passwordNew);
                if (thuThuDao.updateThuThu(thuthu)) {
                    clearForm();
                    notifiChangePass();
                    ToastCustom.successful(getApplicationContext(), "?????i m???t kh???u th??nh c??ng");
                } else {
                    ToastCustom.error(getApplicationContext(), "?????i m???t kh???u kh??ng th??nh c??ng");
                }
            }
        }
    }

    private void notifiChangePass() {
        MyNotification.checkSDK(this);
        MyNotification.getNotification(this, "?????i m???t kh???u th??nh c??ng");
    }

    @NonNull
    private String getText(TextInputLayout til) {
        return Objects.requireNonNull(til.getEditText()).getText().toString();
    }

    private void setErEnablePassRepeat(String passwordNew, String passwordRepeat) {
        /*
        * N???u password repeat != password new
        * -> set l???i cho textInputLayout
        * */
        if (isPasswordRepeat(passwordNew, passwordRepeat)) {
            tilPasswordRepeat.setErrorEnabled(false);
        } else {
            tilPasswordRepeat.setErrorEnabled(true);
            tilPasswordRepeat.setError("Nh???p m???t kh???u m???i kh??ng tr??ng kh???p !");
        }
    }


    private void setErEnablePassOld(String passwordOld, ThuThu thuthu) {
        /*
         * N???u m???t kh???u c?? kh??ng tr??ng v???i m???t kh???u c???a th??? th??
         * -> set l???i cho textInputLayout
         * */
        if (isPasswordOld(passwordOld, thuthu)) {
            tilPasswordOld.setErrorEnabled(false);
        } else {
            tilPasswordOld.setErrorEnabled(true);
            tilPasswordOld.setError("Nh???p sai m???t kh???u c?? !");
        }
    }

    private boolean isPasswordRepeat(String passwordNew, String passwordRepeat) {
        return passwordNew.equals(passwordRepeat);
    }

    private boolean isPasswordOld(String passwordOld, ThuThu thuthu) {
        return thuthu.getMatKhau().equals(passwordOld);
    }


    private void changeTextEditText() {
        changeText(tilPasswordOld);
        changeText(tilPasswordNew);
        changeText(tilPasswordRepeat);
    }


    private void checkBlank(String str, TextInputLayout til) {
        if (str.isEmpty()) {
            til.setErrorEnabled(true);
            til.setError("Vui l??ng kh??g ????? tr???ng !");
        }
    }

    private void changeText(TextInputLayout til) {
        Objects.requireNonNull(til.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!til.getEditText().getText().toString().isEmpty()) {
                    til.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private ThuThu getObject() {
        Intent intent = getIntent();
        String userName = intent.getStringExtra("EXTRA_USER");
        return thuThuDao.getByMaThuThu(userName);
    }

    private void clearForm() {
        Objects.requireNonNull(tilPasswordOld.getEditText()).setText("");
        Objects.requireNonNull(tilPasswordNew.getEditText()).setText("");
        Objects.requireNonNull(tilPasswordRepeat.getEditText()).setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}