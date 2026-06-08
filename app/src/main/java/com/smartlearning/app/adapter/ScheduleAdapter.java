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
import com.smartlearning.app.dao.ScheduleDAO;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.Schedule;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<Schedule> schedules;
    private Context context;
    private ScheduleDAO scheduleDAO;

    public ScheduleAdapter(List<Schedule> schedules, Context context) {
        this.schedules = schedules;
        this.context = context;
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.scheduleDAO = new ScheduleDAO(dbHelper);
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        holder.tvTitle.setText(schedule.getTitle());
        holder.tvDate.setText(schedule.getDate());
        holder.tvTime.setText(schedule.getStartTime() + " - " + schedule.getEndTime());
        holder.tvType.setText(schedule.getType());

        holder.btnEdit.setOnClickListener(v -> showEditDialog(schedule, position));
        holder.btnDelete.setOnClickListener(v -> deleteSchedule(schedule, position));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    private void showEditDialog(Schedule schedule, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sửa lịch học");

        EditText edtTitle = new EditText(context);
        edtTitle.setText(schedule.getTitle());
        EditText edtDate = new EditText(context);
        edtDate.setText(schedule.getDate());
        EditText edtStartTime = new EditText(context);
        edtStartTime.setText(schedule.getStartTime());
        EditText edtEndTime = new EditText(context);
        edtEndTime.setText(schedule.getEndTime());

        builder.setView(edtTitle);
        builder.setView(edtDate);
        builder.setView(edtStartTime);
        builder.setView(edtEndTime);

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            schedule.setTitle(edtTitle.getText().toString());
            schedule.setDate(edtDate.getText().toString());
            schedule.setStartTime(edtStartTime.getText().toString());
            schedule.setEndTime(edtEndTime.getText().toString());
            int result = scheduleDAO.updateSchedule(schedule);
            if (result > 0) {
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                notifyItemChanged(position);
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void deleteSchedule(Schedule schedule, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa lịch học");
        builder.setMessage("Bạn có chắc muốn xóa lịch này?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            int result = scheduleDAO.deleteSchedule(schedule.getId());
            if (result > 0) {
                schedules.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvTime, tvType;
        Button btnEdit, btnDelete;

        ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvType = itemView.findViewById(R.id.tvType);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
