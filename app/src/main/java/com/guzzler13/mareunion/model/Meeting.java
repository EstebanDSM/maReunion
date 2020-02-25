package com.guzzler13.mareunion.model;

import org.joda.time.DateTime;

public class Meeting {


    private int id;
    private String name;
    private DateTime dateBegin;
    private DateTime dateEnd;
    private String participants;
    private Room meetingRoom;

    /* boolean pour liste filtrée ou liste complète) */
    private boolean isMeetingInFilterList;

    public Meeting(int id, String name, DateTime dateBegin, DateTime dateEnd, String participants, Room meetingRoom) {
        this.id = id;
        this.name = name;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.participants = participants;
        this.meetingRoom = meetingRoom;

        this.isMeetingInFilterList = false;


    }

    public boolean isMeetingInFilterList() {
        return isMeetingInFilterList;
    }

    public void setMeetingInFilterList(boolean isMeetingInFilterList) {
        this.isMeetingInFilterList = isMeetingInFilterList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(DateTime dateBegin) {
        this.dateBegin = dateBegin;
    }

    public DateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(DateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public Room getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(Room meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

}
