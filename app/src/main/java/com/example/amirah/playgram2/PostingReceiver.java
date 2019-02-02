package com.example.amirah.playgram2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.amirah.playgram2.service.PostingService;

public class PostingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, PostingService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
