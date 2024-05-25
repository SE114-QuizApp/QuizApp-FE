package com.example.quizapp_fe.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.DiscoverySearchActivity;
import com.example.quizapp_fe.adapters.CategoryCardAdapter;
import com.example.quizapp_fe.api.quiz.get.GetDiscoverQuizzesApi;
import com.example.quizapp_fe.entities.DiscoverQuiz;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.models.CredentialToken;
import com.example.quizapp_fe.models.DiscoverQuizzes;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoveryFragment extends Fragment {
    String teacherId;
    RecyclerView categoryRecyclerView;
    ArrayList<CategoryCard> categoryCardList;
    CategoryCardAdapter categoryCardAdapter;
    SearchView searchView;
    AppCompatButton discoveryFragExploreButton;
    TextView discoveryFragYourScoreTextView;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        categoryRecyclerView = view.findViewById(R.id.discoveryFragCategoryRecyclerView);
        discoveryFragExploreButton = view.findViewById(R.id.discoveryFragExploreButton);
        discoveryFragYourScoreTextView = view.findViewById(R.id.discoveryFragYourScoreTextView);

        teacherId = CredentialToken.getInstance(context).getUserId();

        setPoint(teacherId);
        categoryCardList = new ArrayList<>();

        categoryCardAdapter = new CategoryCardAdapter(context, categoryCardList);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        categoryRecyclerView.setAdapter(categoryCardAdapter);
        callApi();

        searchView = view.findViewById(R.id.discoveryFragSearchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DiscoverySearchActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DiscoverySearchActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Trường tìm kiếm đã nhận trạng thái tập trung
                    Intent intent = new Intent(getContext(), DiscoverySearchActivity.class);
                    startActivity(intent);
                }
            }
        });

        discoveryFragExploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(view.getContext().getApplicationContext(), R.anim.animation_normal);
                discoveryFragExploreButton.startAnimation(animation);
                Intent intent = new Intent(view.getContext(), DiscoverySearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setPoint(String teacherId) {
        int points = CredentialToken.getInstance(requireContext()).getUserProfile().getPoint();
        discoveryFragYourScoreTextView.setText(Integer.toString(points));
    }

    private void callApi() {
        if (context == null) {
            return;
        }

        GetDiscoverQuizzesApi.getAPI(requireContext()).getDiscoverQuizzes().enqueue(new Callback<Map<String, ArrayList<DiscoverQuiz>>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, ArrayList<DiscoverQuiz>>> call, @NonNull Response<Map<String, ArrayList<DiscoverQuiz>>> response) {

                if (response.isSuccessful()) {
                    Map<String, ArrayList<DiscoverQuiz>> quizMap = response.body();
                    DiscoverQuizzes discoverQuizzes = GetDiscoverQuizzesApi.parseDiscoverQuizzes(quizMap);
                    categoryCardList = new ArrayList<>();

                    for (Map.Entry<String, ArrayList<DiscoverQuiz>> entry : discoverQuizzes.getQuizzes().entrySet()) {
                        String subject = entry.getKey();
                        ArrayList<DiscoverQuiz> quizzes = entry.getValue();


                        switch (subject) {
                            case "Math":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_math_24, "Math", quizzes.size() + " Quizzes"));
                                break;
                            case "English":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_english_24, "English", quizzes.size() + " Quizzes"));
                                break;
                            case "Sports":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_sports_24, "Sports", quizzes.size() + " Quizzes"));
                                break;
                            case "Science":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_science_24, "Science", quizzes.size() + " Quizzes"));
                                break;
                            case "Art":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_art_24, "Art", quizzes.size() + " Quizzes"));
                                break;
                            case "History":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_history_24, "History", quizzes.size() + " Quizzes"));
                                break;
                            case "Geography":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_geography_24, "Geography", quizzes.size() + " Quizzes"));
                                break;
                            case "Biology":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_biology_24, "Biology", quizzes.size() + " Quizzes"));
                                break;
                            case "Philosophy":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_philosophy_24, "Philosophy", quizzes.size() + " Quizzes"));
                                break;
                            case "Computer":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_computer_24, "Computer", quizzes.size() + " Quizzes"));
                                break;
                            case "Chemistry":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_chemistry_24, "Chemistry", quizzes.size() + " Quizzes"));
                                break;
                            case "Fun":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_fun_24, "Fun", quizzes.size() + " Quizzes"));
                                break;
                            case "Exercise":
                                categoryCardList.add(new CategoryCard(R.drawable.ic_exercise_24, "Exercise", quizzes.size() + " Quizzes"));
                                break;
                            default:
                                categoryCardList.add(new CategoryCard(R.drawable.ic_other_24, "Other", quizzes.size() + " Quizzes"));
                                break;
                        }

                    }


                    if (context != null && categoryCardList != null && !categoryCardList.isEmpty()) {
                        categoryCardAdapter = new CategoryCardAdapter(context, categoryCardList);
                        categoryRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                        categoryRecyclerView.setAdapter(categoryCardAdapter);
                    }
                    // Notify adapter about data changes
                    if (categoryCardAdapter != null) {
                        categoryCardAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, ArrayList<DiscoverQuiz>>> call, @NonNull Throwable t) {
                Log.e("DISCOVER", "Fail");
            }
        });
    }
}