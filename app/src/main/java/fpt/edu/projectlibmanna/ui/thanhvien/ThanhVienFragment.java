package fpt.edu.projectlibmanna.ui.thanhvien;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import fpt.edu.projectlibmanna.DAO.ThanhVienDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.adpater.ThanhVienAdapter;
import fpt.edu.projectlibmanna.model.ThanhVien;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;


public class ThanhVienFragment extends Fragment {
    private ListView lvThanhVien;
    private ThanhVienDao thanhVienDao;
    private FloatingActionButton fabAddThanhVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        initView(view);
        thanhVienDao = new ThanhVienDao(getContext());
        loadData();
        fabAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewThanhVien();
            }
        });
        return view;
    }

    private void addNewThanhVien() {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_them_thanhvien, null);
        Button btnCancle = viewDialog.findViewById(R.id.btnBackScreenMain);
        Button btnAdd = viewDialog.findViewById(R.id.btnUpdateThanhVien);
        TextInputLayout tilHoVaTen = viewDialog.findViewById(R.id.tilHoVaTen);
        TextInputLayout tilNamSinh = viewDialog.findViewById(R.id.tilNamSinh);
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
                String hoVaTen = getText(tilHoVaTen);
                String namSinh = getText(tilNamSinh);
                if (!hoVaTen.isEmpty() && !namSinh.isEmpty()) {
                    if (thanhVienDao.insertThanhVien(new ThanhVien(hoVaTen, namSinh))) {
                        clearText(tilHoVaTen, tilNamSinh);
                        notifiAdd();
                    }
                    loadData();
                } else {
                    ToastCustom.error(getContext(), "Vui lòng nhập đầy đủ thông tin");
                }
            }
        });
    }

    @NonNull
    private String getText(TextInputLayout tilHoVaTen) {
        return Objects.requireNonNull(tilHoVaTen.getEditText()).getText().toString();
    }

    private void notifiAdd() {
        MyNotification.checkSDK(getContext());
        MyNotification.getNotification(getContext(), "Thêm thành viên thành công");
        ToastCustom.successful(getContext(), "Thêm thành công");
    }

    private void clearText(TextInputLayout tilHoVaTen, TextInputLayout tilNamSinh) {
        Objects.requireNonNull(tilHoVaTen.getEditText()).setText("");
        Objects.requireNonNull(tilNamSinh.getEditText()).setText("");
    }


    private void loadData() {
        ArrayList<ThanhVien> mList = thanhVienDao.getAll();
        ThanhVienAdapter thanhVienAdapter = new ThanhVienAdapter(getContext(), mList, thanhVienDao);
        lvThanhVien.setAdapter(thanhVienAdapter);
    }

    private void initView(View view) {
        lvThanhVien = view.findViewById(R.id.lvThanhVien);
        fabAddThanhVien = view.findViewById(R.id.fabAdd);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}