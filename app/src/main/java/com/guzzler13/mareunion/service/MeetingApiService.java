package com.guzzler13.mareunion.service;

import com.guzzler13.mareunion.model.Meeting;

import java.util.List;


/**
 * Meeting API client
 */
public interface MeetingApiService {


    /**
     * Get all Meetings
     *
     * @return {@link List}
     */
    List<Meeting> getMeetings();


    /**
     * Deletes a meeting
     *
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);


    /**
     * Add a Favorite
     *
     * @param meeting
     */
    void addMeeting(Meeting meeting);


}
