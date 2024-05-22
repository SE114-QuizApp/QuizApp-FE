package com.example.quizapp_fe.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.quizapp_fe.R;
import com.example.quizapp_fe.adapters.UserCardAdapter;
import com.example.quizapp_fe.api.user.getAllUsers.GetAllUsersApi;
import com.example.quizapp_fe.entities.User;
import com.example.quizapp_fe.interfaces.UserCard;
import com.example.quizapp_fe.services.SearchListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment implements SearchListener {
    private Context context;
    RecyclerView recyclerView;
    ArrayList<UserCard> userCardArrayList;
    UserCardAdapter userCardAdapter;
    ArrayList<UserCard> originalUserCardArrayList;

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
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.userFragRecyclerView);
        userCardArrayList = new ArrayList<>();

        callApi();

        return view;
    }


    public void callApi() {
        if (context == null) {
            return;
        }

        GetAllUsersApi.getAPI(context).getAllUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if(response.isSuccessful()){
                    ArrayList<User> usersList = response.body();
                    for (int i = 0; i < usersList.size(); i++) {
                        userCardArrayList.add(new UserCard(
                                usersList.get(i).getAvatar(),
                                usersList.get(i).getUserName(),
                                usersList.get(i).getMail(),
                                usersList.get(i).get_id()));
                    }

                    originalUserCardArrayList = new ArrayList<>(userCardArrayList);

                    userCardAdapter = new UserCardAdapter(context, userCardArrayList);
                    recyclerView.setAdapter(userCardAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.e("USERFRAG", "Call All User Api Fail");
            }
        });
    }

    @Override
    public void onSearchQuery(String query) {
        performSearch(query);
    }

    private void performSearch(String query) {
        userCardArrayList.clear();
        for (UserCard userCard : originalUserCardArrayList) {
            if (userCard.getUserCardName().toLowerCase().contains(query.toLowerCase())) {
                userCardArrayList.add(userCard);
            }
        }
        userCardAdapter.notifyDataSetChanged();
    }
}