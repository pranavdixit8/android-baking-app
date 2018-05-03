package com.example.pranav.bakingtime;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class BakingRemoteViewsService extends RemoteViewsService {

    public static final String TAG = BakingRemoteViewsService.class.getSimpleName();


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int index = intent.getIntExtra(BakingAppWidget.INDEX_KEY,0);
        return new BakingRemoteViewsFactory(this.getApplicationContext(), index);
    }
}
