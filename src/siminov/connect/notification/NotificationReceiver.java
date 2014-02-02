package siminov.connect.notification;

import siminov.connect.notification.design.IMessage;
import siminov.connect.notification.design.INotification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		
		IMessage message = new Message();
		message.setMessage(intent.getExtras().getString(INotification.MESSAGE));
		
		NotificationManager notificationManager = NotificationManager.getInstance();
		notificationManager.onNotification(message);
	}
}
