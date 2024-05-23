package com.example.quizapp_fe.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.quizapp_fe.activities.CreatorQuizActivity;
import com.example.quizapp_fe.activities.DiscoverySearchActivity;
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
    Button findFriendButton;
    String teacherId;
    ImageView avatarImageView;
    TextView userNameTextView;
    ImageButton addNewQuizImageButton;
    TextView noQuizReminderTextView;
    LinearLayout addNewQuizLinearLayout;
    TextView addNewQuizTextView;
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
        findFriendButton = view.findViewById(R.id.homeFragFindFriendButton);
        noQuizReminderTextView = new TextView(context);
        addNewQuizLinearLayout = new LinearLayout(context);
        addNewQuizImageButton = new ImageButton(context);
        addNewQuizTextView = new TextView(context);
        liveQuizCardList = new ArrayList<>();
        teacherId = CredentialToken.getInstance(context).getUserProfile().getId();

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

        findFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiscoverySearchActivity.class);
                intent.putExtra("FindFriendClicked", 4420);
                startActivity(intent);
            }
        });
        if (liveQuizCardList.isEmpty()) {
            addNewQuizLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CreatorQuizActivity.class);
                    startActivity(intent);
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        callAPIGetQuiz(teacherId);
        setAvatar(teacherId);
        setUserName(teacherId);

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
                        }
                    }
                        else if (liveQuizCardList.size() == 0) {
                            seeAllButtonTextView.setVisibility(View.GONE);
                            noQuizReminderTextView.setText("You have no Quiz");
                            noQuizReminderTextView.setTextSize(20);
                            noQuizReminderTextView.setId(View.generateViewId());
                            Typeface typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
                            noQuizReminderTextView.setTypeface(typeface);
                            noQuizReminderTextView.setPadding(20,20,20,20);
                            noQuizReminderTextView.setTextColor(Color.BLACK);
                            myQuizzesConstraintLayout.addView(noQuizReminderTextView);

                            addNewQuizLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            addNewQuizLinearLayout.setId(View.generateViewId());
//                            addNewQuizLinearLayout.setPadding(10,10,10,10);
                            addNewQuizLinearLayout.setBackgroundResource(R.drawable.border_20dp_salmon_pink);
                            addNewQuizLinearLayout.setClickable(true);
                            LinearLayout.LayoutParams layoutParamsAddNewLinearLayout = new LinearLayout.LayoutParams(500,150);
                            addNewQuizLinearLayout.setLayoutParams(layoutParamsAddNewLinearLayout);
                            myQuizzesConstraintLayout.addView(addNewQuizLinearLayout);

                            addNewQuizImageButton.setImageResource(R.drawable.ic_add_24);
                            ShapeDrawable shapeDrawableAddNewImageButton = new ShapeDrawable(new OvalShape());
                            shapeDrawableAddNewImageButton.getPaint().setColor(ContextCompat.getColor(context, R.color.salmon_pink));
                            addNewQuizImageButton.setBackground(shapeDrawableAddNewImageButton);
                            addNewQuizImageButton.setId(View.generateViewId());
                            addNewQuizImageButton.setPadding(5,5,5,5);
                            LinearLayout.LayoutParams layoutParamsAddNewImageButton = new LinearLayout.LayoutParams(150,150);
                            layoutParamsAddNewImageButton.gravity = Gravity.CENTER;
                            addNewQuizImageButton.setLayoutParams(layoutParamsAddNewImageButton);
                            addNewQuizLinearLayout.addView(addNewQuizImageButton);
                            addNewQuizImageButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, CreatorQuizActivity.class);
                                    startActivity(intent);
                                }
                            });



                            addNewQuizTextView.setId(View.generateViewId());
                            addNewQuizTextView.setText("Create Now");
                            addNewQuizTextView.setTypeface(typeface);
                            addNewQuizTextView.setTextColor(Color.WHITE);
                            addNewQuizTextView.setTextSize(20);
                            LinearLayout.LayoutParams layoutParamsAddNewQuizTextView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParamsAddNewQuizTextView.gravity = Gravity.CENTER;
                            addNewQuizTextView.setLayoutParams(layoutParamsAddNewQuizTextView);
                            addNewQuizLinearLayout.addView(addNewQuizTextView);



                            ConstraintSet constraintSet = new ConstraintSet();
                            constraintSet.clone(myQuizzesConstraintLayout);
                            constraintSet.connect(noQuizReminderTextView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 10 );
                            constraintSet.connect(noQuizReminderTextView.getId(), ConstraintSet.TOP, R.id.homeFragLiveQuizzesTextView, ConstraintSet.BOTTOM, 20);
                            constraintSet.connect(noQuizReminderTextView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 10);
                            constraintSet.connect(addNewQuizLinearLayout.getId(), ConstraintSet.TOP, noQuizReminderTextView.getId(), ConstraintSet.BOTTOM, 30);
                            constraintSet.connect(addNewQuizLinearLayout.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 10);
                            constraintSet.connect(addNewQuizLinearLayout.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 10);
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