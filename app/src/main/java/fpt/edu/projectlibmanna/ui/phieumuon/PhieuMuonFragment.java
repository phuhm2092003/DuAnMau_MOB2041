package fpt.edu.projectlibmanna.ui.phieumuon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpt.edu.projectlibmanna.DAO.PhieuMuonDao;
import fpt.edu.projectlibmanna.MainActivity;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.adpater.PhieuMuonAdapter;
import fpt.edu.projectlibmanna.model.PhieuMuon;


public class PhieuMuonFragment extends Fragment {
    private PhieuMuonDao phieuMuonDao;
    private ListView lvPhieuMuon;
    private FloatingActionButton fabAdd;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        initView(view);
        phieuMuonDao = new PhieuMuonDao(getContext());
        loadData();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPhieuMuon();
            }
        });
        return view;
    }

    private void addNewPhieuMuon() {
        mainActivity = (MainActivity) getActivity();
        Intent intent = new Intent(getContext(), ThemPhieuMuonActivity.class);
        intent.putExtra("EXTRA_MATHUTHU", mainActivity.getKey_user());
        startActivity(intent);
    }

    private void initView(View view) {
        lvPhieuMuon = view.findViewById(R.id.lvPhieuMuon);
        fabAdd = view.findViewById(R.id.fabAdd);

    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        ArrayList<PhieuMuon> list = phieuMuonDao.getAll();
        PhieuMuonAdapter adapterPhieuMuon = new PhieuMuonAdapter(getContext(), list, phieuMuonDao);
        lvPhieuMuon.setAdapter(adapterPhieuMuon);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}