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
 * Created by daehyun on 16. 2. 6..
 */
public class ChattingRoomListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Channel> chatrooms = new ArrayList<>();

    public ChattingRoomListAdapter(Context context, List<Channel> chatrooms) {
        this.mContext = context;
        this.chatrooms = chatrooms;
        this.mInflater =  LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chatrooms.size();
    }

    @Override
    public Object getItem(int position) {
        return chatrooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_chatting_rooms, null);
            holder = new ViewHolder();
            holder.memberJoinCountLabel = (TextView) convertView.findViewById(R.id.member_join_count_label);
            holder.coverImage = (ImageView) convertView.findViewById(R.id.chatroom_image);
            holder.titleLabel = (TextView) convertView.findViewById(R.id.chatroom_title);
            holder.lastMessageLabel = (TextView) convertView.findViewById(R.id.last_message_label);
            holder.chatroomIdLabel = (TextView) convertView.findViewById(R.id.chatroom_id_label);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Channel item = (Channel) getItem(position);

//        holder.coverImage.setImageResource();
//        Picasso.with(mContext)
//                .load(item.getCoverUrl())
//                .into(holder.coverImage);
        holder.memberJoinCountLabel.setText(String.format("%02d",item.getMemberCount()));
//        holder.chattingRoomTitle.setText(item.getName());
        holder.chatroomIdLabel.setText(String.format("%02d", position + 1));
//        holder.recentMessage.setText("");
        return convertView;
    }
    public void add(Channel item) {
        this.chatrooms.add(item);
        this.notifyDataSetChanged();
    }

    public void addAll(Collection<Channel> items) {
         this.chatrooms.addAll(items);
        this.notifyDataSetChanged();
    }
    public void clear(){
        this.chatrooms.clear();
    }
    private static class ViewHolder {
        ImageView coverImage;
        TextView memberJoinCountLabel;

        TextView titleLabel;
        TextView lastMessageLabel;
        TextView chatroomIdLabel;
    }
}
