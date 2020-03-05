package com.guzzler13.mareunion.utils;

import android.graphics.drawable.Drawable;

import com.google.android.material.chip.ChipGroup;
import com.guzzler13.mareunion.R;

public class ChipUtils {

    public static com.google.android.material.chip.Chip addChip(String participant, ChipGroup chipgroup, Drawable drawable) {

        final com.google.android.material.chip.Chip chip = new com.google.android.material.chip.Chip(chipgroup.getContext());
        chip.setChipBackgroundColorResource(R.color.colorGrey);

        chip.setText(participant);
        chip.setChipIcon(drawable);
        chip.setCheckable(false);
        chip.setClickable(true);
        chip.setCloseIconVisible(true);
        return chip;
    }


}
