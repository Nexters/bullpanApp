package com.bullpan.bullpanapp.model;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by daehyun on 16. 2. 3..
 */
public class Channel {
    private String title;
    private int logoImageId;
    private String currentProgramTitle;
    private String currentDuration;
    private int channelHitCount;
    private Program mCurrentProgram;

    public Channel(String title, int logoImageId, Program currentProgram, int channelHitCount) {
        this.title = title;
        this.logoImageId = logoImageId;
        mCurrentProgram = currentProgram;
        this.channelHitCount = channelHitCount;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLogoImageId() {
        return logoImageId;
    }

    public String getCurrentProgramTitle() {
         return mCurrentProgram.getTitle();
    }
    public String getCurrentDuration() {


        SimpleDateFormat sdf = new SimpleDateFormat("a hh:mm", Locale.KOREA);
        return sdf.format(mCurrentProgram.getStartTime())+ " - " +
                sdf.format(mCurrentProgram.getEndTime());

    }

    public void setLogoImageId(int logoImageId) {
        this.logoImageId = logoImageId;
    }

    public Program getCurrentProgram() {
        return mCurrentProgram;
    }

    public void setCurrentProgram(Program currentProgram) {
        mCurrentProgram = currentProgram;
    }
    public int getChannelHitCount() {
        return channelHitCount;
    }

    public void setChannelHitCount(int channelHitCount) {
        this.channelHitCount = channelHitCount;
    }

}
