package com.example.assignment_0ct11;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Plan;


public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    EditText edClNum,edNbMonths;
    RadioGroup rgProvider;
    TextView tvSubtotal,tvTps,tvTvq,tvTotal;
    RadioButton rbBell,rbVideotron,rbAcanac;
    Button btnSave, btnShow, btnQuit;
    Plan plan;
    String provider ="";
    float subtotal,tps, tvq,total;
   ArrayList<Plan> planList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
    private void initialize(){
        edClNum=findViewById(R.id.edClNum);
        edNbMonths=findViewById(R.id.edNbMonths);
        edNbMonths.addTextChangedListener(this);
        rgProvider=findViewById(R.id.rgProvider);
        tvSubtotal=findViewById(R.id.tvSubtotal);
        tvTps=findViewById(R.id.tvTps);
        tvTvq=findViewById(R.id.tvTvq);
        tvTotal=findViewById(R.id.tvTotal);
        rbBell=findViewById(R.id.rbBell);
        rbVideotron=findViewById(R.id.rbVideotron);
        rbAcanac=findViewById(R.id.rbAcanac);
        btnSave = findViewById(R.id.btnSave);
        btnQuit = findViewById(R.id.btnQuit);
        btnShow = findViewById(R.id.btnShow);

        btnSave.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        rbVideotron.setOnClickListener(this);
        rbBell.setOnClickListener(this);
        rbAcanac.setOnClickListener(this);
        planList= new ArrayList<Plan>();
    }
    @Override
    public void onClick(View view) {
        int id= view.getId();
        if(id==R.id.btnSave){
            savePlan();
        }
        if(id==R.id.btnQuit){
            System.exit(0);
        }
        if(id==R.id.btnShow){
            showAllPlans();
        }
        if(id==R.id.rbVideotron){
            provider=rbVideotron.getText().toString();
            showAmount();
        }
        if(id==R.id.rbBell){
            provider=rbBell.getText().toString();
            showAmount();
        }
        if(id==R.id.rbAcanac){
            provider=rbAcanac.getText().toString();
            showAmount();
        }

    }
    private void savePlan(){
        try{
            int clientNum=Integer.valueOf(edClNum.getText().toString());
            int nbMonths=Integer.valueOf(edNbMonths.getText().toString());
            plan= new Plan(clientNum,provider,nbMonths);
            planList.add(plan);
            Toast.makeText(this, "the plan is of the client "+
                    clientNum+ "has been saved successfully",Toast.LENGTH_LONG).show();
            clearWidgets();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void showAmount(){
        try{
            int nbMonths= Integer.valueOf(edNbMonths.getText().toString());
            subtotal= Plan.getSubTotal(provider,nbMonths);
            tvSubtotal.setText(String.valueOf(subtotal));
             tps= Plan.getTps(provider,nbMonths);
             tvTps.setText(String.valueOf(tps));
             tvq=Plan.getTvq(provider,nbMonths);
             tvTvq.setText(String.valueOf(tvq));
             total= Plan.getTotal(provider,nbMonths);
             tvTotal.setText(String.valueOf(total));

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private void showAllPlans() {

        Intent intent= new Intent(this,SecondActivity.class);
        intent.putExtra("plans",planList);
        startActivity(intent);
    }

    private void clearWidgets() {
        edClNum.setText(null);
        edNbMonths.setText(null);
        rgProvider.clearCheck();
        tvTotal.setText(null);
        tvTvq.setText(null);
        tvTps.setText(null);
        tvSubtotal.setText(null);
        edClNum.requestFocus();
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        showAmount();

    }


}