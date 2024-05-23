package com.example.quizapp_fe.adapters;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.models.CreateQuizViewModel;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.entities.Category;

import java.util.List;

public class CategoryCreateQuizAdapter extends RecyclerView.Adapter<CategoryCreateQuizAdapter.CategoryViewHolder> {

    private List<CategoryCard> categories;

    public CategoryCreateQuizAdapter(List<CategoryCard> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryCard category = categories.get(position);
        holder.titleView.setText(category.getCategoryCardTitle());
        holder.iconView.setImageResource(category.getCategoryCardImage());

        // customize the selected or unselected item view
        if (category.isSelected()) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.salmon_pink));
            holder.iconView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.pastel_pink)));
            holder.iconView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.white)));
            holder.titleView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.light_purple));
            holder.iconView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.white)));
            holder.iconView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.purple_hue)));
            holder.titleView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.purple_hue));
        }

        // handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                // update the selected field of all categories to false
                for (CategoryCard c : categories) {
                    c.setSelected(false);
                }

                // update the selected field of the clicked category to true
                category.setSelected(true);

                // refresh the recycler view
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        ImageView iconView;
        CardView cardView;

        public CategoryViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.categoryTitle);
            iconView = view.findViewById(R.id.categoryIcon);
            cardView = view.findViewById(R.id.cardViewCategory);
        }
    }
}