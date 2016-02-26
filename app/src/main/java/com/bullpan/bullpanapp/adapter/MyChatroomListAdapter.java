package com.bullpan.bullpanapp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bullpan.bullpanapp.R;
import com.sendbird.android.model.Channel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by daehyun on 16. 2. 20..
 */
public class MyChatroomListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Channel> mChatrooms = new ArrayList<>();

    public MyChatroomListAdapter(Context context, List<Channel> chatrooms) {
        this.mContext = context;
        this.mChatrooms = chatrooms;
        this.mInflater =  LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mChatrooms.size();
    }

    @Override
    public Object getItem(int position) {
        return mChatrooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_my_chatting_room, null);
            holder = new ViewHolder();
            holder.coverImage = (ImageView) convertView.findViewById(R.id.chatroom_image);
            holder.memberJoinCountLabel= (TextView) convertView.findViewById(R.id.member_join_count_label);
            holder.unreadedCountLabel= (TextView) convertView.findViewById(R.id.chatroom_unread_count);
            holder.chattingRoomTitle = (TextView) convertView.findViewById(R.id.chatroom_title);
            holder.chatroomIdLabel = (TextView) convertView.findViewById(R.id.chatroom_id_label);
            holder.lastMessageLabel = (TextView) convertView.findViewById(R.id.last_message_label);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Channel item = (Channel) getItem(position);
//        holder.coverImage.setImageResource();
//        Picasso.with(mContext)
//                .load(item.getCoverUrl())
//                .into(holder.coverImage);
        holder.memberJoinCountLabel.setText(String.format("%02d", 10));
        holder.lastMessageLabel.setText("범인은 누구란 말이냐?");
//        holder.chattingRoomTitle.setText(item.getName());
        holder.chatroomIdLabel.setText(Html.fromHtml("<u>" +
                String.format("%02d", position + 1) + "</u>"));
//        holder.recentMessage.setText("");
        return convertView;
    }
    public void add(Channel item) {
        this.mChatrooms.add(item);
        this.notifyDataSetChanged();
    }

    public void addAll(Collection<Channel> items) {
        this.mChatrooms.addAll(items);
        this.notifyDataSetChanged();
    }
    public void clear(){
        this.mChatrooms.clear();
    }
    private static class ViewHolder {
        ImageView coverImage;
        TextView memberJoinCountLabel;
        TextView chatroomIdLabel;
        TextView chattingRoomTitle;
        TextView lastMessageLabel;
        TextView unreadedCountLabel;
    }
}
