package com.smartlearning.app.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.smartlearning.app.R;
import com.smartlearning.app.dao.UserDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.User;
import com.smartlearning.app.utils.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUserName, tvUserEmail;
    private EditText edtPhone;
    private Button btnUpdate, btnChangePassword, btnLogout;
    private UserDAO userDAO;
    private SessionManager sessionManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        userDAO = new UserDAO(dbHelper);
        sessionManager = new SessionManager(this);

        loadUserProfile();

        btnUpdate.setOnClickListener(v -> updateProfile());
        btnChangePassword.setOnClickListener(v -> showChangePasswordDialog());
        btnLogout.setOnClickListener(v -> logout());
    }

    private void initViews() {
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void loadUserProfile() {
        int userId = sessionManager.getUserId();
        currentUser = userDAO.getUserById(userId);
        if (currentUser != null) {
            tvUserName.setText(currentUser.getName());
            tvUserEmail.setText(currentUser.getEmail());
            edtPhone.setText(currentUser.getPhone());
        }
    }

    private void updateProfile() {
        currentUser.setPhone(edtPhone.getText().toString().trim());
        int result = userDAO.updateUser(currentUser);
        if (result > 0) {
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showChangePasswordDialog() {
        // Implementation for change password
    }

    private void logout() {
        sessionManager.logout();
        finish();
    }
}
