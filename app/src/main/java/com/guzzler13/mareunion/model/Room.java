package com.guzzler13.mareunion.model;

public class Room {

    private String mNameRoom;
    private int mRoomColor;


    public Room(String mNameRoom, int mRoomColor) {
        this.mNameRoom = mNameRoom;
        this.mRoomColor = mRoomColor;
    }

    public String getmNameRoom() {
        return mNameRoom;
    }


    public int getmRoomColor() {
        return mRoomColor;
    }
}
