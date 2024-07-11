package com.example.sce.screen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sce.R;
import com.example.sce.db.branch.BranchDao;
import com.example.sce.db.course.CourseDao;
import com.example.sce.helper.DateHelper;
import com.example.sce.model.Branch;
import com.example.sce.model.Course;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewCourse extends AppCompatActivity {

    private EditText editTextCourseName;
    private EditText editTextCourseFee;
    private MultiAutoCompleteTextView multiAutoCompleteTextViewBranches;
    private EditText editTextDuration;
    private EditText editTextRegistrationCloseDate;
    private EditText editTextStartDate;
    private EditText editTextMaxParticipants;
    private Button buttonAddCourse;
    private Calendar calendar;
    int year;
    int month;
    int dayOfMonth;
    DatePickerDialog datePickerDialog;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextCourseFee = findViewById(R.id.editTextCourseFee);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextRegistrationCloseDate = findViewById(R.id.editTextRegistrationCloseDate);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextMaxParticipants = findViewById(R.id.editTextMaxParticipants);
        buttonAddCourse = findViewById(R.id.buttonAddCourse);

        multiAutoCompleteTextViewBranches = findViewById(R.id.multiAutoCompleteTextViewBranches);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        if (id != null) {
            CourseDao courseDao = new CourseDao(getApplicationContext());
            course = courseDao.getCourse(Long.parseLong(id));
        }

        if (course == null) {
            buttonAddCourse.setText("Add Course");
        } else {
            buttonAddCourse.setText("Save");
            editTextCourseName.setText(course.getCourseName());
            editTextCourseFee.setText(String.valueOf(course.getCourseFee()));
            multiAutoCompleteTextViewBranches.setText(String.join(", ", course.getBranches()));
            editTextDuration.setText(String.valueOf(course.getDuration()));
            editTextRegistrationCloseDate.setText(course.getRegistrationCloseDate());
            editTextStartDate.setText(course.getStartDate());
            editTextMaxParticipants.setText(String.valueOf(course.getMaxParticipants()));
        }

        List<Branch> branchList = new ArrayList<>();
        BranchDao branchDao = new BranchDao(getApplicationContext());
        branchList = branchDao.getAllBranches();

        String[] branches = new String[branchList.size()];

        for (int i = 0; i < branchList.size(); i++) {
            branches[i] = branchList.get(i).getBranchName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, branches);
        multiAutoCompleteTextViewBranches.setAdapter(adapter);
        multiAutoCompleteTextViewBranches.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (course == null) {
                    addNewCourse();
                } else {
                    updateCourse();
                }

            }
        });

        editTextRegistrationCloseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickClosingDate(v);
            }
        });

        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickStartingDate(v);
            }
        });
    }

    private void addNewCourse() {
        String courseName = editTextCourseName.getText().toString();
        String courseFeeStr = editTextCourseFee.getText().toString();
        String durationStr = editTextDuration.getText().toString();
        String publishedDate = DateHelper.getCurrentDate();
        String registrationCloseDate = editTextRegistrationCloseDate.getText().toString();
        String startDate = editTextStartDate.getText().toString();
        String maxParticipantsStr = editTextMaxParticipants.getText().toString();
        String selectedBranches = multiAutoCompleteTextViewBranches.getText().toString();


        if (courseName.isEmpty() || courseFeeStr.isEmpty() || durationStr.isEmpty() ||
                registrationCloseDate.isEmpty() || startDate.isEmpty() || maxParticipantsStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double courseFee = Double.parseDouble(courseFeeStr);
        int duration = Integer.parseInt(durationStr);
        int maxParticipants = Integer.parseInt(maxParticipantsStr);

        String[] branchesArray = selectedBranches.split("\\s*,\\s*");

        Course course = new Course(courseName, courseFee, branchesArray, duration, publishedDate, registrationCloseDate, startDate, maxParticipants);

        CourseDao courseDao = new CourseDao(getApplicationContext());
        courseDao.addCourse(course);

        Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show();
        clearFields();
        finish();
    }

    private void updateCourse() {
        String courseName = editTextCourseName.getText().toString();
        String courseFeeStr = editTextCourseFee.getText().toString();
        String durationStr = editTextDuration.getText().toString();
        String registrationCloseDate = editTextRegistrationCloseDate.getText().toString();
        String startDate = editTextStartDate.getText().toString();
        String maxParticipantsStr = editTextMaxParticipants.getText().toString();
        String selectedBranches = multiAutoCompleteTextViewBranches.getText().toString();


        if (courseName.isEmpty() || courseFeeStr.isEmpty() || durationStr.isEmpty() ||
                registrationCloseDate.isEmpty() || startDate.isEmpty() || maxParticipantsStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double courseFee = Double.parseDouble(courseFeeStr);
        int duration = Integer.parseInt(durationStr);
        int maxParticipants = Integer.parseInt(maxParticipantsStr);

        String[] branchesArray = selectedBranches.split("\\s*,\\s*");

        course.setCourseName(courseName);
        course.setCourseFee(courseFee);
        course.setBranches(branchesArray);
        course.setDuration(duration);
        course.setMaxParticipants(maxParticipants);
        course.setStartDate(startDate);
        course.setRegistrationCloseDate(registrationCloseDate);

        CourseDao courseDao = new CourseDao(getApplicationContext());
        courseDao.updateCourse(course);

        Toast.makeText(this, "Course updated successfully", Toast.LENGTH_SHORT).show();
        clearFields();
        finish();
    }

    private void clearFields() {
        editTextCourseName.setText("");
        editTextCourseFee.setText("");
        multiAutoCompleteTextViewBranches.setText("");
        editTextDuration.setText("");
        editTextRegistrationCloseDate.setText("");
        editTextStartDate.setText("");
        editTextMaxParticipants.setText("");
    }

    public void pickClosingDate(View v) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(NewCourse.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editTextRegistrationCloseDate.setText(day + "/" + (month + 1) + "/" + year);

            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    public void pickStartingDate(View v) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(NewCourse.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editTextStartDate.setText(day + "/" + (month + 1) + "/" + year);

            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}