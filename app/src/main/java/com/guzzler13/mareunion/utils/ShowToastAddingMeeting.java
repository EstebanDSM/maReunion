package com.guzzler13.mareunion.utils;

import android.content.Context;
import android.widget.Toast;

public class ShowToastAddingMeeting {

    public static void showToast(String string, Context mContext) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(mContext, string, duration);
        toast.show();
    }


}
