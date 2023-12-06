package com.example.prjfirebaseoct23;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Person;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ValueEventListener, ChildEventListener {

    EditText edId,edName,edAge;

    Button btnAdd, btnUpdate, btnDelete,btnFind, btnFindAll;
    DatabaseReference personDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        edId=findViewById(R.id.edId);
        edName=findViewById(R.id.edName);
        edAge=findViewById(R.id.edAge);
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
        personDatabase= FirebaseDatabase.getInstance().getReference("Person");
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.btnAdd){
            addUpdatePerson(view, "has been added successfully");
        }
        if(id==R.id.btnUpdate){
            addUpdatePerson(view,"has been updated successfully");

        }
        if(id==R.id.btnDelete){
            deletePerson(view);
        }
        if(id==R.id.btnFind){
            findOnePerson();
        }
        if(id==R.id.btnFindAll){
            findAll();
        }

    }




    private void findOnePerson() {
        String id = edId.getText().toString();
        personDatabase.child(id).addValueEventListener(this);
        //will fire ondatachage event
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            String name= snapshot.child("name").getValue().toString();
            String age= snapshot.child("age").getValue().toString();
            edName.setText(name);
            edAge.setText(age);
        }
        else{
            Toast.makeText(this,"No document",Toast.LENGTH_LONG).show();
        }

    }
//    private void updatePerson(View view) {
//        try {
//            String id = edId.getText().toString();
//            String name = edName.getText().toString();
//            String age = edAge.getText().toString();
//            Person person = new Person(Integer.valueOf(id), name, Integer.valueOf(age));
//            personDatabase.child(id).setValue(person);
//            Snackbar.make(view, "The person with the id: " + id +
//                    "\nhas been updated successfully", Snackbar.LENGTH_LONG).show();
//            clearWidgets();
//        }
//        catch (Exception e){
//            Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
//        }
//    }


    private void addUpdatePerson(View view, String message) {
        try {
            String id = edId.getText().toString();
            String name = edName.getText().toString();
            String age = edAge.getText().toString();
            Person person = new Person(Integer.valueOf(id), name, Integer.valueOf(age));
            personDatabase.child(id).setValue(person);
            Snackbar.make(view, "The person with the id: " + id +
                    message, Snackbar.LENGTH_LONG).show();
            clearWidgets();
        }
        catch (Exception e){
            Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_LONG).show();
        }
    }
    private void deletePerson(View view) {
        String id = edId.getText().toString();
        personDatabase.child(id).removeValue();
        Snackbar.make(view,"the person with the id "+id+
                "\nhas been deleted",Snackbar.LENGTH_LONG).show();
    }


    private void clearWidgets(){
        edId.setText(null);
        edName.setText(null);
        edAge.setText(null);
        edId.requestFocus();
    }

    private void findAll() {
        personDatabase.addChildEventListener(this);
        //will fire onchanged added
    }
    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Person person= snapshot.getValue(Person.class);
        //Toast.makeText(this,person.toString(),Toast.LENGTH_LONG).show();
       // Log.d("Firebase",person.toString());

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        Person person= snapshot.getValue(Person.class);

        Log.d("Firebase",person.toString());
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {


    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}