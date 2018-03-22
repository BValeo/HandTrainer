package com.bvaleo.handtrainer.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bvaleo.handtrainer.R;
import com.bvaleo.handtrainer.database.DatabaseHelper;
import com.bvaleo.handtrainer.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity{

    private static final String USER_ID = "userId";

    @BindView(R.id.etLoginR)
    EditText etLogin;
    @BindView(R.id.etPassR)
    EditText etPass;
    @BindView(R.id.login)
    Button btn_login;
    @BindView(R.id.signin)
    Button btn_signup;
    @BindView(R.id.pb_login)
    ProgressBar pbLogin;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        DatabaseHelper db = new DatabaseHelper(this);
        pbLogin.setVisibility(View.INVISIBLE);

        btn_login.setOnClickListener(view -> {
            String curLogin = etLogin.getText().toString();
            String curPass = etPass.getText().toString();

            if(!curLogin.equals("") && !curPass.equals("")) {
                btn_login.setEnabled(false);
                pbLogin.setVisibility(View.VISIBLE);
                User user = db.getUser(curLogin, curPass);
                if (user != null) {
                    startActivity((new Intent(this, TrainingActivity.class)).putExtra(USER_ID, user.getId()).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                } else {
                    onLoginScreenChanged();
                    Toast.makeText(this, "Пользователь не найден. Введите данные вновь или зарегистрируйтесь.", Toast.LENGTH_SHORT).show();
                }
            } else Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();


        });

        btn_signup.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        onLoginScreenChanged();
    }


    private void onLoginScreenChanged(){
        etLogin.setText("");
        etPass.setText("");
        btn_login.setEnabled(true);
        pbLogin.setVisibility(View.INVISIBLE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
