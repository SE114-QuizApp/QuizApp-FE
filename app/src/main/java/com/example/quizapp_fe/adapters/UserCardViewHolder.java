package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.api.user.getUserById.GetUserByIdApi;
import com.example.quizapp_fe.api.user.updateUserFriend.UpdateUserFriendApi;
import com.example.quizapp_fe.api.user.updateUserUnfriend.UpdateUserUnfriendApi;
import com.example.quizapp_fe.entities.User;
import com.example.quizapp_fe.interfaces.UserCard;
import com.example.quizapp_fe.models.CredentialToken;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCardViewHolder extends RecyclerView.ViewHolder {
    private Context viewHolderContext;
    ImageView userCardItemImageView;
    TextView userCardItemName;
    TextView userCardItemEmail;
    ImageView userCardItemFollowImageView;
    ArrayList<String> myFriends;
    private String myId;
    boolean isFollowing;

    public UserCardViewHolder(@NonNull View itemView) {
        super(itemView);
        viewHolderContext = itemView.getContext();
        userCardItemImageView = itemView.findViewById(R.id.userCardItemImageView);
        userCardItemName = itemView.findViewById(R.id.userCardItemNameTextView);
        userCardItemEmail = itemView.findViewById(R.id.userCardItemEmailTextView);
        userCardItemFollowImageView = itemView.findViewById(R.id.userCardItemFollowImageView);
        myId = CredentialToken.getInstance(viewHolderContext).getUserProfile().getId();
        myFriends = new ArrayList<>();
        callGetMyFriendList();
    }
    public void bind(@NonNull UserCard userCard, Context context){
        Glide.with(context)
             .asBitmap()
                     .load(userCard.getUserCardImage())
                     .error(R.drawable.img_lookup)
                     .into(userCardItemImageView);
        userCardItemName.setText(userCard.getUserCardName());
        userCardItemEmail.setText(userCard.getUserCardEmail());

        String friendId = userCard.getUserCardId();

        isFollowing = myFriends.contains(friendId);

        updateUserCardFollowImageView();

        if(isFollowing){
            userCardItemFollowImageView.setImageResource(R.drawable.ic_delete_friend_512);
        } else  {
            userCardItemFollowImageView.setImageResource(R.drawable.ic_follow_512);
        }
        userCardItemFollowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.animation_normal);
                userCardItemFollowImageView.startAnimation(animation);
                if (isFollowing) {
                    callApiUpdateUserUnFriend(friendId);
                    myFriends.remove(friendId);
                } else {
                    callApiUpdateUserFriend(friendId);
                    myFriends.add(friendId);
                }

                isFollowing = !isFollowing;
                updateUserCardFollowImageView();
            }
        });
    }
    private void callApiUpdateUserFriend(String friendId) {
        UpdateUserFriendApi.putAPI(itemView.getContext()).updateUserFriend(friendId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
    private void callApiUpdateUserUnFriend(String friendId) {
        UpdateUserUnfriendApi.putAPI(itemView.getContext()).updateUserUnfriend(friendId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
    public void callGetMyFriendList() {
        GetUserByIdApi.getAPI(itemView.getContext()).getUserById(myId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User me = response.body();
                myFriends = me.getFriends();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
    private void updateUserCardFollowImageView() {
        if (isFollowing) {
            userCardItemFollowImageView.setImageResource(R.drawable.ic_delete_friend_512);
        } else {
            userCardItemFollowImageView.setImageResource(R.drawable.ic_follow_512);
        }
    }
}