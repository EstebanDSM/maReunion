package com.guzzler13.mareunion.ui.details;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.guzzler13.mareunion.R;
import com.guzzler13.mareunion.di.DI;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.service.MeetingApiService;
import com.guzzler13.mareunion.ui.list.MeetingListActivity;
import com.guzzler13.mareunion.ui.list.MeetingListAdapter;
import com.guzzler13.mareunion.utils.ChipUtils;
import com.guzzler13.mareunion.utils.MeetingUtils;
import com.guzzler13.mareunion.utils.ToastUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.guzzler13.mareunion.ui.list.MeetingListAdapter.isListFilter;
import static com.guzzler13.mareunion.utils.AutocompleteTextViewAdapterUtils.Autocomplete;
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
        final TextInputLayout mTextEmails = findViewById(R.id.textInputLayout);
        mMeetingRoomsSpinner = findViewById(R.id.spinner);
        mMeetingName = findViewById(R.id.meetingName_editText);
        mDateEdit = findViewById(R.id.dateEdit_TextView);
        mBeginTimeEdit = findViewById(R.id.beginTimeEdit_TextView);
        mEndTimeEdit = findViewById(R.id.endTimeEdit_TextView);
        AutoCompleteTextView mParticipantsAutoCompleteTextView = findViewById(R.id.participant_autoCompleteTextView);
        mParticipantsChipGroup = findViewById(R.id.chipGroup);
        Button addParticipantButton = findViewById(R.id.addParticipant_button);
        Button mButtonSave = findViewById(R.id.saveMetting_button);

        /* Spinner to choose meeting room : */
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

        /* Traitement du menu spinner */
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

        /* Affichage toolbar avec flèche retour */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        /* Autocomplete + chips to add the participants : */
        Autocomplete(mParticipantsAutoCompleteTextView, this, addParticipantButton, mParticipantsChipGroup, getDrawable(R.drawable.ic_person_pin_black_18dp));

        int id = getIntent().getIntExtra("id", -1);

        /* click à partir d'un item */
        if (id != -1) {

            Meeting meeting;
            if (isListFilter) {
                meeting = MeetingListAdapter.filterList.get(id);
            } else {
                meeting = mApiService.getMeetings().get(id);
            }

            int position = spinnerAdapter.getPosition(meeting.getMeetingRoom().getmNameRoom());
            mMeetingRoomsSpinner.setSelection(position);

            String mParticipants = meeting.getParticipants();
            String nameReunion = meeting.getName();
            String dateTime = meeting.getDateBegin().toString("dd/MM/yyyy");
            String hourBegin = meeting.getDateBegin().toString("HH:mm");
            String hourEnd = meeting.getDateEnd().toString("HH:mm");
            mMeetingName.setText(nameReunion);

            /* Taille police mMeetingName suivant taille écran */
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
            mTextParticipants.setText(R.string.nameRoom);
            mTextEmails.setHint("Participant(s) :");
            mButtonSave.setVisibility(View.GONE);
            mMeetingRoomsSpinner.setEnabled(false);
            mParticipantsAutoCompleteTextView.setEnabled(false);
            addParticipantButton.setVisibility(View.GONE);

            /* Récupération des participants en splitant à l'endroit de la virgule */
            String[] parts = mParticipants.split(getString(R.string.commaParticipants));
            for (String part : parts) {
                final com.google.android.material.chip.Chip chip = ChipUtils.addChip(part, mParticipantsChipGroup, getDrawable(R.drawable.ic_person_pin_black_18dp));

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

            /* click à partir du FLOAT (création meeting) */

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

                    /* Gestion des cas où l'utilisateur ne remplit pas tous les champs */
                    if (mMeetingName.getText().toString().equals("")) {
                        ToastUtils.showToastLong("Veuillez SVP nommer votre réunion", getApplicationContext());
                    } else if (mDateEdit.getText().toString().equals("")) {
                        ToastUtils.showToastLong("Veuillez SVP définir une date", getApplicationContext());
                    } else if (mBeginTimeEdit.getText().toString().equals("")) {
                        ToastUtils.showToastLong("Veuillez SVP définir l'heure de début", getApplicationContext());
                    } else if (mEndTimeEdit.getText().toString().equals("")) {
                        ToastUtils.showToastLong("Veuillez SVP définir l'heure de fin", getApplicationContext());
                    } else if (mMeetingRoomsSpinner.getSelectedItem().toString().equals("Veuillez choisir une salle")) {
                        ToastUtils.showToastLong("Veuillez SVP choisir une salle", getApplicationContext());
                    } else if (mParticipantsChipGroup.getChildCount() == 0) {
                        ToastUtils.showToastLong("Veuillez SVP renseigner les adresses mail des participants", getApplicationContext());
                    } else {


                        /* String vers DateTime */
                        DateTimeFormatter formatterDate = DateTimeFormat.forPattern("dd/MM/yyyy");
                        DateTime mDateEditJoda = formatterDate.parseDateTime(mDateEdit.getText().toString());

                        DateTimeFormatter formatterHour = DateTimeFormat.forPattern("HH:mm");
                        DateTime mBeginTimeEditJoda = formatterHour.parseDateTime(mBeginTimeEdit.getText().toString());
                        DateTime mEndTimeEditJoda = formatterHour.parseDateTime(mEndTimeEdit.getText().toString());

                        DateTime mBeginCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mBeginTimeEditJoda.getHourOfDay(), mBeginTimeEditJoda.getMinuteOfHour());
                        DateTime mEndCompleteJoda = new DateTime(mDateEditJoda.getYear(), mDateEditJoda.getMonthOfYear(), mDateEditJoda.getDayOfMonth(), mEndTimeEditJoda.getHourOfDay(), mEndTimeEditJoda.getMinuteOfHour());


                        /* Créer la liste des participants dans la liste des réunions sous forme de String séparés par des virgules */
                        String mParticipants = "";
                        for (int i = 0; i < mParticipantsChipGroup.getChildCount(); i++) {
                            com.google.android.material.chip.Chip chip = (com.google.android.material.chip.Chip) mParticipantsChipGroup.getChildAt(i);
                            if (i == 0) {
                                mParticipants = chip.getText().toString().concat(mParticipants);
                            } else {
                                mParticipants = chip.getText().toString().concat(", " + mParticipants);
                            }
                        }

                        /* Création nouveau meeting */
                        Meeting meeting = MeetingUtils.newMeeting(mApiService, mMeetingName, mDateEditJoda, mBeginTimeEditJoda, mEndTimeEditJoda, mParticipants, mMeetingRoomsSpinner);

                        /* Gestion de la disponibilité des salles */
                        boolean timeProblem = false;
                        boolean reserved = false;
                        for (Meeting m : mApiService.getMeetings()) {
                            if (m.getMeetingRoom().getmNameRoom().equals(mMeetingRoomsSpinner.getSelectedItem().toString()) &&
                                    ((mBeginCompleteJoda.isBefore(m.getDateEnd()) && mBeginCompleteJoda.isAfter(m.getDateBegin()))
                                            || (mEndCompleteJoda.isBefore(m.getDateEnd()) && mEndCompleteJoda.isAfter(m.getDateBegin()))
                                            || mBeginCompleteJoda.isEqual(m.getDateBegin())
                                            || mEndCompleteJoda.isEqual(m.getDateEnd())
                                            || m.getDateBegin().isAfter(mBeginCompleteJoda) && m.getDateBegin().isBefore(mEndCompleteJoda)
                                            || m.getDateEnd().isBefore(mEndCompleteJoda) && m.getDateEnd().isAfter(mBeginCompleteJoda))) {
                                reserved = true;
                                break;
                            } else if (mBeginCompleteJoda.isAfter(mEndCompleteJoda) || mBeginCompleteJoda.isEqual(mEndCompleteJoda)) {

                                timeProblem = true;
                            }
                        }
                        if (timeProblem) {
                            ToastUtils.showToastLong("Veuillez vérifier les heures de début et de fin", getApplicationContext());
                        } else if (reserved) {

                            ToastUtils.showToastLong("Cette salle est déjà réservée", getApplicationContext());

                        } else {
                            mApiService.addMeeting(meeting);
                            Intent intent = new Intent(DetailsMeetingActivity.this, MeetingListActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    /* La flèche sur la toolbar a le même comportement que la touche BACK du téléphone */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}