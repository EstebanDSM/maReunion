package com.guzzler13.mareunion.ui.list;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guzzler13.mareunion.R;
import com.guzzler13.mareunion.di.DI;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.service.MeetingApiService;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.List;

public class MeetingListActivity extends AppCompatActivity {
    private MenuItem date;
    private MeetingApiService mApiService;
    private RecyclerView.Adapter mMeetingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mApiService = DI.getMeetingApiService();
        setContentView(R.layout.meeting_list_activity);
        RecyclerView mRecyclerView = findViewById(R.id.list_meetings);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<Meeting> mMeetings = mApiService.getMeetings();
        mMeetingListAdapter = new MeetingListAdapter(mMeetings);
        mRecyclerView.setAdapter(mMeetingListAdapter);
        mMeetingListAdapter.notifyDataSetChanged();


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
        switch (item.getItemId()) {

            case R.id.filtre_date_croissante:
                mApiService.getMeetingsByOrderDate();
                mMeetingListAdapter.notifyDataSetChanged();
                date.setTitle("");

                return true;

            case R.id.filtre_date_décroissante:
                mApiService.getMeetingsByReverseOrderDate();
                mMeetingListAdapter.notifyDataSetChanged();
                date.setTitle("");

                return true;

            case R.id.date_selectionnée:
                // calender class's instance and get current date , month and year from calender
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
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

                            }
                        }, mYear, mMonth, mDay);


                DateTime TimeChoose = new DateTime(mYear, mMonth, mDay, 00, 00);
                Log.d("", String.valueOf(TimeChoose));



                datePickerDialog.show();

                return true;


            case R.id.filtre_salle:
                mApiService.getMeetingsByRoom();
                mMeetingListAdapter.notifyDataSetChanged();
                date.setTitle("");
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

                            }
                        }, mYear, mMonth, mDay);


                TimeChoose = new DateTime(mYear, mMonth, mDay, 00, 00);
                Log.d("", String.valueOf(TimeChoose));



                datePickerDialog.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


