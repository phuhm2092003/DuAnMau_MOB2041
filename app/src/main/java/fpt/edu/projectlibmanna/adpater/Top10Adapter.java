package fpt.edu.projectlibmanna.adpater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpt.edu.projectlibmanna.DAO.PhieuMuonDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.model.Top10;

public class Top10Adapter extends BaseAdapter {
    Context mContext;
    ArrayList<Top10> mListTop10;
    PhieuMuonDao phieuMuonDao;

    public Top10Adapter(Context mContext, ArrayList<Top10> mListTop10, PhieuMuonDao phieuMuonDao) {
        this.mContext = mContext;
        this.mListTop10 = mListTop10;
        this.phieuMuonDao = phieuMuonDao;
    }

    public void loadData(){
        mListTop10.clear();
        mListTop10 = phieuMuonDao.getTop(mContext);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mListTop10.size();
    }

    @Override
    public Object getItem(int i) {
        return mListTop10.get(i);
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
            view = inflater.inflate(R.layout.layout_item_top10, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvSoThuTu = view.findViewById(R.id.tvSoThuTu);
            viewOfItem.tvTenSach = view.findViewById(R.id.tvTenSach);
            viewOfItem.tvSoLuotMuon = view.findViewById(R.id.tvSoLuotMuon);
            view.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) view.getTag();
        }
        int soThuTu = i + 1;
        if(soThuTu <= 9){
            viewOfItem.tvSoThuTu.setText("O" + soThuTu);
        }else {
            viewOfItem.tvSoThuTu.setText(String.valueOf(soThuTu));
        }
        viewOfItem.tvTenSach.setText(mListTop10.get(i).getTenSach());
        viewOfItem.tvSoLuotMuon.setText("Số lượt mượn: " + mListTop10.get(i).getSoLuong());
        return view;

    }

    public static class ViewOfItem{
        TextView tvSoThuTu, tvTenSach, tvSoLuotMuon;
    }
}
