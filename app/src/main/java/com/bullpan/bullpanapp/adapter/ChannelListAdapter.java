package com.bullpan.bullpanapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bullpan.bullpanapp.R;
import com.bullpan.bullpanapp.model.Channel;
import com.bullpan.bullpanapp.model.Program;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daehyun on 16. 2. 3..
 */
public class ChannelListAdapter  extends BaseAdapter{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Channel> mChannelList = new ArrayList<>();

    public ChannelListAdapter(Context context, List<Channel> channelList) {
        this.mContext = context;
        this.mChannelList = channelList;
        this.mInflater =  LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mChannelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mChannelList.get(position);
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
        Channel channel = (Channel) getItem(position);

        holder.logoImage.setImageResource(channel.getLogoImageId());
//        Picasso.with(mContext)
//                .load(channel.getLogoImageId())
//                .into(holder.logoImage);
        holder.programTitle.setText(channel.getCurrentProgramTitle());
        holder.programDuartion.setText(channel.getCurrentDuration());
        holder.programCount.setText(channel.getChannelHitCount()+"");
        return convertView;
    }
    public boolean add(Channel item) {
       return this.mChannelList.add(item);
    }

    public boolean addAll(List<Channel> items) {
        return this.mChannelList.addAll(items);
    }
    public void clear(){
         this.mChannelList.clear();
    }
    private static class ViewHolder {
        ImageView logoImage;
        TextView programTitle;
        TextView programDuartion;
        TextView programCount;
    }
}
