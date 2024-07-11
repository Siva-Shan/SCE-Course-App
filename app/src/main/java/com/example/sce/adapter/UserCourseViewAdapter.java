package com.example.sce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sce.R;
import com.example.sce.helper.PreferenceManager;
import com.example.sce.model.Course;
import com.example.sce.screen.ViewLocations;

import java.util.ArrayList;
import java.util.List;

public class UserCourseViewAdapter extends RecyclerView.Adapter<UserCourseViewAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> courseList;
    private List<Course> selectedCourses;

    public UserCourseViewAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
        this.selectedCourses = new ArrayList<>();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_course_details_recycler_view, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);

        String dayIdentifier = course.getDuration() > 1 ? " Days" : " Day";
        holder.textViewCourseName.setText(course.getCourseName());
        holder.textViewCourseFee.setText("$ " + course.getCourseFee());
        holder.textViewCourseDuration.setText(course.getDuration() + dayIdentifier);
        holder.textViewCourseDue.setText("Due : " + course.getRegistrationCloseDate());
        holder.textViewCourseStart.setText("From : " + course.getStartDate());

        holder.itemView.setOnClickListener(v -> {

            if (selectedCourses.contains(course)) {
                selectedCourses.remove(course);
                holder.userCourseLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            } else {
                selectedCourses.add(course);
                holder.userCourseLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_course_background));
            }
        });

        holder.imageViewLocation.setOnClickListener(v -> {
            double[] latitudes = {9.66845, 6.8385207,7.0897,6.0367 , 6.927079};
            double[] longitudes = {80.0074, 79.965432,79.992, 80.217,79.86124};

            Intent intent = new Intent(context, ViewLocations.class);
            intent.putExtra("latitudes", latitudes);
            intent.putExtra("longitudes", longitudes);
            context.startActivity(intent);
        });

        if (selectedCourses.contains(course)) {
            holder.userCourseLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_course_background));
        } else {
            holder.userCourseLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public List<Course> getSelectedCourses() {
        return selectedCourses;
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCourseName, textViewCourseDue, textViewCourseDuration, textViewCourseFee, textViewCourseStart;
        ImageView imageViewLocation;
        LinearLayout userCourseLayout;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCourseName = itemView.findViewById(R.id.textViewCourseName);
            imageViewLocation = itemView.findViewById(R.id.imageViewLocation);
            textViewCourseDue = itemView.findViewById(R.id.textViewCourseDue);
            textViewCourseDuration = itemView.findViewById(R.id.textViewCourseDuration);
            textViewCourseFee = itemView.findViewById(R.id.textViewCourseFee);
            textViewCourseStart = itemView.findViewById(R.id.textViewCourseStart);
            userCourseLayout = itemView.findViewById(R.id.userCourseLayout);
        }
    }
}
