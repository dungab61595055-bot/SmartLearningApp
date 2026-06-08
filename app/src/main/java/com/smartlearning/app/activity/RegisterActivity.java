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

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPassword, edtConfirmPassword, edtPhone;
    private Button btnRegister;
    private TextView tvLogin;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        userDAO = new UserDAO(dbHelper);

        btnRegister.setOnClickListener(v -> register());
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void initViews() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtPhone = findViewById(R.id.edtPhone);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
    }

    private void register() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtName.setError("Tên không được để trống");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Email không được để trống");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Mật khẩu không được để trống");
            return;
        }

        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            return;
        }

        if (userDAO.isEmailExists(email)) {
            Toast.makeText(this, "Email đã được đăng ký!", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(name, email, password, phone);
        long result = userDAO.addUser(user);

        if (result > 0) {
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
