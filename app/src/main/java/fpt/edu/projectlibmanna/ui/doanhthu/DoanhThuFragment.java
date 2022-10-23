package fpt.edu.projectlibmanna.ui.doanhthu;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fpt.edu.projectlibmanna.DAO.PhieuMuonDao;
import fpt.edu.projectlibmanna.XDate.MyDate;
import fpt.edu.projectlibmanna.R;
import fpt.edu.projectlibmanna.notification.MyNotification;
import fpt.edu.projectlibmanna.ui.toast.ToastCustom;

public class DoanhThuFragment extends Fragment {
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    EditText edtTuNgay, edtDenNgay;
    Button btnTinhDoanhThu;
    PhieuMuonDao phieuMuonDao;
    TextView tvTuNgay, tvDenNgay, tvDoanhThu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        initView(view);
        onClickShowDatePicker(edtTuNgay);
        onClickShowDatePicker(edtDenNgay);
        phieuMuonDao = new PhieuMuonDao(getContext());
        btnTinhDoanhThu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                caculatorDoanhThu();
            }
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void caculatorDoanhThu() {
        boolean isErrorFormatDate = false;
        String tuNgay = edtTuNgay.getText().toString();
        String denNgay = edtDenNgay.getText().toString();
        try {
            Date mDate = MyDate.toDate(tuNgay);
        } catch (ParseException e) {
            e.printStackTrace();
            isErrorFormatDate = true;
        }
        try {
            Date mDate = MyDate.toDate(denNgay);
        } catch (ParseException e) {
            e.printStackTrace();
            isErrorFormatDate = true;
        }

        if (tuNgay.isEmpty() || denNgay.isEmpty()) {
            ToastCustom.error(getContext(), "Vui lòng nhập đầy đủ thông tin");
        } else {
            if (!isErrorFormatDate) {
                tvTuNgay.setText(tuNgay);
                tvDenNgay.setText(denNgay);
                int doanhThu = phieuMuonDao.getDoanhThu(tuNgay, denNgay);
                tvDoanhThu.setText(doanhThu + "VND");
                notifiDoanhThu(tuNgay, denNgay, doanhThu);
            } else {
                ToastCustom.error(getContext(), "Sai định dạng ngày tháng");
            }
        }
    }

    private void notifiDoanhThu(String tuNgay, String denNgay, int doanhThu) {
        MyNotification.checkSDK(getContext());
        MyNotification.getNotification(getContext(), "Doanh thu từ " + tuNgay + " đến " + denNgay + " là " + doanhThu + "VND");
        ToastCustom.successful(getContext(), "Hoàn thành tính doanh thu");
    }

    private void initView(View view) {
        edtTuNgay = view.findViewById(R.id.edtTuNgay);
        edtDenNgay = view.findViewById(R.id.edtDenNgay);
        btnTinhDoanhThu = view.findViewById(R.id.btnTinhDoanhThu);
        tvTuNgay = view.findViewById(R.id.tvTuNgay);
        tvDenNgay = view.findViewById(R.id.tvDenNgay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
    }

    private void onClickShowDatePicker(EditText edt) {
        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        edt.setText(MyDate.toString(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

}