package fpt.edu.projectlibmanna.adpater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

import fpt.edu.projectlibmanna.DAO.LoaiSachDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.model.LoaiSach;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class LoaiSachAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<LoaiSach> mListLoaiSach;
    LoaiSachDao loaiSachDao;


    public LoaiSachAdapter(Context mContext, ArrayList<LoaiSach> mListLoaiSach, LoaiSachDao loaiSachDao) {
        this.mContext = mContext;
        this.mListLoaiSach = mListLoaiSach;
        this.loaiSachDao = loaiSachDao;
    }

    public void loadData() {
        mListLoaiSach.clear();
        mListLoaiSach = loaiSachDao.getAll();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListLoaiSach.size();
    }

    @Override
    public Object getItem(int i) {
        return mListLoaiSach.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        ViewOfItem viewOfItem = null;
        if (view == null) {
            view = inflater.inflate(R.layout.layout_item_loaisach, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvTenSach = view.findViewById(R.id.tvTenLoaiSach);
            viewOfItem.tvMaLoai = view.findViewById(R.id.tvMaLoai);
            viewOfItem.imgDelete = view.findViewById(R.id.imgDelete);
            viewOfItem.imgUpdate = view.findViewById(R.id.imgUpdate);
            view.setTag(viewOfItem);
        } else {
            viewOfItem = (ViewOfItem) view.getTag();
        }

        viewOfItem.tvTenSach.setText(mListLoaiSach.get(i).getTenLoai());
        viewOfItem.tvMaLoai.setText("M?? lo???i: "+mListLoaiSach.get(i).getMaLoai());
        viewOfItem.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLoaiSach(i);
            }
        });
        viewOfItem.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLoaiSach(i);
            }
        });
        return view;
    }

    private void updateLoaiSach(int position) {
        View viewDialog = ((Activity) mContext).getLayoutInflater().inflate(R.layout.layout_dialog_sua_loaisach, null);
        Button btnBack = viewDialog.findViewById(R.id.btnBack);
        Button btnUpdate = viewDialog.findViewById(R.id.btnUpdate);
        TextInputLayout tilTenLoaiSach = viewDialog.findViewById(R.id.tilTenLoaiSach);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.show();
        setDataDialog(position, tilTenLoaiSach);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenLoaiSach = Objects.requireNonNull(tilTenLoaiSach.getEditText()).getText().toString();
                if (!tenLoaiSach.isEmpty()) {
                    LoaiSach loaiSach = mListLoaiSach.get(position);
                    loaiSach.setTenLoai(tenLoaiSach);
                    if (loaiSachDao.updateLoaiSach(loaiSach)) {
                        tilTenLoaiSach.getEditText().setText("");
                        dialog.dismiss();
                        notifiUpdate(loaiSach);
                    }
                    loadData();
                } else {
                    ToastCustom.error(mContext, "Vui l??ng nh???p ?????y ????? th??ng tin");
                }
            }
        });
    }

    private void notifiUpdate(LoaiSach loaiSach) {
        MyNotification.checkSDK(mContext);
        MyNotification.getNotification(mContext, "C???p nh???t lo???i s??ch c?? m?? lo???i LS0" + loaiSach.getMaLoai() + " th??nh c??ng");
        ToastCustom.successful(mContext, "C???p nh???t th??nh c??ng");
    }

    private void setDataDialog(int position, TextInputLayout tilTenLoaiSach) {
        Objects.requireNonNull(tilTenLoaiSach.getEditText()).setText(mListLoaiSach.get(position).getTenLoai());
    }

    private void deleteLoaiSach(int position) {
        String maLoai = String.valueOf(mListLoaiSach.get(position).getMaLoai());
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("B???n c?? mu???n xo?? lo???i s??ch " + mListLoaiSach.get(position).getTenLoai() + "?");
        builder.setPositiveButton("Xo??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                if (loaiSachDao.deleteLoaiSach(maLoai)) {
                    notifiDelete();
                }else {
                    ToastCustom.error(mContext, "Xo?? kh??ng th??nh c??ng !\n V?? ???? c?? s??ch thu???c th??? lo???i n??y");
                }
                loadData();
            }
        });
        builder.setNegativeButton("Hu???", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void notifiDelete() {
        MyNotification.checkSDK(mContext);
        MyNotification.getNotification(mContext, "Xo?? lo???i s??ch th??nh c??ng");
        ToastCustom.successful(mContext, "Xo?? th??nh c??ng");
    }

    public static class ViewOfItem {
        TextView  tvTenSach, tvMaLoai;
        ImageView imgDelete, imgUpdate;
    }
}
