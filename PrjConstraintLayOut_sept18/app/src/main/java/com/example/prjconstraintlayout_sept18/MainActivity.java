package com.example.prjconstraintlayout_sept18;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    //declaration of the objects
    EditText edName,edAge;
    Button btnShow,btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        //manipulate the objects==>automatically manipulate the widgets
    }

    private void initialize() {
        //link between objects and widgets
        edName=findViewById(R.id.edName);
        edAge=findViewById(R.id.edAge);
        btnShow=findViewById(R.id.btnShow);
        btnQuit=findViewById(R.id.btnQuit);
    }

//    public void showInfo(View v){
//        //display the name and the age in a window: Toast
//        String name= edName.getText().toString();
//        int age=Integer.valueOf(edAge.getText().toString());
//        String message= "My Name is: "+ name+ "\n"+"my age is: "+ age;
//        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
//
//    }
//    public void quit(View v){
//        System.exit(0);
//
//    }
    public void processButton(View v){
        int btnId=v.getId();
        if(btnId==R.id.btnShow){
            String name= edName.getText().toString();
            int age=Integer.valueOf(edAge.getText().toString());
            String message= "My Name is: "+ name+ "\n"+"my age is: "+ age;
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        }
        if (btnId == R.id.btnQuit){
            System.exit(0);
        }
    }
}