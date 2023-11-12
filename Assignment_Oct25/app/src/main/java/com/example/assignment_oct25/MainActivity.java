package com.example.assignment_oct25;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import model.Car;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ValueEventListener, ChildEventListener {

    EditText edId;
    RadioGroup rgbarnd;
    RadioButton rbToyota, rbMazda,rbHyundai;
    CheckBox cbStatus;
    DatabaseReference carDatabase;
    Button btnAdd, btnUpdate, btnDelete,btnFind, btnFindAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        edId=findViewById(R.id.edId);
        rgbarnd= findViewById(R.id.rgbarnd);
        rbToyota= findViewById(R.id.rbToyota);
        rbMazda= findViewById(R.id.rbMazda);
        rbHyundai= findViewById(R.id.rbHyundai);
        cbStatus= findViewById(R.id.cbStatus);
        btnAdd=findViewById(R.id.btnAdd);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        btnFind=findViewById(R.id.btnFind);
        btnFindAll=findViewById(R.id.btnFindAll);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnFind.setOnClickListener(this);
        btnFindAll.setOnClickListener(this);
        carDatabase= FirebaseDatabase.getInstance().getReference("Car");
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if(id==R.id.btnFind){
            findOneCar();
        }
        if(id==R.id.btnAdd){
            addUpdateCar(view,"has been added");
        }
        if(id==R.id.btnDelete){
            deleteCar(view);
        }
        if(id==R.id.btnUpdate){
            addUpdateCar(view,"has been updated");
        }
        if(id==R.id.btnFindAll){
            findAll();
        }

    }

    private void findOneCar() {
        String id = edId.getText().toString();
        carDatabase.child(id).addValueEventListener(this);
        //will fire ondatachage event
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            String brand= snapshot.child("brand").getValue().toString();
            String status= snapshot.child("status").getValue().toString();
            String id= snapshot.child("id").getValue().toString();


            Intent intent= new Intent(this,SecondActivity.class);
            intent.putExtra("brands",brand);
            intent.putExtra("status",status);
            intent.putExtra("id",id);
            startActivity(intent);


        }else{
            Toast.makeText(this,"No document",Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
    private void addUpdateCar(View view, String message){
        try{
            String id = edId.getText().toString();
            String brand= "";
            String status= "";
            int brandSelectedButtonId= rgbarnd.getCheckedRadioButtonId();

            if(brandSelectedButtonId==R.id.rbToyota){
                brand="Toyata";
            }else if(brandSelectedButtonId == R.id.rbMazda){
                brand="Mazda";
            } else if (brandSelectedButtonId==R.id.rbHyundai) {
                brand="Hyundai";
            }


            boolean isChecked= cbStatus.isChecked();
            status = isChecked ? "new" : "old";
            Car car = new Car(Integer.valueOf(id),brand,status);
            carDatabase.child(id).setValue(car);
            Snackbar.make(view, "The car with the id: " + id +
                    message, Snackbar.LENGTH_LONG).show();
            Intent intent= new Intent(this,SecondActivity.class);
            intent.putExtra("brands",brand);
            intent.putExtra("status",status);
            intent.putExtra("id",id);
            startActivity(intent);
            //clearWidgets();

        }
        catch (Exception e){
            Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
        }
    }
    private void deleteCar(View view){
        String id= edId.getText().toString();
        carDatabase.child(id).removeValue();
        Snackbar.make(view,"the car with the id "+id+
                "\nhas been deleted",Snackbar.LENGTH_LONG).show();

    }

    private void findAll() {
        carDatabase.addChildEventListener(this);
        //will fire onchanged added
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Car car = snapshot.getValue(Car.class);
        //firstway
        Toast.makeText(this,car.toString(),Toast.LENGTH_LONG).show();
        Log.d("Firebase",car.toString());
        //secondway
        Map<String,String> map= (Map)snapshot.getValue();
        String name= map.get("brand");
        Log.d("Firebase_map","brand: "+name);

        Map<Long,Long> map1= (Map)snapshot.getValue();
        long id= Long.valueOf(map1.get("id"));
        Log.d("Firebase_map1","id:"+id);

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        Car car = snapshot.getValue(Car.class);
        Log.d("Firebase",car.toString());
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }
}