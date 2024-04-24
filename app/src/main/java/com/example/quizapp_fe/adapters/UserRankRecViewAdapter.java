package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.entities.UserRank;
import com.example.quizapp_fe.models.CredentialToken;

import java.util.ArrayList;
import java.util.List;

public class UserRankRecViewAdapter extends RecyclerView.Adapter<UserRankRecViewAdapter.ViewHolder> {

    private List<UserRank> userList = new ArrayList<>();
    final Context context;

    public UserRankRecViewAdapter(Context context) {
        this.context = context;
    }

    public void setUserList(List<UserRank> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_ranking_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        UserRank user = userList.get(position);
        holder.txtNumber.setText(String.valueOf(user.getRank() ));
        holder.txtName.setText(user.getUsername());
        if (user.getId().equals(CredentialToken.getInstance(context).getUserProfile().getId())){
            holder.txtName.setTextColor(context.getResources().getColor(R.color.dark_yellow, null));
            holder.txtName.setText(String.format("%s (You)", user.getUsername()));
        }
        else {
            holder.txtName.setTextColor(context.getResources().getColor(R.color.black, null));
        }
        holder.txtScore.setText(String.valueOf(user.getPoint() + " points"));
        Glide.with(context)
                .asBitmap()
                .load(user.getAvatar())
                .error(R.drawable.default_avatar)
                .into(holder.imgAvatar);
        if(user.getRank() <= 3){
            holder.imgMedal.setVisibility(View.VISIBLE);
            switch (user.getRank()){
                case 1:
                    holder.imgMedal.setImageResource(R.drawable.gold_badge);
                    break;
                case 2:
                    holder.imgMedal.setImageResource(R.drawable.silver_badge);
                    break;
                case 3:
                    holder.imgMedal.setImageResource(R.drawable.bronze_badge);
                    break;
                default:
                    holder.imgMedal.setVisibility(View.GONE);
            }
        }

        if (user.getRank() >3){
            holder.imgMedal.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumber;
        TextView txtName;
        TextView txtScore;
        ImageView imgAvatar;
        ImageView imgMedal;

        LinearLayout parent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtName = itemView.findViewById(R.id.txtName);
            txtScore = itemView.findViewById(R.id.txtScore);
            imgAvatar = itemView.findViewById(R.id.avatarImageView);
            imgMedal = itemView.findViewById(R.id.imgViewMedal);
            parent = itemView.findViewById(R.id.user_ranking_card_view);
            // Initialize other views here
        }
    }
}