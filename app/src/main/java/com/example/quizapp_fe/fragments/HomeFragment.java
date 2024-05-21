package com.example.quizapp_fe.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.TeacherQuizActivity;
import com.example.quizapp_fe.adapters.LiveQuizAdapter;
import com.example.quizapp_fe.api.quiz.get.GetTeacherQuizzesApi;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.interfaces.LiveQuizCard;
import com.example.quizapp_fe.models.CredentialToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView liveQuizRecyclerView;
    ArrayList<LiveQuizCard> liveQuizCardList;
    LiveQuizAdapter liveQuizAdapter;
    ImageButton editQuizImageButton;
    TextView seeAllButtonTextView;
    String teacherId;
    ImageView avatarImageView;
    TextView userNameTextView;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (GetTeacherQuizzesApi.getTeacherQuizAPI(context).getTeacherQuiz(teacherId) != null) {
            GetTeacherQuizzesApi.getTeacherQuizAPI(context).getTeacherQuiz(teacherId).cancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        liveQuizRecyclerView = view.findViewById(R.id.homeFragLiveQuizzesRecylerView);
        seeAllButtonTextView = view.findViewById(R.id.homeFragSeeAllTextView);
        avatarImageView = view.findViewById(R.id.homeFragAvatarImageView);
        userNameTextView = view.findViewById(R.id.homeFragUserNameTextView);
        liveQuizCardList = new ArrayList<>();
        teacherId = CredentialToken.getInstance(context).getUserProfile().getId();
        callAPIGetQuiz(teacherId);
        setAvatar(teacherId);
        setUserName(teacherId);


        seeAllButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.animation_normal);
                seeAllButtonTextView.startAnimation(animation);
                Intent intent = new Intent(context, TeacherQuizActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void callAPIGetQuiz(String teacherId) {
         GetTeacherQuizzesApi.getTeacherQuizAPI(context).getTeacherQuiz(teacherId).enqueue(new Callback<ArrayList<Quiz>>() {
            @Override
            public void onResponse(Call<ArrayList<Quiz>> call, Response<ArrayList<Quiz>> response) {
                if (response.isSuccessful()){
                    ArrayList<Quiz> teacherQuizList = response.body();
                    assert  teacherQuizList != null;
                    for (int i = 0; i < teacherQuizList.size(); i++){
                        String quizTitle;
                        String quizSubTitle;
                        String quizImage;
                        String quizId;
                        quizTitle = teacherQuizList.get(i).getName();
                        quizSubTitle = teacherQuizList.get(i).getDescription();
                        quizImage = teacherQuizList.get(i).getBackgroundImage();
                        quizId = teacherQuizList.get(i).get_id();
                        LiveQuizCard liveQuizCard = new LiveQuizCard(quizImage,quizTitle,quizSubTitle,quizId);
                        liveQuizCardList.add(liveQuizCard);
                        liveQuizAdapter = new LiveQuizAdapter(context,liveQuizCardList);
                        liveQuizRecyclerView.setAdapter(liveQuizAdapter);
                        liveQuizRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Quiz>> call, Throwable t) {
                Toast.makeText(context, "Call API Failed", Toast.LENGTH_SHORT).show();
                Log.e("HomeFrament", "failed");
            }
        });
    }
    private void setAvatar(String teacherId) {
        String avatarUrl;
        avatarUrl = CredentialToken.getInstance(context).getUserProfile().getAvatar();
        Glide.with(context).load(avatarUrl).circleCrop().into(avatarImageView);
    }
    private void setUserName(String teacherId) {
        String userName;
        String firstName;
        String lastName;
        firstName = CredentialToken.getInstance(context).getUserProfile().getFirstName();
        lastName = CredentialToken.getInstance(context).getUserProfile().getLastName();
        userName = firstName + " "  + lastName;
        userNameTextView.setText(userName);
    }
}