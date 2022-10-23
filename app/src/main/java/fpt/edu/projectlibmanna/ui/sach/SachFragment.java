package fpt.edu.projectlibmanna.ui.sach;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.projectlibmanna.DAO.LoaiSachDao;
import fpt.edu.projectlibmanna.DAO.SachDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.adpater.SachAdapter;
import fpt.edu.projectlibmanna.adpater.SpinerLoaiSachAdapter;
import fpt.edu.projectlibmanna.model.LoaiSach;
import fpt.edu.projectlibmanna.model.Sach;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class SachFragment extends Fragment {
    private ListView lvSach;
    private FloatingActionButton fabAdd;
    private SachDao sachDao;
    private LoaiSachDao loaiSachDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        initView(view);
        initDao();
        loadData();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewSach();
            }
        });
        return view;
    }

    private void initView(View view) {
        lvSach = view.findViewById(R.id.lvSach);
        fabAdd = view.findViewById(R.id.fabAdd);
    }

    private void initDao() {
        sachDao = new SachDao(getContext());
        loaiSachDao = new LoaiSachDao(getContext());
    }

    private void loadData() {
        SachAdapter adapterSach = new SachAdapter(getContext(), sachDao.getAll(), sachDao);
        lvSach.setAdapter(adapterSach);
    }

    private void addNewSach() {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_them_sach, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        Button btnCancel = viewDialog.findViewById(R.id.btnBack);
        Button btnAdd = viewDialog.findViewById(R.id.btnAdd);
        TextInputLayout tilTenSach = viewDialog.findViewById(R.id.tilTenSach);
        TextInputLayout tilGiaThue = viewDialog.findViewById(R.id.tilGiaThue);
        Spinner spinner = viewDialog.findViewById(R.id.spinnerLoaiSach);
        fillSpinner(spinner);
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenSach = getText(tilTenSach);
                String tienThue = getText(tilGiaThue);
                int maLoai = getMaLoai(spinner);
                if (!tenSach.isEmpty() && !tienThue.isEmpty()) {
                    if (sachDao.insertSach(new Sach(tenSach, Integer.parseInt(tienThue), maLoai))) {
                        clearFormDialog(tilTenSach, tilGiaThue);
                        loadData();
                        notifiAdd();
                    }
                } else {
                    ToastCustom.error(getContext(), "Vui lòng nhập đầy đủ thông tin");
                }
            }
        });
    }

    private void notifiAdd() {
        MyNotification.checkSDK(getContext());
        MyNotification.getNotification(getContext(), "Thêm sách thành công");
        ToastCustom.successful(getContext(), "Thêm thành công!");
    }

    private int getMaLoai(Spinner spinner) {
        LoaiSach loaiSach = (LoaiSach) spinner.getSelectedItem();
        return loaiSach.getMaLoai();
    }

    @NonNull
    private String getText(TextInputLayout tilTenSach) {
        return Objects.requireNonNull(tilTenSach.getEditText()).getText().toString();
    }

    private void clearFormDialog(TextInputLayout tilTenSach, TextInputLayout tilGiaThue) {
        Objects.requireNonNull(tilTenSach.getEditText()).setText("");
        Objects.requireNonNull(tilGiaThue.getEditText()).setText("");
    }

    private void fillSpinner(Spinner spinner) {
        SpinerLoaiSachAdapter arrayAdapter = new SpinerLoaiSachAdapter(getContext(), loaiSachDao.getAll());
        spinner.setAdapter(arrayAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}