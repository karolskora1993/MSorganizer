package com.karolskora.msorgranizer.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.karolskora.msorgranizer.services.NotificationService;

public class PostponedNotification extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, NotificationService.class);
        Log.d(this.getClass().toString(), "service start");
        context.startService(service);
    }
}
