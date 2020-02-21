package com.guzzler13.mareunion.utils;

import com.guzzler13.mareunion.service.RoomGenerator;

//Donner la bonne couleur à la réunion en fonction du nom de la salle

public class SetColorMeeting {

    public static int SetColor(String string){

        int color = 0;
        String mSelected = string;
        String mTarget;

        for (int i = 0; i < RoomGenerator.generateRooms().size(); i++) {
            mTarget = RoomGenerator.generateRooms().get(i).getmNameRoom();
            if (mSelected.equals(mTarget)) {
                color = RoomGenerator.generateRooms().get(i).getmRoomColor();
            }
        }

        return color;
    }

}
