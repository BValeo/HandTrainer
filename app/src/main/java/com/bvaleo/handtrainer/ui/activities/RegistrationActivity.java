package com.bvaleo.handtrainer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bvaleo.handtrainer.R;
import com.bvaleo.handtrainer.database.DatabaseHelper;
import com.bvaleo.handtrainer.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Valery on 20.03.2018.
 */

public class RegistrationActivity extends AppCompatActivity {

    private static final String USER_ID = "userId";

    @BindView(R.id.etLoginR)
    EditText etLoginR;
    @BindView(R.id.etPassR)
    EditText etPassR;
    @BindView(R.id.reg)
    Button reg;

    private String login;
    private String pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        DatabaseHelper db = new DatabaseHelper(this);


        reg.setOnClickListener(view -> {
            login = etLoginR.getText().toString();
            pass = etPassR.getText().toString();

            if(!login.equals("") && !pass.equals("")){
                User user = db.getUser(login, pass);
                if(user == null) {
                    long userId = db.createUser(login, pass);
                    startActivity((new Intent(this, TrainingActivity.class)).putExtra(USER_ID, userId).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
                else
                    Toast.makeText(this, "Пользователь с таким логином уже присутствует в системе", Toast.LENGTH_SHORT).show();

            } else Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        });
    }
}
