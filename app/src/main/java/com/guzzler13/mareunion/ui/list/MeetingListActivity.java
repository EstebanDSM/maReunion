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
    private List<Meeting> mMeetings = new ArrayList<>();
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
        mMeetings.addAll(mApiService.getMeetings());
        mMeetingListAdapter = new MeetingListAdapter(mMeetings);
        mRecyclerView.setAdapter(mMeetingListAdapter);

        FloatingActionButton btnFab = findViewById(R.id.btnFab);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingListActivity.this, DetailsMeetingActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        date = menu.findItem(R.id.date_visible_toolbar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        switch (item.getItemId()) {

            case R.id.filtre_date_croissante:
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetingsByOrderDate());
                mMeetingListAdapter.notifyDataSetChanged();
                date.setTitle("Tri par date croissante");
                return true;


            case R.id.filtre_date_décroissante:
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetingsByReverseOrderDate());
                mMeetingListAdapter.notifyDataSetChanged();
                date.setTitle("Tri par date décroissante");
                return true;


            case R.id.date_visible_toolbar:
                DatePickerDialog datePickerDialog = new DatePickerDialog(MeetingListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String d;
                                String m;
                                if (String.valueOf(dayOfMonth).length() == 1) {
                                    d = ("0" + dayOfMonth);
                                } else {
                                    d = String.valueOf(dayOfMonth);
                                }
                                if (String.valueOf(monthOfYear + 1).length() == 1) {
                                    m = ("0" + (monthOfYear + 1));
                                } else {
                                    m = String.valueOf(monthOfYear);
                                }
                                date.setTitle(d + "/"
                                        + m + "/" + year);
                                DateTime temp = new DateTime(year, monthOfYear + 1, dayOfMonth, 00, 00);

                                mMeetings.clear();
                                mMeetings.addAll(mApiService.getMeetingsByDate(temp));
                                mMeetingListAdapter.notifyDataSetChanged();
                            }
                        }, mYear, mMonth, mDay);

                if (date.getTitle() != "Tri par salle" && date.getTitle() != "Tri par date décroissante" && date.getTitle() != "Tri par date croissante" && date.getTitle() != "") {
                    datePickerDialog.show();
                }
                return true;


            case R.id.selection_date:
                datePickerDialog = new DatePickerDialog(MeetingListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String d;
                                String m;
                                if (String.valueOf(dayOfMonth).length() == 1) {
                                    d = ("0" + dayOfMonth);
                                } else {
                                    d = String.valueOf(dayOfMonth);
                                }
                                if (String.valueOf(monthOfYear + 1).length() == 1) {
                                    m = ("0" + (monthOfYear + 1));
                                } else {
                                    m = String.valueOf(monthOfYear);
                                }
                                date.setTitle(d + "/"
                                        + m + "/" + year);
                                DateTime time = new DateTime(year, monthOfYear + 1, dayOfMonth, 00, 00);
                                mMeetings.clear();
                                mMeetings.addAll(mApiService.getMeetingsByDate(time));
                                Log.e("", mMeetings.toString());

                                mMeetingListAdapter.notifyDataSetChanged();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                return true;


            case R.id.filtre_salle:
                mMeetings.clear();
                mMeetings.addAll(mApiService.getMeetingsByRoom());
                mMeetingListAdapter.notifyDataSetChanged();
                date.setTitle("Tri par salle");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


