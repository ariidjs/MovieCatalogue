package com.rizkhan.moviecatalogue;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rizkhan.moviecatalogue.adapter.SectionPagerAdapter;
import com.rizkhan.moviecatalogue.broadcast.AlarmReceiver;
import com.rizkhan.moviecatalogue.fragments.ListMovieFragment;
import com.rizkhan.moviecatalogue.fragments.ListTvShowFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String MY_LANG = "my_lang";
    private static final String SETTINGS = "settings";
    SectionPagerAdapter sectionPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        setUpViewPager(viewPager);

    }
    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(new ListMovieFragment(), getResources().getString(R.string.title_tab1));
        sectionPagerAdapter.addFragment(new ListTvShowFragment(), getResources().getString(R.string.title_tab2));
        viewPager.setAdapter(sectionPagerAdapter);
    }

    private void showLanguageDialog() {
        final String[] listLang = {getResources().getString(R.string.english), getResources().getString(R.string.indonesia)};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getResources().getString(R.string.choose_lang));
        builder.setSingleChoiceItems(listLang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("en");
                    Bundle bundle = new Bundle();
                    bundle.putString("lang", "en-US");
                    ListMovieFragment fragmentclass = new ListMovieFragment();
                    fragmentclass.setArguments(bundle);
                    recreate();
                } else if (which == 1) {
                    setLocale("in");
                    String lang = "id";
                    Bundle bundle = new Bundle();
                    bundle.putString("lang", lang);
                    ListMovieFragment fragmentclass = new ListMovieFragment();
                    fragmentclass.setArguments(bundle);
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Deprecated
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences(SETTINGS, MODE_PRIVATE).edit();
        editor.putString(MY_LANG, lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences sf = getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE);
        String lang = sf.getString(MY_LANG, "");
        setLocale(lang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu1) {
            showLanguageDialog();
        } else if (id == R.id.menu2) {
            startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
        } else if (id == R.id.menu4) {
            startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.exit_msg);
        builder.setPositiveButton(R.string.exit_yes, (DialogInterface dialogInterface, int i) -> {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
        });
        builder.setNegativeButton(R.string.exit_cancel, (DialogInterface dialogInterface, int i) -> {
        });
        builder.show();
        return true;

    }
}