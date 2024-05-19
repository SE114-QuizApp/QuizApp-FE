package com.example.quizapp_fe.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.HomeActivity;
import com.example.quizapp_fe.activities.TeacherQuizActivity;
import com.example.quizapp_fe.adapters.LiveQuizAdapter;
import com.example.quizapp_fe.api.quiz.get.GetPublicQuizzesApi;
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
    TeacherQuizActivity teacherQuizActivity;
    HomeActivity homeActivity;
    TextView seeAllButtonTextView;
    String teacherId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        liveQuizRecyclerView = view.findViewById(R.id.homeFragLiveQuizzesRecylerView);
        seeAllButtonTextView = view.findViewById(R.id.homeFragSeeAllTextView);
        liveQuizCardList = new ArrayList<>();
        teacherId = CredentialToken.getInstance(requireContext()).getUserProfile().getId();
        callAPIGetQuiz(teacherId);


        seeAllButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context1 = getActivity();
                Intent intent = new Intent(context1, TeacherQuizActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void callAPIGetQuiz(String teacherId) {
        GetPublicQuizzesApi.getTeacherQuizAPI(requireContext()).getTeacherQuiz(teacherId).enqueue(new Callback<ArrayList<Quiz>>() {
            @Override
            public void onResponse(Call<ArrayList<Quiz>> call, Response<ArrayList<Quiz>> response) {
                if (response.isSuccessful()){
                    ArrayList<Quiz> teacherQuizList = response.body();
                    assert  teacherQuizList != null;
                    for (int i = 0; i < teacherQuizList.size(); i++){
                        String quizTitle;
                        String quizSubTitle;
                        String quizImage;
                        quizTitle = teacherQuizList.get(i).getName();
                        quizSubTitle = teacherQuizList.get(i).getDescription();
                        quizImage = teacherQuizList.get(i).getBackgroundImage();
                        LiveQuizCard liveQuizCard = new LiveQuizCard(quizImage,quizTitle,quizSubTitle);
                        liveQuizCardList.add(liveQuizCard);
                        liveQuizAdapter = new LiveQuizAdapter(requireContext(),liveQuizCardList);
                        liveQuizRecyclerView.setAdapter(liveQuizAdapter);
                        liveQuizRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Quiz>> call, Throwable t) {
                Toast.makeText(requireContext(), "Call API Failed", Toast.LENGTH_SHORT).show();
                Log.e("HomeFrament", "failed");
            }
        });
    }
}