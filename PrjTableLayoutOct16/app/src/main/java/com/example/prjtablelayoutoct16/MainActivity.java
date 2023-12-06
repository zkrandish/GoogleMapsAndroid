package com.example.prjtablelayoutoct16;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import model.Schedule;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView[] tvList;
    int widgets[]={R.id.tvMondayMorning,R.id.tvMondayAfternoonEvening,R.id.tvTuesdayMorningAfternoon, R.id.tvTuesdayEvening};
    Schedule[] schList;
    TextView clickedTv;

    ActivityResultLauncher<Intent> actresL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initialize();
    }

    private void initialize() {
        tvList= new TextView[widgets.length];
        schList= new Schedule[widgets.length];
        schList[0]=new Schedule(100,"Android", Color.BLUE);
        schList[1]= new Schedule(200, "Sport", Color.BLACK);
        schList[2]= new Schedule(300, "Project");
        schList[3]= new Schedule(400,"Shopping", Color.RED);

        for(int i = 0; i<widgets.length; i++){
            tvList[i]=findViewById(widgets[i]);
            tvList[i].setText(schList[i].toString());
            tvList[i].setTextColor(schList[i].getTxtColor());
            tvList[i].setOnClickListener(this);
        }
        registActResL();
    }

    //Registarion of activity result launcher
    private void registActResL() {
        actresL= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode()==RESULT_OK){
                            String newData= o.getData().getStringExtra("new_schedule");
                            clickedTv.setText(newData);
                        }
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {

       clickedTv= (TextView)v;
        Intent intent= new Intent(this,SecondActivity.class);
        intent.putExtra("schedule",clickedTv.getText().toString());
        actresL.launch(intent);

    }
}