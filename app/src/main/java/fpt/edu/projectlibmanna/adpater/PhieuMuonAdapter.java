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

import fpt.edu.projectlibmanna.DAO.PhieuMuonDao;
import fpt.edu.projectlibmanna.DAO.ThanhVienDao;
import fpt.edu.projectlibmanna.ui.phieumuon.PhieuMuonChiTietActivity;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.model.PhieuMuon;

public class PhieuMuonAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<PhieuMuon> mListPhieuMuon;
    PhieuMuonDao phieuMuonDao;

    public PhieuMuonAdapter(Context mContext, ArrayList<PhieuMuon> mListPhieuMuon, PhieuMuonDao phieuMuonDao) {
        this.mContext = mContext;
        this.mListPhieuMuon = mListPhieuMuon;
        this.phieuMuonDao = phieuMuonDao;
    }

    public void loadData() {
        mListPhieuMuon.clear();
        mListPhieuMuon = phieuMuonDao.getAll();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListPhieuMuon.size();
    }

    @Override
    public Object getItem(int i) {
        return mListPhieuMuon.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThanhVienDao thanhVienDao = new ThanhVienDao(mContext);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        ViewOfItem viewOfItem = null;
        if (view == null) {
            view = inflater.inflate(R.layout.layout_item_phieu_muon, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvMaPhieuMuon = view.findViewById(R.id.maPhieuMuon);
            viewOfItem.tvThanhVienMuon = view.findViewById(R.id.tenThanhVienMuon);
            viewOfItem.tvChiTiet = view.findViewById(R.id.tvChiTiet);

            view.setTag(viewOfItem);
        } else {
            viewOfItem = (ViewOfItem) view.getTag();
        }
        viewOfItem.tvMaPhieuMuon.setText("PM0"+mListPhieuMuon.get(i).getMaPhieuMuon());
        viewOfItem.tvThanhVienMuon.setText(thanhVienDao.getByMaThanhVien(String.valueOf(mListPhieuMuon.get(i).getMaThanhVien())).getHoTen());
        viewOfItem.tvChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PhieuMuonChiTietActivity.class);
                intent.putExtra("EXTRA_MAPHIEUMUON", String.valueOf(mListPhieuMuon.get(i).getMaPhieuMuon()));
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    public static class ViewOfItem {
        TextView tvMaPhieuMuon,tvThanhVienMuon, tvChiTiet;
    }
}
