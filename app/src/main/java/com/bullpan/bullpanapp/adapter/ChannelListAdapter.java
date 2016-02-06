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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daehyun on 16. 2. 3..
 */
public class ChannelListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<TvChannel> mTvChannelList = new ArrayList<>();

    public ChannelListAdapter(Context context, List<TvChannel> tvChannelList) {
        this.mContext = context;
        this.mTvChannelList = tvChannelList;
        this.mInflater = LayoutInflater.from(context);
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_channels, null);
            holder = new ViewHolder();
            holder.logoImage = (ImageView) convertView.findViewById(R.id.channel_logo_image);
            holder.programTitle = (TextView) convertView.findViewById(R.id.program_title_label);
            holder.programDuartion = (TextView) convertView.findViewById(R.id.program_duration_label);
            holder.programCount = (TextView) convertView.findViewById(R.id.channel_count_label);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TvChannel tvChannel = (TvChannel) getItem(position);

        holder.logoImage.setImageResource(tvChannel.getLogoImageId());
//        Picasso.with(mContext)
//                .load(channel.getLogoImageId())
//                .into(holder.logoImage);
        holder.programTitle.setText(tvChannel.getCurrentProgramTitle());
        holder.programDuartion.setText(tvChannel.getCurrentDuration());
        holder.programCount.setText(tvChannel.getChannelHitCount() + "");
        return convertView;
    }

    public void add(TvChannel item) {
        this.mTvChannelList.add(item);
        this.notifyDataSetChanged();

    }

    public void addAll(List<TvChannel> items) {
        this.mTvChannelList.addAll(items);
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.mTvChannelList.clear();
        this.notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView logoImage;
        TextView programTitle;
        TextView programDuartion;
        TextView programCount;
    }
}
