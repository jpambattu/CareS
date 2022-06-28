package com.cea.cares;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.cea.cares.ui.activity.ActivityFragment;


public class Background extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // broadcast received here
        ActivityFragment activityFragment = new ActivityFragment();
        activityFragment.addNotification(arg0);

    }


}
