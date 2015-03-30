package com.example.android.serversync;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

public class SyncUtil {

    private static final int JOB_ID = 42;

    public static void triggerSyncLegacy() {
        /*
         * Extras key the sync operation with the framework. Modify
         * the extras to schedule a parallel sync with the same adapter
         */
        Bundle extras = new Bundle();

        //SyncAdapter operations are implicitly assumed to require the network
        ContentResolver.requestSync(DummyAccountAuthenticator.getDummyAccount(),
                StubContentProvider.AUTHORITY, extras);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void triggerSyncLollipop(Context context) {
        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo job = new JobInfo.Builder(JOB_ID,
                new ComponentName(context.getPackageName(),
                        ScheduledJobService.class.getName()))
                //We must be connected to the network
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                //Only run this job if connected to stable power
                .setRequiresCharging(true)
                //At all costs, run this after 45 seconds
                .setOverrideDeadline(45000)
                .build();

        jobScheduler.schedule(job);
    }

    public static void cancelSyncLegacy() {
        ContentResolver.cancelSync(DummyAccountAuthenticator.getDummyAccount(),
                StubContentProvider.AUTHORITY);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void cancelSyncLollipop(Context context) {
        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(JOB_ID);
    }
}
