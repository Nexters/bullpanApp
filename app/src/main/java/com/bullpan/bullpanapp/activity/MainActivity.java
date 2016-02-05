package com.bullpan.bullpanapp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bullpan.bullpanapp.R;
import com.bullpan.bullpanapp.adapter.ChannelListAdapter;
import com.bullpan.bullpanapp.model.TvChannel;
import com.bullpan.bullpanapp.model.Program;
import com.bullpan.bullpanapp.utils.SendbirdUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener {
        SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton fab;
    ListView mChannelListView;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private ChannelListAdapter mListAdapter;
    private List<TvChannel> mTvChannels;

    private final String appKey = SendbirdUtils.appKey;
    String userID =SendbirdUtils.generateDeviceUUID(MainActivity.this);
    String userName = SendbirdUtils.getUsername(MainActivity.this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initResources();
        initEvents();
    }

    private void initResources() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //우선적으로 처리해야 함
        setSupportActionBar(toolbar);
        setupActionBar();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mChannelListView = (ListView) findViewById(android.R.id.list);
        mTvChannels = new ArrayList<TvChannel>();
        mListAdapter = new ChannelListAdapter(this, mTvChannels);
    }

    private void initEvents() {
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mChannelListView.setAdapter(mListAdapter);
        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         mSwipeRefreshLayout.setRefreshing(true);
                                         fetchChannels();
                                         mSwipeRefreshLayout.setRefreshing(false);
                                     }
                                 }
        );
        mChannelListView.setOnItemClickListener( new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TvChannel item  = (TvChannel) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, ChannelActivity.class);
                Bundle args = SendbirdUtils.makeSendBirdArgs(appKey,userID,userName);
                args.putString("channelName", item.getName());
                intent.putExtras(args);
                startActivity(intent);
            }
        });
    }

    private void fetchChannels() {
        mTvChannels.clear();
        mTvChannels.add(new TvChannel("KBS1",
                            R.drawable.kbs1_logo,
                            new Program("애국가" ,
                                        "",
                                        new Date(2016,2,4,0,0),
                                        new Date(2016,2,4,0,1)),
                            0));
        mTvChannels.add(new TvChannel("KBS2",
                R.drawable.kbs2_logo,
                new Program("정오뉴스" ,
                        "",
                        new Date(2016,2,4,0,0),
                        new Date(2016,2,4,0,1)),
                0));
        mTvChannels.add(new TvChannel("SBS",
                R.drawable.sbs_logo,
                new Program("런닝맨" ,
                        "",
                        new Date(2016,2,4,0,0),
                        new Date(2016,2,4,0,1)),
                0));
        mTvChannels.add(new TvChannel("MBC",
                R.drawable.mbc_logo,
                new Program("그녀는 예뻤다" ,
                        "",
                        new Date(2016,2,4,0,0),
                        new Date(2016,2,4,0,1)),
                0));
        mListAdapter.notifyDataSetChanged();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camara) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        fetchChannels();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
