package com.guzzler13.mareunion.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimeUtils {

    public static CharSequence dateTexte;

    public static void beginTimeHandle(final TextView textView, Context context) {
        final Calendar calendar1 = Calendar.getInstance();

        int HOUR = calendar1.get(Calendar.HOUR_OF_DAY);
        int MINUTE = calendar1.get(Calendar.MINUTE);

        boolean is24hourFormat = DateFormat.is24HourFormat(context);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(textView.getContext(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY, hour);
                calendar1.set(Calendar.MINUTE, minute);
                CharSequence timeTexte = DateFormat.format("HH:mm", calendar1);
                textView.setText(timeTexte);
            }
        }, HOUR, MINUTE, is24hourFormat);
        timePickerDialog.show();
    }

    public static void endTimeHandle(final TextView textView, Context context) {

        final Calendar calendar1 = Calendar.getInstance();

        int HOUR = calendar1.get(Calendar.HOUR_OF_DAY);
        int MINUTE = calendar1.get(Calendar.MINUTE);

        boolean is24hourFormat = DateFormat.is24HourFormat(context);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(textView.getContext(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY, hour);
                calendar1.set(Calendar.MINUTE, minute);
                CharSequence timeTexte = DateFormat.format("HH:mm", calendar1);
                textView.setText(timeTexte);
            }
        }, HOUR, MINUTE, is24hourFormat);
        timePickerDialog.show();
    }

    public static void dateHandle(final TextView textView) {
        final Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(textView.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                dateTexte = DateFormat.format("dd/MM/yyyy", calendar1);
                textView.setText(dateTexte);
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }


}
