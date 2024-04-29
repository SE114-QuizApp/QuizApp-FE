package com.example.quizapp_fe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionFragment extends Fragment {

    private RecyclerView questionNumberRecyclerView;
    private NumberQuestionAdapter numberQuestionAdapter;

    LinearLayout durationQuestion;
    TextView textDurationQuestion;

    LinearLayout typeQuestion;
    TextView textTypeQuestion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_question, container, false);
        textDurationQuestion = view.findViewById(R.id.tvDurationQuestion);
        textTypeQuestion = view.findViewById(R.id.tvTypeQuestion);

        questionNumberRecyclerView = view.findViewById(R.id.questionNumberScrollView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        questionNumberRecyclerView.setLayoutManager(layoutManager);


        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            numbers.add(i);
        }

        numberQuestionAdapter = new NumberQuestionAdapter(numbers);
        questionNumberRecyclerView.setAdapter(numberQuestionAdapter);

        // get the durationQuestionContainer and set an onClickListener
        durationQuestion = view.findViewById(R.id.durationQuestionContainer);
        durationQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuDuration();
            }
        });

        // get the typeQuestionContainer and set an onClickListener
        typeQuestion = view.findViewById(R.id.typeQuestionContainer);
        typeQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenuType();
            }
        });

        ImageView btnBack = view.findViewById(R.id.btnBackCreateQuestion);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void ShowMenuDuration() {
        PopupMenu popup = new PopupMenu(getActivity(), durationQuestion);
        popup.getMenuInflater().inflate(R.menu.popup_menu_duration_question, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                textDurationQuestion.setText(item.getTitle());
                return true;
            }
        });
        popup.show();
    }

    private void ShowMenuType() {
        PopupMenu popup = new PopupMenu(getActivity(), typeQuestion);
        popup.getMenuInflater().inflate(R.menu.popup_menu_type_question, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                textTypeQuestion.setText(item.getTitle());
                return true;
            }
        });
        popup.show();
    }

    private class NumberQuestionAdapter extends RecyclerView.Adapter<NumberQuestionAdapter.NumberQuestionViewHolder> {

        private List<Integer> numbers;
        private int selectedPos = 0;

        NumberQuestionAdapter(List<Integer> numbers) {
            this.numbers = numbers;
        }

        @Override
        public NumberQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_number_question, parent, false);
            return new NumberQuestionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(NumberQuestionViewHolder holder, int position) {
            Integer number = numbers.get(position);
            holder.textView.setText(String.valueOf(number));

            if (selectedPos == position) {
                holder.textView.setSelected(true);
            } else {
                holder.textView.setSelected(false);
            }
        }

        @Override
        public int getItemCount() {
            return numbers.size();
        }

        class NumberQuestionViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            NumberQuestionViewHolder(View view) {
                super(view);
                textView = view.findViewById(R.id.tvQuestionNumber);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifyItemChanged(selectedPos);
                        selectedPos = getLayoutPosition();
                        notifyItemChanged(selectedPos);
                    }
                });
            }
        }
    }
}