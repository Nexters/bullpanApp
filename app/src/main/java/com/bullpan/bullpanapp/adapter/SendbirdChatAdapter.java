package com.bullpan.bullpanapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bullpan.bullpanapp.R;
import com.bullpan.bullpanapp.utils.SendbirdUtils;
import com.sendbird.android.model.BroadcastMessage;
import com.sendbird.android.model.FileLink;
import com.sendbird.android.model.Message;
import com.sendbird.android.model.MessageModel;
import com.sendbird.android.model.SystemMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by daehyun on 16. 2. 6..
 */
public class SendBirdChatAdapter extends BaseAdapter {
    private static final int TYPE_UNSUPPORTED = 0;
    private static final int TYPE_MESSAGE = 1;
    private static final int TYPE_SYSTEM_MESSAGE = 2;
    private static final int TYPE_FILELINK = 3;
    private static final int TYPE_BROADCAST_MESSAGE = 4;

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final ArrayList<Object> mItemList;
    private long mMaxMessageTimestamp = Long.MIN_VALUE;
    private long mMinMessageTimestamp = Long.MAX_VALUE;

    public SendBirdChatAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemList = new ArrayList<Object>();
    }

    private void updateMessageTimestamp(MessageModel model) {
        mMaxMessageTimestamp = mMaxMessageTimestamp < model.getTimestamp() ? model.getTimestamp() : mMaxMessageTimestamp;
        mMinMessageTimestamp = mMinMessageTimestamp > model.getTimestamp() ? model.getTimestamp() : mMinMessageTimestamp;
    }

    public long getMaxMessageTimestamp() {
        return mMaxMessageTimestamp == Long.MIN_VALUE ? Long.MAX_VALUE : mMaxMessageTimestamp;
    }

    public long getMinMessageTimestamp() {
        return mMinMessageTimestamp == Long.MAX_VALUE ? Long.MIN_VALUE : mMinMessageTimestamp;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    public void clear() {
        mMaxMessageTimestamp = Long.MIN_VALUE;
        mMinMessageTimestamp = Long.MAX_VALUE;
        mItemList.clear();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addMessageModel(MessageModel model) {
        if(model.isPast()) {
            mItemList.add(0, model);
        } else {
            mItemList.add(model);
        }
        updateMessageTimestamp(model);
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mItemList.get(position);
        if(item instanceof Message) {
            return TYPE_MESSAGE;
        } else if(item instanceof FileLink) {
            return TYPE_FILELINK;
        } else if(item instanceof SystemMessage) {
            return TYPE_SYSTEM_MESSAGE;
        } else if(item instanceof BroadcastMessage) {
            return TYPE_BROADCAST_MESSAGE;
        }

        return TYPE_UNSUPPORTED;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Object item = getItem(position);

        if(convertView == null || ((ViewHolder)convertView.getTag()).getViewType() != getItemViewType(position)) {
            viewHolder = new ViewHolder();
            viewHolder.setViewType(getItemViewType(position));

            switch(getItemViewType(position)) {
                case TYPE_UNSUPPORTED:
                    convertView = new View(mInflater.getContext());
                    convertView.setTag(viewHolder);
                    break;
                case TYPE_MESSAGE: {
                    TextView tv;

                    convertView = mInflater.inflate(R.layout.sendbird_view_message, parent, false);
                    tv = (TextView) convertView.findViewById(R.id.txt_message);
                    viewHolder.setView("message", tv);
                    viewHolder.setView("img_op_icon", (ImageView)convertView.findViewById(R.id.img_op_icon));
                    convertView.setTag(viewHolder);
                    break;
                }
                case TYPE_SYSTEM_MESSAGE: {
                    convertView = mInflater.inflate(R.layout.sendbird_view_system_message, parent, false);
                    viewHolder.setView("message", convertView.findViewById(R.id.txt_message));
                    convertView.setTag(viewHolder);
                    break;
                }
                case TYPE_BROADCAST_MESSAGE: {
                    convertView = mInflater.inflate(R.layout.sendbird_view_system_message, parent, false);
                    viewHolder.setView("message", convertView.findViewById(R.id.txt_message));
                    convertView.setTag(viewHolder);
                    break;
                }
                case TYPE_FILELINK: {
                    TextView tv;

                    convertView = mInflater.inflate(R.layout.sendbird_view_filelink, parent, false);
                    tv = (TextView) convertView.findViewById(R.id.txt_sender_name);
                    viewHolder.setView("txt_sender_name", tv);
                    viewHolder.setView("img_op_icon", (ImageView)convertView.findViewById(R.id.img_op_icon));

                    viewHolder.setView("img_file_container", convertView.findViewById(R.id.img_file_container));

                    viewHolder.setView("image_container", convertView.findViewById(R.id.image_container));
                    viewHolder.setView("img_thumbnail", convertView.findViewById(R.id.img_thumbnail));
                    viewHolder.setView("txt_image_name", convertView.findViewById(R.id.txt_image_name));
                    viewHolder.setView("txt_image_size", convertView.findViewById(R.id.txt_image_size));

                    viewHolder.setView("file_container", convertView.findViewById(R.id.file_container));
                    viewHolder.setView("txt_file_name", convertView.findViewById(R.id.txt_file_name));
                    viewHolder.setView("txt_file_size", convertView.findViewById(R.id.txt_file_size));

                    convertView.setTag(viewHolder);

                    break;
                }
            }
        }


        viewHolder = (ViewHolder) convertView.getTag();
        switch(getItemViewType(position)) {
            case TYPE_UNSUPPORTED:
                break;
            case TYPE_MESSAGE:
                Message message = (Message)item;
                if(message.isOpMessage()) {
                    viewHolder.getView("img_op_icon", ImageView.class).setVisibility(View.VISIBLE);
                    viewHolder.getView("message", TextView.class).setText(Html.fromHtml("&nbsp;&nbsp;&nbsp;<font color='#824096'><b>" + message.getSenderName() + "</b></font>: " + message.getMessage()));
                } else {
                    viewHolder.getView("img_op_icon", ImageView.class).setVisibility(View.GONE);
                    viewHolder.getView("message", TextView.class).setText(Html.fromHtml("<font color='#824096'><b>" + message.getSenderName() + "</b></font>: " + message.getMessage()));
                }
//                viewHolder.getView("message").setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new AlertDialog.Builder(mContext)
//                                .setTitle("SendBird")
//                                .setMessage("Do you want to start 1:1 messaging with " + ((Message) item).getSenderName() + "?")
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent data = new Intent();
//                                        data.putExtra("userIds", new String[]{((Message) item).getSenderId()});
//                                        setResult(RESULT_OK, data);
//                                        mDoNotDisconnect = true;
//                                        SendBirdChatActivity.this.finish();
//                                    }
//                                })
//                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                                .create()
//                                .show();
//                    }
//                });
                break;
            case TYPE_SYSTEM_MESSAGE:
                SystemMessage systemMessage = (SystemMessage)item;
                viewHolder.getView("message", TextView.class).setText(Html.fromHtml(systemMessage.getMessage()));
                break;
            case TYPE_BROADCAST_MESSAGE:
                BroadcastMessage broadcastMessage = (BroadcastMessage)item;
                viewHolder.getView("message", TextView.class).setText(Html.fromHtml(broadcastMessage.getMessage()));
                break;
            case TYPE_FILELINK:
                FileLink fileLink = (FileLink)item;

                if(fileLink.isOpMessage()) {
                    viewHolder.getView("img_op_icon", ImageView.class).setVisibility(View.VISIBLE);
                    viewHolder.getView("txt_sender_name", TextView.class).setText(Html.fromHtml("&nbsp;&nbsp;&nbsp;<font color='#824096'><b>" + fileLink.getSenderName() + "</b></font>: "));
                } else {
                    viewHolder.getView("img_op_icon", ImageView.class).setVisibility(View.GONE);
                    viewHolder.getView("txt_sender_name", TextView.class).setText(Html.fromHtml("<font color='#824096'><b>" + fileLink.getSenderName() + "</b></font>: "));
                }
                if(fileLink.getFileInfo().getType().toLowerCase().startsWith("image")) {
                    viewHolder.getView("file_container").setVisibility(View.GONE);

                    viewHolder.getView("image_container").setVisibility(View.VISIBLE);
                    viewHolder.getView("txt_image_name", TextView.class).setText(fileLink.getFileInfo().getName());
                    viewHolder.getView("txt_image_size", TextView.class).setText(SendbirdUtils.readableFileSize(fileLink.getFileInfo().getSize()));
//                    displayUrlImage(viewHolder.getView("img_thumbnail", ImageView.class), fileLink.getFileInfo().getUrl());
                } else {
                    viewHolder.getView("image_container").setVisibility(View.GONE);

                    viewHolder.getView("file_container").setVisibility(View.VISIBLE);
                    viewHolder.getView("txt_file_name", TextView.class).setText(fileLink.getFileInfo().getName());
                    viewHolder.getView("txt_file_size", TextView.class).setText("" + fileLink.getFileInfo().getSize());
                }
//                viewHolder.getView("txt_sender_name").setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        new AlertDialog.Builder(mContext)
////                                .setTitle("SendBird")
////                                .setMessage("Do you want to start 1:1 messaging with " + ((FileLink) item).getSenderName() + "?")
////                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int which) {
//////                                        Intent data = new Intent();
//////                                        data.putExtra("userIds", new String[]{((FileLink) item).getSenderId()});
//////                                        setResult(RESULT_OK, data);
//////                                        SendBirdChatActivity.this.finish();
////                                    }
////                                })
////                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int which) {
////                                    }
////                                })
////                                .create()
////                                .show();
//                    }
//                });
//                viewHolder.getView("img_file_container").setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new AlertDialog.Builder(mContext)
//                                .setTitle("SendBird")
//                                .setMessage("Do you want to download this file?")
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        try {
//                                            downloadUrl((FileLink) item, mContext);
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                })
//                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                                .create()
//                                .show();
//                    }
//                });
                break;
        }

        return convertView;
    }

    private class ViewHolder {
        private Hashtable<String, View> holder = new Hashtable<String, View>();
        private int type;

        public int getViewType() {
            return this.type;
        }

        public void setViewType(int type) {
            this.type = type;
        }
        public void setView(String k, View v) {
            holder.put(k, v);
        }

        public View getView(String k) {
            return holder.get(k);
        }

        public <T> T getView(String k, Class<T> type) {
            return type.cast(getView(k));
        }
    }
}

