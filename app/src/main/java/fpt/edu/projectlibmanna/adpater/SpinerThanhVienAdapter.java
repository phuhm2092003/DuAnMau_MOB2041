package fpt.edu.projectlibmanna.adpater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.model.ThanhVien;

public class SpinerThanhVienAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ThanhVien> list;

    public SpinerThanhVienAdapter(Context mContext, ArrayList<ThanhVien> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
        ViewOfItem viewOfItem = null;
        if (view == null){
            view = layoutInflater.inflate(R.layout.layout_item_spiner, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvTen = view.findViewById(R.id.tvTen);
            view.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) view.getTag();
        }
        viewOfItem.tvTen.setText(list.get(i).getHoTen());
        return view;
    }

    public static class ViewOfItem {
        TextView tvTen;
    }
}
