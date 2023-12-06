package com.example.midtermexam_zahra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import model.User;

public class StartActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText edId;
    CheckBox chBus,chMetro,chcar,chBike;
    Button btnSave, btnClear, btnQuit,btnShowAll;
    User user;
    ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initialize();

    }

    private void initialize() {
        edId= findViewById(R.id.edId);
        chBus=findViewById(R.id.chBus);
        chMetro=findViewById(R.id.chMetro);
        chcar= findViewById(R.id.chcar);
        chBike=findViewById(R.id.chBike);
        btnSave= findViewById(R.id.btnSave);
        btnClear=findViewById(R.id.btnClear);
        btnQuit= findViewById(R.id.btnQuit);
        btnShowAll= findViewById(R.id.btnShowAll);

        btnSave.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnShowAll.setOnClickListener(this);
        chBus.setOnClickListener(this);
        chMetro.setOnClickListener(this);
        chcar.setOnClickListener(this);
        chBike.setOnClickListener(this);
        userList= new ArrayList<User>();
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if(id==R.id.btnSave){
            SaveUser(view,"has been saved successully");
        }
        if(id==R.id.btnClear){
            clearWidgets();
        }
        if(id==R.id.btnShowAll){
            ShowAllUsers();
        }
        if(id==R.id.btnQuit){
            System.exit(0);
        }

    }
    private void SaveUser(View view, String message){
        try{
            String userName= edId.getText().toString();
            String takeBus = "No";
            String takeMetro = "No";
            String takeCar = "No";
            String takeBike = "No";
            if (chBus.isChecked()) {
                takeBus = "Yes";
            }
            if (chMetro.isChecked()) {
                takeMetro = "Yes";
            }
            if (chcar.isChecked()) {
                takeCar = "Yes";
            }
            if (chBike.isChecked()) {
                takeBike = "Yes";
            }
            user = new User(userName,takeBus,takeMetro,takeCar,takeBike);
            userList.add(user);
            Snackbar.make(view, "The client with the number: " + userName +
                    message, Snackbar.LENGTH_LONG).show();
            //clearWidgets();

        }
        catch (Exception e){
            Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();

        }
    }
    private void clearWidgets(){
        edId.setText(null);
        chBike.setChecked(false);
        chcar.setChecked(false);
        chBus.setChecked(false);
        chMetro.setChecked(false);
    }
    private void ShowAllUsers(){
        Intent intent= new Intent(this, ResultActivity.class);
        intent.putExtra("users",userList);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}