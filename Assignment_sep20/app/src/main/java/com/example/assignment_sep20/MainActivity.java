package com.example.assignment_sep20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edNum1,edNum2, edResult;
    RadioButton rbAdd,rbMultiply,rbSubtract;
    Button btnCalculate,btnClear,btnQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
    private void initialize(){
        edNum1=findViewById(R.id.edNum1);
        edNum2= findViewById(R.id.edNum2);
        edResult= findViewById(R.id.edResult);
        rbAdd=findViewById(R.id.rbAdd);
        rbMultiply=findViewById(R.id.rbMultiply);
        rbSubtract=findViewById(R.id.rbSubtract);
        btnCalculate=findViewById(R.id.btnCalculate);
        btnClear=findViewById(R.id.btnClear);
        btnQuit=findViewById(R.id.btnQuit);

    }

    public void processButton(View view){
        int btnId= view.getId();
        int radioId=view.getId();
        int dbresult;

        int dNum1= Integer.valueOf(edNum1.getText().toString());
        int dNum2= Integer.valueOf(edNum2.getText().toString());

        if( btnId==R.id.btnCalculate){
            if(rbAdd.isChecked()){
                dbresult=dNum1+dNum2;
            } else if (rbMultiply.isChecked()){
                dbresult=dNum1*dNum2;
            }else if (rbSubtract.isChecked()){
                dbresult=dNum1-dNum2;
            }
            else{
                //no radio button selected
                return;
            }
            edResult.setText(String.valueOf(dbresult));
        }
        if(btnId==R.id.btnClear){
            edNum1.setText("");
            edNum2.setText("");
            edResult.setText("");
            rbAdd.setChecked(false);
            rbSubtract.setChecked(false);
            rbMultiply.setChecked(false);


        }

        if(btnId==R.id.btnQuit){
            System.exit(0);
        }


    }
}