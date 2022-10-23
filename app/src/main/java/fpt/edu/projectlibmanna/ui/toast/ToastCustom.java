package fpt.edu.projectlibmanna.ui.toast;

import android.content.Context;
import android.widget.Toast;

import fpt.edu.projectlibmanna.R;
import io.github.muddz.styleabletoast.StyleableToast;

public class ToastCustom {
    public static void  successful(Context context, String notification){
        StyleableToast.makeText(context, notification + " !", Toast.LENGTH_LONG, R.style.ToastOk).show();
    }

    public static void  error(Context context, String notification){
        StyleableToast.makeText(context, notification + " !", Toast.LENGTH_LONG, R.style.ToastError).show();
    }
}

