package uk.co.alt236.btlescan;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import uk.co.alt236.btlescan.ui.main.MainActivity;

/**
 * Created by User on 19-03-2018.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FirebaseDatasendServie extends JobService {
    private static final String TAG = "SyncService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), MainActivity.class);
        getApplicationContext().startService(service);
        ServiceUtil.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}
