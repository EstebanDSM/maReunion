package com.guzzler13.mareunion.utils;

import com.guzzler13.mareunion.service.RoomGenerator;

//Donner la bonne couleur à la réunion en fonction du nom de la salle

class ColorMeetingUtils {

    static int SetColor(String string) {

        int color = 0;
        String mTarget;

        for (int i = 0; i < RoomGenerator.generateRooms().size(); i++) {
            mTarget = RoomGenerator.generateRooms().get(i).getmNameRoom();
            if (string.equals(mTarget)) {
                color = RoomGenerator.generateRooms().get(i).getmRoomColor();
            }
        }

        return color;
    }

}
