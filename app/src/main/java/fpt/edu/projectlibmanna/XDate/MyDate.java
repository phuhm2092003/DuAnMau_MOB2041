package fpt.edu.projectlibmanna.XDate;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public static String toString(Date date) {
        return sdf.format(date);
    }

    public static Date toDate(String strDate) throws ParseException {
        return sdf.parse(strDate);
    }
}
