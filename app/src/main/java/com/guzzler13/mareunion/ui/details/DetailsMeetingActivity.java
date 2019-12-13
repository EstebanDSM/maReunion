package com.guzzler13.mareunion.ui.details;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.guzzler13.mareunion.di.DI;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.service.MeetingApiService;

public class DetailsMeetingActivity extends AppCompatActivity {

    private MeetingApiService mApiService = DI.getMeetingApiService();
    private Meeting meeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_details_meeting);
//
//        int id = getIntent().getIntExtra("id", -1);
//
//        if (id != -1) {
//            meeting = mApiService.getMeetings().get(id);
//
//            String nameReunion = meeting.getName();
//            String dateTime = meeting.getDateBegin().toString("dd/MM");
//            String hourBegin = meeting.getDateBegin().toString("kk:mm");
//            String hourEnd = meeting.getDateEnd().toString("kk:mm");
//
//
//            TextView textView = findViewById(R.id.meetingName_editText);
//            TextView textView1 = findViewById(R.id.dateEdit_TextView);
//            TextView textView2 = findViewById(R.id.beginTimeEdit_TextView);
//            TextView textView3 = findViewById(R.id.endTimeEdit_TextView);
//
//
//            textView.setText(nameReunion);
//            textView1.setText(dateTime);
//            textView2.setText(hourBegin);
//            textView3.setText(hourEnd);
//
//        } else {
//            TextView textView = findViewById(R.id.meetingName_editText);
//            textView.setText("click a partir du Float");
//            Log.d("DEBUG", "NULL");



    }
}

