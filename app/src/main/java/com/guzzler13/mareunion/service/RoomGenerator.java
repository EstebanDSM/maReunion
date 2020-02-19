package com.guzzler13.mareunion.service;


import com.guzzler13.mareunion.R;
import com.guzzler13.mareunion.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomGenerator {


    private static List<Room> LIST_ROOM = Arrays.asList(

            new Room("Peach", R.drawable.ic_brightness_1_amber_300_24dp),
            new Room("Mario", R.drawable.ic_brightness_1_brown_300_24dp),
            new Room("Luigi", R.drawable.ic_brightness_1_deep_orange_300_24dp),
            new Room("Toad", R.drawable.ic_brightness_1_grey_500_24dp),
            new Room("Bowser", R.drawable.ic_brightness_1_indigo_300_24dp),
            new Room("Yoshi", R.drawable.ic_brightness_1_light_green_300_24dp),
            new Room("Wario", R.drawable.ic_brightness_1_pink_300_24dp),
            new Room("Daisy", R.drawable.ic_brightness_1_purple_300_24dp),
            new Room("Harmonie", R.drawable.ic_brightness_1_red_200_24dp),
            new Room("Pokey", R.drawable.ic_brightness_1_yellow_300_24dp)

    );

    public static List<Room> generateRooms() {
        return new ArrayList<>(LIST_ROOM);
    }


}
