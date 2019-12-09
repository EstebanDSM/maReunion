package com.guzzler13.mareunion.service;

import com.guzzler13.mareunion.model.Meeting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetings = DummyMeetingGenerator.generateMeetings();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetingsOrder() {

        Collections.sort(mMeetings);

        return mMeetings;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetingsReverseOrder() {

        Collections.sort(mMeetings, Collections.<Meeting>reverseOrder());

        return mMeetings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);

    }

    @Override
    public List<Meeting> getMeetingsByRoom() {
        Collections.sort(mMeetings, new Comparator<Meeting>() {
            public int compare(Meeting o1, Meeting o2) {
                return o1.getMeetingRoom() - (o2.getMeetingRoom());
            }
        });
        return mMeetings;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }


}
