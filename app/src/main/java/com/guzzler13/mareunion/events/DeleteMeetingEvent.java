package com.guzzler13.mareunion.events;

import com.guzzler13.mareunion.model.Meeting;

public class DeleteMeetingEvent {
    private Meeting meeting;

    public Meeting getMeeting() {
        return meeting;
    }

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }


}
