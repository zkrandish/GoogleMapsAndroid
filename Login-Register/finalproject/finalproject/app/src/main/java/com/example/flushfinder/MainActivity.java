package com.example.flushfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText edEmail, edPassword;
    Button btnLogin, btnNoAccount, btnForgotPassword;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnNoAccount = findViewById(R.id.btnNoAccount);
        btnLogin = findViewById(R.id.btnLogin);

        btnNoAccount.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });

//        btnForgotPassword.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
//                startActivity(intent);
////                finish();
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(edEmail.getText());
                password = String.valueOf(edPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Please enter you email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Please enter you password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    Toast.makeText(MainActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    public void onComplete(@NonNull Task<AuthResult> task){

                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Welcome back to FlushFinder!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Incorrect email or password!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }
}