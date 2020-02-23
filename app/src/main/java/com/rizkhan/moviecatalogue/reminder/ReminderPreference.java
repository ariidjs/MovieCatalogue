package com.rizkhan.moviecatalogue.reminder;

import android.content.Context;
import android.content.SharedPreferences;

public class ReminderPreference {
    public static final String KEY_RELEASE = "ReleaseReminder";
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public final static String PREF_NAME = "reminderPreferences";
    public final static String KEY_DAILY = "DailyReminder";
    public ReminderPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setReminderReleaseTime(String time){
        editor.putString(KEY_RELEASE,time);
        editor.commit();
    }
    public void setReminderDailyTime(String time){
        editor.putString(KEY_DAILY,time);
        editor.commit();
    }
}
