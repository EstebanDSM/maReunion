package com.guzzler13.mareunion.utils;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class SeparateParticipantsByString {


    //Créer la liste des participants dans la liste des réunions sous forme de String séparés par des virgules


    public static void separate(ChipGroup chipgroup, String string) {

        for (int i = 0; i < chipgroup.getChildCount(); i++) {
            Chip chip = (Chip) chipgroup.getChildAt(i);
            if (i == 0) {//
                string = chip.getText().toString().concat(string);
            } else {
                string = chip.getText().toString().concat(", " + string);
            }

        }
    }

}
