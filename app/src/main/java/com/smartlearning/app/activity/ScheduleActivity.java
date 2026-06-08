package com.smartlearning.app.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.smartlearning.app.R;
import com.smartlearning.app.adapter.ScheduleAdapter;
import com.smartlearning.app.dao.ScheduleDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.utils.SessionManager;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView rvSchedules;
    private ScheduleDAO scheduleDAO;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initViews();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        scheduleDAO = new ScheduleDAO(dbHelper);
        sessionManager = new SessionManager(this);

        loadSchedules();
    }

    private void initViews() {
        rvSchedules = findViewById(R.id.rvSchedules);
        rvSchedules.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadSchedules() {
        int userId = sessionManager.getUserId();
        // Load schedules for the user
    }
}
