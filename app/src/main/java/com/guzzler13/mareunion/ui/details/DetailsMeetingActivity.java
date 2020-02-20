package com.guzzler13.mareunion.ui.details;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.guzzler13.mareunion.R;
import com.guzzler13.mareunion.di.DI;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.model.Room;
import com.guzzler13.mareunion.service.MeetingApiService;
import com.guzzler13.mareunion.service.RoomGenerator;
import com.guzzler13.mareunion.ui.list.MeetingListActivity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class DetailsMeetingActivity extends AppCompatActivity {

    private MeetingApiService mApiService = DI.getMeetingApiService();
    private Spinner mMeetingRoomsSpinner;
    private TextView mMeetingName;
    private TextView mDateEdit;
    private TextView mBeginTimeEdit;
    private TextView mEndTimeEdit;
    private AutoCompleteTextView mParticipantsAutoCompleteTextView;
    private ChipGroup mParticipantsChipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_meeting);

        mMeetingRoomsSpinner = findViewById(R.id.spinner);
        mMeetingName = findViewById(R.id.meetingName_editText);
        mDateEdit = findViewById(R.id.dateEdit_TextView);
        mBeginTimeEdit = findViewById(R.id.beginTimeEdit_TextView);
        mEndTimeEdit = findViewById(R.id.endTimeEdit_TextView);
        mParticipantsAutoCompleteTextView = findViewById(R.id.participant_autoCompleteTextView);
        mParticipantsChipGroup = findViewById(R.id.chipGroup);
        Button addParticipantButton = findViewById(R.id.addParticipant_button);
        Button mButtonSave = findViewById(R.id.saveMetting_button);

        //Spinner to choose meeting room :
        final ArrayList<String> meetingRooms = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.meeting_rooms_arrays)));
        meetingRooms.add(0, getString(R.string.choose_meetingRoom));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, meetingRooms) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner, first item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);// Set the hint text color gray
                    tv.setTextSize(18);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        mMeetingRoomsSpinner.setAdapter(spinnerAdapter);
        mMeetingRoomsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0 && view != null) {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Affichage toolbar avec flèche retour
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //AutocompleteTextView + chips to add the participants :
        ArrayAdapter<CharSequence> adapterParticipants = ArrayAdapter.createFromResource(this,
                R.array.participants_arrays, android.R.layout.simple_dropdown_item_1line);
        mParticipantsAutoCompleteTextView.setAdapter(adapterParticipants);
        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mParticipantsAutoCompleteTextView.getText() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(mParticipantsAutoCompleteTextView.getWindowToken(), 1);

                    String participant;
                    participant = mParticipantsAutoCompleteTextView.getText().toString();

                    // Vérifier que l'utilisateur renseigne un e-mail
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(participant).matches() || participant.isEmpty()) {
                        Toast.makeText(DetailsMeetingActivity.this, R.string.its_not_an_email_message
                                , Toast.LENGTH_SHORT).show();
                    } else {

                        final Chip chip = addChip(participant);
                        chip.setChipBackgroundColorResource(R.color.colorGreen);
                        mParticipantsChipGroup.addView(chip);
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mParticipantsChipGroup.removeView(chip);
                            }
                        });
                    }
                }
                mParticipantsAutoCompleteTextView.setText("");
            }
        });

        int id = getIntent().getIntExtra("id", -1);


        // click à partir d'un item
        if (id != -1) {

            Meeting meeting = mApiService.getMeetings().get(id);

            int position = spinnerAdapter.getPosition(meeting.getMeetingRoom().getmNameRoom());
            mMeetingRoomsSpinner.setSelection(position);

            String mParticipants = meeting.getParticipants();
            String nameReunion = meeting.getName();
            String dateTime = meeting.getDateBegin().toString("dd/MM/yyyy");
            String hourBegin = meeting.getDateBegin().toString("HH:mm");
            String hourEnd = meeting.getDateEnd().toString("HH:mm");
            mMeetingName.setText(nameReunion);

            //Taille police mMeetingName suivant taille écran
            if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                mMeetingName.setTextSize(60);
            } else if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_NORMAL) {
                mMeetingName.setTextSize(40);
            } else if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_LARGE) {
                mMeetingName.setTextSize(50);
            }

            mMeetingName.setEnabled(false);
            mMeetingName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            mMeetingName.setTextColor(getResources().getColor(R.color.colorBlack));
            mDateEdit.setText(dateTime);
            mDateEdit.setTextColor(getResources().getColor(R.color.colorBlack));
            mBeginTimeEdit.setText(hourBegin);
            mBeginTimeEdit.setTextColor(getResources().getColor(R.color.colorBlack));
            mEndTimeEdit.setText(hourEnd);
            mEndTimeEdit.setTextColor(getResources().getColor(R.color.colorBlack));
            mButtonSave.setVisibility(View.GONE);
            mMeetingRoomsSpinner.setEnabled(false);
            mParticipantsAutoCompleteTextView.setEnabled(false);
            addParticipantButton.setVisibility(View.GONE);


            String[] parts = mParticipants.split(", ");
            for (String part : parts) {
                final Chip chip = addChip(part);

                mParticipantsChipGroup.addView(chip);
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setCloseIconVisible(false);
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mParticipantsChipGroup.removeView(chip);
                    }
                });
            }

        } else {

//             click à partir du FLOAT (création ou modif meeting)

            mMeetingName.setHint(R.string.meeting_name);

            mDateEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dateHandle();
                }
            });

            mBeginTimeEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BegintimeHandle();
                }
            });

            mEndTimeEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EndtimeHandle();
                }
            });

            mButtonSave.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {


                    //Gestion des cas où l'utilisateur ne remplit pas tous les champs
                    if (mMeetingName.getText().toString().equals("")) {
                        Context context = getApplicationContext();
                        CharSequence text = "Veuillez SVP nommer votre réunion";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (mDateEdit.getText().toString().equals("")) {
                        Context context = getApplicationContext();
                        CharSequence text = "Veuillez SVP définir une date";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (mBeginTimeEdit.getText().toString().equals("")) {
                        Context context = getApplicationContext();
                        CharSequence text = "Veuillez SVP définir l'heure de début";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (mEndTimeEdit.getText().toString().equals("")) {
                        Context context = getApplicationContext();
                        CharSequence text = "Veuillez SVP définir l'heure de fin";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (mMeetingRoomsSpinner.getSelectedItem().toString().equals("Veuillez choisir une salle")) {
                        Context context = getApplicationContext();
                        CharSequence text = "Veuillez SVP choisir une salle";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else if (mParticipantsChipGroup.getChildCount() == 0) {
                        Context context = getApplicationContext();
                        CharSequence text = "Veuillez SVP renseigner les adresses mail des participants";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {

                        //String vers DateTime
                        DateTimeFormatter formatterDate = DateTimeFormat.forPattern("dd/MM/yyyy");
                        DateTime mDateEditJoda = formatterDate.parseDateTime(mDateEdit.getText().toString());

                        DateTimeFormatter formatterHour = DateTimeFormat.forPattern("HH:mm");
                        DateTime mBeginTimeEditJoda = formatterHour.parseDateTime(mBeginTimeEdit.getText().toString());
                        DateTime mEndTimeEditJoda = formatterHour.parseDateTime(mEndTimeEdit.getText().toString());

                        DateTime mBeginCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mBeginTimeEditJoda.getHourOfDay(), mBeginTimeEditJoda.getMinuteOfHour());
                        DateTime mEndCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mEndTimeEditJoda.getHourOfDay(), mEndTimeEditJoda.getMinuteOfHour());

                        // Donner un id à la réunion différent de ceux déjà existants :
                        int id = 0;
                        for (int i = 0; i < mApiService.getMeetings().size(); i++) {
                            id++;
                        }

                        //Donner la bonne couleur à la réunion en fonction du nom de la salle
                        int color = 0;
                        String mSelected = mMeetingRoomsSpinner.getSelectedItem().toString();
                        String mTarget;

                        for (int i = 0; i < RoomGenerator.generateRooms().size(); i++) {
                            mTarget = RoomGenerator.generateRooms().get(i).getmNameRoom();
                            if (mSelected.equals(mTarget)) {
                                color = RoomGenerator.generateRooms().get(i).getmRoomColor();
                            }
                        }

                        //Créer la liste des participants sous forme de String séparés par des virgules
                        String mParticipants = "";
                        for (int i = 0; i < mParticipantsChipGroup.getChildCount(); i++) {
                            Chip chip = (Chip) mParticipantsChipGroup.getChildAt(i);
                            if (mParticipantsChipGroup.getChildAt(i).getId() == 1) {
                                mParticipants = chip.getText().toString().concat(mParticipants);
                            } else
                                mParticipants = chip.getText().toString().concat(", " + mParticipants);
                        }

                        //Création nouveau meeting
                        Meeting meeting = new Meeting(
                                id,
                                mMeetingName.getText().toString(),
                                new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mBeginTimeEditJoda.getHourOfDay(), mBeginTimeEditJoda.getMinuteOfHour()),
                                new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mEndTimeEditJoda.getHourOfDay(), mEndTimeEditJoda.getMinuteOfHour()),
                                mParticipants,
                                new Room(mMeetingRoomsSpinner.getSelectedItem().toString(), color)
                        );


                        //Gestion de la disponibilité des salles
                        for (Meeting m : mApiService.getMeetings()) {
                            DateTime dateTime = new DateTime(DateTime.now());
                            if (m.getMeetingRoom().getmNameRoom().equals(mMeetingRoomsSpinner.getSelectedItem().toString())) {
                                if ((mBeginCompleteJoda.isBefore(m.getDateEnd()) && mBeginCompleteJoda.isAfter(m.getDateBegin()))
                                        || (mEndCompleteJoda.isBefore(m.getDateEnd()) && mEndCompleteJoda.isAfter(m.getDateBegin()))
                                        || mBeginCompleteJoda.isEqual(m.getDateBegin())
                                        || mEndCompleteJoda.isEqual(m.getDateEnd())
                                        || m.getDateBegin().isAfter(mBeginCompleteJoda) && m.getDateBegin().isBefore(mEndCompleteJoda)
                                        || m.getDateEnd().isBefore(mEndCompleteJoda) && m.getDateEnd().isAfter(mBeginCompleteJoda)) {

                                    Context context = getApplicationContext();
                                    CharSequence text = "Cette salle est déjà réservée";
                                    int duration = Toast.LENGTH_LONG;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    break;
                                } else if (mBeginCompleteJoda.isEqualNow() || mBeginCompleteJoda.isBefore(dateTime.plusMinutes(17))) {
                                    Context context = getApplicationContext();
                                    CharSequence text = "Attention il faut en moyenne 17 min pour trouver la salle";
                                    int duration = Toast.LENGTH_LONG;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    break;
                                }





                                else {
                                    mApiService.addMeeting(meeting);
                                    mParticipantsChipGroup.removeAllViews();
                                    Intent intent = new Intent(DetailsMeetingActivity.this, MeetingListActivity.class);
                                    startActivity(intent);
                                    break;
                                }
                            } else {
                                mApiService.addMeeting(meeting);
                                mParticipantsChipGroup.removeAllViews();

                                Intent intent = new Intent(DetailsMeetingActivity.this, MeetingListActivity.class);
                                startActivity(intent);
                                break;
                            }
                        }


                    }
                }
            });
        }
    }

    private Chip addChip(String participant) {
        final Chip chip = new Chip(mParticipantsChipGroup.getContext());
        chip.setChipBackgroundColorResource(R.color.colorGrey);
        chip.setText(participant);
        chip.setChipIcon(getDrawable(R.drawable.ic_person_pin_black_18dp));
        chip.setCheckable(false);
        chip.setClickable(true);
        chip.setCloseIconVisible(true);
        return chip;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void BegintimeHandle() {

        final Calendar calendar1 = Calendar.getInstance();

        int HOUR = calendar1.get(Calendar.HOUR_OF_DAY);
        int MINUTE = calendar1.get(Calendar.MINUTE);

        boolean is24hourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                String timeString = hour + " " + minute;
                mBeginTimeEdit.setText(timeString);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY, hour);
                calendar1.set(Calendar.MINUTE, minute);

                CharSequence timeTexte = DateFormat.format("HH:mm", calendar1);

                mBeginTimeEdit.setText(timeTexte);

            }
        }, HOUR, MINUTE, is24hourFormat);

        timePickerDialog.show();
    }

    public void EndtimeHandle() {

        final Calendar calendar1 = Calendar.getInstance();

        int HOUR = calendar1.get(Calendar.HOUR_OF_DAY);
        int MINUTE = calendar1.get(Calendar.MINUTE);

        boolean is24hourFormat = DateFormat.is24HourFormat(this);
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String timeString = hour + " " + minute;
                mEndTimeEdit.setText(timeString);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY, hour);
                calendar1.set(Calendar.MINUTE, minute);
                CharSequence timeTexte = DateFormat.format("HH:mm", calendar1);
                mEndTimeEdit.setText(timeTexte);
            }
        }, HOUR, MINUTE, is24hourFormat);
        timePickerDialog.show();
    }

    public void dateHandle() {
        final Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                String dateString = year + " " + month + " " + " " + date;
                mDateEdit.setText(dateString);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                CharSequence dateTexte = DateFormat.format("dd/MM/yyyy", calendar1);
                mDateEdit.setText(dateTexte);
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}

