package com.guzzler13.mareunion.utils;

import com.guzzler13.mareunion.service.MeetingApiService;

//Associer un nouvel ID à chaque nouvelle réunion créée

class SetID {

    static int SetId(MeetingApiService meetingApiService) {
        int id = 0;
        for (int i = 0; i < meetingApiService.getMeetings().size(); i++) {
            if (id < meetingApiService.getMeetings().get(i).getId())
                id = meetingApiService.getMeetings().get(i).getId();
        }
        return id + 1;
    }


}
