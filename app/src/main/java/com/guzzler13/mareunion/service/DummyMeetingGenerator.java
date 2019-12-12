package com.guzzler13.mareunion.service;


import com.guzzler13.mareunion.model.Meeting;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class DummyMeetingGenerator {

    private static final DateTime dateTimeMeeting0 = new DateTime(2019, 12, 25, 10, 0);
    private static final DateTime dateTimeEndMeeting0 = new DateTime(2019, 12, 25, 10, 45);
    private static final DateTime dateTimeMeeting1 = new DateTime(2019, 12, 26, 18, 0);
    private static final DateTime dateTimeEndMeeting1 = new DateTime(2019, 12, 26, 19, 0);
    private static final DateTime dateTimeMeeting2 = new DateTime(2019, 12, 24, 9, 0);
    private static final DateTime dateTimeEndMeeting2 = new DateTime(2019, 12, 24, 9, 30);
    private static final DateTime dateTimeMeeting3 = new DateTime(2019, 12, 26, 15, 0);
    private static final DateTime dateTimeEndMeeting3 = new DateTime(2019, 12, 26, 15, 45);
    private static final DateTime dateTimeMeeting4 = new DateTime(2019, 12, 27, 14, 30);
    private static final DateTime dateTimeEndMeeting4 = new DateTime(2019, 12, 27, 15, 30);


    private static final List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(0, "Réunion A", dateTimeMeeting0, dateTimeEndMeeting0,
                    "McFly@bttf.com", RoomGenerator.generateRooms().get(0)),
            new Meeting(1, "Réunion B", dateTimeMeeting1, dateTimeEndMeeting1,
                    "lucas@boulik.com, dark.vador@starwars.com, toto@smiley.com", RoomGenerator.generateRooms().get(1)),
            new Meeting(2, "Réunion C", dateTimeMeeting2, dateTimeEndMeeting2,
                    "doc@bttf.com", RoomGenerator.generateRooms().get(2)),
            new Meeting(3, "Réunion D", dateTimeMeeting3, dateTimeEndMeeting3,
                    "sarah.connor@inyourface.com", RoomGenerator.generateRooms().get(3)),
            new Meeting(4, "Réunion E", dateTimeMeeting4, dateTimeEndMeeting4,
                    "skywalker@starwars.es", RoomGenerator.generateRooms().get(4))

            );


    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
