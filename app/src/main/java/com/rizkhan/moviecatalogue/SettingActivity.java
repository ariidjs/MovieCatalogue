package com.rizkhan.moviecatalogue;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rizkhan.moviecatalogue.broadcast.AlarmReceiver;
import com.rizkhan.moviecatalogue.reminder.ReminderPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SettingActivity extends AppCompatActivity {

    public static final String KEY_FIELD_RELEASE = "checkedRelease";
    public static final String KEY_FIELD_DAILY = "checkedDaily";
    public static final String KEY_HEADER_RELEASE = "releaseReminder";
    public static final String KEY_HEADER_DAILY = "dailyReminder";
    @BindView(R.id.daily_reminder)
    public Switch dailyReminder;
    @BindView(R.id.release_Reminder)
    public Switch releaseReminder;
    public ReminderPreference reminderPreference;
    public SharedPreferences sRelease, sDaily;
    public SharedPreferences.Editor editorRelease, editorDaily;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        alarmReceiver = new AlarmReceiver();
        reminderPreference = new ReminderPreference(this);
        loadPreference();
    }

    private void releaseReminderOn() {
        String time = "08:00";
        reminderPreference.setReminderReleaseTime(time);
        alarmReceiver.setOneTimeAlarm(this, time, AlarmReceiver.ID_RELEASE);
        Toast.makeText(SettingActivity.this, "Release reminder Enabled", Toast.LENGTH_SHORT).show();
    }

    private void releaseReminderOff() {
        alarmReceiver.cancelAlarm(getApplicationContext(), AlarmReceiver.ID_RELEASE);
    }

    private void dailyReminderOn() {
        String time = "07:00";
        reminderPreference.setReminderDailyTime(time);
        alarmReceiver.setOneTimeAlarm(this, time, AlarmReceiver.ID_DAILY);
        Toast.makeText(SettingActivity.this, "Daily reminder Enabled", Toast.LENGTH_SHORT).show();

    }

    private void dailyReminderOff() {
        alarmReceiver.cancelAlarm(this, AlarmReceiver.ID_DAILY);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @OnCheckedChanged(R.id.daily_reminder)
    public void setDailyReminder(boolean isChecked) {
        editorDaily = sDaily.edit();
        if (isChecked) {
            editorDaily.putBoolean(KEY_FIELD_DAILY, true);
            editorDaily.commit();
            dailyReminderOn();
        } else {
            editorDaily.putBoolean(KEY_FIELD_DAILY, false);
            editorDaily.commit();
            dailyReminderOff();
        }
    }

    @OnCheckedChanged(R.id.release_Reminder)
    public void setReleaseReminder(boolean isChecked) {
        editorRelease = sRelease.edit();
        if (isChecked) {
            editorRelease.putBoolean(KEY_FIELD_RELEASE, true);
            editorRelease.commit();
            releaseReminderOn();
        } else {
            editorRelease.putBoolean(KEY_FIELD_RELEASE, false);
            editorRelease.commit();
            releaseReminderOff();
        }
    }

    private void loadPreference() {
        sRelease = getSharedPreferences(KEY_HEADER_RELEASE, MODE_PRIVATE);
        sDaily = getSharedPreferences(KEY_HEADER_DAILY, MODE_PRIVATE);
        boolean checkUpcomingReminder = sRelease.getBoolean(KEY_FIELD_RELEASE, false);
        releaseReminder.setChecked(checkUpcomingReminder);
        boolean checkDailyReminder = sDaily.getBoolean(KEY_FIELD_DAILY, false);
        dailyReminder.setChecked(checkDailyReminder);
    }
}
