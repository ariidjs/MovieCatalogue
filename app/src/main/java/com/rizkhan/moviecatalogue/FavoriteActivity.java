package com.rizkhan.moviecatalogue;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rizkhan.moviecatalogue.adapter.SectionPagerAdapter;
import com.rizkhan.moviecatalogue.fragments.FavoriteMovieFragment;
import com.rizkhan.moviecatalogue.fragments.FavoriteTvFragment;
import com.rizkhan.moviecatalogue.fragments.ListMovieFragment;

import java.util.Locale;

public class FavoriteActivity extends AppCompatActivity {

    private static final String MY_LANG = "my_lang";
    private static final String SETTINGS = "settings";
    SectionPagerAdapter sectionPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = findViewById(R.id.toolbar_fav);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_activity_favorite);
        }

        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager_fav);
        TabLayout tabs = findViewById(R.id.tabs_fav);
        tabs.setupWithViewPager(viewPager);

        setUpViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(new FavoriteMovieFragment(), getResources().getString(R.string.title_tab1));
        sectionPagerAdapter.addFragment(new FavoriteTvFragment(), getResources().getString(R.string.title_tab2));
        viewPager.setAdapter(sectionPagerAdapter);
    }

    private void showLanguageDialog() {
        final String[] listLang = {getResources().getString(R.string.english), getResources().getString(R.string.indonesia)};
        AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteActivity.this);
        builder.setTitle(getResources().getString(R.string.choose_lang));
        builder.setSingleChoiceItems(listLang, -1, (DialogInterface dialog, int which) ->{
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
        getMenuInflater().inflate(R.menu.option_menu_fav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu1) {
            showLanguageDialog();
        }
        return super.onOptionsItemSelected(item);
    }
}