package com.guzzler13.mareunion.service;

import com.guzzler13.mareunion.model.Meeting;

import java.util.List;


/**
 * Meeting API client
 */
public interface MeetingApiService {


    /**
     * Get all Meetings in order of date
     *
     * @return {@link List}
     */
    List<Meeting> getMeetingsOrder();


    /**
     * Get all Meetings in Reverse order of date
     *
     * @return {@link List}
     */
    List<Meeting> getMeetingsReverseOrder();



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


}
