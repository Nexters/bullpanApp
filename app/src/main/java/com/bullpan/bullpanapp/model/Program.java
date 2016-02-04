package com.bullpan.bullpanapp.model;

import java.util.Date;

/**
 * Created by daehyun on 16. 2. 3..
 */
public class Program {
    private String title;
    private String programlInfo;
    private Date startTime;
    private Date endTime;

    public Program(String title, String programlInfo, Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.programlInfo = programlInfo;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProgramlInfo() {
        return programlInfo;
    }

    public void setProgramlInfo(String programlInfo) {
        this.programlInfo = programlInfo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


}
