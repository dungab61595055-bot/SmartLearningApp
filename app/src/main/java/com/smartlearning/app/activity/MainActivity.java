package com.smartlearning.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartlearning.app.R;
import com.smartlearning.app.dao.SubjectDAO;
import com.smartlearning.app.dao.TaskDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private FloatingActionButton fabAdd;
    private TextView tvWelcome;
    private SessionManager sessionManager;
    private SubjectDAO subjectDAO;
    private TaskDAO taskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        sessionManager = new SessionManager(this);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        subjectDAO = new SubjectDAO(dbHelper);
        taskDAO = new TaskDAO(dbHelper);

        String userName = sessionManager.getUserName();
        tvWelcome.setText("Chào " + userName);

        fabAdd.setOnClickListener(v -> showAddDialog());
        bottomNav.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        // Load dashboard fragment
        if (savedInstanceState == null) {
            loadDashboardFragment();
        }
    }

    private void initViews() {
        bottomNav = findViewById(R.id.bottomNav);
        fabAdd = findViewById(R.id.fabAdd);
        tvWelcome = findViewById(R.id.tvWelcome);
    }

    private boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_dashboard) {
            loadDashboardFragment();
            return true;
        } else if (itemId == R.id.nav_subjects) {
            startActivity(new Intent(MainActivity.this, SubjectActivity.class));
            return true;
        } else if (itemId == R.id.nav_schedule) {
            startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            return true;
        } else if (itemId == R.id.nav_statistics) {
            startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
            return true;
        } else if (itemId == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            return true;
        }
        return false;
    }

    private void loadDashboardFragment() {
        // Load dashboard content
    }

    private void showAddDialog() {
        // Show dialog to add subject, schedule, or task
    }
}
