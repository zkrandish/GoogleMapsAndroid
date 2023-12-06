package com.example.midterm_preparation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import model.Client;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText edId,edNbKm;
    RadioGroup rgTransType;
    RadioButton rbBus, rbMetro,rbPrivate,rbTaxi;
    Button btnSave, btnClear, btnQuit,btnShowAll;
    Client client;
    ArrayList<Client> clientList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        edId= findViewById(R.id.edId);
        edNbKm = findViewById(R.id.edNbKm);
        rgTransType= findViewById(R.id.rgTransType);
        rbBus= findViewById(R.id.rbBus);
        rbMetro= findViewById(R.id.rbMetro);
        rbPrivate= findViewById(R.id.rbPrivate);
        rbTaxi= findViewById(R.id.rbTaxi);
        btnSave= findViewById(R.id.btnSave);
        btnClear=findViewById(R.id.btnClear);
        btnQuit= findViewById(R.id.btnQuit);
        btnShowAll= findViewById(R.id.btnShowAll);

        btnSave.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnShowAll.setOnClickListener(this);
        rbBus.setOnClickListener(this);
        rbMetro.setOnClickListener(this);
        rbPrivate.setOnClickListener(this);
        rbTaxi.setOnClickListener(this);

        clientList= new ArrayList<Client>();

    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if(id==R.id.btnSave){
            saveClient(view,"has been saved successfully");
        }
        if(id==R.id.btnClear){
            clearWidgets();
        }
        if(id==R.id.btnQuit){
            System.exit(0);
        }
        if(id==R.id.btnShowAll){
            ShowAllClients();
        }


    }
    private void clearWidgets(){
        edId.setText(null);
        edNbKm.setText(null);
       rgTransType.clearCheck();
        edId.requestFocus();
    }
    private void saveClient(View view, String message){
        try{
            int clNumber= Integer.valueOf(edId.getText().toString());
            int nbKm= Integer.valueOf(edNbKm.getText().toString());
            int type=0;
            int typeTrans= rgTransType.getCheckedRadioButtonId();
            if(typeTrans==R.id.rbBus){
                type= 1;
            } else if (typeTrans==R.id.rbMetro) {
                type=2;
            } else if (typeTrans==R.id.rbPrivate) {
                type=3;
            } else if (typeTrans==R.id.rbTaxi) {
                type=4;
            }
            client= new Client(clNumber,type,nbKm);
            clientList.add(client);
            Snackbar.make(view, "The client with the number: " + clNumber +
                    message, Snackbar.LENGTH_LONG).show();
            clearWidgets();


        }catch (Exception e){

            Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
        }
    }
    private void ShowAllClients(){
        Intent intent= new Intent(this, SecondActivity.class);
        intent.putExtra("clients",clientList);
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