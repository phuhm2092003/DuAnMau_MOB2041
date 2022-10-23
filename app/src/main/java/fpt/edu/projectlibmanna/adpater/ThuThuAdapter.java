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

import fpt.edu.projectlibmanna.DAO.ThuThuDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.ui.thuthu.ThuThuChiTietActivity;
import fpt.edu.projectlibmanna.model.ThuThu;

public class ThuThuAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThuThu> list;
    ThuThuDao thuThuDao;
    public ThuThuAdapter(Context context, ArrayList<ThuThu> list, ThuThuDao thuThuDao) {
        this.context = context;
        this.list = list;
        this.thuThuDao = thuThuDao;
    }
    public void loadData(){
        list.clear();
        list = thuThuDao.getAll();
        notifyDataSetChanged();
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

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewOfItem viewOfItem = null;
        if(view == null){
            view = inflater.inflate(R.layout.layout_item_thuthu, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvHoten = view.findViewById(R.id.tvHoten);
            viewOfItem.tvChiTiet = view.findViewById(R.id.tvChiTiet);
            view.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) view.getTag();
        }

        viewOfItem.tvHoten.setText(list.get(i).getHoTen());
        viewOfItem.tvChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThuThuChiTietActivity.class);
                intent.putExtra("EXTRA_MATHUTHU", list.get(i).getMaThuThu());
                intent.putExtra("EXTRA_TENTHUTU", list.get(i).getHoTen());
                intent.putExtra("EXTRA_MATKHAU", list.get(i).getMatKhau());
                context.startActivity(intent);
            }
        });
        return view;

    }

    public static class ViewOfItem{
        TextView tvHoten, tvChiTiet;
    }
}
