package com.guzzler13.mareunion.service;

import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.model.Room;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();

    private List<Room> mRooms = RoomGenerator.generateRooms();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return mMeetings;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetingsByOrderDate() {

        Collections.sort(mMeetings, new Comparator<Meeting>() {
            public int compare(Meeting o1, Meeting o2) {
                return o1.getDateBegin().compareTo(o2.getDateBegin());
            }
        });
        return mMeetings;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetingsByReverseOrderDate() {

        Collections.sort(mMeetings, new Comparator<Meeting>() {
            public int compare(Meeting o1, Meeting o2) {
                return o2.getDateBegin().compareTo(o1.getDateBegin());
            }
        });
        return mMeetings;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetingsByRoom() {
        Collections.sort(mMeetings, new Comparator<Meeting>() {
            public int compare(Meeting o1, Meeting o2) {
                return o1.getMeetingRoom().getmRoomColor() - (o2.getMeetingRoom().getmRoomColor());
            }
        });
        return mMeetings;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);

    }




    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }

    @Override
    public List<Room> getRooms() {
        return mRooms;
    }


}