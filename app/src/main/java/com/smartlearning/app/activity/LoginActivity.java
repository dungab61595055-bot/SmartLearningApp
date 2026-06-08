package com.smartlearning.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private UserDAO userDAO;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        userDAO = new UserDAO(dbHelper);
        sessionManager = new SessionManager(this);

        btnLogin.setOnClickListener(v -> login());
        tvRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void login() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Email không được để trống");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Mật khẩu không được để trống");
            return;
        }

        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            sessionManager.createSession(user);
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Email hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
        }
    }
}
