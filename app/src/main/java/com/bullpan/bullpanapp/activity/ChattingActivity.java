package com.bullpan.bullpanapp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bullpan.bullpanapp.R;
import com.bullpan.bullpanapp.adapter.SendBirdChatAdapter;
import com.bullpan.bullpanapp.utils.SendBirdUtils;
import com.sendbird.android.MessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdEventHandler;
import com.sendbird.android.SendBirdFileUploadEventHandler;
import com.sendbird.android.model.BroadcastMessage;
import com.sendbird.android.model.Channel;
import com.sendbird.android.model.FileInfo;
import com.sendbird.android.model.FileLink;
import com.sendbird.android.model.Message;
import com.sendbird.android.model.MessageModel;
import com.sendbird.android.model.MessagingChannel;
import com.sendbird.android.model.ReadStatus;
import com.sendbird.android.model.SystemMessage;
import com.sendbird.android.model.TypeStatus;

import java.io.File;
import java.util.List;

public class ChattingActivity extends AppCompatActivity {

    public static final String TAG = "ys";

    private SendBirdChatFragment mSendBirdChatFragment;
    private SendBirdChatAdapter mSendBirdChatAdapter;
    private String uuid;
    private String appKey;
    private String nickname;
    private String mChannelUrl;

    //private ImageButton mBtnClose;
    //private ImageButton mBtnSettings;
    //private TextView mTxtChannelUrl;
    //private View mTopBarContainer;
    //private View mSettingsContainer;
    //private Button mBtnLeave;
    //private boolean mDoNotDisconnect;

    private Button mBtnBack;
    private Button mBtnExport;
    private TextView mTxtChannelName;

    private void initSendBird(Bundle extras) {
        appKey = extras.getString("appKey");
        uuid = extras.getString("uuid");
        nickname = extras.getString("nickname");
        SendBird.init(appKey);
        SendBird.login(uuid, nickname);
        mChannelUrl = extras.getString("channelUrl");

        SendBird.init(appKey);
        SendBird.login(uuid, nickname);
        SendBird.setEventHandler(new SendBirdEventHandler() {
            @Override
            public void onConnect(Channel channel) {
                mTxtChannelName.setText("#" + channel.getUrlWithoutAppPrefix());
            }

            @Override
            public void onError(int code) {
                Log.e("SendBird", "Error code: " + code);
            }

            @Override
            public void onChannelLeft(Channel channel) {
            }

            @Override
            public void onMessageReceived(Message message) {
                mSendBirdChatAdapter.addMessageModel(message);
            }

            @Override
            public void onSystemMessageReceived(SystemMessage systemMessage) {
                mSendBirdChatAdapter.addMessageModel(systemMessage);
            }

            @Override
            public void onBroadcastMessageReceived(BroadcastMessage broadcastMessage) {
                mSendBirdChatAdapter.addMessageModel(broadcastMessage);
            }

            @Override
            public void onFileReceived(FileLink fileLink) {
                mSendBirdChatAdapter.addMessageModel(fileLink);
            }

            @Override
            public void onAllDataReceived(SendBird.SendBirdDataType type, int count) {
                mSendBirdChatAdapter.notifyDataSetChanged();
                mSendBirdChatFragment.mListView.setSelection(mSendBirdChatAdapter.getCount());

            }

            @Override
            public void onMessageDelivery(boolean sent, String message, String data, String id) {
                if (!sent) {
                    mSendBirdChatFragment.mEtxtMessage.setText(message);
                }
            }

            @Override
            public void onReadReceived(ReadStatus readStatus) {

            }

            @Override
            public void onTypeStartReceived(TypeStatus typeStatus) {

            }

            @Override
            public void onTypeEndReceived(TypeStatus typeStatus) {

            }

            @Override
            public void onMessagingStarted(MessagingChannel messagingChannel) {

            }

            @Override
            public void onMessagingUpdated(MessagingChannel messagingChannel) {

            }

            @Override
            public void onMessagingEnded(MessagingChannel messagingChannel) {

            }

            @Override
            public void onAllMessagingEnded() {

            }

            @Override
            public void onMessagingHidden(MessagingChannel messagingChannel) {

            }

            @Override
            public void onAllMessagingHidden() {

            }

        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check
        setContentView(R.layout.activity_chatting);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, mSendBirdChatFragment)
                .commit();
        initUIComponents();
        initSendBird(getIntent().getExtras());

        mSendBirdChatFragment = new SendBirdChatFragment();
        mSendBirdChatAdapter = new SendBirdChatAdapter(this);
        mSendBirdChatFragment.setSendBirdChatAdapter(mSendBirdChatAdapter);
        mSendBirdChatAdapter.setNickName(nickname);
        mSendBirdChatFragment.setSendBirdChatHandler(new SendBirdChatFragment.SendBirdChatHandler() {

            @Override
            public void onChannelListClicked() {
//                Intent intent = new Intent(SendBirdChatActivity.this, SendBirdChannelListActivity.class);
//                intent.putExtras(getIntent().getExtras());
//                startActivityForResult(intent, REQUEST_CHANNEL_LIST);
            }
        });


        SendBird.queryMessageList(mChannelUrl).prev(Long.MAX_VALUE, 50, new MessageListQuery.MessageListQueryResult() {
            @Override
            public void onResult(List<MessageModel> messageModels) {
                for (MessageModel model : messageModels) {
                    mSendBirdChatAdapter.addMessageModel(model);
                }

                mSendBirdChatAdapter.notifyDataSetChanged();
                mSendBirdChatFragment.mListView.setSelection(mSendBirdChatAdapter.getCount());
                SendBird.join(mChannelUrl);
                SendBird.connect(mSendBirdChatAdapter.getMaxMessageTimestamp());
            }

            @Override
            public void onError(Exception e) {

            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }
    public static class SendBirdChatFragment extends Fragment {
        private static final int REQUEST_PICK_IMAGE = 100;

        private ListView mListView;
        private SendBirdChatAdapter mAdapter;
        private EditText mEtxtMessage;
        private ImageButton mBtnSendOn;
        private ImageButton mBtnSendOff;
        private SendBirdChatHandler mHandler;

        public static interface SendBirdChatHandler {
            public void onChannelListClicked();
        }

        public void setSendBirdChatHandler(SendBirdChatHandler handler) {
            mHandler = handler;
        }

        public SendBirdChatFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.content_chatting, container, false);
            initUIComponents(rootView);
            return rootView;
        }


        private void initUIComponents(View rootView) {
            mListView = (ListView)rootView.findViewById(R.id.list);
            //turnOffListViewDecoration(mListView);
            mListView.setAdapter(mAdapter);

            mBtnSendOn = (ImageButton)rootView.findViewById(R.id.btn_send_on);
            mBtnSendOff = (ImageButton)rootView.findViewById(R.id.btn_send_off);
            mEtxtMessage = (EditText)rootView.findViewById(R.id.msg);

            mBtnSendOff.setEnabled(true);
            mBtnSendOn.setEnabled(true);

            mBtnSendOn.setVisibility(View.INVISIBLE);
            mBtnSendOff.setVisibility(View.VISIBLE);

            mBtnSendOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    send();
                }
            });


            /*
            mBtnChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mHandler != null) {
                        mHandler.onChannelListClicked();
                    }
                }
            });


            mBtnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);
                }
            });
*/
            mEtxtMessage.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            send();
                        }
                        return true; // Do not hide keyboard.
                    }
                    return false;
                }
            });
            mEtxtMessage.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
            mEtxtMessage.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        mBtnSendOn.setVisibility(View.VISIBLE);
                        mBtnSendOff.setVisibility(View.INVISIBLE);
                    } else {
                        mBtnSendOn.setVisibility(View.INVISIBLE);
                        mBtnSendOff.setVisibility(View.VISIBLE);

                    }
                }
            });
            mListView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //SendBirdUtils.hideKeyboard(getActivity());
                    return false;
                }
            });
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (view.getFirstVisiblePosition() == 0 && view.getChildCount() > 0 && view.getChildAt(0).getTop() == 0) {
                            SendBird.queryMessageList(SendBird.getChannelUrl()).prev(mAdapter.getMinMessageTimestamp(), 30, new MessageListQuery.MessageListQueryResult() {
                                @Override
                                public void onResult(List<MessageModel> messageModels) {
                                    for (MessageModel model : messageModels) {
                                        mAdapter.addMessageModel(model);
                                    }

                                    mAdapter.notifyDataSetChanged();
                                    mListView.setSelection(messageModels.size());
                                }

                                @Override
                                public void onError(Exception e) {
                                }
                            });
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                }
            });
        }
        /*
        private void showUploadProgress(boolean tf) {
            if(tf) {
                mBtnUpload.setEnabled(false);
                mBtnUpload.setVisibility(View.INVISIBLE);
                mProgressBtnUpload.setVisibility(View.VISIBLE);
            } else {
                mBtnUpload.setEnabled(true);
                mBtnUpload.setVisibility(View.VISIBLE);
                mProgressBtnUpload.setVisibility(View.GONE);
            }
        }
        */

        //얘 뭐하는거징
        /*
        private void turnOffListViewDecoration(ListView listView) {
            listView.setDivider(null);
            listView.setDividerHeight(0);
            listView.setHorizontalFadingEdgeEnabled(false);
            listView.setVerticalFadingEdgeEnabled(false);
            listView.setHorizontalScrollBarEnabled(false);
            listView.setVerticalScrollBarEnabled(true);
            listView.setSelector(new ColorDrawable(0x00ffffff));
            listView.setCacheColorHint(0x00000000); // For Gingerbread scrolling bug fix
        }
        */
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == Activity.RESULT_OK) {
                //if(requestCode == REQUEST_PICK_IMAGE && data != null && data.getData() != null) {
                //    upload(data.getData());
                //}
            }
        }

        private void send() {
            SendBird.send(mEtxtMessage.getText().toString());
            mEtxtMessage.setText("");

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                SendBirdUtils.hideKeyboard(getActivity());
            }
        }

        /*
        private void upload(Uri uri) {
            try {
                Cursor cursor = getActivity().getContentResolver().query(uri,
                        new String[] {
                                MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.MIME_TYPE,
                                MediaStore.Images.Media.SIZE,
                        },
                        null, null, null);
                cursor.moveToFirst();
                final String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                final String mime = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
                final int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                cursor.close();

                if(path == null) {
                    Toast.makeText(getActivity(), "Uploading file must be located in local storage.", Toast.LENGTH_LONG).show();
                } else {
                    showUploadProgress(true);
                    SendBird.uploadFile(new File(path), mime, size, "", new SendBirdFileUploadEventHandler() {
                        @Override
                        public void onUpload(FileInfo fileInfo, Exception e) {
                            showUploadProgress(false);
                            if (e != null) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Fail to upload the file.", Toast.LENGTH_LONG).show();
                                return;
                            }

                            SendBird.sendFile(fileInfo);
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Fail to upload the file.", Toast.LENGTH_LONG).show();
            }
        }
        */

        public void setSendBirdChatAdapter(SendBirdChatAdapter adapter) {
            mAdapter = adapter;
            if(mListView != null) {
                mListView.setAdapter(adapter);
            }
        }
    }


    private void initUIComponents() {
        mBtnBack = (Button)findViewById(R.id.btn_back);
        mBtnExport = (Button)findViewById(R.id.btn_export);
        mTxtChannelName = (TextView)findViewById(R.id.tv_name);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendBird.leave(SendBird.getChannelUrl());
                finish();
                //startActivity(new Intent(ChattingActivity.this, ChannelActivity.class));
            }
        });


        /*
        mTopBarContainer = findViewById(R.id.top_bar_container);
        mTxtChannelUrl = (TextView)findViewById(R.id.txt_channel_url);

        mSettingsContainer = findViewById(R.id.settings_container);
        mSettingsContainer.setVisibility(View.GONE);

        mBtnLeave = (Button)findViewById(R.id.btn_leave);

        mBtnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingsContainer.setVisibility(View.GONE);
                SendBird.leave(SendBird.getChannelUrl());
                finish();
            }
        });

        mBtnClose = (ImageButton)findViewById(R.id.btn_close);
        mBtnSettings = (ImageButton)findViewById(R.id.btn_settings);

        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSettingsContainer.getVisibility() != View.VISIBLE) {
                    mSettingsContainer.setVisibility(View.VISIBLE);
                } else {
                    mSettingsContainer.setVisibility(View.GONE);
                }
            }
        });
        */
        //resizeMenubar();
    }


    /*
    private void resizeMenubar() {
        ViewGroup.LayoutParams lp = mTopBarContainer.getLayoutParams();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lp.height = (int) (28 * getResources().getDisplayMetrics().density);
        } else {
            lp.height = (int) (48 * getResources().getDisplayMetrics().density);
        }
        mTopBarContainer.setLayoutParams(lp);
    }
*/

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


}
