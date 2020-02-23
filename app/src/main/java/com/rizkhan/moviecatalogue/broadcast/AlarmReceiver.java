package com.rizkhan.moviecatalogue.broadcast;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.rizkhan.moviecatalogue.BuildConfig;
import com.rizkhan.moviecatalogue.MainActivity;
import com.rizkhan.moviecatalogue.R;
import com.rizkhan.moviecatalogue.model.MovieResponse;
import com.rizkhan.moviecatalogue.model.Movies;
import com.rizkhan.moviecatalogue.remote.Client;
import com.rizkhan.moviecatalogue.remote.Server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmReceiver extends BroadcastReceiver {
    public static final int ID_DAILY = 100;
    public static final int ID_RELEASE = 101;
    private static final String EXTRA_CODE = "requestCode";
    private final static String DATE_FORMAT = "yyyy-MM-dd";
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "movie catalogue";

    public AlarmReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        int code = intent.getIntExtra(EXTRA_CODE, 0);
        if (code == ID_RELEASE) {
            Date date = new Date();
            String tanggal = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date);
            Server server = Client.getClient().create(Server.class);
            Call<MovieResponse> response = server.getUpComingMovies(BuildConfig.GoogleSecAPIKEY, tanggal, tanggal);
            response.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            List<Movies> list = response.body().getResults();
                            for (int i = 0; i < list.size(); i++) {
                                String title = list.get(i).getTitle();
                                showAlarmNotification(context, title, context.getString(R.string.message_release), AlarmReceiver.ID_RELEASE);
                            }

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(context, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            showAlarmNotification(context, context.getString(R.string.app_name), context.getString(R.string.message_daily), code);
        }
    }

    private void showAlarmNotification(Context context, String title, String message, int id) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_favorite_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setAutoCancel(true)
                .setSound(sound);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }
        Notification notification = builder.build();
        if (notificationManager != null)
            notificationManager.notify(id, notification);
    }

    public void setOneTimeAlarm(Context context, String time, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_CODE, requestCode);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        // Check if the Calendar time is in the past
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1); // it will tell to run to next day
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

}
