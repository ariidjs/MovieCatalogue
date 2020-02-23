package com.rizkhan.favoriteapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rizkhan.favoriteapp.adapter.SectionsPagerAdapter;
import com.rizkhan.favoriteapp.fragment.FavoriteMovieFragment;
import com.rizkhan.favoriteapp.fragment.FavoriteTvFragment;

public class MainActivity extends AppCompatActivity {

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        setUpViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionsPagerAdapter sectionPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(new FavoriteMovieFragment(), getResources().getString(R.string.title_tab1));
        sectionPagerAdapter.addFragment(new FavoriteTvFragment(), getResources().getString(R.string.title_tab2));
        viewPager.setAdapter(sectionPagerAdapter);
    }
}