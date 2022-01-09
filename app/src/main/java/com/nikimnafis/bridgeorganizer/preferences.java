package com.nikimnafis.bridgeorganizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class preferences {
    private static final String DATA_LOGIN = "login_status";

    private static SharedPreferences getSharedReferences (Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataLogin (Context context, boolean status) {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putBoolean(DATA_LOGIN, status);
        editor.apply();
    }

    public static boolean getDataLogin (Context context) {
        return getSharedReferences(context).getBoolean(DATA_LOGIN, false);
    }

    public static void  clearData (Context context) {
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.remove(DATA_LOGIN);
        editor.apply();
    }


}
