package com.example.akshayprince.stesmess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class update extends AppCompatActivity {

    EditText menuL;
    EditText menuD;
    Button update2;
    DatabaseReference ref;
    ProgressDialog progressDialog;
    String menu1,menu2;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        menuL = (EditText)findViewById(R.id.menu2L);
        menuD = (EditText)findViewById(R.id.menu2D);
        update2 = (Button)findViewById(R.id.update2);
        progressDialog = new ProgressDialog(this);
        ref = FirebaseDatabase.getInstance().getReference();

        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update2();
                finish();
                Intent i = new Intent(update.this,khanaDisplay.class);
                startActivity(i);
            }
        });
        adView = (AdView)findViewById(R.id.add_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void update2(){
        menu1 = menuL.getText().toString();
        menu2 = menuD.getText().toString();

        if(TextUtils.isEmpty(menu1)|| TextUtils.isEmpty(menu2)) {
            Toast.makeText(this, "Please Enter Menu!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Updating Menu....");
        progressDialog.show();

        ref.child("GaneshMess").child("todaymenu").child("launch").setValue(menu1);
        ref.child("GaneshMess").child("todaymenu").child("dinner").setValue(menu2);

        Toast.makeText(this,"Menu Updated Successfully", Toast.LENGTH_SHORT).show();
    }
}
