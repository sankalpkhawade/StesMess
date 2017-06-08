package com.example.akshayprince.stesmess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    private Button b3;
    private FirebaseAuth firebaseAuth;
    private TextView email;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        b3 = (Button)findViewById(R.id.logout);
        email = (TextView)findViewById(R.id.email_p);
        firebaseAuth.getInstance();
        user = firebaseAuth.getInstance().getCurrentUser();
        email.setText(user.getEmail());
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){
        finish();
        firebaseAuth.signOut();
        Intent intent = new Intent(profile.this,MainActivity.class);
        startActivity(intent);
    }
}
