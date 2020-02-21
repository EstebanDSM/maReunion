package com.guzzler13.mareunion.ui.details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.guzzler13.mareunion.R;
import com.guzzler13.mareunion.di.DI;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.model.Room;
import com.guzzler13.mareunion.service.MeetingApiService;
import com.guzzler13.mareunion.ui.list.MeetingListActivity;
import com.guzzler13.mareunion.utils.AddChip;
import com.guzzler13.mareunion.utils.SetColorMeeting;
import com.guzzler13.mareunion.utils.SetID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.guzzler13.mareunion.utils.AutocompleteTextViewAdapter.AutocompleteTextViewAdapter;
import static com.guzzler13.mareunion.utils.TimeUtils.beginTimeHandle;
import static com.guzzler13.mareunion.utils.TimeUtils.dateHandle;
import static com.guzzler13.mareunion.utils.TimeUtils.endTimeHandle;

public class DetailsMeetingActivity extends AppCompatActivity {

    private MeetingApiService mApiService = DI.getMeetingApiService();
    private Spinner mMeetingRoomsSpinner;
    private TextView mMeetingName;
    private TextView mDateEdit;
    private TextView mBeginTimeEdit;
    private TextView mEndTimeEdit;
    private ChipGroup mParticipantsChipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_meeting);

        TextView mTextParticipants = findViewById(R.id.textView4);
        TextInputLayout mTextEmails = findViewById(R.id.textInputLayout);
        mMeetingRoomsSpinner = findViewById(R.id.spinner);
        mMeetingName = findViewById(R.id.meetingName_editText);
        mDateEdit = findViewById(R.id.dateEdit_TextView);
        mBeginTimeEdit = findViewById(R.id.beginTimeEdit_TextView);
        mEndTimeEdit = findViewById(R.id.endTimeEdit_TextView);
        AutoCompleteTextView mParticipantsAutoCompleteTextView = findViewById(R.id.participant_autoCompleteTextView);
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
                    tv.setTextSize(25);
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
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundColor(getResources().getColor(R.color.colorBlack));
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


        //AutocompleteTextViewAdapter + chips to add the participants :
        AutocompleteTextViewAdapter(mParticipantsAutoCompleteTextView, this, addParticipantButton, mParticipantsChipGroup, getDrawable(R.drawable.ic_person_pin_black_18dp));

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
                mMeetingName.setTextSize(65);
            } else if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_NORMAL) {
                mMeetingName.setTextSize(35);
            } else if ((getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_LARGE) {
                mMeetingName.setTextSize(55);
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
            mTextParticipants.setText("Nom de la salle :");
            mTextEmails.setHint("Participant(s) :");
            mButtonSave.setVisibility(View.GONE);
            mMeetingRoomsSpinner.setEnabled(false);
            mParticipantsAutoCompleteTextView.setEnabled(false);
            addParticipantButton.setVisibility(View.GONE);


            String[] parts = mParticipants.split(", ");
            for (String part : parts) {
                final Chip chip = AddChip.addChip(part, mParticipantsChipGroup, getDrawable(R.drawable.ic_person_pin_black_18dp));

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
                    dateHandle(mDateEdit);
                }
            });

            mBeginTimeEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    beginTimeHandle(mBeginTimeEdit, getApplicationContext());
                }
            });

            mEndTimeEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    endTimeHandle(mEndTimeEdit, getApplicationContext());
                }
            });

            mButtonSave.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {

                    //Gestion des cas où l'utilisateur ne remplit pas tous les champs
                    if (mMeetingName.getText().toString().equals("")) {
                        showToast("Veuillez SVP nommer votre réunion");
                    } else if (mDateEdit.getText().toString().equals("")) {
                        showToast("Veuillez SVP définir une date");
                    } else if (mBeginTimeEdit.getText().toString().equals("")) {
                        showToast("Veuillez SVP définir l'heure de début");
                    } else if (mEndTimeEdit.getText().toString().equals("")) {
                        showToast("Veuillez SVP définir l'heure de fin");
                    } else if (mMeetingRoomsSpinner.getSelectedItem().toString().equals("Veuillez choisir une salle")) {
                        showToast("Veuillez SVP choisir une salle");
                    } else if (mParticipantsChipGroup.getChildCount() == 0) {
                        showToast("Veuillez SVP renseigner les adresses mail des participants");
                    } else {

                        //String vers DateTime
                        DateTimeFormatter formatterDate = DateTimeFormat.forPattern("dd/MM/yyyy");
                        DateTime mDateEditJoda = formatterDate.parseDateTime(mDateEdit.getText().toString());

                        DateTimeFormatter formatterHour = DateTimeFormat.forPattern("HH:mm");
                        DateTime mBeginTimeEditJoda = formatterHour.parseDateTime(mBeginTimeEdit.getText().toString());
                        DateTime mEndTimeEditJoda = formatterHour.parseDateTime(mEndTimeEdit.getText().toString());

                        DateTime mBeginCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mBeginTimeEditJoda.getHourOfDay(), mBeginTimeEditJoda.getMinuteOfHour());
                        DateTime mEndCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mEndTimeEditJoda.getHourOfDay(), mEndTimeEditJoda.getMinuteOfHour());

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
                                SetID.SetId(mApiService),
                                mMeetingName.getText().toString(),
                                new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mBeginTimeEditJoda.getHourOfDay(), mBeginTimeEditJoda.getMinuteOfHour()),
                                new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mEndTimeEditJoda.getHourOfDay(), mEndTimeEditJoda.getMinuteOfHour()),
                                mParticipants,
                                new Room(mMeetingRoomsSpinner.getSelectedItem().toString(), SetColorMeeting.SetColor(mMeetingRoomsSpinner.getSelectedItem().toString())));


                        //Gestion de la disponibilité des salles
                        for (Meeting m : mApiService.getMeetings()) {

                            if (m.getMeetingRoom().getmNameRoom().equals(mMeetingRoomsSpinner.getSelectedItem().toString()) &&

                                    ((mBeginCompleteJoda.isBefore(m.getDateEnd()) && mBeginCompleteJoda.isAfter(m.getDateBegin()))
                                            || (mEndCompleteJoda.isBefore(m.getDateEnd()) && mEndCompleteJoda.isAfter(m.getDateBegin()))
                                            || mBeginCompleteJoda.isEqual(m.getDateBegin())
                                            || mEndCompleteJoda.isEqual(m.getDateEnd())
                                            || m.getDateBegin().isAfter(mBeginCompleteJoda) && m.getDateBegin().isBefore(mEndCompleteJoda)
                                            || m.getDateEnd().isBefore(mEndCompleteJoda) && m.getDateEnd().isAfter(mBeginCompleteJoda))) {


                                Context context = getApplicationContext();
                                CharSequence text = "Cette salle est déjà réservée";
                                int duration = Toast.LENGTH_LONG;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                break;


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

    // La flèche / toolbar a le même comportement que le BACK
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showToast(String string) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, string, duration);
        toast.show();
    }
}

