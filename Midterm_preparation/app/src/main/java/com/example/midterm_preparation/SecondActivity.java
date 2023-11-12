package com.example.midterm_preparation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import model.Client;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvClient;
    Button btnReturn;
    ArrayList<Client> clientList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initialize();
        clientList= (ArrayList<Client>) getIntent().getExtras().getSerializable("clients");
        StringBuilder list= new StringBuilder("");
        for (Client oneClient :clientList){
            list.append(oneClient+"\n");
        }
        tvClient.setText(list);
    }

    private void initialize() {
        tvClient=findViewById(R.id.tvClient);
        btnReturn=findViewById(R.id.btnFinish);
        btnReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();

    }
}