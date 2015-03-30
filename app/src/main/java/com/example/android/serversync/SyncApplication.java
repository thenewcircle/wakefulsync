package com.example.android.serversync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ContentResolver;
import android.os.Build;

public class SyncApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //Add the dummy account
            AccountManager manager = AccountManager.get(this);
            final Account account = new Account(DummyAccountAuthenticator.ACCOUNT_NAME,
                    DummyAccountAuthenticator.ACCOUNT_TYPE);
            manager.addAccountExplicitly(account, null, null);

            //Configure the sync settings
            ContentResolver.setIsSyncable(account, StubContentProvider.AUTHORITY, 1);
            //Let the adapter sync automatically on network state changes
            ContentResolver.setSyncAutomatically(account, StubContentProvider.AUTHORITY, true);
        }
    }
}
