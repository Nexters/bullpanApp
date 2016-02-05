package com.bullpan.bullpanapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bullpan.bullpanapp.R;
import com.bullpan.bullpanapp.model.TvChannel;
import com.sendbird.android.model.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daehyun on 16. 2. 6..
 */
public class ChattingListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Channel> mTvChannelList = new ArrayList<>();

    public ChattingListAdapter(Context context, List<Channel> tvChannelList) {
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
            convertView = mInflater.inflate(R.layout.list_channels, null);
            holder = new ViewHolder();
            holder.logoImage = (ImageView) convertView.findViewById(R.id.channel_logo_image);
            holder.programTitle = (TextView) convertView.findViewById(R.id.program_title_label);
            holder.programDuartion = (TextView) convertView.findViewById(R.id.program_duration_label);
            holder.programCount = (TextView) convertView.findViewById(R.id.channel_count_label);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        TvChannel tvChannel = (TvChannel) getItem(position);

        holder.logoImage.setImageResource(tvChannel.getLogoImageId());
//        Picasso.with(mContext)
//                .load(channel.getLogoImageId())
//                .into(holder.logoImage);
        holder.programTitle.setText(tvChannel.getCurrentProgramTitle());
        holder.programDuartion.setText(tvChannel.getCurrentDuration());
        holder.programCount.setText(tvChannel.getChannelHitCount()+"");
        return convertView;
    }
    public boolean add(Channel item) {
        return this.mTvChannelList.add(item);
    }

    public boolean addAll(List<Channel> items) {
        return this.mTvChannelList.addAll(items);
    }
    public void clear(){
        this.mTvChannelList.clear();
    }
    private static class ViewHolder {
        ImageView logoImage;
        TextView programTitle;
        TextView programDuartion;
        TextView programCount;
    }
}
