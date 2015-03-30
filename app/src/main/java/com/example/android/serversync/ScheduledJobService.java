package com.example.android.serversync;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScheduledJobService extends JobService implements Handler.Callback {
    private static final String TAG = ScheduledJobService.class.getSimpleName();

    private HandlerThread mWorkerThread;
    private Handler mJobProcessor;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "ScheduledJobService Created");
        //Create a background worker thread for asynchronous jobs
        mWorkerThread = new HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND);
        mWorkerThread.start();
        mJobProcessor = new Handler(mWorkerThread.getLooper(), this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ScheduledJobService Destroyed");
        //Terminate the worker
        mWorkerThread.quit();
    }

    //The code in this method is triggered on the background worker thread
    @Override
    public boolean handleMessage(Message msg) {
        final JobParameters params = (JobParameters) msg.obj;
        Log.d(TAG, "Executing job " + params.getJobId());

        //Do interesting work
        doWork();

        //Once we have finished the work, notify the framework
        //Next job is scheduled from this point in time
        jobFinished(params, false);

        return true;
    }

    private void doWork() {
        Log.d(TAG, "JobService doing important background work");
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Log.v(TAG, "…not on the main thread");
        }
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Request to start job " + params.getJobId());
        //We can determine if this happened because of a deadline
        if (params.isOverrideDeadlineExpired()) {
            Log.v(TAG, "Deadline has expired");
        }

        /*
         * This is the main thread, blocking work should be deferred.
         * JobScheduler will hold a wakelock until you indicated work is done
         */

        //To simulate a long task queue, we delay execution by 7.5 seconds
        mJobProcessor.sendMessageDelayed(
                Message.obtain(mJobProcessor, 0, params),
                7500
        );

        //Work is not done yet, we'll notify of the completion later
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.w(TAG, "Request to stop job "+params.getJobId());
        //If a cancel request comes in, clear out the pending work
        mJobProcessor.removeMessages(0, params);

        //We are not interested in rescheduling the jobs
        return false;
    }
}
