package com.example.quizapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyPreferences {
    private static String MY_PREF = "Quiz";

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(MY_PREF, 0);
    }

    private static SharedPreferences.Editor getPreferenceForEdit(Context context) {
        return context.getSharedPreferences(MY_PREF, 0).edit();
    }


    public static void setHighScore(Context context, int score) {
        if (getPreference(context).getInt("SCORE", 0) < score) {
            getPreferenceForEdit(context).putInt("SCORE", score).apply();
        }

    }

    public static int getHighScore(Context context) {
        return getPreference(context).getInt("SCORE", 0);
    }


}
