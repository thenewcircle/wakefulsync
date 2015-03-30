package com.example.android.serversync;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTriggerClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SyncUtil.triggerSyncLollipop(this);
        } else {
            SyncUtil.triggerSyncLegacy();
        }
    }

    public void onCancelClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SyncUtil.cancelSyncLollipop(this);
        } else {
            SyncUtil.cancelSyncLegacy();
        }
    }
}
