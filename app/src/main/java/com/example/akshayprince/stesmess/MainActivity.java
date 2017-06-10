package com.example.akshayprince.stesmess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button b1;
    private Button b2 ;
    private EditText email ;
    private EditText pass ;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.sign_in);
        b2 = (Button) findViewById(R.id.sign_up_1);
        progressDialog = new ProgressDialog(this);
        email = (EditText) findViewById(R.id.email_o);
        pass = (EditText) findViewById(R.id.pass_o);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            Intent intent = new Intent(this,khanaDisplay.class);
            startActivity(intent);
        }
        b1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                usersignin();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,register.class);
                startActivity(intent);
            }
        });

    }


    private void usersignin() {

        String email1 = email.getText().toString().trim();
        String pass1 = pass.getText().toString().trim();

        if (TextUtils.isEmpty(email1)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass1)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Signing In...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email1, pass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    finish();
                    Intent intent = new Intent(MainActivity.this,khanaDisplay.class);
                    startActivity(intent);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Could Not Sign In, Please Sign In Again", Toast.LENGTH_LONG).show();
                }
            }
        });

        email.setText("");
        pass.setText("");

    }

    }

