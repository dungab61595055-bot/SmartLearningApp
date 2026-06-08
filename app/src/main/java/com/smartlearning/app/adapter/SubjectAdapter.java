package com.smartlearning.app.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.smartlearning.app.R;
import com.smartlearning.app.dao.SubjectDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.Subject;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<Subject> subjects;
    private Context context;
    private SubjectDAO subjectDAO;

    public SubjectAdapter(List<Subject> subjects, Context context) {
        this.subjects = subjects;
        this.context = context;
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.subjectDAO = new SubjectDAO(dbHelper);
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.tvSubjectName.setText(subject.getName());
        holder.tvSubjectDescription.setText(subject.getDescription());

        holder.btnEdit.setOnClickListener(v -> showEditDialog(subject, position));
        holder.btnDelete.setOnClickListener(v -> deleteSubject(subject, position));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    private void showEditDialog(Subject subject, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sửa môn học");

        EditText edtName = new EditText(context);
        edtName.setText(subject.getName());
        EditText edtDescription = new EditText(context);
        edtDescription.setText(subject.getDescription());

        builder.setView(edtName);
        builder.setView(edtDescription);

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            subject.setName(edtName.getText().toString());
            subject.setDescription(edtDescription.getText().toString());
            int result = subjectDAO.updateSubject(subject);
            if (result > 0) {
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                notifyItemChanged(position);
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void deleteSubject(Subject subject, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa môn học");
        builder.setMessage("Bạn có chắc muốn xóa môn học này?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            int result = subjectDAO.deleteSubject(subject.getId());
            if (result > 0) {
                subjects.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectName, tvSubjectDescription;
        Button btnEdit, btnDelete;

        SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvSubjectDescription = itemView.findViewById(R.id.tvSubjectDescription);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
