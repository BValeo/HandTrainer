package com.bvaleo.handtrainer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bvaleo.handtrainer.R;
import com.bvaleo.handtrainer.model.Statistic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Valery on 20.03.2018.
 */

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder> {


    private List<Statistic> mData;


    public StatisticAdapter(List<Statistic> data){
        this.mData = data;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_timestamp)
        TextView tvTimestamp;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_counter)
        TextView tvCounter;
        @BindView(R.id.tv_duration)
        TextView tvDuration;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Statistic stat = mData.get(position);
        holder.tvCounter.setText(Long.toString(stat.getCount()) + " движений");
        holder.tvDuration.setText(stat.getDuration());
        holder.tvTimestamp.setText(stat.getDate());

        if(!stat.getComment().equals("")) holder.tvDescription.setText(stat.getComment());
        else holder.tvDescription.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
