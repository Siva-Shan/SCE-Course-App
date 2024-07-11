package com.example.sce.screen.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sce.R;
import com.example.sce.adapter.CourseAdapter;
import com.example.sce.db.course.CourseDao;
import com.example.sce.model.Course;
import com.example.sce.screen.Branches;
import com.example.sce.screen.Login;
import com.example.sce.screen.NewCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private Button manageBranchesButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        manageBranchesButton = view.findViewById(R.id.manageBranchesButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        manageBranchesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Branches.class));
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), NewCourse.class));
            }
        });

        courseList = new ArrayList<>();
        CourseDao courseDao = new CourseDao(getContext());
        courseList = courseDao.getAllCourses();

        courseAdapter = new CourseAdapter(courseList, getContext());
        recyclerView.setAdapter(courseAdapter);

        return view;
    }
}