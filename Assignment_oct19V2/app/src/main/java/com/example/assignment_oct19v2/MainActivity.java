package com.example.assignment_oct19v2;

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
import model.Menue;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView[] tvList;
    int widgets[] = {R.id.tvMonTuBr,
            R.id.tvWedBr,
            R.id.tvThFrBr,
            R.id.tvMonLun,
            R.id.tvTuLun,
            R.id.tvWnLun,
            R.id.tvThLun,
            R.id.tvFrLun,
            R.id.tvMonSn,
            R.id.tvTuWedSn,
            R.id.tvThFrSn};
    Menue[] menuList;
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
        tvList = new TextView[widgets.length];
        menuList = new Menue[widgets.length];
        menuList[0] = new Menue(100, "Cheerios");
        menuList[1] = new Menue(101, "Pancakes");
        menuList[2] = new Menue(102, "Scrambled Eggs");
        menuList[3] = new Menue(103, "Mashed");
        menuList[4] = new Menue(104, "TunaFish");
        menuList[5] = new Menue(105, "Rice&Chicken");
        menuList[6] = new Menue(106, "Macaroni");
        menuList[7] = new Menue(107, "Whole Wheat");
        menuList[8] = new Menue(108, "Crackers", Color.BLACK);
        menuList[9] = new Menue(109, "Yogurt");
        menuList[10] = new Menue(110, "Home-made");

        for (int i = 0; i <widgets.length; i++) {
            tvList[i] = findViewById(widgets[i]);
            tvList[i].setText(menuList[i].toString());
            tvList[i].setTextColor(menuList[i].getTxtColor());
            tvList[i].setOnClickListener(this);
        }
        registActResL();
    }

    //Registarion of activity result launcher
    private void registActResL() {
        actresL = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == RESULT_OK) {
                            String newData = o.getData().getStringExtra("new_meal");
                            int textColor= o.getData().getIntExtra("text_color",Color.BLACK);
                            int textBckColor= o.getData().getIntExtra("text_backColor",Color.TRANSPARENT);
                            clickedTv.setText(newData);
                            clickedTv.setTextColor(textColor);
                            clickedTv.setBackgroundColor(textBckColor);
                        }

                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        clickedTv= (TextView)view;
        Intent intent= new Intent(this,SecondActivity.class);
        intent.putExtra("menue",clickedTv.getText().toString());
        actresL.launch(intent);

    }
}