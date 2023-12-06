package com.example.assignmentadvfb;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import model.Project;
import model.Teams;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ValueEventListener, OnSuccessListener, OnFailureListener, OnCompleteListener {

    EditText edId,edTeamName,edProjectTitle,edDesc,edtotal,edName1,edName2,edName3,edName4;
    Button btnAdd,btnFind,btnBrowse,btnUpload;
    ImageView imPhoto;
    DatabaseReference teamsDatabase;
    FirebaseStorage storage;
    StorageReference storageReference,sRef;
    String urlPhoto;
    Uri filePath;

    //for browse
    ActivityResultLauncher activityResultLauncher;
    ProgressDialog progressDialog;
    String fileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
    private void initialize() {
        edId= findViewById(R.id.edId);
        edTeamName= findViewById(R.id.edTeamName);
        edProjectTitle=findViewById(R.id.edProjectTitle);
        edDesc= findViewById(R.id.edDesc);
        edtotal=findViewById(R.id.edtotal);
        edName1=findViewById(R.id.edName1);
        edName2=findViewById(R.id.edName2);
        edName3=findViewById(R.id.edName3);
        edName4=findViewById(R.id.edName4);
        imPhoto = findViewById(R.id.imPhoto);
        btnAdd = findViewById(R.id.btnAdd);
        btnBrowse = findViewById(R.id.btnBrowse);
        btnUpload = findViewById(R.id.btnUpload);
        btnFind = findViewById(R.id.btnFind);
        btnAdd.setOnClickListener(this);
        btnBrowse.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnFind.setOnClickListener(this);
        teamsDatabase= FirebaseDatabase.getInstance().getReference("Teams");
        //initialize the reference to firebase storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        getPhoto(result);

                    }
                });
    }

    private void getPhoto(ActivityResult result) {
        if(result.getResultCode()==RESULT_OK && result.getData()!=null) {
            filePath = result.getData().getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imPhoto.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.d("ADV_FIREBASE", e.getMessage());
            }
        }
        else{
            Toast.makeText(this,"No result",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id ==R.id.btnAdd){
            addTeams(view);
        } else if (id== R.id.btnFind) {
            findTeams();
        } else if (id==R.id.btnBrowse) {
            selectPhoto();
        } else if (id==R.id.btnUpload) {
            uploadPhoto();
        }

    }


    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent,"select image"));
    }

//    private void addPerson(View view) {
//        ArrayList<String> hobbies= new ArrayList<String>();
//        hobbies.add("Soccer");
//        hobbies.add("Handball");
//        hobbies.add("Hockey");
//        hobbies.add("Music");
//        Car car = new Car("M300","Mazda","Mazda 6");
//        Person person = new Person(300,"Richard",fileUrl,car,hobbies);
//        personDatabase.child("300").setValue(person);
//        Snackbar.make(view,"the person with id :300 has been added to the database"
//                ,Snackbar.LENGTH_LONG).show();
//    }
    private void addTeams(View view) {
        ArrayList<String>members= new ArrayList<String>();
        members.add(edName1.getText().toString().trim());
        members.add(edName2.getText().toString().trim());
        members.add(edName3.getText().toString().trim());
        members.add(edName4.getText().toString().trim());
        String projectTitle = edProjectTitle.getText().toString().trim();
        String projectDesc= edDesc.getText().toString().trim();
        int total= Integer.valueOf(edtotal.getText().toString().trim());

        Project project= new Project(projectTitle,projectDesc,total);

        int teamId = Integer.valueOf(edId.getText().toString().trim());
        String teamName= edTeamName.getText().toString();
        Teams teams = new Teams(teamId, teamName, fileUrl, project,members);

        teamsDatabase.child(String.valueOf(teamId)).setValue(teams);
        Snackbar.make(view, "The team with the id " + teamId + " has been added successfully", Snackbar.LENGTH_LONG).show();
    }

    private void findTeams() {
        String id = edId.getText().toString();
        DatabaseReference teamsChild= teamsDatabase.child(id);
        //will fire ondata changed
        teamsChild.addValueEventListener(this);

    }

    //from interface ValueEventListener to find
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String id = edId.getText().toString();
        if(snapshot.exists()){
            String name= snapshot.child("name").getValue().toString();
            edTeamName.setText(name);
            Log.d("ADV_FIREBASE",name);

            Map project= (Map)snapshot.child("project").getValue();
            edProjectTitle.setText(project.get("title").toString());
            edDesc.setText(project.get("description").toString());
            edtotal.setText(project.get("total").toString());
            Log.d("ADV_FIREBASE","the title is "+project.get("title").toString());
            Log.d("ADV_FIREBASE","the Description is "+project.get("description").toString());
            Log.d("ADV_FIREBASE","the project is "+project.toString());

            ArrayList<String>members= (ArrayList)snapshot.child("members").getValue();
            edName1.setText(members.get(0));
            edName2.setText(members.get(1));
            edName3.setText(members.get(2));
            edName4.setText(members.get(3));
            Log.d("ADV_FIREBASE",members.toString());

             urlPhoto= snapshot.child("photo").getValue().toString();
             Log.d("ADV_FIREBASE",urlPhoto);
            Picasso.with(this).load(urlPhoto).placeholder(R.drawable.temp_image).into(imPhoto);

        }
        else {
            Toast.makeText(this, "The document with the id"+id+
                    "doesn't exist", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    private void uploadPhoto() {
        if(filePath!=null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading the photo in progress...");
            progressDialog.show();
            //create the unique id for the file to be uploaded
            sRef=storageReference.child("images/"+ UUID.randomUUID());
            sRef.putFile(filePath).addOnSuccessListener(this);
            sRef.putFile(filePath).addOnFailureListener(this);
        }
    }


    @Override
    public void onSuccess(Object o) {
        Toast.makeText(this,"the photo has been uploaded successfully",Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        sRef.getDownloadUrl().addOnCompleteListener(this);

    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this, "ERROR while uploading the photo: "+ e.getMessage(),
                Toast.LENGTH_LONG).show();
        progressDialog.dismiss();

    }

    @Override
    public void onComplete(@NonNull Task task) {
        fileUrl= task.getResult().toString();
        Log.d("ADV_FIREBASE","The Url of the photo is: "+
                fileUrl);

    }
}