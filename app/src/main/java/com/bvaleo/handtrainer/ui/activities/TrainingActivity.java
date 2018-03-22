package com.bvaleo.handtrainer.ui.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bvaleo.handtrainer.R;
import com.bvaleo.handtrainer.presenters.ITrainingPresenter;
import com.bvaleo.handtrainer.presenters.TrainingPresenter;
import com.bvaleo.handtrainer.ui.ITrainingView;
import com.bvaleo.handtrainer.ui.OnFinishTrainingDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrainingActivity extends AppCompatActivity implements ITrainingView {

    private static final String dialogTag = "commentDialog";
    private static final String USER_ID = "userId";

    ITrainingPresenter presenter;

    @BindView(R.id.btn_training_control)
    Button btnTrainingControl;
    @BindView(R.id.tw_counter)
    TextView tvCounter;
    @BindView(R.id.btn_choose_max_value)
    Button btnChooseMaxValue;
    @BindView(R.id.np_max_value)
    NumberPicker npMaxValue;
    @BindView(R.id.btn_set_max_value)
    Button btnSetMaxValue;
    @BindView(R.id.main_layout_training)
    RelativeLayout mainLayoutTraining;


    private String timeStamp;
    private long timeStampMillis;
    private String duration;
    private boolean isActive = false;
    private String[] colors;

    private int valueOfRotation;

    private long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        ButterKnife.bind(this);

        colors = getResources().getStringArray(R.array.colors_training);

        presenter = new TrainingPresenter(getBaseContext(), this);

        btnChooseMaxValue.setOnClickListener(view -> {
            btnTrainingControl.setEnabled(false);
            btnChooseMaxValue.setVisibility(View.INVISIBLE);
            btnSetMaxValue.setVisibility(View.VISIBLE);
            npMaxValue.setMaxValue(1000);
            npMaxValue.setMinValue(10);
            npMaxValue.setValue(30);
            npMaxValue.setVisibility(View.VISIBLE);
        });
        btnSetMaxValue.setOnClickListener(view -> {
            btnChooseMaxValue.setVisibility(View.VISIBLE);
            npMaxValue.setVisibility(View.GONE);
            btnSetMaxValue.setVisibility(View.GONE);
            valueOfRotation = npMaxValue.getValue();
            btnTrainingControl.setEnabled(true);

        });
        btnTrainingControl.setOnClickListener(view -> {
            if (!isActive) {
                saveTimeStart();
                isActive = true;
                presenter.startTraining();
                btnTrainingControl.setText(R.string.finish_training);

            } else {
                calculateDuration();
                isActive = false;
                presenter.finishTraining();
                tvCounter.setText("0");
                btnTrainingControl.setText(R.string.start_training);
            }
        });


        if (getIntent().hasExtra(USER_ID)) {
            userId = getIntent().getLongExtra(USER_ID, 0);
        }


    }

    private void saveTimeStart() {
        Calendar c = Calendar.getInstance();
        timeStampMillis = c.getTimeInMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yy HH:mm");
        timeStamp = simpleDateFormat.format(c.getTime());
    }

    private void calculateDuration() {
        Calendar c = Calendar.getInstance();
        long durationLong = c.getTimeInMillis() - timeStampMillis;

        int seconds = (int) (durationLong / 1000) % 60;
        int minutes = (int) ((durationLong / (1000 * 60)) % 60);

        StringBuilder str = new StringBuilder();

        if (minutes != 0) str.append(minutes + " мин ");
        if (seconds != 0) str.append(seconds + " сек");

        duration = str.toString();
    }

    @Override
    public void finishTraining(int counter) {
        DialogFragment dialogFragment = new OnFinishTrainingDialog();

        if (duration.equals("")) return;

        Bundle bundle = new Bundle();
        bundle.putLong("counter", counter);
        bundle.putLong("userId", userId);
        bundle.putString("timeStamp", timeStamp);
        bundle.putString("duration", duration);
        dialogFragment.setArguments(bundle);

        dialogFragment.show(getFragmentManager(), dialogTag);
    }

    @Override
    public void refreshCounter(int counter) {
        if(counter == valueOfRotation) {
            calculateDuration();
            isActive = false;
            presenter.finishTraining();
            tvCounter.setText("0");
            btnTrainingControl.setText(R.string.start_training);
        }
        tvCounter.setText(Long.toString(counter));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_traning, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_stat:
                openStatistics();
                return true;
            case R.id.item_change_user:
                changeUser();
                return true;
            case R.id.item_exit:
                exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeUser() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void exit() {
        finish();
    }

    private void openStatistics() {
        Intent intent = new Intent(this, StatisticActivity.class);
        intent.putExtra(USER_ID, userId);
        startActivity(intent);
    }
}