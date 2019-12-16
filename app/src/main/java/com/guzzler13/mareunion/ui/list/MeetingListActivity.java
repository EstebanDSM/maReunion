package com.guzzler13.mareunion.ui.list;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.guzzler13.mareunion.R;
import com.guzzler13.mareunion.di.DI;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.service.MeetingApiService;
import com.guzzler13.mareunion.ui.details.DetailsMeetingActivity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MeetingListActivity extends AppCompatActivity {

    private MeetingApiService mApiService;
    private List<Meeting> mMeetings = new ArrayList<>();
    private RecyclerView.Adapter mMeetingListAdapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_list_activity);
        mApiService = DI.getMeetingApiService();


        mRecyclerView = findViewById(R.id.list_meetings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMeetings.addAll(mApiService.getMeetings());
        mMeetingListAdapter = new MeetingListAdapter(mMeetings);
        mRecyclerView.setAdapter(mMeetingListAdapter);
        mMeetingListAdapter.notifyDataSetChanged();


        FloatingActionButton btnFab = findViewById(R.id.btnFab);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingListActivity.this, DetailsMeetingActivity.class);
                startActivity(intent);
            }
        });

        mDateSetListener = generateDatePickerDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filtre_date_croissante:
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetingsByOrderDate());
                mMeetingListAdapter.notifyDataSetChanged();
                return true;

            case R.id.filtre_date_décroissante:
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetingsByReverseOrderDate());
                mMeetingListAdapter.notifyDataSetChanged();
                return true;

            case R.id.selection_date:
                configureDialogCalendar();
                mDateSetListener = generateDatePickerDialog();
                return true;

            case R.id.filtre_salle:
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetingsByRoom());
                mMeetingListAdapter.notifyDataSetChanged();
                return true;

            case R.id.Bowser:
                filterItemRoom("Bowser");
                return true;

            case R.id.Peach:
                filterItemRoom("Peach");
                return true;

            case R.id.Mario:
                filterItemRoom("Mario");
                return true;

            case R.id.Wario:
                filterItemRoom("Wario");
                return true;

            case R.id.Toad:
                filterItemRoom("Toad");
                return true;

            case R.id.Harmonie:
                filterItemRoom("Harmonie");
                return true;

            case R.id.Yoshi:
                filterItemRoom("Yoshi");
                return true;

            case R.id.Luigi:
                filterItemRoom("Luigi");
                return true;

            case R.id.Daisy:
                filterItemRoom("Daisy");
                return true;

            case R.id.Pokey:
                filterItemRoom("Pokey");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private DatePickerDialog.OnDateSetListener generateDatePickerDialog() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                DateTime time = new DateTime(year, monthOfYear + 1, dayOfMonth, 00, 00);

                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetingsByDate(time));
                mMeetingListAdapter = new MeetingListAdapter(mMeetings);
                mRecyclerView.setAdapter(mMeetingListAdapter);
                mMeetingListAdapter.notifyDataSetChanged();


            }
        };
    }

    private void configureDialogCalendar() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogDate = new DatePickerDialog(this, mDateSetListener, year, month, day);
        dialogDate.getDatePicker().setMinDate(System.currentTimeMillis());
        dialogDate.show();
    }

    private void filterItemRoom(String salle) {
        mMeetingListAdapter = new MeetingListAdapter(mMeetings);
        mRecyclerView.setAdapter(mMeetingListAdapter);


        mMeetings.clear();
        mMeetings.addAll(mApiService.getMeetingsFilterRoom(salle));
        mMeetingListAdapter.notifyDataSetChanged();

    }
}


