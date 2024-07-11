package com.example.sce.screen.ui.userHome;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.Toast;

import com.example.sce.R;
import com.example.sce.adapter.UserCourseViewAdapter;
import com.example.sce.common.CommonConstant;
import com.example.sce.db.course.CourseDao;
import com.example.sce.helper.PreferenceManager;
import com.example.sce.model.Course;
import com.example.sce.screen.Checkout;
import com.example.sce.screen.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UserHomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserCourseViewAdapter courseAdapter;
    private List<Course> courseList;
    private EditText searchVal;
    private FloatingActionButton fabCheckout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchVal = view.findViewById(R.id.editTextInput);
        fabCheckout = view.findViewById(R.id.fabCheckout);

        courseList = new ArrayList<>();
        loadCourses();
        courseAdapter = new UserCourseViewAdapter(getActivity(), courseList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(courseAdapter);

        searchVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filterCourses(searchVal.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCourses(searchVal.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterCourses(searchVal.getText().toString());
            }
        });

        PreferenceManager preferenceManager = new PreferenceManager(getActivity());
        String userType = preferenceManager.getUserType();

        fabCheckout.setOnClickListener(v -> {
            List<Course> selectedCourses = courseAdapter.getSelectedCourses();
            if (!selectedCourses.isEmpty() && userType.equals(CommonConstant.SIGNING_USER)) {
                Intent intent = new Intent(getActivity(), Checkout.class);
                intent.putParcelableArrayListExtra("selectedCourses", new ArrayList<>(selectedCourses));
                startActivity(intent);
            }
            if (userType.equals(CommonConstant.GUEST_USER)) {
                startActivity(new Intent(getContext(), Login.class));
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void loadCourses() {
        CourseDao courseDao = new CourseDao(getContext());
        courseList = courseDao.getAllCourses();
    }

    private void filterCourses(String query) {
        List<Course> filteredList = new ArrayList<>();
        if (!TextUtils.isEmpty(query)) {
            for (Course course : courseList) {
                if (course.getCourseName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(course);
                }
            }
        } else {
            filteredList.addAll(courseList);
        }
        courseAdapter = new UserCourseViewAdapter(getContext(), filteredList);
        recyclerView.setAdapter(courseAdapter);
    }
}
