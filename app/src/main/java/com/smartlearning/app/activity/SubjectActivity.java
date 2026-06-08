package com.smartlearning.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.smartlearning.app.R;
import com.smartlearning.app.adapter.SubjectAdapter;
import com.smartlearning.app.dao.SubjectDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.Subject;
import com.smartlearning.app.utils.SessionManager;
import java.util.List;

public class SubjectActivity extends AppCompatActivity {

    private RecyclerView rvSubjects;
    private Button btnAddSubject;
    private SubjectDAO subjectDAO;
    private SessionManager sessionManager;
    private SubjectAdapter adapter;
    private List<Subject> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        initViews();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        subjectDAO = new SubjectDAO(dbHelper);
        sessionManager = new SessionManager(this);

        loadSubjects();
        btnAddSubject.setOnClickListener(v -> showAddSubjectDialog());
    }

    private void initViews() {
        rvSubjects = findViewById(R.id.rvSubjects);
        btnAddSubject = findViewById(R.id.btnAddSubject);
        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadSubjects() {
        int userId = sessionManager.getUserId();
        subjects = subjectDAO.getAllSubjects(userId);
        adapter = new SubjectAdapter(subjects, this);
        rvSubjects.setAdapter(adapter);
    }

    private void showAddSubjectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm môn học");

        EditText edtName = new EditText(this);
        edtName.setHint("Tên môn học");
        EditText edtDescription = new EditText(this);
        edtDescription.setHint("Mô tả");
        EditText edtColor = new EditText(this);
        edtColor.setHint("Màu sắc (Hex code)");

        builder.setView(edtName);
        builder.setView(edtDescription);
        builder.setView(edtColor);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String name = edtName.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();
            String color = edtColor.getText().toString().trim();

            if (!name.isEmpty()) {
                Subject subject = new Subject(name, description, color);
                subject.setUserId(sessionManager.getUserId());
                long result = subjectDAO.addSubject(subject);
                if (result > 0) {
                    Toast.makeText(SubjectActivity.this, "Thêm môn học thành công!", Toast.LENGTH_SHORT).show();
                    loadSubjects();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
