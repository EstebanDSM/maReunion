package com.guzzler13.mareunion.model;

import org.joda.time.DateTime;

public class Meeting {

    private Integer id;
    private String name;
    private DateTime dateBegin;
    private DateTime dateEnd;
    private String participants;
    private String meetingRoom;
    private Boolean isReserved;

    public Meeting(Integer id, String name, DateTime dateBegin, DateTime dateEnd, String participants, String meetingRoom) {
        this.id = id;
        this.name = name;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.participants = participants;
        this.meetingRoom = meetingRoom;
        this.isReserved = false;
    }

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateBegin() {
        return dateBegin.toString();
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

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }
}
