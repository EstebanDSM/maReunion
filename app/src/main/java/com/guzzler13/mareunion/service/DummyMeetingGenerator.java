package com.guzzler13.mareunion.service;


import com.guzzler13.mareunion.model.Meeting;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    private static final DateTime dateTimeMeeting0 = new DateTime(2020, 2, 25, 10, 0);
    private static final DateTime dateTimeEndMeeting0 = new DateTime(2020, 2, 25, 10, 45);
    private static final DateTime dateTimeMeeting1 = new DateTime(2020, 2, 26, 18, 0);
    private static final DateTime dateTimeEndMeeting1 = new DateTime(2020, 2, 26, 19, 0);
    private static final DateTime dateTimeMeeting2 = new DateTime(2020, 2, 24, 9, 0);
    private static final DateTime dateTimeEndMeeting2 = new DateTime(2020, 2, 24, 9, 30);
    private static final DateTime dateTimeMeeting3 = new DateTime(2020, 2, 26, 15, 0);
    private static final DateTime dateTimeEndMeeting3 = new DateTime(2020, 2, 26, 15, 45);
    private static final DateTime dateTimeMeeting4 = new DateTime(2020, 2, 27, 14, 30);
    private static final DateTime dateTimeEndMeeting4 = new DateTime(2020, 2, 27, 14, 45);
    private static final DateTime dateTimeMeeting5 = new DateTime(2020, 2, 27, 15, 30);
    private static final DateTime dateTimeEndMeeting5 = new DateTime(2020, 2, 27, 16, 30);
    private static final DateTime dateTimeMeeting6 = new DateTime(2020, 2, 28, 15, 30);
    private static final DateTime dateTimeEndMeeting6 = new DateTime(2020, 2, 28, 16, 30);


    public static final List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(0, "Logistique", dateTimeMeeting0, dateTimeEndMeeting0,
                    "McFly@bttf.com", RoomGenerator.generateRooms().get(0), false, false),
            new Meeting(1, "Qualité", dateTimeMeeting1, dateTimeEndMeeting1,
                    "lucas@boulik.com, dark.vador@starwars.com, toto@smiley.com, abc123@jacksonFive.com", RoomGenerator.generateRooms().get(1), false, false),
            new Meeting(2, "Réception", dateTimeMeeting2, dateTimeEndMeeting2,
                    "doc@bttf.com", RoomGenerator.generateRooms().get(2), false, false),
            new Meeting(3, "Expédition", dateTimeMeeting3, dateTimeEndMeeting3,
                    "sarah.connor@inyourface.com", RoomGenerator.generateRooms().get(3), false, false),
            new Meeting(4, "Noël", dateTimeMeeting4, dateTimeEndMeeting4,
                    "skywalker@starwars.es", RoomGenerator.generateRooms().get(4), false, false),
            new Meeting(5, "Production", dateTimeMeeting5, dateTimeEndMeeting5,
                    "Jenifer@bttf.com", RoomGenerator.generateRooms().get(2), false, false),
            new Meeting(6, "Achats", dateTimeMeeting6, dateTimeEndMeeting6,
                    "Jenifer@bttf.com", RoomGenerator.generateRooms().get(7), false, false)
    );


    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
