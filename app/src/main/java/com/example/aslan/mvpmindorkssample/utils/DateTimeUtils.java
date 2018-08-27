package com.example.aslan.mvpmindorkssample.utils;

import android.text.format.DateUtils;

public class DateTimeUtils {

    public static String formatRelativeTime(long time){
        return DateUtils.getRelativeTimeSpanString(time*1000,
                System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
    }
}
