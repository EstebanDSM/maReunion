package com.guzzler13.mareunion.utils;

import android.widget.Spinner;
import android.widget.TextView;

import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.model.Room;
import com.guzzler13.mareunion.service.MeetingApiService;

import org.joda.time.DateTime;

public class NewMeeting {
    public static Meeting meeting(MeetingApiService apiservice, TextView textView, DateTime dateTime, DateTime beginTime, DateTime endTime, String participants, Spinner spinner) {

        /* Création nouveau meeting */

        return new Meeting(
                SetID.SetId(apiservice),
                textView.getText().toString(),
                new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), beginTime.getHourOfDay(), beginTime.getMinuteOfHour()),
                new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), endTime.getHourOfDay(), endTime.getMinuteOfHour()),
                participants,
                new Room(spinner.getSelectedItem().toString(), SetColorMeeting.SetColor(spinner.getSelectedItem().toString())), false, false);
    }


}
