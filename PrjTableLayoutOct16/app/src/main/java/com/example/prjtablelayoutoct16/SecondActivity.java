package com.example.prjtablelayoutoct16;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity  implements View.OnClickListener {

    EditText edDescription;
    Button btnReturn;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initialize();
    }

    private void initialize() {
        edDescription = findViewById(R.id.edNewSchedule);
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);
        data = getIntent().getStringExtra("schedule");
        edDescription.setText(data);
    }

    @Override
    public void onClick(View v) {
        String newData= edDescription.getText().toString();
        Intent intent = new Intent();
        if(data.equalsIgnoreCase(newData)){
            setResult(RESULT_CANCELED,intent);

        }
        else{
            intent.putExtra("new_schedule",newData);
            setResult(RESULT_OK,intent);

        }
        finish();
    }
}