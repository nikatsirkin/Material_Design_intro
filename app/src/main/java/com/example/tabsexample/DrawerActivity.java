package com.example.tabsexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton fab;
    FragmentManager fm;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        toolbar = findViewById(R.id.main_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fab = findViewById(R.id.fab_add_issue);
        fm = getSupportFragmentManager();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = fm.beginTransaction();
                AddIssueFragment asf = new AddIssueFragment();
                ft.replace(R.id.frag_container, asf);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openNav, R.string.closeNav
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_hardware:
                        //Toast.makeText(DrawerActivity.this, "hardware", Toast.LENGTH_LONG).show()
                        HardwareFragment hf = new HardwareFragment();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frag_container, hf);
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.nav_software:
                        //Toast.makeText(DrawerActivity.this, "software", Toast.LENGTH_LONG).show();
                        fm = getSupportFragmentManager();
                        SoftwareFragment sf = new SoftwareFragment();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frag_container, sf);
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.nav_lecturer:
                        fm = getSupportFragmentManager();
                        MapFragment mf = new MapFragment();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frag_container, mf);
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                }
                return false;
            }
        });
    }
}
