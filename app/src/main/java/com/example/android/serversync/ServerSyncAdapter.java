package com.example.android.serversync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

public class ServerSyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = ServerSyncAdapter.class.getSimpleName();

    public ServerSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public ServerSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        /*
         * This is a background thread. Blocking work can safely be performed here.
         * SyncAdapter will hold a wakelock for you while inside this method.
         */
        Log.d(TAG, "SyncAdapter doing important background work");

    }
}
