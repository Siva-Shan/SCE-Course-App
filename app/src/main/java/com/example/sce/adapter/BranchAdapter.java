package com.example.sce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sce.R;
import com.example.sce.model.Branch;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder> {

    private List<Branch> branchList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Branch branch);
    }

    public BranchAdapter(List<Branch> branchList, OnItemClickListener listener) {
        this.branchList = branchList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_details_recycler_view, parent, false);
        return new BranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        Branch branch = branchList.get(position);
        holder.bind(branch, listener);
    }

    @Override
    public int getItemCount() {
        return branchList.size();
    }

    static class BranchViewHolder extends RecyclerView.ViewHolder {
        TextView tvBranchCode;
        TextView tvBranchName;

        public BranchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBranchCode = itemView.findViewById(R.id.tvBranchCode);
            tvBranchName = itemView.findViewById(R.id.tvBranchName);
        }

        public void bind(final Branch branch, final OnItemClickListener listener) {
            tvBranchCode.setText(branch.getBranchCode());
            tvBranchName.setText(branch.getBranchName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(branch);
                }
            });
        }
    }
}
