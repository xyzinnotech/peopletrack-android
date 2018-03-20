package uk.co.alt236.btlescan;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

/**
 * Created by User on 19-03-2018.
 */

class ServiceUtil {

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, FirebaseDatasendServie.class);
        JobInfo.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new JobInfo.Builder(0, serviceComponent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setMinimumLatency(1 * 1000); // wait at least
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setOverrideDeadline(3 * 1000); // maximum delay
        }
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            jobScheduler = context.getSystemService(JobScheduler.class);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobScheduler.schedule(builder.build());
        }
    }

}
