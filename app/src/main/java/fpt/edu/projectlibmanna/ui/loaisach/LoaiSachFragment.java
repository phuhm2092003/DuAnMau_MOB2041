package fpt.edu.projectlibmanna.ui.loaisach;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import fpt.edu.projectlibmanna.DAO.LoaiSachDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.adpater.LoaiSachAdapter;
import fpt.edu.projectlibmanna.model.LoaiSach;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class LoaiSachFragment extends Fragment {
    private ListView lvLoaiSach;
    private FloatingActionButton fabAdd;
    private LoaiSachDao loaiSachDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        initView(view);
        loaiSachDao = new LoaiSachDao(getContext());
        loadData();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewLoaiSach();
            }
        });
        return view;
    }

    private void addNewLoaiSach() {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_them_loaisach, null);

        Button btnBack = viewDialog.findViewById(R.id.btnBack);
        Button btnAdd = viewDialog.findViewById(R.id.btnAdd);
        TextInputLayout tilTenLoaiSach = viewDialog.findViewById(R.id.tilTenLoaiSach);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.show();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenLoaiSach = Objects.requireNonNull(tilTenLoaiSach.getEditText()).getText().toString();
                if (!tenLoaiSach.isEmpty()) {
                    if (loaiSachDao.insertLoaiSach(new LoaiSach(tenLoaiSach))) {
                        tilTenLoaiSach.getEditText().setText("");
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
        MyNotification.getNotification(getContext(), "Thêm loại sách thành công");
        ToastCustom.successful(getContext(), "Thêm thành công");
    }

    private void initView(View view) {
        lvLoaiSach = view.findViewById(R.id.lvLoaiSach);
        fabAdd = view.findViewById(R.id.fabAdd);
    }

    private void loadData() {
        LoaiSachAdapter adapterLoaiSach = new LoaiSachAdapter(getContext(), loaiSachDao.getAll(), loaiSachDao);
        lvLoaiSach.setAdapter(adapterLoaiSach);
    }
}