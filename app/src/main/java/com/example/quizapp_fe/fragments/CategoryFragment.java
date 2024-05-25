package com.example.quizapp_fe.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapp_fe.R;

import com.example.quizapp_fe.adapters.CategoryCardAdapter;
import com.example.quizapp_fe.api.quiz.get.GetDiscoverQuizzesApi;
import com.example.quizapp_fe.api.user.getListRanking.GetListRankingApi;
import com.example.quizapp_fe.entities.DiscoverQuiz;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.interfaces.QuizCard;
import com.example.quizapp_fe.models.DiscoverQuizzes;
import com.example.quizapp_fe.services.SearchListener;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment implements SearchListener {

    RecyclerView recyclerView;
    ArrayList<CategoryCard> categoryCardArrayList;
    CategoryCardAdapter categoryCardAdapter;
    ArrayList<CategoryCard> originalCategoryCardArrayList;
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
        GetDiscoverQuizzesApi.getAPI(requireContext()).getDiscoverQuizzes().enqueue(new Callback<Map<String, ArrayList<DiscoverQuiz>>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, ArrayList<DiscoverQuiz>>> call, @NonNull Response<Map<String, ArrayList<DiscoverQuiz>>> response) {

                if(response.isSuccessful()) {
                    Map<String, ArrayList<DiscoverQuiz>> quizMap = response.body();
                    DiscoverQuizzes discoverQuizzes = GetDiscoverQuizzesApi.parseDiscoverQuizzes(quizMap);

                    for (Map.Entry<String, ArrayList<DiscoverQuiz>> entry : discoverQuizzes.getQuizzes().entrySet()) {
                        String subject = entry.getKey();
                        ArrayList<DiscoverQuiz> quizzes = entry.getValue();
                        switch (subject) {
                            case "Math":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_math_24, "Math", quizzes.size() + " Quizzes"));
                                break;
                            case "English":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_english_24, "English", quizzes.size() + " Quizzes"));
                                break;
                            case "Sports":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_sports_24, "Sports", quizzes.size() + " Quizzes"));
                                break;
                            case "Science":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_science_24, "Science", quizzes.size() + " Quizzes"));
                                break;
                            case "Art":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_art_24, "Art", quizzes.size() + " Quizzes"));
                                break;
                            case "History":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_history_24, "History", quizzes.size() + " Quizzes"));
                                break;
                            case "Geography":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_geography_24, "Geography", quizzes.size() + " Quizzes"));
                                break;
                            case "Biology":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_biology_24, "Biology", quizzes.size() + " Quizzes"));
                                break;
                            case "Philosophy":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_philosophy_24, "Philosophy", quizzes.size() + " Quizzes"));
                                break;
                            case "Computer":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_computer_24, "Computer", quizzes.size() + " Quizzes"));
                                break;
                            case "Chemistry":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_chemistry_24, "Chemistry", quizzes.size() + " Quizzes"));
                                break;
                            case "Fun":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_fun_24, "Fun", quizzes.size() + " Quizzes"));
                                break;
                            case "Exercise":
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_exercise_24, "Exercise", quizzes.size() + " Quizzes"));
                                break;
                            default:
                                categoryCardArrayList.add(new CategoryCard(R.drawable.ic_other_24, "Other", quizzes.size() + " Quizzes"));
                                break;
                        }

                    }

                    originalCategoryCardArrayList = new ArrayList<>(categoryCardArrayList);

                    categoryCardAdapter = new CategoryCardAdapter(requireContext(), categoryCardArrayList);
                    recyclerView.setAdapter(categoryCardAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

                }
            }

            @Override
            public void onFailure(Call<Map<String, ArrayList<DiscoverQuiz>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSearchQuery(String query) {
        Log.e("CategoryFragment", "SearchFrom CategoryFragment");
        performSearch(query);
    }

    private void performSearch(String query) {
        categoryCardArrayList.clear();
        for (CategoryCard categoryCard : originalCategoryCardArrayList) {
            if (categoryCard.getCategoryCardTitle().toLowerCase().contains(query.toLowerCase())) {
                originalCategoryCardArrayList.add(categoryCard);
            }
        }
        categoryCardAdapter.notifyDataSetChanged();
    }
}