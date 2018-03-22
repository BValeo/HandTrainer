package com.bvaleo.handtrainer.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bvaleo.handtrainer.R;
import com.bvaleo.handtrainer.database.DatabaseHelper;

/**
 * Created by Valery on 19.03.2018.
 */

public class OnFinishTrainingDialog extends DialogFragment {

    private long count;
    private long userId;
    private String timeStamp;
    private String duration;


    private EditText etComment;
    private TextView tvDesc;
    private Button btnOk;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);

        Bundle b = getArguments();

        if(b != null){
            count = b.getLong("counter");
            userId = b.getLong("userId", 0);
            timeStamp = b.getString("timeStamp");
            duration = b.getString("duration");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_comment, container, false);

        etComment = v.findViewById(R.id.et_comment);
        tvDesc = v.findViewById(R.id.tv_desc);
        btnOk = v.findViewById(R.id.btn_ok);

        String message = "Тренировка завершена c результатом: " + count  + ". Вы можете оставить комментарии о тренировке в поле ниже.";
        tvDesc.setText(message);

        btnOk.setOnClickListener(view -> {
            DatabaseHelper db = new DatabaseHelper(getActivity());
            db.createStatistic(userId, count, etComment.getText().toString(), timeStamp, duration);
            db.close();
            dismiss();
        });

        return v;
    }
}
