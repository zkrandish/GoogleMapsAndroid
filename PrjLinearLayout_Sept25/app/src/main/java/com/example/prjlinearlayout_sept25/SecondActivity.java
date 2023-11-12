package com.example.prjlinearlayout_sept25;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvName,tvSport;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initialize();
    }

    private void initialize() {
        tvName=findViewById(R.id.tvName);
        tvSport=findViewById(R.id.tvSport);
        btnFinish=findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(this);//the button finish is listening to the click
        //Receiving data
        String name= getIntent().getStringExtra("name");
        int sport= getIntent().getIntExtra("sport",-1);
        String strSport="";
        switch (sport){
            case 0: strSport="soccer";
            break;
            case 1: strSport="handball";
            break;
            case 2: strSport="hockey";
            break;
            default:strSport="No sport";
        }
        tvName.setText(name);
        tvSport.setText(strSport);

    }

    @Override
    public void onClick(View view) {
//if youhave to button
        //int id= view,getid())
        //if(id==r.id.btnfinish
        //{
        //finish
    //}
        //esle{
        //if (id=r.id.btnpok{
        //...
        finish();
    }
}