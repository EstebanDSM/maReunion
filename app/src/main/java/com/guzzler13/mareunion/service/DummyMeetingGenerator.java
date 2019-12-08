package com.guzzler13.mareunion.service;


import com.guzzler13.mareunion.model.Meeting;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    private static final DateTime dateTimeMeeting0 = new DateTime(2019, 12, 25, 10, 0);
    private static final DateTime dateTimeEndMeeting0 = new DateTime(2019, 12, 25, 10, 45);
    private static final DateTime dateTimeMeeting1 = new DateTime(2019, 12, 26, 18, 0);
    private static final DateTime dateTimeEndMeeting1 = new DateTime(2019, 12, 26, 19, 0);
    private static final DateTime dateTimeMeeting2 = new DateTime(2019, 12, 24, 9, 0);
    private static final DateTime dateTimeEndMeeting2 = new DateTime(2019, 12, 24, 9, 30);
    private static final DateTime dateTimeMeeting3 = new DateTime(2019, 12, 28, 15, 0);
    private static final DateTime dateTimeEndMeeting3 = new DateTime(2019, 12, 28, 15, 45);
    private static final DateTime dateTimeMeeting4 = new DateTime(2019, 12, 29, 14, 30);
    private static final DateTime dateTimeEndMeeting4 = new DateTime(2019, 12, 29, 15, 30);

    public static final List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting(0, "Réunion A", dateTimeMeeting0, dateTimeEndMeeting0,
                    "Salle 2", "toto@smiley.fr"),
            new Meeting(1, "Réunion B", dateTimeMeeting1, dateTimeEndMeeting1,
                    "Salle 3", "zaphod@beltegeuse.com, dark.vador@starwars.gl, toto@smiley.fr"),
            new Meeting(2, "Réunion C", dateTimeMeeting2, dateTimeEndMeeting2,
                    "Salle 4", "zaphod@beltegeuse.com"),
            new Meeting(3, "Réunion D", dateTimeMeeting3, dateTimeEndMeeting3,
                    "Salle 5", "sarah.connor@nofate.us"),
            new Meeting(4, "Réunion E", dateTimeMeeting4, dateTimeEndMeeting4,
                    "Salle 6", "vincent.team@wallet.biz")


    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
