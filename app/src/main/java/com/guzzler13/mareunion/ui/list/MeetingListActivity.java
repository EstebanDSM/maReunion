package com.guzzler13.mareunion.ui.list;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private MenuItem date;
    private MeetingApiService mApiService;
    private RecyclerView.Adapter mMeetingListAdapter;
    private final List<Meeting> mMeetings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mApiService = DI.getMeetingApiService();
        setContentView(R.layout.meeting_list_activity);
        RecyclerView mRecyclerView = findViewById(R.id.list_meetings);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMeetings.addAll(mApiService.getMeetings());
        mMeetingListAdapter = new MeetingListAdapter(mMeetings);
        mRecyclerView.setAdapter(mMeetingListAdapter);
        mMeetingListAdapter.notifyDataSetChanged();


        FloatingActionButton btnFab = findViewById(R.id.btnFab);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("DEBUG", "onClick:  Open Activity");

                Intent intent = new Intent(MeetingListActivity.this , DetailsMeetingActivity.class);
                startActivity(intent);


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        date = menu.findItem(R.id.date_selectionnée);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // calender class's instance and get current date , month and year from calender
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        final DateTime mTime = new DateTime(mYear, mMonth, mDay, 00, 00);
        switch (item.getItemId()) {

            case R.id.filtre_date_croissante:
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetings());
                mApiService.getMeetingsByOrderDate();
                mMeetingListAdapter.notifyDataSetChanged();
                date.setTitle("Tri par date croissante");

                return true;

            case R.id.filtre_date_décroissante:
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetings());
                mApiService.getMeetingsByReverseOrderDate();
                mMeetingListAdapter.notifyDataSetChanged();
                date.setTitle("Tri par date décroissante");

                return true;

            case R.id.date_selectionnée:

                // date picker dialog
                // set day of month , month and year value in the edit text
                DatePickerDialog datePickerDialog = new DatePickerDialog(MeetingListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // set day of month , month and year value in the edit text
                                date.setTitle(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                DateTime dateTimeTemp = mTime.withDate(year, monthOfYear +1, dayOfMonth);

                                Log.d("", String.valueOf(mTime));
                                mMeetings.clear();
                                mMeetings.addAll(mApiService.getMeetingsByDate(dateTimeTemp));
                                mMeetingListAdapter.notifyDataSetChanged();

                            }
                        }, mYear, mMonth, mDay);








                datePickerDialog.show();

                return true;


            case R.id.filtre_salle:
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetings());
                mApiService.getMeetingsByRoom();
                mMeetingListAdapter.notifyDataSetChanged();
                date.setTitle("Tri par salle");
                return true;

            case R.id.selection_date:

                // calender class's instance and get current date , month and year from calender
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR); // current year
                mMonth = c.get(Calendar.MONTH); // current month
                mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                // set day of month , month and year value in the edit text
                datePickerDialog = new DatePickerDialog(MeetingListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // set day of month , month and year value in the edit text
                                date.setTitle(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                DateTime dateTimeTemp = mTime.withDate(year, monthOfYear +1, dayOfMonth);
                                mMeetings.clear();
                                mMeetings.addAll(mApiService.getMeetingsByDate(dateTimeTemp));
                                mMeetingListAdapter.notifyDataSetChanged();


                                Log.d("", String.valueOf(mTime));

                            }
                        }, mYear, mMonth, mDay);


//                mTime = new DateTime(mYear, mMonth, mDay, 00, 00);



                datePickerDialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


