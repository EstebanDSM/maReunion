package com.guzzler13.mareunion.ui.list;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guzzler13.mareunion.R;
import com.guzzler13.mareunion.di.DI;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.service.MeetingApiService;

import java.util.List;

public class MeetingListActivity extends AppCompatActivity {
     private MeetingApiService mApiService;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mMeetingListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();

        setContentView(R.layout.meeting_list_activity);
        mRecyclerView = findViewById(R.id.list_meetings);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Meeting> mMeetings = mApiService.getMeetings();
        mMeetingListAdapter = new MeetingListAdapter(mMeetings);
        mRecyclerView.setAdapter(mMeetingListAdapter);


        mMeetingListAdapter.notifyDataSetChanged();

    }


}


