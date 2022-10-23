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

import fpt.edu.projectlibmanna.DAO.ThanhVienDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.ui.thanhvien.ThanhVienChiTietActivity;
import fpt.edu.projectlibmanna.model.ThanhVien;

public class ThanhVienAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ThanhVien> mList;
    ThanhVienDao thanhVienDao;

    public ThanhVienAdapter(Context mContext, ArrayList<ThanhVien> mList, ThanhVienDao thanhVienDao) {
        this.mContext = mContext;
        this.mList = mList;
        this.thanhVienDao = thanhVienDao;
    }

    public void loadData(){
        mList.clear();
        mList = thanhVienDao.getAll();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
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
        if(view == null){
            view = inflater.inflate(R.layout.layout_item_thanhvien, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvHoten = view.findViewById(R.id.tvHoten);
            viewOfItem.tvChiTiet = view.findViewById(R.id.tvChiTiet);
            view.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) view.getTag();
        }

        viewOfItem.tvHoten.setText(mList.get(i).getHoTen());
        viewOfItem.tvChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ThanhVienChiTietActivity.class);
                intent.putExtra("EXTRA_MATHANHVIEN", String.valueOf(mList.get(i).getMaThanhVien()));
                intent.putExtra("EXTRA_TENTHANHVIEN", mList.get(i).getHoTen());
                intent.putExtra("EXTRA_NAMSINH", mList.get(i).getNamSinh());
                mContext.startActivity(intent);
            }
        });
        return view;

    }

    public static class ViewOfItem{
        TextView tvHoten, tvChiTiet;
    }
}
