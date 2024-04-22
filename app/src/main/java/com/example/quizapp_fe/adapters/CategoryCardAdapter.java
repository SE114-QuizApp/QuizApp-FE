package com.example.quizapp_fe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.interfaces.CategoryCard;

import java.util.List;

public class CategoryCardAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {
    private Context context;
    private List<CategoryCard> dataList;

    public CategoryCardAdapter(Context context, List<CategoryCard> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public CategoryCardAdapter(List<CategoryCard> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_category_card, parent, false);
        return new CategoryCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, int position) {
        CategoryCard categoryCard = dataList.get(position);
        holder.bind(categoryCard);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

