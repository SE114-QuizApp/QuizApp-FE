package com.example.quizapp_fe.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.CategoryCardAdapter;
import com.example.quizapp_fe.adapters.QuizCardAdapter;
import com.example.quizapp_fe.api.quiz.get.GetDiscoverQuizzesApi;
import com.example.quizapp_fe.api.quiz.get.GetPublicQuizzesApi;
import com.example.quizapp_fe.entities.PublicQuiz;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.interfaces.QuizCard;
import com.example.quizapp_fe.interfaces.UserCard;
import com.example.quizapp_fe.models.DiscoverQuizzes;
import com.example.quizapp_fe.services.SearchListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizFragment extends Fragment implements SearchListener {

    RecyclerView recyclerView;
    ArrayList<QuizCard> quizCardArrayList;
    QuizCardAdapter quizCardAdapter;
    ArrayList<QuizCard> originalQuizCardArrayList;

    String sectionName = "";
    String page = "1";
    String pageSize = "100";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        recyclerView = view.findViewById(R.id.quizFragRecyclerView);
        quizCardArrayList = new ArrayList<>();
        callPublicQuizzesApi();
        return view;
    }


    public void callPublicQuizzesApi() {
        GetPublicQuizzesApi.getAPI(requireContext()).getPublicQuizzes(sectionName, page , pageSize).enqueue(new Callback<PublicQuiz>() {
            @Override
            public void onResponse(Call<PublicQuiz> call, Response<PublicQuiz> response) {
                if (response.isSuccessful()) {
                    Log.e("QuizFragment", "Call Public Quiz Api Successfully");
                    PublicQuiz publicQuiz = response.body();
                    for (int i = 0 ; i < publicQuiz.getData().size(); i++) {
                        String name = publicQuiz.getData().get(i).getCreator().getFullName();
                        QuizCard quizCard = new QuizCard(publicQuiz.getData().get(i).get_id(),
                                                         publicQuiz.getData().get(i).getBackgroundImage(),
                                                         publicQuiz.getData().get(i).getName(),
                                                         name,
                                                         publicQuiz.getData().get(i).getCategory().getName());
                        quizCardArrayList.add(quizCard);
                    }
                    originalQuizCardArrayList = new ArrayList<>(quizCardArrayList);
                    quizCardAdapter = new QuizCardAdapter(requireContext(), quizCardArrayList);
                    recyclerView.setAdapter(quizCardAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
                }
            }

            @Override
            public void onFailure(Call<PublicQuiz> call, Throwable t) {
                Log.e("QuizFragment", "Call Public Quiz Api Fail");
            }
        });
    }

    @Override
    public void onSearchQuery(String query) {
        performSearch(query);
    }

    private void performSearch(String query) {
        quizCardArrayList.clear();
        for (QuizCard quizCard : originalQuizCardArrayList) {
            if (quizCard.getTitleText().toLowerCase().contains(query.toLowerCase())) {
                quizCardArrayList.add(quizCard);
            }
        }
        quizCardAdapter.notifyDataSetChanged();
    }
}