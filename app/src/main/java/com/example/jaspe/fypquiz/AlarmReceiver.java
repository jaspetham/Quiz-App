package com.example.jaspe.fypquiz;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.jaspe.fypquiz.Login;
import com.example.jaspe.fypquiz.R;

import static java.security.AccessController.getContext;

public class AlarmReceiver extends ContextWrapper {

    private NotificationManager manager;
    private final String TIME_QUIZ_CHANNEL_ID = "com.example.jaspe.fypquiz.BroadcastReceiver.FYPQuiz";
    private final String TIME_QUIZ_CHANNEL_NAME = "FYP Quiz";

    public AlarmReceiver(Context base){
        super(base);
        createChannels();
    }

    private void createChannels(){
        NotificationChannel FYPChannel = new NotificationChannel(TIME_QUIZ_CHANNEL_ID,TIME_QUIZ_CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
        FYPChannel.enableLights(true);
        FYPChannel.enableVibration(true);
        FYPChannel.setLightColor(Color.GREEN);
        FYPChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        
        getManager().createNotificationChannel(FYPChannel);
    }

    public Notification.Builder getFYPChannelNotification() {
        return new Notification.Builder(getApplicationContext(), TIME_QUIZ_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Time Quiz")
                .setContentText("Hey! Try to play this new question!")
                .setAutoCancel(true);
    }

    public NotificationManager getManager() {
        if (manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }
}
