package com.pmit.ems.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.pmit.ems.R;
import com.pmit.ems.model.LeadCount;

import java.util.List;

public class LeadCountAdapter extends RecyclerView.Adapter<LeadCountAdapter.ViewHolder> {
    private Context mCtx;
    private List<LeadCount> leadCounts;
    int listview;
    public LeadCountAdapter(Context mCtx, List<LeadCount> leadCounts) {
        this.mCtx = mCtx;
        this.leadCounts = leadCounts;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_count, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LeadCount leadCount = leadCounts.get(position);
        listview = position;
        holder.led_tv4.setText(leadCount.getCourseName());
        holder.led_tvf4.setText(leadCount.getTotalLeads());
    }
    @Override
    public int getItemCount() {
        return leadCounts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView led_tv4;
        private MaterialButton led_tvf4;
        public ViewHolder(View view) {
            super(view);
            led_tv4 = (TextView) view.findViewById(R.id.led_tv4);
            led_tvf4 = (MaterialButton) view.findViewById(R.id.led_tvf4);
        }
    }
}
