package com.guzzler13.mareunion.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.guzzler13.mareunion.R;
import com.guzzler13.mareunion.model.Meeting;

import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.ViewHolder> {

    List<Meeting> mMeetings;


    MeetingListAdapter(List<Meeting> items) {


        mMeetings = items;

    }

    @NonNull
    @Override
    public MeetingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.mRoom.setText(meeting.getMeetingRoom());
        holder.mTime.setText(meeting.getDateBegin());
        holder.mNameMeeting.setText(meeting.getName());
        holder.mParticipants.setText(meeting.getParticipants());

//        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mApiService.deleteMeeting(meeting);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.meeting_delete_button_meeting_list)
        public ImageButton mDeleteButton;
//        @BindView(R.id.imageView)
        public ImageView mImageView;
//        @BindView(R.id.participants_email_meeting_list)
        public TextView mParticipants;
//        @BindView(R.id.meeting_name_meeting_list)
        public TextView mNameMeeting;
//        @BindView(R.id.time_textView)
        public TextView mTime;
//        @BindView(R.id.room_textView)
        public TextView mRoom;


        ViewHolder(View view) {
            super(view);
//            ButterKnife.bind(this, view);
            mDeleteButton = view.findViewById(R.id.meeting_delete_button_meeting_list);
            mImageView = view.findViewById(R.id.imageView);
            mParticipants = view.findViewById(R.id.participants_email_meeting_list);
            mNameMeeting = view.findViewById(R.id.meeting_name_meeting_list);
            mTime = view.findViewById(R.id.time_textView);
            mRoom = view.findViewById(R.id.room_textView);
        }
    }
}
