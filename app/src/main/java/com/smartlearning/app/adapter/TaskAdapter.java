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
import com.smartlearning.app.dao.TaskDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private Context context;
    private TaskDAO taskDAO;

    public TaskAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.taskDAO = new TaskDAO(dbHelper);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.tvTaskTitle.setText(task.getTitle());
        holder.tvTaskDescription.setText(task.getDescription());
        holder.tvDueDate.setText("Hạn chót: " + task.getDueDate());
        holder.btnComplete.setText(task.isCompleted() ? "✓ Đã hoàn thành" : "Hoàn thành");
        holder.btnComplete.setEnabled(!task.isCompleted());

        holder.btnComplete.setOnClickListener(v -> completeTask(task, position));
        holder.btnDelete.setOnClickListener(v -> deleteTask(task, position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private void completeTask(Task task, int position) {
        task.setCompleted(true);
        task.setCompletedDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        int result = taskDAO.updateTask(task);
        if (result > 0) {
            Toast.makeText(context, "Đánh dấu hoàn thành!", Toast.LENGTH_SHORT).show();
            notifyItemChanged(position);
        }
    }

    private void deleteTask(Task task, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa bài tập");
        builder.setMessage("Bạn có chắc muốn xóa bài tập này?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            int result = taskDAO.deleteTask(task.getId());
            if (result > 0) {
                tasks.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskTitle, tvTaskDescription, tvDueDate;
        Button btnComplete, btnDelete;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskDescription = itemView.findViewById(R.id.tvTaskDescription);
            tvDueDate = itemView.findViewById(R.id.tvDueDate);
            btnComplete = itemView.findViewById(R.id.btnComplete);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
