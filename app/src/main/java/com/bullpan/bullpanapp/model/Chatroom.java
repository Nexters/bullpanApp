package com.bullpan.bullpanapp.model;

import java.util.Date;

/**
 * Created by yoonsun on 2016. 2. 7..
 */
public class Chatroom {
    private int no;
    private String name;

    private int joinNumber;
    private int totalNumber;

    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String url;

    //이건 어떻게 해야하지?
    private int chatCount;

    //program이미지나 정보 가져오려고
    private Program program;

    public Chatroom(String name, int totalNumber)
    {
        this.name = name;
        this.joinNumber = 1;
        this.totalNumber = totalNumber;

    }

    public int getNo() { return no; }
    public void setNo(int no) { this.no = no; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getJoinNumber() { return joinNumber; }
    public void setJoinNumber(int joinNumber) { this.joinNumber = joinNumber; }

    public int getTotalNumber() { return totalNumber; }
    public void setTotalNumber(int totalNumber) { this.totalNumber = totalNumber; }

    public Program getProgram() { return program; }
    public void setProgram(Program program) { this.program = program; }

    public int getChatCount() { return chatCount; }
    public void setChatCount(int chatCount) { this.chatCount = chatCount; }


}
