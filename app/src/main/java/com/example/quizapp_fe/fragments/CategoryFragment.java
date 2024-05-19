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
import com.example.quizapp_fe.api.quiz.get.GetDiscoverQuizzesApi;
import com.example.quizapp_fe.api.user.getListRanking.GetListRankingApi;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.models.DiscoverQuizzes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<CategoryCard> categoryCardArrayList;
    CategoryCardAdapter categoryCardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.categoryFragRecyclerView);
        categoryCardArrayList = new ArrayList<>();
        callApi();

        return view;
    }

    public void callApi() {
        GetDiscoverQuizzesApi.getAPI(requireContext()).getDiscoverQuizzes().enqueue(new Callback<DiscoverQuizzes>() {
            @Override
            public void onResponse(Call<DiscoverQuizzes> call, Response<DiscoverQuizzes> response) {

                if(response.isSuccessful()) {
                    DiscoverQuizzes discoverQuizzes = response.body();

                    CategoryCard englishCategoryCard = new CategoryCard(
                            R.drawable.ic_english_24,
                            discoverQuizzes.getEnglish().get(0).getCategory().getName(),
                            discoverQuizzes.getEnglish().size() + " Quizzes"
                    );

                    CategoryCard mathCategoryCard = new CategoryCard(
                            R.drawable.ic_math_24_black,
                            discoverQuizzes.getMath().get(0).getCategory().getName(),
                            discoverQuizzes.getMath().size() + " Quizzes"
                    );

                    CategoryCard computerCategoryCard = new CategoryCard(
                            R.drawable.ic_computer_24,
                            discoverQuizzes.getComputer().get(0).getCategory().getName(),
                            discoverQuizzes.getComputer().size() + " Quizzes"
                    );

                    categoryCardArrayList.add(englishCategoryCard);
                    categoryCardArrayList.add(mathCategoryCard);
                    categoryCardArrayList.add(computerCategoryCard);

                    categoryCardAdapter = new CategoryCardAdapter(requireContext(), categoryCardArrayList);
                    recyclerView.setAdapter(categoryCardAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

                }
            }

            @Override
            public void onFailure(Call<DiscoverQuizzes> call, Throwable t) {

            }
        });
    }
}