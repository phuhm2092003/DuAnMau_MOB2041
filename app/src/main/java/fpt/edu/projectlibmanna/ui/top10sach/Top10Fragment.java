package fpt.edu.projectlibmanna.ui.top10sach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import fpt.edu.projectlibmanna.DAO.PhieuMuonDao;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.adpater.Top10Adapter;


public class Top10Fragment extends Fragment {
    PhieuMuonDao phieuMuonDao;
    ListView lvTop10Sach;
    Top10Adapter adapterTop10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top10, container, false);
        lvTop10Sach = view.findViewById(R.id.lvTop10Sach);
        phieuMuonDao = new PhieuMuonDao(getContext());
        loadData();
        return view;
    }

    private void loadData() {
        adapterTop10 = new Top10Adapter(getContext(), phieuMuonDao.getTop(getContext()), phieuMuonDao);
        lvTop10Sach.setAdapter(adapterTop10);
    }
}