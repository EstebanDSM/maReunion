package com.guzzler13.mareunion.service;

import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.model.Room;

import org.joda.time.DateTime;

import java.util.List;


/**
 * Meeting API client
 */
public interface MeetingApiService {


    List<Meeting> getMeetings();

    /**
     * Get all Meetings in order of date
     */
    void getMeetingsByOrderDate();


    /**
     * Get all Meetings in Reverse order of date
     */
    void getMeetingsByReverseOrderDate();


    /**
     * Deletes a meeting
     *
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);


    /**
     * Get all Meetings in order of Rooms (liste triée)
     */
    void getMeetingsByRoom();


    /**
     * Get all Meetings in order of Rooms (liste filtrée)
     */
    List<Meeting> getMeetingsFilterRoom(String salle);


    /**
     * Add meeting
     *
     * @param meeting
     */
    void addMeeting(Meeting meeting);


    List<Room> getRooms();

    /* liste filtrée par salle */
    List<Meeting> getMeetingsByDate(DateTime mDate);


    void resetFilter();
}
