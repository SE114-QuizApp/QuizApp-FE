package com.example.quizapp_fe.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.activities.HomeActivity;
import com.example.quizapp_fe.adapters.CategoryCreateQuizAdapter;
import com.example.quizapp_fe.databinding.FragmentChooseCategoryBinding;
import com.example.quizapp_fe.dialogs.ConfirmationDialog;
import com.example.quizapp_fe.entities.Category;
import com.example.quizapp_fe.entities.Quiz;
import com.example.quizapp_fe.interfaces.CategoryCard;
import com.example.quizapp_fe.interfaces.OnBackPressedListener;
import com.example.quizapp_fe.models.CreateQuizViewModel;
import com.google.gson.Gson;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ChooseCategoryFragment extends Fragment  {
    private FragmentChooseCategoryBinding binding;

    private CreateQuizViewModel createQuizViewModel;

    private Map<String, CategoryCard> categories;

    private Quiz quiz;
    private ConfirmationDialog confirmationDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categories = new LinkedHashMap<>();
        categories.put("Other", new CategoryCard("Other", R.drawable.ic_other_24));
        categories.put("Math", new CategoryCard("Math", R.drawable.ic_math_24));
        categories.put("English", new CategoryCard("English", R.drawable.ic_english_24));
        categories.put("Sports", new CategoryCard("Sports", R.drawable.ic_sports_24));
        categories.put("Science", new CategoryCard("Science", R.drawable.ic_science_24));
        categories.put("Art", new CategoryCard("Art", R.drawable.ic_art_24));
        categories.put("History", new CategoryCard("History", R.drawable.ic_history_24));
        categories.put("Geography", new CategoryCard("Geography", R.drawable.ic_geography_24));
        categories.put("Biology", new CategoryCard("Biology", R.drawable.ic_biology_24));
        categories.put("Philosophy", new CategoryCard("Philosophy", R.drawable.ic_philosophy_24));
        categories.put("Computer", new CategoryCard("Computer", R.drawable.ic_computer_24));
        categories.put("Chemistry", new CategoryCard("Chemistry", R.drawable.ic_chemistry_24));
        categories.put("Fun", new CategoryCard("Fun", R.drawable.ic_fun_24));
        categories.put("Exercise", new CategoryCard("Exercise", R.drawable.ic_exercise_24));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChooseCategoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        confirmationDialog = new ConfirmationDialog(requireActivity());
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                confirmationDialog.show("Discard changes", "Are you sure you want to exit?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                }, null);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        createQuizViewModel = new ViewModelProvider(requireActivity()).get(CreateQuizViewModel.class);
        quiz = createQuizViewModel.getQuiz().getValue();
        Gson gson = new Gson();
        Log.d("ChooseCategoryFragment", gson.toJson(quiz));

        CategoryCreateQuizAdapter categoryAdapter = new CategoryCreateQuizAdapter(new ArrayList<>(categories.values()));
        binding.categoryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.categoryRecyclerView.setAdapter(categoryAdapter);


        // select the first category by default
        Objects.requireNonNull(categories.get("Other")).setSelected(true);

        // set the selected category if the quiz object is not null
        if (quiz != null && quiz.getCategory() != null && quiz.getCategory().getName() != null && !quiz.getCategory().getName().isEmpty()) {
            String selectedCategoryName = quiz.getCategory().getName();
            CategoryCard selectedCategory = categories.get(selectedCategoryName);

            if (selectedCategory != null) {
                Objects.requireNonNull(categories.get("Other")).setSelected(false);
                selectedCategory.setSelected(true);

                binding.categoryRecyclerView.scrollToPosition(new ArrayList<>(categories.values()).indexOf(selectedCategory));
            }
        }

        // handle the click event of the Back button
        binding.btnBackChooseCategory.setOnClickListener(v -> {
            confirmationDialog.show("Discard changes", "Are you sure you want to exit?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }, null);
        });

        // handle the click event of the Next button
        binding.btnNextChooseCategory.setOnClickListener(v -> {
            // get the selected category
            CategoryCard selectedCategory = null;

            for (CategoryCard category : categories.values()) {
                if (category.isSelected()) {
                    selectedCategory = category;
                    break;
                }
            }

            // update the Quiz object with the selected category
            if (quiz != null) {
                if (quiz.getCategory() != null) {
                    quiz.getCategory().setName(selectedCategory.getCategoryCardTitle());
                } else {
                    quiz.setCategory(new Category(selectedCategory.getCategoryCardTitle()));
                }
            }

            createQuizViewModel.setQuiz(quiz);

            // replace the current fragment with the CreateQuizFragment
            CreateQuizFragment createQuizFragment = new CreateQuizFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.creatorQuizContainer, createQuizFragment);
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