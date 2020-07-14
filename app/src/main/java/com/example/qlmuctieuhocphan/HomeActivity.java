package com.example.qlmuctieuhocphan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Database db = new Database(this);

    RecyclerView recyclerView;
    ImageView imgEmptyData;
    TextView txtEmptyData;
    CourseAdapter adapter;

    List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        courseList = new ArrayList<>();

        recyclerView = findViewById(R.id.courseRecycleView);
        imgEmptyData = findViewById(R.id.imgEmptyData);
        txtEmptyData = findViewById(R.id.txtEmptyData);

        adapter = new CourseAdapter(this, courseList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        retrieve();
    }

    public void retrieve() {

        Cursor cursor = db.getData("courses");
        courseList.clear();

        if (cursor.getCount() == 0) {
            imgEmptyData.setVisibility(View.VISIBLE);
            txtEmptyData.setVisibility(View.VISIBLE);

        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double time1 = cursor.getDouble(2);
                double time2 = cursor.getDouble(3);
                double target = cursor.getDouble(4);

                Course course = new Course(id, name, time1, time2, target);

                courseList.add(course);
            }

            imgEmptyData.setVisibility(View.GONE);
            txtEmptyData.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addCourse:
                addCourse();
                break;
            case R.id.editUser:
                editUser();
                break;
            case R.id.aboutApp:
                Toast.makeText(getApplicationContext(), "Thong tin ung dung!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validateCourse(String name, String time1, String time2, String target) {

        if (name.length() < 3 || name.length() > 50) {
            Toast.makeText(getApplicationContext(), "Tên học phần phải từ 3 đến 50 ký tự!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Double.valueOf(time1) < 0.0 || Double.valueOf(time1) > 10.0) {
            Toast.makeText(getApplicationContext(), "Giá trị điểm lần 1 không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Double.valueOf(time2) < 0.0 || Double.valueOf(time2) > 10.0) {
            Toast.makeText(getApplicationContext(), "Giá trị điểm lần 1 không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Double.valueOf(target) < 0.0 || Double.valueOf(target) > 10.0) {
            Toast.makeText(getApplicationContext(), "Giá trị điểm mục tiêu không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void addCourse() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_course_dialog);

        final EditText course_name = dialog.findViewById(R.id.course_name);
        final EditText course_time1 = dialog.findViewById(R.id.course_time1);
        final EditText course_time2 = dialog.findViewById(R.id.course_time2);
        final EditText course_target = dialog.findViewById(R.id.course_target);
        Button btnSaveCourse = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnSaveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =  course_name.getText().toString().trim();
                String time1 = course_time1.getText().toString().trim();
                String time2 = course_time2.getText().toString().trim();
                String target = course_target.getText().toString().trim();

                if (time1.isEmpty()) {
                    time1 = "0";
                }
                if (time2.isEmpty()) {
                    time2 = "0";
                }
                if (target.isEmpty()) {
                    target = "0";
                }

                if (validateCourse(name, time1, time2, target) == true) {
                    boolean course = db.addCourse(name, Double.parseDouble(time1), Double.parseDouble(time2), Double.parseDouble(target));
                    if (course == true) {
                        retrieve();
                        Toast.makeText(getApplicationContext(), "Thêm học phần thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Thêm học phần thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void editCourse(final int id, String name, double time1, double time2, double target) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_course_dialog);

        final EditText course_name_edit = dialog.findViewById(R.id.course_name_edit);
        final EditText course_time1_edit = dialog.findViewById(R.id.course_time1_edit);
        final EditText course_time2_edit = dialog.findViewById(R.id.course_time2_edit);
        final EditText course_target_edit = dialog.findViewById(R.id.course_target_edit);
        Button btnEdit = dialog.findViewById(R.id.btnSave_edit);
        Button btnCancel = dialog.findViewById(R.id.btnCancel_edit);

        course_name_edit.setText(name);
        course_time1_edit.setText(String.valueOf(time1));
        course_time2_edit.setText(String.valueOf(time2));
        course_target_edit.setText(String.valueOf(target));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =  course_name_edit.getText().toString().trim();
                String time1 = course_time1_edit.getText().toString().trim();
                String time2 = course_time2_edit.getText().toString().trim();
                String target = course_target_edit.getText().toString().trim();

                if (time1.isEmpty()) {
                    time1 = "0";
                }
                if (time2.isEmpty()) {
                    time2 = "0";
                }
                if (target.isEmpty()) {
                    target = "0";
                }

                if (validateCourse(name, time1, time2, target) == true) {
                    boolean course = db.updateCourse(id, name, Double.parseDouble(time1), Double.parseDouble(time2), Double.parseDouble(target));
                    if (course == true) {
                        retrieve();
                        Toast.makeText(getApplicationContext(), "Sửa học phần thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sửa học phần thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void deleteCourse(String id) {
        db.deleteCourse(id);
        retrieve();
    }

    public void editUser() {
        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
    }
}