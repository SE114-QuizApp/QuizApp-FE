package com.example.quizapp_fe.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.entities.Question;

import java.util.List;

public class QuestionIndexAdapter extends RecyclerView.Adapter<QuestionIndexAdapter.QuestionIndexViewHolder> {

    private List<Question> questions;
    private MutableLiveData<Integer> selectedPos = new MutableLiveData<>();

    // Add a private variable for the listener
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MutableLiveData<Integer> getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos.setValue(selectedPos);
        notifyDataSetChanged();
    }

    public QuestionIndexAdapter(List<Question> questions) {
        this.questions = questions;
    }

    // Add a method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public QuestionIndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_number_question, parent, false);
        return new QuestionIndexViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionIndexViewHolder holder, int position) {
        holder.textView.setText(String.valueOf(String.valueOf(position + 1)));

        if (selectedPos.getValue() == position) {
            holder.textView.setSelected(true);
        } else {
            holder.textView.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    class QuestionIndexViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        QuestionIndexViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.tvQuestionNumber);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedPos.getValue());
                    selectedPos.setValue(getLayoutPosition());
                    notifyItemChanged(selectedPos.getValue());

                    // When the itemView is clicked, call the listener's onItemClick method
                    if (listener != null) {
                        listener.onItemClick(getBindingAdapterPosition());
                    }
                }
            });
        }
    }
}