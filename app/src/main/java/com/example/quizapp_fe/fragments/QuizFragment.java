package com.example.quizapp_fe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.CategoryCardAdapter;
import com.example.quizapp_fe.adapters.QuizCardAdapter;
import com.example.quizapp_fe.api.quiz.get.GetDiscoverQuizzesApi;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.interfaces.QuizCard;
import com.example.quizapp_fe.models.DiscoverQuizzes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<QuizCard> quizCardArrayList;
    QuizCardAdapter quizCardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        recyclerView = view.findViewById(R.id.quizFragRecyclerView);
        quizCardArrayList = new ArrayList<>();
        callApi();

        return view;
    }

    public void callApi() {
        GetDiscoverQuizzesApi.getAPI(requireContext()).getDiscoverQuizzes().enqueue(new Callback<DiscoverQuizzes>() {
            @Override
            public void onResponse(Call<DiscoverQuizzes> call, Response<DiscoverQuizzes> response) {
                if (response.isSuccessful()){
                    DiscoverQuizzes discoverQuizzes = response.body();
//                    English
                    for(int i =0 ;i< discoverQuizzes.getEnglish().size(); i++) {
                        String name = discoverQuizzes.getEnglish().get(i).getCreator().getFirstName() +
                                " " +
                                discoverQuizzes.getEnglish().get(i).getCreator().getLastName();
                        QuizCard quizCard = new QuizCard(R.drawable.ic_english_24,
                                         discoverQuizzes.getEnglish().get(i).getName(),
                                         name,
                                         "English");
                        quizCardArrayList.add(quizCard);
                    }
//                    Math
                    for(int i =0 ;i< discoverQuizzes.getMath().size(); i++) {
                        String name = discoverQuizzes.getMath().get(i).getCreator().getFirstName() +
                                " " +
                                discoverQuizzes.getMath().get(i).getCreator().getLastName();
                        QuizCard quizCard = new QuizCard(R.drawable.ic_math_24_black,
                                                         discoverQuizzes.getMath().get(i).getName(),
                                                         name,
                                                         "Math");
                        quizCardArrayList.add(quizCard);
                    }
//                    Computer
                    for(int i =0 ;i< discoverQuizzes.getComputer().size(); i++) {
                        String name = discoverQuizzes.getComputer().get(i).getCreator().getFirstName() +
                                " " +
                                discoverQuizzes.getComputer().get(i).getCreator().getLastName();
                        QuizCard quizCard = new QuizCard(R.drawable.ic_computer_24,
                                                         discoverQuizzes.getComputer().get(i).getName(),
                                                         name,
                                                         "English");
                        quizCardArrayList.add(quizCard);
                    }
//
                    quizCardAdapter = new QuizCardAdapter(requireContext(), quizCardArrayList);
                    recyclerView.setAdapter(quizCardAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
                }
            }

            @Override
            public void onFailure(Call<DiscoverQuizzes> call, Throwable t) {

            }
        });
    }
}