package com.example.quizapp_fe.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
    ConstraintLayout myQuizzesConstraintLayout;
    String teacherId;
    ImageView avatarImageView;
    TextView userNameTextView;
    ImageButton addNewQuizImageButton;
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
        myQuizzesConstraintLayout = view.findViewById(R.id.homeFragMyQuizzesConstraintLayout);
        liveQuizCardList = new ArrayList<>();
        teacherId = CredentialToken.getInstance(context).getUserProfile().getId();
        callAPIGetQuiz(teacherId);
//        if (liveQuizCardList.size() == 0) {
//            TextView noQuizReminderTextView;
//            noQuizReminderTextView = new TextView(context);
//            noQuizReminderTextView.setText("You have no Quiz");
//            noQuizReminderTextView.setTextSize(20);
//            noQuizReminderTextView.setId(View.generateViewId());
//            Typeface typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
//            noQuizReminderTextView.setTypeface(typeface);
//            noQuizReminderTextView.setPadding(20,20,20,20);
//            noQuizReminderTextView.setTextColor(Color.BLACK);
//            myQuizzesConstraintLayout.addView(noQuizReminderTextView);
//
//        }
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
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeMainContainerFrameLayout, profileFragment);
                fragmentTransaction.commit();
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
                    if (teacherQuizList.size() > 0) {
                        for (int i = 0; i < teacherQuizList.size(); i++) {
                            String quizTitle;
                            String quizImage;
                            String quizId;
                            quizTitle = teacherQuizList.get(i).getName();
                            quizImage = teacherQuizList.get(i).getBackgroundImage();
                            quizId = teacherQuizList.get(i).get_id();
                            LiveQuizCard liveQuizCard = new LiveQuizCard(quizImage, quizTitle, quizId);
                            liveQuizCardList.add(liveQuizCard);
                            liveQuizAdapter = new LiveQuizAdapter(context, liveQuizCardList);
                            liveQuizRecyclerView.setAdapter(liveQuizAdapter);
                            liveQuizRecyclerView.setLayoutManager(new LinearLayoutManager(context));

//                            addNewQuizImageButton = new ImageButton(context);
//                            addNewQuizImageButton.setImageResource(R.drawable.ic_add_24);
//                            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
//                            shapeDrawable.getPaint().setColor(ContextCompat.getColor(context, R.color.salmon_pink));
//                            addNewQuizImageButton.setBackground(shapeDrawable);
//                            addNewQuizImageButton.setId(View.generateViewId());
//                            addNewQuizImageButton.setPadding(5,5,5,5);
//                            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            layoutParams.width = 150;
//                            layoutParams.height = 150;
//                            addNewQuizImageButton.setLayoutParams(layoutParams);
//                            myQuizzesConstraintLayout.addView(addNewQuizImageButton);
//                            ConstraintSet constraintSet = new ConstraintSet();
//                            constraintSet.clone(myQuizzesConstraintLayout);
//                            constraintSet.connect(addNewQuizImageButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 10);
//                            constraintSet.connect(addNewQuizImageButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 10);
//                            constraintSet.connect(addNewQuizImageButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 150);
//                            constraintSet.applyTo(myQuizzesConstraintLayout);
                        }
                    }
                        else {
                            TextView noQuizReminderTextView;
                            noQuizReminderTextView = new TextView(context);
                            noQuizReminderTextView.setText("You have no Quiz");
                            noQuizReminderTextView.setTextSize(20);
                            Typeface typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
                            noQuizReminderTextView.setTypeface(typeface);
                            noQuizReminderTextView.setPadding(20,20,20,20);
                            noQuizReminderTextView.setTextColor(Color.BLACK);
                            myQuizzesConstraintLayout.addView(noQuizReminderTextView);

                            addNewQuizImageButton = new ImageButton(context);
                            addNewQuizImageButton.setImageResource(R.drawable.ic_add_24);
                            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
                            shapeDrawable.getPaint().setColor(ContextCompat.getColor(context, R.color.salmon_pink));
                            addNewQuizImageButton.setBackground(shapeDrawable);
                            addNewQuizImageButton.setId(View.generateViewId());
                            addNewQuizImageButton.setPadding(5,5,5,5);
                            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.width = 150;
                            layoutParams.height = 150;
                            addNewQuizImageButton.setLayoutParams(layoutParams);
                            myQuizzesConstraintLayout.addView(addNewQuizImageButton);

                            ConstraintSet constraintSet = new ConstraintSet();
                            constraintSet.clone(myQuizzesConstraintLayout);
                            constraintSet.connect(noQuizReminderTextView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 10 );
                            constraintSet.connect(noQuizReminderTextView.getId(), ConstraintSet.TOP, R.id.homeFragLiveQuizzesTextView, ConstraintSet.BOTTOM, 20);
                            constraintSet.connect(noQuizReminderTextView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 10);
                            constraintSet.connect(addNewQuizImageButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 10);
                            constraintSet.connect(addNewQuizImageButton.getId(), ConstraintSet.TOP , noQuizReminderTextView.getId(), ConstraintSet.BOTTOM, 30);
                            constraintSet.connect(addNewQuizImageButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 10);
                            constraintSet.applyTo(myQuizzesConstraintLayout);
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