package com.smartlearning.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.smartlearning.app.R;
import com.smartlearning.app.model.Quiz;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private List<Quiz> quizzes;
    private Context context;
    private OnQuizItemClickListener listener;

    public interface OnQuizItemClickListener {
        void onStartQuiz(Quiz quiz);
    }

    public QuizAdapter(List<Quiz> quizzes, Context context, OnQuizItemClickListener listener) {
        this.quizzes = quizzes;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizzes.get(position);
        holder.tvQuizTitle.setText(quiz.getTitle());
        holder.tvQuizDescription.setText(quiz.getDescription());
        holder.tvTotalQuestions.setText("Số câu: " + quiz.getTotalQuestions());

        holder.btnStart.setOnClickListener(v -> {
            if (listener != null) {
                listener.onStartQuiz(quiz);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuizTitle, tvQuizDescription, tvTotalQuestions;
        Button btnStart;

        QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuizTitle = itemView.findViewById(R.id.tvQuizTitle);
            tvQuizDescription = itemView.findViewById(R.id.tvQuizDescription);
            tvTotalQuestions = itemView.findViewById(R.id.tvTotalQuestions);
            btnStart = itemView.findViewById(R.id.btnStart);
        }
    }
}
