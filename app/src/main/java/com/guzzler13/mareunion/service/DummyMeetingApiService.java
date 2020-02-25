package com.guzzler13.mareunion.service;

import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.model.Room;
import com.guzzler13.mareunion.ui.list.MeetingListAdapter;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> res = new ArrayList<>();
    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();
    private List<Room> mRooms = RoomGenerator.generateRooms();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        resetFilter();
        return mMeetings;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void getMeetingsByOrderDate() {
        if (MeetingListAdapter.isListFilterRoom || MeetingListAdapter.isListFilterDate) {
            Collections.sort(res, new Comparator<Meeting>() {
                public int compare(Meeting o1, Meeting o2) {
                    return o1.getDateBegin().compareTo(o2.getDateBegin());
                }
            });
        } else {
            Collections.sort(mMeetings, new Comparator<Meeting>() {
                public int compare(Meeting o1, Meeting o2) {
                    return o1.getDateBegin().compareTo(o2.getDateBegin());
                }
            });
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void getMeetingsByReverseOrderDate() {

        Collections.sort(mMeetings, new Comparator<Meeting>() {
            public int compare(Meeting o1, Meeting o2) {
                return o2.getDateBegin().compareTo(o1.getDateBegin());
            }
        });
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void getMeetingsByRoom() {

        Collections.sort(mMeetings, new Comparator<Meeting>() {
            public int compare(Meeting o2, Meeting o1) {
                return o1.getMeetingRoom().getmRoomColor() - (o2.getMeetingRoom().getmRoomColor());
            }
        });

    }

    @Override

    public List<Meeting> getMeetingsFilterRoom(String salle) {

        resetFilter();

        for (Meeting m : mMeetings) {
            if (m.getMeetingRoom().getmNameRoom().equals(salle)) {
                m.setFilterRoom(true);
                res.add(m);
            }
        }

        return res;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(Meeting meeting) {

        resetFilter();

        mMeetings.remove(meeting);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeeting(Meeting meeting) {

        resetFilter();

        mMeetings.add(meeting);
    }


    @Override
    public List<Room> getRooms() {

        resetFilter();

        return mRooms;
    }


    @Override
    public List<Meeting> getMeetingsByDate(DateTime mDate) {

        resetFilter();


        for (Meeting m : mMeetings) {
            if (m.getDateBegin().toLocalDate().equals(mDate.toLocalDate())) {
                m.setFilterDate(true);
                res.add(m);
            }
        }
        return res;
    }


    @Override
    public void resetFilter() {
        for (Meeting m : mMeetings) {
            res.clear();
            m.setFilterDate(false);
            m.setFilterRoom(false);
        }
    }

}



