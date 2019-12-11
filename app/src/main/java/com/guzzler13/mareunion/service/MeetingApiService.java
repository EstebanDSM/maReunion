package com.guzzler13.mareunion.service;

import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.model.Room;

import java.util.List;


/**
 * Meeting API client
 */
public interface MeetingApiService {


    List<Meeting> getMeetings();

    /**
     * Get all Meetings in order of date
     *
     * @return {@link List}
     */
    List<Meeting> getMeetingsByOrderDate();


    /**
     * Get all Meetings in Reverse order of date
     *
     * @return {@link List}
     */
    List<Meeting> getMeetingsByReverseOrderDate();



    /**
     * Deletes a meeting
     *
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);



    /**
     * Get all Meetings in order of Rooms
     *
     * @return {@link List}
     */
    List<Meeting> getMeetingsByRoom();



    /**
     * Add a Favorite
     *
     * @param meeting
     */
    void addMeeting(Meeting meeting);


    List<Room> getRooms ();


}
