package com.smartlearning.app.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.smartlearning.app.R;
import com.smartlearning.app.dao.QuizDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.utils.SessionManager;

public class QuizActivity extends AppCompatActivity {

    private RecyclerView rvQuizzes;
    private QuizDAO quizDAO;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initViews();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        quizDAO = new QuizDAO(dbHelper);
        sessionManager = new SessionManager(this);

        loadQuizzes();
    }

    private void initViews() {
        rvQuizzes = findViewById(R.id.rvQuizzes);
        rvQuizzes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadQuizzes() {
        int userId = sessionManager.getUserId();
        // Load quizzes for the user
    }
}
