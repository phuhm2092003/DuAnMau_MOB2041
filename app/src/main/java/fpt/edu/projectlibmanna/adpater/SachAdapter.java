package fpt.edu.projectlibmanna.adpater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpt.edu.projectlibmanna.DAO.SachDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.model.Sach;
import fpt.edu.projectlibmanna.ui.sach.SachChiTietActivity;

public class SachAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Sach> mlistSach;
    SachDao sachDao;


    public SachAdapter(Context mContext, ArrayList<Sach> mlistSach, SachDao sachDao) {
        this.mContext = mContext;
        this.mlistSach = mlistSach;
        this.sachDao = sachDao;
    }

    public void loadData() {
        mlistSach.clear();
        mlistSach = sachDao.getAll();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mlistSach.size();
    }

    @Override
    public Object getItem(int i) {
        return mlistSach.get(i);
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
            view = inflater.inflate(R.layout.layout_item_sach, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvTenSach = view.findViewById(R.id.tvTenSach);
            viewOfItem.tvChiTiet = view.findViewById(R.id.tvChiTiet);
            view.setTag(viewOfItem);
        } else {
            viewOfItem = (ViewOfItem) view.getTag();
        }
        viewOfItem.tvTenSach.setText(mlistSach.get(i).getTenSach());
        viewOfItem.tvChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SachChiTietActivity.class);
                intent.putExtra("EXTRA_MASACH", String.valueOf(mlistSach.get(i).getMaSach()));
                intent.putExtra("EXTRA_TENSACH", mlistSach.get(i).getTenSach());
                intent.putExtra("EXTRA_GIATHUE", String.valueOf(mlistSach.get(i).getGiaThue()));
                intent.putExtra("EXTRA_MALOAI", String.valueOf(mlistSach.get(i).getMaLoai()));
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    public static class ViewOfItem {
        TextView tvTenSach, tvChiTiet;
    }
}
