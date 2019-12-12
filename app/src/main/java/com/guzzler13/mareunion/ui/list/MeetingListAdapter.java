package com.guzzler13.mareunion.ui.list;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.guzzler13.mareunion.R;
import com.guzzler13.mareunion.di.DI;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.service.MeetingApiService;
import com.guzzler13.mareunion.ui.details.DetailsMeetingActivity;

import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.ViewHolder> {

    private List<Meeting> mMeetings;
    private MeetingApiService mApiService = DI.getMeetingApiService();


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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final Meeting meeting = mMeetings.get(position);

        if (position % 2 == 0) {
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#DCDCDC"));
        }
        holder.mRoom.setText(meeting.getMeetingRoom().getmNameRoom());
        holder.mTime.setText(meeting.getDateBegin().toString("dd/MM kk:mm"));
        holder.mNameMeeting.setText(meeting.getName());
        holder.mParticipants.setText(meeting.getParticipants());

        Glide.with(holder.mImageView.getContext())
                .load(meeting.getMeetingRoom().getmRoomColor())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mImageView);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mApiService.deleteMeeting(meeting);
                int sizeList = mMeetings.size();
                Log.e("size", Integer.toString(sizeList));
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsMeetingActivity.class);
                intent.putExtra("id", mMeetings.indexOf(meeting));

                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ImageButton mDeleteButton;
        ImageView mImageView;
        TextView mParticipants;
        TextView mNameMeeting;
        TextView mTime;
        TextView mRoom;


        ViewHolder(View view) {
            super(view);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);
            mImageView = view.findViewById(R.id.imageView);
            mDeleteButton = view.findViewById(R.id.meeting_delete_button_meeting_list);
            mImageView = view.findViewById(R.id.imageView);
            mParticipants = view.findViewById(R.id.participants_email_meeting_list);
            mNameMeeting = view.findViewById(R.id.meeting_name_meeting_list);
            mTime = view.findViewById(R.id.time_textView);
            mRoom = view.findViewById(R.id.room_textView);
        }
    }
}
