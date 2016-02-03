package com.bullpan.bullpanapp.model;

/**
 * Created by daehyun on 16. 2. 3..
 */
public class Channel {
    private String title;
    private int logoImageId;


    private int channelHitCount;
    private Program mCurrentProgram;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLogoImageId() {
        return logoImageId;
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
