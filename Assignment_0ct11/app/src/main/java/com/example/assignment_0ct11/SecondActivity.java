package com.example.assignment_0ct11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import model.Plan;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvClientOrder;
    Button btnReturn;
    ArrayList<Plan> planList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initialize();
        planList = (ArrayList<Plan>) getIntent().getExtras().getSerializable("plans");
        StringBuilder list = new StringBuilder("");
        float totalPlans = 0;

        for (Plan onePlan : planList) {
            list.append(onePlan + "\n");
            float amount = Plan.getTotal(onePlan.getProvider(), onePlan.getNbMonths());
            totalPlans += amount;
        }
        tvClientOrder.setText(list);
        //tvTotalOrders.append(""+totalPlans);
    }
    public void initialize(){
        tvClientOrder=findViewById(R.id.tvClient);
        //tvTotalOrders=findViewById(R.id.tvTotal);
        btnReturn=findViewById(R.id.btnFinish);
        btnReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();

    }
}