package com.example.akshayprince.stesmess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class profile extends AppCompatActivity {

    private Button b3;
    private FirebaseAuth firebaseAuth;
    private TextView email,name;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        b3 = (Button)findViewById(R.id.logout);
        email = (TextView)findViewById(R.id.email_p);
        name = (TextView)findViewById(R.id.name_p);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String userName = getIntent().getExtras().getString("name_user");
        name.setText(userName);
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
