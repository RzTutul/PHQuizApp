package com.example.quizapp.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Global {
    public static ProgressDialog DialogShow(Context context, String msg) {
        ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        return dialog;
    }

    public static class InternetConnection {

        public static boolean checkConnection(Context context) {
            final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connMgr != null) {
                NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

                if (activeNetworkInfo != null) {

                    if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        return true;
                    } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                }
            }
            return false;
        }
    }


}
