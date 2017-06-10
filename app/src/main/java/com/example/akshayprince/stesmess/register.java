package com.example.akshayprince.stesmess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.lang.UCharacterEnums;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private EditText name1;
    private EditText email;
    private EditText password;
    private Button registerB;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private UserProfileChangeRequest userC;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        name1 = (EditText) findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email_n);
        password = (EditText)findViewById(R.id.pass_n);
        registerB = (Button)findViewById(R.id.registerButton);

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });
    }

    private void register(){
        String email1 = email.getText().toString().trim();
        String pass1 = password.getText().toString().trim();
        final String name = name1.getText().toString();

        if(TextUtils.isEmpty(email1)){
            Toast.makeText(register.this,"Please Enter Email ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass1)){
            Toast.makeText(register.this,"Please Enter Password ",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email1,pass1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    databaseReference.child("userInfo").child(firebaseAuth.getCurrentUser().getUid()).child("name").setValue(name);
                    progressDialog.dismiss();
                    Toast.makeText(register.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(register.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(register.this,"Could Not Register, Please Try Again !",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
            }
        });
    }
}
