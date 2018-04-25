package com.example.pranav.bakingtime;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class BakingRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingRemoteViewsFactory(this.getApplicationContext());
    }
}
