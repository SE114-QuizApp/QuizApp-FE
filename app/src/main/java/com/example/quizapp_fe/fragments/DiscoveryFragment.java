package com.example.quizapp_fe.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.models.CredentialToken;
import com.example.quizapp_fe.models.DiscoverQuizzes;

import java.util.ArrayList;

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
        int points = CredentialToken.getInstance(context).getUserProfile().getPoint();
        discoveryFragYourScoreTextView.setText(Integer.toString(points));
    }

    private void callApi() {
        if (context == null) {
            return;
        }

        GetDiscoverQuizzesApi.getAPI(context).getDiscoverQuizzes().enqueue(new Callback<DiscoverQuizzes>() {
            @Override
            public void onResponse(Call<DiscoverQuizzes> call, Response<DiscoverQuizzes> response) {
                Log.e("DISCOVER", "Success");
                if (response.isSuccessful()) {
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

                    categoryCardList.add(englishCategoryCard);
                    categoryCardList.add(mathCategoryCard);
                    categoryCardList.add(computerCategoryCard);

                    if (context != null) {
                        categoryCardAdapter = new CategoryCardAdapter(context, categoryCardList);
                        categoryRecyclerView.setAdapter(categoryCardAdapter);
                        categoryRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                    }
                }
            }

            @Override
            public void onFailure(Call<DiscoverQuizzes> call, Throwable t) {
                Log.e("DISCOVER", "Fail");
            }
        });
    }
}