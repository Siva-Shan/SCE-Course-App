package com.example.sce.screen.ui.userEnrollment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sce.R;
import com.example.sce.adapter.PurchaseAdapter;
import com.example.sce.db.enrollment.EnrollmentDao;
import com.example.sce.helper.PreferenceManager;
import com.example.sce.model.Purchase;

import java.util.List;

public class UserEnrollmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private PurchaseAdapter purchaseAdapter;
    private List<Purchase> purchaseList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_enrollment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPurchases);

        EnrollmentDao enrollmentDao = new EnrollmentDao(getContext());

        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        String userID = preferenceManager.getUserId();

        purchaseList = enrollmentDao.getAllPurchases(Integer.parseInt(userID));
        enrollmentDao.close();

        purchaseAdapter = new PurchaseAdapter(getActivity(), purchaseList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(purchaseAdapter);

        return view;
    }
}
