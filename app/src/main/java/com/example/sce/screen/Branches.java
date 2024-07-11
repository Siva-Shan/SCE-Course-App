package com.example.sce.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sce.R;
import com.example.sce.adapter.BranchAdapter;
import com.example.sce.db.branch.BranchDao;
import com.example.sce.model.Branch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Branches extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BranchAdapter branchAdapter;
    private List<Branch> branchList;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);

        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        branchList = new ArrayList<>();
        BranchDao branchDao = new BranchDao(getApplicationContext());
        branchList = branchDao.getAllBranches();

        branchAdapter = new BranchAdapter(branchList, new BranchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Branch branch) {
                Intent intent = new Intent(Branches.this, NewBranch.class);
                intent.putExtra("branch", branch);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(branchAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Branches.this, NewBranch.class);
                startActivity(intent);
            }
        });
    }
}
