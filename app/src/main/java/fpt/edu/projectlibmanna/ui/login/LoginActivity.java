package fpt.edu.projectlibmanna.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.projectlibmanna.DAO.ThuThuDao;
import fpt.edu.projectlibmanna.MainActivity;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;
import fpt.edu.projectlibmanna.ui.welcome.WelcomeActivity;

public class LoginActivity extends AppCompatActivity {
    public static final String USER_FILE = "USER_FILE";
    private Button btnSkip, btnSignIn;
    private TextInputLayout tilUserName, tilUserPassword;
    private CheckBox chkRemember;
    private ThuThuDao thuThuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getUser();
        changeText(tilUserName);
        changeText(tilUserPassword);
        thuThuDao = new ThuThuDao(LoginActivity.this);
        btnSkip.setOnClickListener(view -> onBackPressed());
        btnSignIn.setOnClickListener(view -> loginSystem());
    }

    private void initView() {
        btnSkip = findViewById(R.id.btnSkip);
        btnSignIn = findViewById(R.id.btnSignIn);
        tilUserName = findViewById(R.id.tilUserName);
        tilUserPassword = findViewById(R.id.tilUserPassword);
        chkRemember = findViewById(R.id.chkRememberUser);
    }

    @SuppressLint("CommitPrefEdits")
    private void rememberUser(String userName, String userPassword) {
        SharedPreferences sharedPreference = this.getSharedPreferences(USER_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        if (chkRemember.isChecked()) {
            editor.putString("USER_NAME", userName);
            editor.putString("USER_PASSWORD", userPassword);
            editor.putBoolean("STATUS", true);
        } else {
            editor.clear();
        }
        editor.apply();
    }

    private void getUser() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(USER_FILE, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("USER_NAME", "");
        String userPassword = sharedPreferences.getString("USER_PASSWORD", "");
        boolean status = sharedPreferences.getBoolean("STATUS", false);

        Objects.requireNonNull(tilUserName.getEditText()).setText(userName);
        Objects.requireNonNull(tilUserPassword.getEditText()).setText(userPassword);
        chkRemember.setChecked(status);
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

    private void loginSystem() {
        String userName = getText(tilUserName);
        String userPassword = getText(tilUserPassword);
        checkBlank(userName, tilUserName, "Không để trống tên đăng nhập !");
        checkBlank(userPassword, tilUserPassword, "Không để trống mật khuẩu !");
        if (!userName.isEmpty() && !userPassword.isEmpty()) {
            if (thuThuDao.checkLogin(userName, userPassword)) {
                notifiLogin();
                rememberUser(userName, userPassword);
                startScreenMain(userName);
            } else {
                ToastCustom.error(getApplicationContext(), "Sai tên đăng nhập hoặc mật khẩu");
            }
        }
    }

    private void notifiLogin() {
        ToastCustom.successful(getApplicationContext(), "Đăng nhập thành công");
        MyNotification.checkSDK(this);
        MyNotification.getNotification(this,"Đăng nhập hệ thống thành công");
    }

    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
    }

    private void checkBlank(String text, TextInputLayout textInputLayout, String notification) {
        if (text.isEmpty()) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(notification);
        }
    }

    private void startScreenMain(String userName) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("EXTRA_USER", userName);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }
}