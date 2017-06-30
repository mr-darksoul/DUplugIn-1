package com.cocodev.TheDuChronicle;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.cocodev.TheDuChronicle.EH.EventsHolder;
import com.cocodev.TheDuChronicle.articles.ArticleHolder;
import com.cocodev.TheDuChronicle.notices.Notices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.leakcanary.RefWatcher;

import java.util.Iterator;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final int REQUEST_CODE_SETTINGS_ACTIVITY = 1001;
    String[] submenus = {"Articles","Notices","Events"};
    private String CURRENT_FRAGMENT = "currentFragment";
    private final int HOME_FRAGMENT =0;
    private final int NOTICE_BOARD_FRAGMENT =1;
    private Menu menu;
    public static RefWatcher getRefWatcher(Context context) {
        MainActivity application = (MainActivity) context.getApplicationContext();
        return application.refWatcher;
    }


    private RefWatcher refWatcher;
    public static final String TAG = "check";
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Normal app init code...
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menu = navigationView.getMenu();
        for (final String subMenu:submenus
             ) {

            final SubMenu  sM = menu.addSubMenu(subMenu);

            databaseReference = FirebaseDatabase.getInstance().getReference().child("Categories").child(subMenu);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                    while(iterator.hasNext()){
                        final String SubMenuItemTitle=iterator.next().getKey();
                        MenuItem menuItem = sM.add(SubMenuItemTitle);
                        menuItem.setCheckable(true);
                        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {

                                if(subMenu.equals("Articles")){
                                    ArticleHolder articleHolder =  ArticleHolder.newInstance(menuItem.getTitle().toString());

                                    getSupportFragmentManager().beginTransaction().replace(
                                            R.id.fragment_layout,
                                            articleHolder,
                                            null).commit();
                                }else
                                if(subMenu.equals("Notices")){
                                    Notices notices =  Notices.newInstance(menuItem.getTitle().toString());
                                    getSupportFragmentManager().beginTransaction().replace(
                                            R.id.fragment_layout,
                                            notices,
                                            null).commit();
                                } else
                                if(subMenu.equals("Events")){
                                    EventsHolder events =  EventsHolder.newInstance(menuItem.getTitle().toString());
                                    getSupportFragmentManager().beginTransaction().replace(
                                            R.id.fragment_layout,
                                            events,
                                            null).commit();
                                }
                                getSupportActionBar().setTitle(menuItem.getTitle().toString()+" "+subMenu);
                                return false;
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        if(getSupportFragmentManager().findFragmentById(R.id.fragment_layout)==null){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_layout,
                    new Home()
            ).commit();
            navigationView.setCheckedItem(R.id.home);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SA.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            return true;
        }

        return true;

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
            Home home = new Home();
            Bundle bundle = new Bundle();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout,home,null).commit();

        } else if (id == R.id.notices) {
            NoticeBoard noticeBoard = new NoticeBoard();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, noticeBoard, null).commit();

        }else if (id == R.id.events) {
            Events events = new Events();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, events, null).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
