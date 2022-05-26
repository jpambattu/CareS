package com.cea.cares;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cea.cares.ui.activity.ActivityFragment;


public class Background extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // For our recurring task, we'll just display a message
        Toast.makeText(arg0, "Background process is triggered every minute!!! ", Toast.LENGTH_LONG).show();


        ActivityFragment activityFragment = new ActivityFragment();
        activityFragment.addNotification(arg0);

    }


}
