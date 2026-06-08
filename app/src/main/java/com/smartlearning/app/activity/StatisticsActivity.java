package com.smartlearning.app.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.smartlearning.app.R;
import com.smartlearning.app.dao.QuizResultDAO;
import com.smartlearning.app.dao.SubjectDAO;
import com.smartlearning.app.dao.TaskDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.utils.SessionManager;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvAverageScore, tvCompletedTasks, tvTotalQuizzes;
    private QuizResultDAO quizResultDAO;
    private TaskDAO taskDAO;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initViews();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        quizResultDAO = new QuizResultDAO(dbHelper);
        taskDAO = new TaskDAO(dbHelper);
        sessionManager = new SessionManager(this);

        loadStatistics();
    }

    private void initViews() {
        tvAverageScore = findViewById(R.id.tvAverageScore);
        tvCompletedTasks = findViewById(R.id.tvCompletedTasks);
        tvTotalQuizzes = findViewById(R.id.tvTotalQuizzes);
    }

    private void loadStatistics() {
        int userId = sessionManager.getUserId();
        double averageScore = quizResultDAO.getAverageScore(userId);
        int completedTasks = taskDAO.getCompletedTasksCount(userId);
        int totalQuizzes = quizResultDAO.getTotalQuizzesAttempted(userId);

        tvAverageScore.setText(String.format("%.1f", averageScore));
        tvCompletedTasks.setText(String.valueOf(completedTasks));
        tvTotalQuizzes.setText(String.valueOf(totalQuizzes));
    }
}
