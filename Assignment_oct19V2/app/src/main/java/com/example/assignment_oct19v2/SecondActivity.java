package com.example.assignment_oct19v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edDescription;
    Button btnReturn;
    String data;
    int newColor;
    RadioGroup rgTextColor,rgBackColor;
    RadioButton rbRed,rbGreen,rbMagenta,rbYellow,rbWhite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initialize();
    }
    private void initialize() {
        edDescription = findViewById(R.id.edDesc);
        btnReturn = findViewById(R.id.btnReturn);
        rgTextColor= findViewById(R.id.rgTextColor);
        rgBackColor=findViewById(R.id.rgBackColor);
        rbRed=findViewById(R.id.rbRed);
        rbGreen=findViewById(R.id.rbGreen);
        rbMagenta= findViewById(R.id.rbMagenta);
        rbYellow=findViewById(R.id.rbYellow);
        rbWhite=findViewById(R.id.rbWhite);
        btnReturn.setOnClickListener(this);
        data = getIntent().getStringExtra("menue");
        edDescription.setText(data);

        rbRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edDescription.setTextColor(Color.RED);
            }
        });
        rbGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edDescription.setTextColor(Color.GREEN);
            }
        });

        rbMagenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edDescription.setTextColor(Color.MAGENTA);
            }
        });
        rbWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edDescription.setBackgroundColor(Color.WHITE);
            }
        });
        rbYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edDescription.setBackgroundColor(Color.YELLOW);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        String newData= edDescription.getText().toString();
        int selectedColor = edDescription.getCurrentTextColor();
        int selectedBackColor = Color.TRANSPARENT;

        // Check the selected text color radio button
        int textColorRadioButtonId = rgTextColor.getCheckedRadioButtonId();
        if (textColorRadioButtonId == R.id.rbRed) {
            selectedColor = Color.RED;
        } else if (textColorRadioButtonId == R.id.rbGreen) {
            selectedColor = Color.GREEN;
        } else if (textColorRadioButtonId == R.id.rbMagenta) {
            selectedColor = Color.MAGENTA;
        }

        // Check the selected background color radio button
        int backgroundColorRadioButtonId = rgBackColor.getCheckedRadioButtonId();
        if (backgroundColorRadioButtonId == R.id.rbWhite) {
            selectedBackColor = Color.WHITE;
        } else if (backgroundColorRadioButtonId == R.id.rbYellow) {
            selectedBackColor = Color.YELLOW;
        }
        Intent intent = new Intent();
        if(data.equalsIgnoreCase(newData)){
            setResult(RESULT_CANCELED,intent);
        }
        else {

//            if (id==R.id.rbRed) {
//                selectedColor = Color.RED;
//
//            } else if (id==R.id.rbGreen) {
//                selectedColor = Color.GREEN;
//            } else if (id==R.id.rbMagenta) {
//                selectedColor = Color.MAGENTA;
//        }
           edDescription.setTextColor(selectedColor);
            edDescription.setBackgroundColor(selectedBackColor);
            intent.putExtra("new_meal",newData);
            intent.putExtra("text_color",selectedColor);
            intent.putExtra("text_backColor",selectedBackColor);
            setResult(RESULT_OK,intent);

        }
        finish();
    }



}