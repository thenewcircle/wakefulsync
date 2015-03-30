package com.example.android.serversync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServerSyncService extends Service {
    /*
     * Singleton instance of the adapter. This adapter can be returned from multiple
     * wrapper service calls. We cannot create a new instance every time.
     */
    private static ServerSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        super.onCreate();

        if (sSyncAdapter == null) {
            sSyncAdapter = new ServerSyncAdapter(getApplicationContext(), true);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
