package com.guzzler13.mareunion.model;

public class Room {

    private String mNameRoom;
    private int mRoomColor;
    private boolean mOccupied;

    public Room(String mNameRoom, int mRoomColor) {
        this.mNameRoom = mNameRoom;
        this.mRoomColor = mRoomColor;
        this.mOccupied = false;
    }


    public String getmNameRoom() {
        return mNameRoom;
    }


    public boolean getmOccupied() {
        return mOccupied;
    }

    public void setmOccupied(boolean mOccupied) {
        this.mOccupied = mOccupied;
    }

    public int getmRoomColor() {
        return mRoomColor;
    }

}
