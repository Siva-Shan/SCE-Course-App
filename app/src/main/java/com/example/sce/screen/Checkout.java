package com.example.sce.screen;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sce.R;
import com.example.sce.adapter.UserCourseViewAdapter;
import com.example.sce.model.Course;
import java.util.ArrayList;

public class Checkout extends AppCompatActivity {
    private RecyclerView recyclerViewCheckout;
    private UserCourseViewAdapter courseAdapter;
    private ArrayList<Course> selectedCourses;
    private TextView textViewTotalPrice;
    private TextView textViewDiscountedPrice;
    private EditText editTextPromoCode;
    private Button buttonApplyPromo;
    private Button buttonCheckout;
    private double totalPrice = 0.0;
    private double discountedPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerViewCheckout = findViewById(R.id.recyclerViewCheckout);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        textViewDiscountedPrice = findViewById(R.id.textViewDiscountedPrice);
        editTextPromoCode = findViewById(R.id.editTextPromoCode);
        buttonApplyPromo = findViewById(R.id.buttonApplyPromo);
        buttonCheckout = findViewById(R.id.buttonCheckout);

        selectedCourses = getIntent().getParcelableArrayListExtra("selectedCourses");
        courseAdapter = new UserCourseViewAdapter(this, selectedCourses);

        recyclerViewCheckout.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCheckout.setAdapter(courseAdapter);

        calculateTotalPrice();

        buttonApplyPromo.setOnClickListener(v -> applyPromoCode());

        buttonCheckout.setOnClickListener(v -> navigateToPayment());
    }

    private void calculateTotalPrice() {
        totalPrice = 0.0;
        for (Course course : selectedCourses) {
            totalPrice += course.getCourseFee();
        }
        textViewTotalPrice.setText(String.format("Total Price: $%.2f", totalPrice));
    }

    private void applyPromoCode() {
        String promoCode = editTextPromoCode.getText().toString().trim();
        if (promoCode.equalsIgnoreCase("SCEDISCOUNT15")) {
            discountedPrice = totalPrice * 0.90;
        } else {
            discountedPrice = totalPrice;
        }
        textViewDiscountedPrice.setText(String.format("Discounted Price: $%.2f", discountedPrice));
        textViewDiscountedPrice.setVisibility(View.VISIBLE);
    }

    private void navigateToPayment() {
        Intent intent = new Intent(Checkout.this, Payment.class);
        intent.putExtra("totalPrice", discountedPrice > 0 ? discountedPrice : totalPrice);
        intent.putParcelableArrayListExtra("selectedCourses", selectedCourses);
        startActivity(intent);
        finish();
    }
}

