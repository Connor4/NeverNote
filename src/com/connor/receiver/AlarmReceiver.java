package com.connor.receiver;

import com.connor.activity.EditNote;
import com.connor.application.NeverNoteApplication;
import com.connor.nevernote.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver
{
	private NeverNoteApplication mApplication;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Log.d("TAG", "onReceive");
		addNotification(mApplication.getContext());
	}

	private void addNotification(Context context)
	{
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher,
				"This is ticker text", System.currentTimeMillis());
		Intent intent = new Intent(context, EditNote.class);
		PendingIntent pi = PendingIntent.getActivity(context,
				0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		notification.setLatestEventInfo(context, "你的笔记提示","具体内容进去慢慢找吧", pi);
		long[] vibrates = { 0, 1000, 1000, 1000 };
		notification.vibrate = vibrates;
		manager.notify(1, notification);
	}

}
