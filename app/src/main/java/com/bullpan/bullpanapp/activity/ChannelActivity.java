package com.bullpan.bullpanapp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.bullpan.bullpanapp.R;
import com.bullpan.bullpanapp.adapter.ChannelListAdapter;
import com.bullpan.bullpanapp.model.Channel;
import com.bullpan.bullpanapp.model.Program;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChannelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private View mHeaderView;
    private ListView mChattingListView;
    private ChannelListAdapter mListAdapter;
    private List<Channel> mChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        Intent intent = getIntent();
        String channelName = intent.getStringExtra("channelName");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(channelName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initResource();
        initEvent();
        fetchChannels();

    }
    private void fetchChannels() {
        mChannels.clear();
        mChannels.add(new Channel("KBS1",
                R.drawable.kbs1_logo,
                new Program("애국가",
                        "",
                        new Date(2016, 2, 4, 0, 0),
                        new Date(2016, 2, 4, 0, 1)),
                0));
        mChannels.add(new Channel("KBS2",
                R.drawable.kbs2_logo,
                new Program("정오뉴스",
                        "",
                        new Date(2016, 2, 4, 0, 0),
                        new Date(2016, 2, 4, 0, 1)),
                0));
        mChannels.add(new Channel("SBS",
                R.drawable.sbs_logo,
                new Program("런닝맨",
                        "",
                        new Date(2016, 2, 4, 0, 0),
                        new Date(2016, 2, 4, 0, 1)),
                0));
        mChannels.add(new Channel("MBC",
                R.drawable.mbc_logo,
                new Program("그녀는 예뻤다",
                        "",
                        new Date(2016, 2, 4, 0, 0),
                        new Date(2016, 2, 4, 0, 1)),
                0));
        mListAdapter.notifyDataSetChanged();
    }
    private void initResource() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.list_chattings_header, null);
        mChattingListView = (ListView)findViewById(android.R.id.list);
        mChannels = new ArrayList<Channel>();
        mListAdapter = new ChannelListAdapter(this, mChannels);
        mChattingListView.setAdapter(mListAdapter);

        mChattingListView.addHeaderView(mHeaderView);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
    private void initEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
