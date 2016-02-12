package com.bullpan.bullpanapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bullpan.bullpanapp.R;
import com.sendbird.android.model.Channel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by daehyun on 16. 2. 6..
 */
public class ChattingRoomListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Channel> mTvChannelList = new ArrayList<>();

    public ChattingRoomListAdapter(Context context, List<Channel> tvChannelList) {
        this.mContext = context;
        this.mTvChannelList = tvChannelList;
        this.mInflater =  LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTvChannelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTvChannelList.get(position);
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
            holder.coverImage = (ImageView) convertView.findViewById(R.id.chatting_room_image);
            holder.memberJoinCountLabel= (TextView) convertView.findViewById(R.id.member_join_count_label);
            holder.memberTotalCountLabel = (TextView) convertView.findViewById(R.id.member_total_count_label);
            holder.chattingRoomTitle = (TextView) convertView.findViewById(R.id.chatting_room_title);
            holder.chattingOrderLabel= (TextView) convertView.findViewById(R.id.chatting_order_label);
            holder.recentMessage = (TextView) convertView.findViewById(R.id.chatting_room_recent_message);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Channel item = (Channel) getItem(position);

//        holder.coverImage.setImageResource();
        Picasso.with(mContext)
                .load(item.getCoverUrl())
                .into(holder.coverImage);
        holder.memberJoinCountLabel.setText(item.getMemberCount()+"");
        holder.memberTotalCountLabel.setText("40");
        holder.chattingRoomTitle.setText(item.getName());
        holder.chattingOrderLabel.setText(String.format("%02d",position+1));
        holder.recentMessage.setText("");
        return convertView;
    }
    public void add(Channel item) {
        this.mTvChannelList.add(item);
        this.notifyDataSetChanged();
    }

    public void addAll(Collection<Channel> items) {
         this.mTvChannelList.addAll(items);
        this.notifyDataSetChanged();
    }
    public void clear(){
        this.mTvChannelList.clear();
    }
    private static class ViewHolder {
        ImageView coverImage;
        TextView memberJoinCountLabel;
        TextView memberTotalCountLabel;
        TextView chattingOrderLabel;
        TextView chattingRoomTitle;
        TextView recentMessage;
    }
}
