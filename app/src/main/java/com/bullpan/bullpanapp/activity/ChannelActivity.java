package com.bullpan.bullpanapp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bullpan.bullpanapp.R;
import com.bullpan.bullpanapp.adapter.ChattingRoomListAdapter;
import com.bullpan.bullpanapp.utils.SendBirdUtils;
import com.sendbird.android.ChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.model.Channel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChannelActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar toolbar;
    private ImageButton fab;
    private View mHeaderView;
    private ListView mChattingRoomListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ChattingRoomListAdapter mListAdapter;
    private List<Channel> mChannels;
    private ChannelListQuery mChannelListQuery;
    private String appKey;
    private String channelName;
    private String nickname;
    private String uuid;
    private ImageButton mUpBtn;

    private static String TAG = "ys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        Intent intent = getIntent();
        channelName = intent.getStringExtra("channelName");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setPadding(0,0,0,0);
        setSupportActionBar(toolbar);
        setupActionBar();




        initSendBird(intent.getExtras());
        initResource();
        initEvent();
//        fetchChattingRooms();


    }
    private void initSendBird(Bundle extras) {
        appKey = extras.getString("appKey");
        uuid = extras.getString("uuid");
        nickname = extras.getString("nickname");
        Log.d(TAG, "닉네임 여기선 뭐?"+nickname);
        SendBird.init(appKey);
        SendBird.login(uuid, nickname);
    }

    private void fetchChattingRooms() {
        mListAdapter.clear();
        mChannelListQuery =  SendBird.queryChannelList("_"+channelName);
            mChannelListQuery.next(new ChannelListQuery.ChannelListQueryResult() {
                @Override
                public void onResult(Collection<Channel> channels) {
                    mListAdapter.addAll(channels);
                    if (channels.size() <= 0) {
                        Toast.makeText(ChannelActivity.this, "No channels were found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(ChannelActivity.this, "Fetch chatting rooms Error!!", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void loadMoreChattingRooms() {
        if(mChannelListQuery != null && mChannelListQuery.hasNext() && !mChannelListQuery.isLoading()) {
            mChannelListQuery.next(new ChannelListQuery.ChannelListQueryResult() {
                @Override
                public void onResult(Collection<Channel> channels) {
                    mListAdapter.addAll(channels);
                    mListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(ChannelActivity.this, "Fetch chatting rooms Error!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initResource() {
        mUpBtn = (ImageButton) findViewById(R.id.btn_up);
        fab = (ImageButton) findViewById(R.id.fab);
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.list_chatting_rooms_header, null);
        mChattingRoomListView = (ListView)findViewById(android.R.id.list);
        mChannels = new ArrayList<Channel>();
        mListAdapter = new ChattingRoomListAdapter(this, mChannels);
        mChattingRoomListView.setAdapter(mListAdapter);

        TextView programTitle = (TextView)mHeaderView.findViewById(R.id.program_title_label);
//        programTitle.setIncludeFontPadding(false);

        mChattingRoomListView.addHeaderView(mHeaderView, null, false);
//        mChattingRoomListView.setSelectionAfterHeaderView();

        mSwipeRefreshLayout =  (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_red);

        }
    }
    private void initEvent() {
        mUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mChattingRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Channel item = (Channel)parent.getItemAtPosition(position);
                goToChattingActivity(item);
            }
        });
        mChattingRoomListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= (int) (totalItemCount * 0.8f)) {
                    loadMoreChattingRooms();
                }
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                fetchChattingRooms();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    private void goToChattingActivity(Channel item) {
        Intent data = new Intent(ChannelActivity.this, ChattingActivity.class);
        Bundle bundle = SendBirdUtils.makeSendBirdArgs(appKey, uuid, nickname, item.getUrl());
        data.putExtras(bundle);
        startActivity(data);
    }


    @Override
    public void onRefresh() {
        fetchChattingRooms();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
