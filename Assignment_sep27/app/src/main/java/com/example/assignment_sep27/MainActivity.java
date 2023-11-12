package com.example.assignment_sep27;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edName;
    Button btnTemp, btnMetrix, btnQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
    private void initialize(){
        edName=findViewById(R.id.edName);
        btnTemp= findViewById(R.id.btnTemp);
        btnMetrix= findViewById(R.id.btnMetrix);
        btnMetrix.setOnClickListener(this);
        btnTemp.setOnClickListener(this);

    }
    private void navigateToActivitySecond(){
        Intent intent= new Intent(this, SecondActivity.class);
        String name= edName.getText().toString();
        intent.putExtra("name",name);
        startActivity(intent);

    }
    private void navigateToActivityThird(){
        Intent intent= new Intent(this,ThirdActivity.class);
        String name= edName.getText().toString();
        intent.putExtra("name",name);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == btnTemp) {
            navigateToActivitySecond();
        } else if (view==btnMetrix) {
            navigateToActivityThird();

        }


    }
}