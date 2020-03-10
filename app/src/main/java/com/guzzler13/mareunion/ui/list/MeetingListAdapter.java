package com.guzzler13.mareunion.ui.list;

import android.content.Intent;
import android.graphics.Color;
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
import com.guzzler13.mareunion.events.DeleteMeetingEvent;
import com.guzzler13.mareunion.model.Meeting;
import com.guzzler13.mareunion.ui.details.DetailsMeetingActivity;
import com.guzzler13.mareunion.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.ViewHolder> {

    public static boolean isListFilter = false;

    public static List<Meeting> filterList = new ArrayList<>();
    private List<Meeting> mMeetings;

    MeetingListAdapter(List<Meeting> items) {

        //On vide la liste filterList
        filterList.clear();

        //Si un filtre est déjà activé on le supprime pour les prochains filtres
        if (isListFilter) {

            isListFilter = false;
        }

        /* si un filtre est activé, on rempli la liste filterList avec les meetings correspondants */
        for (Meeting m : items) {
            if (m.isMeetingInFilterList()) {
                filterList.add(m);
                isListFilter = true;
            }
        }

        if (isListFilter) {
            mMeetings = filterList;
        } else mMeetings = items;
    }


    /* Première méthode RecyclerView : Renvoie le ViewHolder en prenant son constructeur pour le fabriquer
    *
    *  Cette Méthode convertit le XML représentant un item en java afin de le rendre exploitable dans le code */

    @NonNull
    @Override
    public MeetingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting, parent, false);

        return new ViewHolder(view);
    }


    /* Seconde Méthode : prend en paramètre le ViewHolder et la position de l'élément
    *
    *  Cette méthode permet de définir ce qui sera affiché pour chaque item, et c'est ici que l'on dit ce qu'il se passera au click sur un élément de l'item (méthode onClick)
    *
    * Pour chaque élément, grâce au ViewHolder qui récupère tous les champs de l'item, on peut leur affecter les valeurs des propriétés de la classe Meeting */

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        final Meeting meeting = mMeetings.get(position);

        /* Un item sur deux d'une couleur différente */
        if (position % 2 == 0) {
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#DCDCDC"));
        } else {
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }
        holder.mRoom.setText(meeting.getMeetingRoom().getmNameRoom());
        holder.mTime.setText(meeting.getDateBegin().toString("dd/MM HH:mm"));
        holder.mNameMeeting.setText(meeting.getName());
        holder.mParticipants.setText(meeting.getParticipants());

        Glide.with(holder.mImageView.getContext())
                .load(meeting.getMeetingRoom().getmRoomColor())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mImageView);


        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isListFilter) {
                    filterList.remove(meeting);
                    EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
                    notifyDataSetChanged();

                    /*Si liste filtrée vide, lancement activité avec liste principale*/
                    if (filterList.isEmpty() && isListFilter) {
                        Intent intent = new Intent(holder.itemView.getContext(), MeetingListActivity.class);
                        holder.itemView.getContext().startActivity(intent);
                        ToastUtils.showToastShort("La liste filtrée est vide", holder.itemView.getContext());
                    }

                    /* Sinon on se sert de l'event DeleteMeetingEvent */
                } else {
                    EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailsMeetingActivity.class);

                /*Injection de l'id dans l'intent pour pouvoir récupérer le bon item*/
                intent.putExtra("id", mMeetings.indexOf(meeting));

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    /* 3ème méthode RecyclerView : Récupère le nombre d'éléments */
    @Override
    public int getItemCount() {
        return mMeetings.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
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
            mParticipants = view.findViewById(R.id.participants_email_meeting_list);
            mNameMeeting = view.findViewById(R.id.meeting_name_meeting_list);
            mTime = view.findViewById(R.id.time_textView);
            mRoom = view.findViewById(R.id.room_textView);
        }
    }
}
