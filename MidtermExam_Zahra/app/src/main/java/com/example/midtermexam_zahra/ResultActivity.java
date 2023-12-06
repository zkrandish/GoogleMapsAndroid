package com.example.midtermexam_zahra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import model.User;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvUser;
    Button btnReturn;
    ArrayList<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initialize();
        userList= (ArrayList<User>) getIntent().getExtras().getSerializable("users");
        StringBuilder list= new StringBuilder("");
        for (User oneUser :userList){
            list.append(oneUser+"\n");
        }
        tvUser.setText(list);
    }
    private void initialize() {
        tvUser=findViewById(R.id.tvUser);
        btnReturn=findViewById(R.id.btnFinish);
        btnReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}