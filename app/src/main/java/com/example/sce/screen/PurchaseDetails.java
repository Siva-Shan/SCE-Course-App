package com.example.sce.screen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sce.R;
import com.example.sce.db.course.CourseDao;
import com.example.sce.db.enrollment.EnrollmentDao;
import com.example.sce.model.Course;
import com.example.sce.model.Enrollment;
import com.example.sce.model.Purchase;

public class PurchaseDetails extends AppCompatActivity {

    private TextView textViewPurchaseDetails, courses;
    private Button buttonCancelPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);

        textViewPurchaseDetails = findViewById(R.id.textViewPurchaseDetails);
        courses = findViewById(R.id.courses);
        buttonCancelPurchase = findViewById(R.id.buttonCancelPurchase);

        String purchaseCode = getIntent().getStringExtra("purchaseCode");

        if (purchaseCode != null) {
            EnrollmentDao enrollmentDao = new EnrollmentDao(this);
            Purchase purchase = enrollmentDao.getPurchaseById(purchaseCode);

            if (purchase != null) {
                StringBuilder purchaseDetails = new StringBuilder();
                purchaseDetails.append("Total Price: $ ").append(calculateTotalPrice(purchase)).append("\n");
                purchaseDetails.append("Branch: ").append(purchase.getEnrollments().get(0).getBranch());

                String courseNames = "\n";
                CourseDao courseDao = new CourseDao(getApplicationContext());
                for (Enrollment enrollment : purchase.getEnrollments()) {
                    courseNames += courseDao.getCourse(enrollment.getCourseId()).getCourseName() + "\n";
                }

                textViewPurchaseDetails.setText(purchaseDetails.toString());
                courses.setText(courseNames);
            }

            buttonCancelPurchase.setOnClickListener(v -> {
                enrollmentDao.deletePurchase(purchaseCode);
                finish();
            });
        }
    }

    private double calculateTotalPrice(Purchase purchase) {
        CourseDao courseDao = new CourseDao(getApplicationContext());
        double total = 0;
        for (Enrollment enrollment : purchase.getEnrollments()) {
            try {
                Course course = courseDao.getCourse(enrollment.getCourseId());
                total += course.getCourseFee();
            } catch (Exception e) {

            }
        }
        return total;
    }
}
