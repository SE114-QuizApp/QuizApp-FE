package com.example.quizapp_fe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.HomeActivity;
import com.example.quizapp_fe.adapters.CategoryCreateQuizAdapter;
import com.example.quizapp_fe.databinding.FragmentChooseCategoryBinding;
import com.example.quizapp_fe.entities.Category;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.models.CreateQuizViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChooseCategoryFragment extends Fragment {
    private FragmentChooseCategoryBinding binding;

    private CreateQuizViewModel createQuizViewModel;

    private List<CategoryCard> categories;

    private Quiz quiz;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChooseCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        createQuizViewModel = new ViewModelProvider(requireActivity()).get(CreateQuizViewModel.class);
        binding.setViewModel(createQuizViewModel);


        if (categories == null) {
            categories = new ArrayList<>();
            // add categories to the list
            categories.add(new CategoryCard("All", R.drawable.ic_all_24));
            categories.add(new CategoryCard("Math", R.drawable.ic_math_24));
            categories.add(new CategoryCard("English", R.drawable.ic_english_24));
            categories.add(new CategoryCard("Sports", R.drawable.ic_sports_24));
            categories.add(new CategoryCard("Science", R.drawable.ic_science_24));
            categories.add(new CategoryCard("Art", R.drawable.ic_art_24));
            categories.add(new CategoryCard("History", R.drawable.ic_history_24));
            categories.add(new CategoryCard("Geography", R.drawable.ic_geography_24));
            categories.add(new CategoryCard("Biology", R.drawable.ic_biology_24));
            categories.add(new CategoryCard("Philosophy", R.drawable.ic_philosophy_24));
        }


        CategoryCreateQuizAdapter categoryAdapter = new CategoryCreateQuizAdapter(categories);
        binding.categoryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.categoryRecyclerView.setAdapter(categoryAdapter);

        // select the first category by default
        categories.get(0).setSelected(true);


        Quiz quiz = createQuizViewModel.getQuiz().getValue();

        // set the selected category if the quiz object is not null
        if (quiz != null && quiz.getCategory() != null && !quiz.getCategory().getName().isEmpty()) {
            String selectedCategoryName = quiz.getCategory().getName();
            for (CategoryCard category : categories) {
                if (category.getCategoryCardTitle().equals(selectedCategoryName)) {
                    // Unselect the first category
                    categories.get(0).setSelected(false);
                    // Select the previously selected category
                    category.setSelected(true);
                    break;
                }
            }
        }


        // handle the click event of the Back button
        binding.btnBackChooseCategory.setOnClickListener(v -> {
//            createQuizViewModel.clearQuiz();

            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);

            getActivity().finish();

            Toast.makeText(getActivity(), "Exit creating quiz", Toast.LENGTH_SHORT).show();
        });

        // handle the click event of the Next button
        binding.btnNextChooseCategory.setOnClickListener(v -> {
            // get the selected category
            CategoryCard selectedCategory = null;

            for (CategoryCard category : categories) {
                if (category.isSelected()) {
                    selectedCategory = category;
                    break;
                }
            }

            // update the Quiz object with the selected category
            quiz.setCategory(new Category(selectedCategory.getCategoryCardTitle()));
            createQuizViewModel.setQuiz(quiz);

            // replace the current fragment with the CreateQuizFragment
            CreateQuizFragment createQuizFragment = new CreateQuizFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.creatorQuizMainContainerFrameLayout, createQuizFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}