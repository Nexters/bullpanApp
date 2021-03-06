package com.bullpan.bullpanapp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bullpan.bullpanapp.R;
import com.bullpan.bullpanapp.adapter.ChannelListAdapter;
import com.bullpan.bullpanapp.model.TvChannel;
import com.bullpan.bullpanapp.model.Program;
import com.bullpan.bullpanapp.utils.SendBirdUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener {
        SwipeRefreshLayout mSwipeRefreshLayout;
    ImageButton fab;
    ListView mChannelListView;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageButton btnUser;
    private ChannelListAdapter mListAdapter;
    private List<TvChannel> mTvChannels;

    private final String appKey = SendBirdUtils.appKey;
    String userID = SendBirdUtils.generateDeviceUUID(MainActivity.this);
    String userName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initResources();
        initEvents();
    }

    private void initResources() {
        userName = SendBirdUtils.getUsername(MainActivity.this);
        fab = (ImageButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //우선적으로 처리해야 함
        setSupportActionBar(toolbar);
        setupActionBar();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_drawer_red);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mChannelListView = (ListView) findViewById(android.R.id.list);

        mTvChannels = new ArrayList<TvChannel>();
        mListAdapter = new ChannelListAdapter(this, mTvChannels);

    }

    private void initEvents() {
        drawer.setDrawerListener(toggle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        toggle.syncState();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(navigationView);
                SearchView sv = (SearchView) navigationView.findViewById(R.id.channelSearchView);
                sv.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.showSoftInput(v, 0);
                            }

                        }
                    }
                });
                sv.requestFocusFromTouch();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
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
                Bundle args = SendBirdUtils.makeSendBirdArgs(appKey, userID, userName);
                args.putString("channelName", item.getName());
                intent.putExtras(args);
                startActivity(intent);
            }
        });
    }

    private void fetchChannels() {
        mTvChannels.clear();
        mTvChannels.add(new TvChannel("MBC",
                R.drawable.mbc_logo,
                new Program("그녀는 예뻣다 : 최종화",
                        "",
                        new Date(2016, 2, 4, 12, 0),
                        new Date(2016, 2, 4, 13, 0)),
                9));
        mTvChannels.add(new TvChannel("JTBC",
                R.drawable.jtbc_logo,
                new Program("치즈인더트랩 : 2화" ,
                        "",
                        new Date(2016,2,4,12,0),
                        new Date(2016,2,4,13,0)),
                4));
        mTvChannels.add(new TvChannel("TVN",
                R.drawable.tvn_logo,
                new Program("냉장고를 부탁해 : 23화" ,
                        "",
                        new Date(2016,2,4,12,0),
                        new Date(2016,2,4,13,0)),
                3));
        mTvChannels.add(new TvChannel("CNTN",
                R.drawable.cntn_logo,
                new Program("바람의 화원 : 4화" ,
                        "",
                        new Date(2016,2,4,12,0),
                        new Date(2016,2,4,13,0)),
                0));
        mTvChannels.add(new TvChannel("SBS",
                R.drawable.sbs_logo,
                new Program("꽃가족 : 1화 (첫방송)" ,
                        "",
                        new Date(2016,2,4,12,0),
                        new Date(2016,2,4,13,0)),
                0));
        mTvChannels.add(new TvChannel("KBS2",
                R.drawable.kbs2_logo,
                new Program("고양이를 부탁해 : 2화" ,
                        "",
                        new Date(2016,2,4,12,0),
                        new Date(2016,2,4,13,0)),
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
        if (id == R.id.action_mypage) {
            startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
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
