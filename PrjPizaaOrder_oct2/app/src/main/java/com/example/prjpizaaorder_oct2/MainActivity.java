package com.example.prjpizaaorder_oct2;

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
import java.util.concurrent.ExecutionException;

import model.Order;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText edClientNum,edNbSlices;
    RadioGroup rgPizza;
    TextView tvAmount;
    Button btnSave, btnShowAll, btnQuit;
    RadioButton rbCheese, rbVegi, rbMexican;
    Order order;
    String pizzaType= "";
    float amount;
    float price;

    ArrayList<Order> orderList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        edClientNum=findViewById(R.id.edClientNum);
        edNbSlices=findViewById(R.id.edNbSlices);
        edNbSlices.addTextChangedListener(this);
        rgPizza= findViewById(R.id.rgPizaa);
        tvAmount= findViewById(R.id.tvAmount);
        btnSave= findViewById(R.id.btnSave);
        btnQuit= findViewById(R.id.btnQuit);
        btnShowAll=findViewById(R.id.btnShowALL);
        rbCheese= findViewById(R.id.rbCheese);
        rbVegi=findViewById(R.id.rbvegi);
        rbMexican=findViewById(R.id.rbMexican);

        btnSave.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        btnShowAll.setOnClickListener(this);
        rbCheese.setOnClickListener(this);
        rbVegi.setOnClickListener(this);
        rbMexican.setOnClickListener(this);

        orderList= new ArrayList<Order>();
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if(id==R.id.btnSave){
            saveOrder();
        }
        if(id==R.id.btnQuit){
            System.exit(0);
        }
        if (id==R.id.btnShowALL){
            showAllOrders();
        }
        if (id==R.id.rbCheese){
            pizzaType= rbCheese.getText().toString();
            showAmount();
        }
        if (id==R.id.rbvegi){
            pizzaType= rbVegi.getText().toString();
            showAmount();
        }
        if (id==R.id.rbMexican){
            pizzaType= rbMexican.getText().toString();
            showAmount();
        }

    }
    private void saveOrder() {
        //save one order (client number, pizza, nbslices) in the arraylist order list
        try {
            int clientNumber = Integer.valueOf(edClientNum.getText().toString());
            int nbSlices = Integer.valueOf(edNbSlices.getText().toString());
            order = new Order(clientNumber, pizzaType, nbSlices);
            orderList.add(order);
            Toast.makeText(this, "the order is of the client" + clientNumber +
                    "has been saved successfully", Toast.LENGTH_LONG).show();
            clearWidgets();
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void showAmount() {
        try {

            int nbSlices = Integer.valueOf(edNbSlices.getText().toString());
            float amount = Order.getAmount(pizzaType,nbSlices);
            tvAmount.setText(String.valueOf(amount));
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }



    private void showAllOrders() {

        Intent intent= new Intent(this,SecondActivity.class);
        intent.putExtra("orders",orderList);
        startActivity(intent);
    }



    private void clearWidgets() {
        edClientNum.setText(null);
        edNbSlices.setText(null);
        rgPizza.clearCheck();
        tvAmount.setText(null);
        edClientNum.requestFocus();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        showAmount();

    }
}