package com.example.prjadvfb;

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

import model.Car;
import model.Person;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ValueEventListener, OnSuccessListener, OnFailureListener, OnCompleteListener {

    EditText edId;
    Button btnAdd,btnFind,btnBrowse,btnUpload;
    ImageView imPhoto;
    // For Realtime database
    DatabaseReference personDatabase;

    // For Firebase Storage
    FirebaseStorage storage;
    StorageReference storageReference,sRef;

//    // 1-For receiving results (image) when we click the button browse
    //for the button browse
    //declare activty result launcher
    ActivityResultLauncher aResL;
    // The path of the image in the device
    Uri filePath;

// For the progression of uploading the file to firebase storage
    ProgressDialog progressDialog;
    String fileUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        edId = findViewById(R.id.edId);
        imPhoto = findViewById(R.id.imPhoto);
        btnAdd = findViewById(R.id.btnAdd);
        btnBrowse = findViewById(R.id.btnBrowse);
        btnUpload = findViewById(R.id.btnUpload);
        btnFind = findViewById(R.id.btnFind);
        btnAdd.setOnClickListener(this);
        btnBrowse.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnFind.setOnClickListener(this);

        personDatabase= FirebaseDatabase.getInstance().getReference("Person");

        //Initialize the reference to firebase storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        runActivityesLauncher();
//        //2 register activity result launcher
//        aResL= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        //4- process the result: assign the result to the image
//                        getPhoto(result);
//
//                    }
//                });
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id ==R.id.btnAdd){
            addPerson(view);
        } else if (id== R.id.btnFind) {
            findPerson();
        } else if (id==R.id.btnBrowse) {
            selectPhoto();
        } else if (id==R.id.btnUpload) {
            uploadPhoto();
        }


    }
  private void runActivityesLauncher(){
      //2 register activity result launcher
      aResL= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
              new ActivityResultCallback<ActivityResult>() {
                  @Override
                  public void onActivityResult(ActivityResult result) {
                      //4- process the result: assign the result to the image
                      getPhoto(result);

                  }
              });
    }
    private void getPhoto(ActivityResult result) {
        if(result.getResultCode()==RESULT_OK && result.getData()!=null){
            filePath= result.getData().getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imPhoto.setImageBitmap(bitmap);
            }catch (Exception e){
                Log.d("ADV_FIREBASE",e.getMessage());
            }
        }
        else{
            Toast.makeText(this,"No result",Toast.LENGTH_LONG).show();
        }
    }

    private void selectPhoto() {
        //3- to call the built-in activity:Intent.createChooser(...
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        aResL.launch(Intent.createChooser(intent,"select image"));
    }


    private void addPerson(View view) {
        ArrayList<String> hobbies= new ArrayList<String>();
        hobbies.add("Soccer");
        hobbies.add("Handball");
        hobbies.add("Hockey");
        hobbies.add("Music");
        Car car = new Car("M300","Mazda","Mazda 6");
        Person person = new Person(300,"Richard",fileUrl,car,hobbies);
        personDatabase.child("300").setValue(person);
        Snackbar.make(view,"the person with id :300 has been added to the database"
        ,Snackbar.LENGTH_LONG).show();
    }
    private void findPerson() {
        String id = edId.getText().toString();
        DatabaseReference personChild = personDatabase.child(id);
        personChild.addValueEventListener(this);
        //will fire the ondatachage and try to find the data on the website
        //if the data exists the snapshot will contain the data
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String id = edId.getText().toString();
        if(snapshot.exists()){
            //we found the data
            String name = snapshot.child("name").getValue().toString();
            Log.d("ADV_FIREBASE",name);
            //find info stored in sub-document:object car
            Map car = (Map)snapshot.child("car").getValue();
            Log.d("ADV_FIREBASE","Brand: "+car.get("brand").toString());
            Log.d("ADV_FIREBASE","Model: "+car.get("model").toString());
            Log.d("ADV_FIREBASE","car: "+car.toString());
            //find info stored in sub-document: the array list
            ArrayList<String> hobbies= (ArrayList)snapshot.child("hobbies").getValue();
            Log.d("ADV_FIREBASE",hobbies.get(0));
            Log.d("ADV_FIREBASE",hobbies.toString());
            //find the photo
            String urlPhoto = snapshot.child("photo").getValue().toString();
            Log.d("ADV_FIREBASE",urlPhoto);
            Picasso.with(this).load(urlPhoto)
                    .placeholder(R.drawable.temp_image).into(imPhoto);
        }
        else{
            Toast.makeText(this,"the document with the id "+id+"doesnt exist",Toast.LENGTH_LONG).show();
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
            sRef= storageReference.child("images/"+ UUID.randomUUID());
            sRef.putFile(filePath).addOnSuccessListener(this);
            sRef.putFile(filePath).addOnFailureListener(this);
        }


    }
    @Override
    public void onSuccess(Object o) {
        Toast.makeText(this,"the photo has been uploaded successfully",
                Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        //comlet by building the url of the uploaded photo
        sRef.getDownloadUrl().addOnCompleteListener(this);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this, "Error while uploading the photo"+
                e.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();

    }

    @Override
    public void onComplete(@NonNull Task task) {
        fileUrl= task.getResult().toString();
       Log.d("ADV_FIREBASE","The Url of the photo is: "+
              fileUrl);
    }
}