package uk.co.alt236.btlescan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by User on 19-03-2018.
 */

public class MyStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ServiceUtil.scheduleJob(context);
    }
}

