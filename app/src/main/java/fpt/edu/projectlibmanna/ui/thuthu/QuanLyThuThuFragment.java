package fpt.edu.projectlibmanna.ui.thuthu;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import fpt.edu.projectlibmanna.DAO.ThuThuDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.adpater.ThuThuAdapter;
import fpt.edu.projectlibmanna.model.ThuThu;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;


public class QuanLyThuThuFragment extends Fragment {
    private ThuThuDao thuThuDao;
    private ArrayList<ThuThu> listThuThu;
    private ListView lvThuThu;
    private FloatingActionButton fabAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_thu_thu, container, false);
        initView(view);
        thuThuDao = new ThuThuDao(getContext());
        loadData();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewThuThu();
            }
        });
        return view;
    }
    private void initView(View view) {
        lvThuThu = view.findViewById(R.id.lvThuThu);
        fabAdd = view.findViewById(R.id.fabAddThuThu);
    }

    public void loadData() {
        listThuThu = thuThuDao.getAll();
        ThuThuAdapter thuThuAdapter = new ThuThuAdapter(getContext(), listThuThu, thuThuDao);
        lvThuThu.setAdapter(thuThuAdapter);
    }
    private void addNewThuThu() {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_them_thuthu, null);

        Button btnCancle = viewDialog.findViewById(R.id.btnBack);
        Button btnAdd = viewDialog.findViewById(R.id.btnAdd);
        TextInputLayout tilUserName = viewDialog.findViewById(R.id.tilUserName);
        TextInputLayout tilFullName = viewDialog.findViewById(R.id.tilFullName);
        TextInputLayout tilUserPassword = viewDialog.findViewById(R.id.tilUserPassword);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = Objects.requireNonNull(tilUserName.getEditText()).getText().toString();
                String fullName = Objects.requireNonNull(tilFullName.getEditText()).getText().toString();
                String userPassword = Objects.requireNonNull(tilUserPassword.getEditText()).getText().toString();
                if (!userName.isEmpty() && !fullName.isEmpty() && !userPassword.isEmpty()) {
                    if (!isExist(userName)) {
                        if (thuThuDao.insertThuThu(new ThuThu(userName, fullName, userPassword))) {
                            clearText(tilUserName, tilUserPassword, tilFullName);
                            notifiAdd();
                        }
                        loadData();
                    } else {
                        ToastCustom.error(getContext(), "Mã thủ thư tồn tại");
                    }
                } else {
                    ToastCustom.error(getContext(), "Vui lòng nhập đầy đủ thông tin");
                }

            }
        });
    }

    private void notifiAdd() {
        MyNotification.checkSDK(getContext());
        MyNotification.getNotification(getContext(),"Thêm mới thủ thư thành công");
        ToastCustom.successful(getContext(), "Thêm thành công");
    }

    private void clearText(TextInputLayout tilUserName, TextInputLayout tilUserPassword, TextInputLayout tilFullName) {
        Objects.requireNonNull(tilUserName.getEditText()).setText("");
        Objects.requireNonNull(tilUserPassword.getEditText()).setText("");
        Objects.requireNonNull(tilFullName.getEditText()).setText("");
    }

    private boolean isExist(String userName) {
        for (int position = 0; position < listThuThu.size(); position++) {
            if (userName.equals(listThuThu.get(position).getMaThuThu())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}