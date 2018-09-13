package com.example.aslan.mvpmindorkssample.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String state = extras.getString("extra");
        Log.e("AAA", "onReceive: "+state);
        // updateView(state);// update your textView in the main layout


    }
}
