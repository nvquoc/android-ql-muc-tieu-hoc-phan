package com.example.qlmuctieuhocphan;

import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private HomeActivity context;
    private List<Course> courseList;

    public CourseAdapter(HomeActivity context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_item, parent, false);
        CourseViewHolder holder = new CourseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, final int position) {
        final Course course = courseList.get(position);

        DecimalFormat df = new DecimalFormat("#.##");

        holder.course_name.setText(course.getName());
        holder.course_time1.setText("Điểm lần 1: " + df.format(course.getTime1()));
        holder.course_time2.setText("Điểm lần 2: " + df.format(course.getTime2()));
        holder.course_target.setText("Điểm mục tiêu: " + df.format(course.getTarget()));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.editCourse(course.getId(), course.getName(), course.getTime1(), course.getTime2(), course.getTarget());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.deleteCourse(String.valueOf(course.getId()));
                Toast.makeText(context, "Đã xóa học phần "+course.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView course_name;
        TextView course_time1;
        TextView course_time2;
        TextView course_target;
        ImageView btnEdit;
        ImageView btnDelete;

        public CourseViewHolder(View itemView) {
            super(itemView);

            course_name = itemView.findViewById(R.id.txtCourseName);
            course_time1 = itemView.findViewById(R.id.txtTime1);
            course_time2 = itemView.findViewById(R.id.txtTime2);
            course_target = itemView.findViewById(R.id.txtTarget);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
