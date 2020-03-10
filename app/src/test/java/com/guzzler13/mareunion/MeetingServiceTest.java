package com.guzzler13.mareunion;

import com.guzzler13.mareunion.di.DI;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.model.Room;
import com.guzzler13.mareunion.service.DummyMeetingGenerator;
import com.guzzler13.mareunion.service.MeetingApiService;
import com.guzzler13.mareunion.service.RoomGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingServiceTest {

    private MeetingApiService mApiService;

    @Before
    public void setUp() {
        mApiService = DI.getNewInstanceApiService();
    }


    @Test
    public void getMeetings() {
        List<Meeting> meetings = mApiService.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void addMeeting() {
        Meeting meetingToAdded = new Meeting(7, "toto",
                new DateTime(2021, 12, 25, 10, 0),
                new DateTime(2021, 12, 25, 10, 0),
                "moi@blabla", RoomGenerator.generateRooms().get(2));
        mApiService.addMeeting(meetingToAdded);
        assertTrue(mApiService.getMeetings().contains(meetingToAdded));
    }

    @Test
    public void deleMeeting() {

        int size = DI.getNewInstanceApiService().getMeetings().size();

        Meeting meetingToDelete = mApiService.getMeetings().get(1);
        mApiService.deleteMeeting(meetingToDelete);
        assertFalse(mApiService.getMeetings().contains(meetingToDelete));
        assertNotEquals(size, mApiService.getMeetings().size());
    }

    @Test
    public void getRooms() {
        List<Room> rooms = mApiService.getRooms();
        List<Room> expectedRooms = RoomGenerator.generateRooms();
        assertThat(rooms, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedRooms.toArray()));
    }

    @Test
    public void getMeetingsFilterRoom() {
        String expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS.get(4).getMeetingRoom().getmNameRoom();
        assertEquals(mApiService.getMeetingsFilterRoom("Bowser").get(0).getMeetingRoom().getmNameRoom(), expectedMeetings);
    }

    @Test
    public void getMeetingFilterDate() {
        String expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS.get(2).getMeetingRoom().getmNameRoom();
        assertEquals(mApiService.getMeetingsByDate(new DateTime(2020, 3, 24, 0, 0)).get(0).getMeetingRoom().getmNameRoom(), expectedMeetings);
    }
}