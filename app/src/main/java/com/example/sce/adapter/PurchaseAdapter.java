package com.example.sce.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sce.R;
import com.example.sce.db.course.CourseDao;
import com.example.sce.model.Course;
import com.example.sce.model.Enrollment;
import com.example.sce.model.Purchase;
import com.example.sce.screen.PurchaseDetails;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {

    private Context context;
    private List<Purchase> purchaseList;

    public PurchaseAdapter(Context context, List<Purchase> purchaseList) {
        this.context = context;
        this.purchaseList = purchaseList;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.enrollment_item_recyler_view, parent, false);
        return new PurchaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        Purchase purchase = purchaseList.get(position);

        holder.textViewPurchasePrice.setText("Purchase Code: " + purchase.getPurchaseCode());
        holder.textViewCourseNames.setText(getCoursesNames(purchase));

        holder.buttonViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, PurchaseDetails.class);
            intent.putExtra("purchaseCode", purchase.getPurchaseCode());
            context.startActivity(intent);
        });
    }

    private String getCoursesNames(Purchase purchase) {
        StringBuilder courseNames = new StringBuilder();
        CourseDao courseDao = new CourseDao(context);
        for (Enrollment enrollment : purchase.getEnrollments()) {
            if (courseNames.length() > 0) {
                courseNames.append("\n");
            }
            try{
                Course course = courseDao.getCourse(enrollment.getCourseId());
                courseNames.append(course.getCourseName());
            }catch (Exception e){

            }

        }
        return courseNames.toString();
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    static class PurchaseViewHolder extends RecyclerView.ViewHolder {

        TextView textViewPurchasePrice, textViewCourseNames;
        Button buttonViewDetails;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPurchasePrice = itemView.findViewById(R.id.textViewPurchasePrice);
            textViewCourseNames = itemView.findViewById(R.id.textViewCourseNames);
            buttonViewDetails = itemView.findViewById(R.id.buttonViewDetails);
        }
    }
}
