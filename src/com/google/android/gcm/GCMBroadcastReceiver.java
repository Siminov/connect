package com.google.android.gcm;

import siminov.connect.notification.NotificationService;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GCMBroadcastReceiver extends BroadcastReceiver {

    public final void onReceive(Context context, Intent intent) {

    	String receiverClassName = getClass().getName();
        if (!receiverClassName.equals(GCMBroadcastReceiver.class.getName())) {
            GCMRegistrar.setRetryReceiverClassName(receiverClassName);
        }
        
        String className = NotificationService.class.getName();
        		
        // Delegates to the application-specific intent service.
        GCMBaseIntentService.runIntentInService(context, intent, className);
        setResult(Activity.RESULT_OK, null /* data */, null /* extra */);
    }
}
