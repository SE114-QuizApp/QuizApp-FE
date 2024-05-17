package com.example.quizapp_fe.fragments;

import static java.lang.String.format;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;

import com.example.quizapp_fe.activities.DiscoverySearchActivity;
import com.example.quizapp_fe.activities.HomeActivity;
import com.example.quizapp_fe.adapters.CategoryCardAdapter;
import com.example.quizapp_fe.api.account.profile.GetMeApi;
import com.example.quizapp_fe.api.quiz.get.GetDiscoverQuizzesApi;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.models.CredentialToken;
import com.example.quizapp_fe.models.DiscoverQuizzes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoveryFragment extends Fragment {

    RecyclerView categoryRecyclerView;
    ArrayList<CategoryCard> categoryCardList;
    CategoryCardAdapter categoryCardAdapter;
    SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        callApi();
        categoryRecyclerView = view.findViewById(R.id.discoveryFragCategoryRecyclerView);
        categoryCardList = new ArrayList<>();
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardList.add(new CategoryCard(R.drawable.ic_music_24, "Music", "14 Quizzes" ));
        categoryCardAdapter = new CategoryCardAdapter(view.getContext(), categoryCardList);
        categoryRecyclerView.setAdapter(categoryCardAdapter);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        searchView = view.findViewById(R.id.discoveryFragSearchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DiscoverySearchActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void callApi() {
        GetDiscoverQuizzesApi.getAPI(requireContext()).getDiscoverQuizzes().enqueue(new Callback<DiscoverQuizzes>() {
            @Override
            public void onResponse(Call<DiscoverQuizzes> call, Response<DiscoverQuizzes> response) {
                Log.e("DISCOVER", "Success");
                
            }

            @Override
            public void onFailure(Call<DiscoverQuizzes> call, Throwable t) {
                Log.e("DISCOVER", "Fail");
            }
        });
    }

}