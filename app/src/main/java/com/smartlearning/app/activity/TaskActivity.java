package com.smartlearning.app.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.smartlearning.app.R;
import com.smartlearning.app.dao.TaskDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.utils.SessionManager;

public class TaskActivity extends AppCompatActivity {

    private RecyclerView rvTasks;
    private TaskDAO taskDAO;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        initViews();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        taskDAO = new TaskDAO(dbHelper);
        sessionManager = new SessionManager(this);

        loadTasks();
    }

    private void initViews() {
        rvTasks = findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadTasks() {
        int userId = sessionManager.getUserId();
        // Load tasks for the user
    }
}
