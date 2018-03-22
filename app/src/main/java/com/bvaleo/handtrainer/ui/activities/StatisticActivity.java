package com.bvaleo.handtrainer.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.bvaleo.handtrainer.R;
import com.bvaleo.handtrainer.adapters.StatisticAdapter;
import com.bvaleo.handtrainer.database.DatabaseHelper;
import com.bvaleo.handtrainer.model.Statistic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Valery on 20.03.2018.
 */

public class StatisticActivity extends AppCompatActivity {

    private static final String USER_ID = "userId";

    @BindView(R.id.rv_statistic)
    RecyclerView rvStatistic;

    private StatisticAdapter mAdapter;
    private long userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        ButterKnife.bind(this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_action_name);
        actionBar.setTitle(R.string.statistics);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);

        rvStatistic.setLayoutManager(layoutManager);
        rvStatistic.addItemDecoration(decoration);

        Bundle b = getIntent().getExtras();
        if(b != null) userId = b.getLong(USER_ID);

        DatabaseHelper db = new DatabaseHelper(this);
        mAdapter = new StatisticAdapter(db.getStatisticByUserId(userId));
        rvStatistic.setAdapter(mAdapter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
