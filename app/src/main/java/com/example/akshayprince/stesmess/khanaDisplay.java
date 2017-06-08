package com.example.akshayprince.stesmess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class khanaDisplay extends AppCompatActivity {

    TextView menuL,menuD;
    Button updateM;
    Button refreshM;
    ProgressDialog p;
    String menu1,old_menuL,old_menuD;
    FirebaseDatabase database;
    DatabaseReference refL,refD;
    private DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khana_display);

        database = FirebaseDatabase.getInstance();
        refL = database.getReference();
        refD = database.getReference();

        menuL = (TextView)findViewById(R.id.khanaL);
        menuD = (TextView)findViewById(R.id.khanaD);
        updateM = (Button)findViewById(R.id.upadte_id);
        refreshM = (Button)findViewById(R.id.refresh_id);
        p = new ProgressDialog(this);

        refresh();

        updateM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        refreshM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }


    public void refresh() {
        p.setMessage("Refreshing...");
        p.show();

        refL = refL.child("GaneshMess").child("todaymenu").child("launch");
        refL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String m = dataSnapshot.getValue().toString();
                menuL.setText(m);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(khanaDisplay.this,"Could Not Refresh! Check Connection",Toast.LENGTH_SHORT).show();
                p.dismiss();
            }
        });

        refD = refD.child("GaneshMess").child("todaymenu").child("dinner");
        refD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String m = dataSnapshot.getValue().toString();
                menuD.setText(m);
                p.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(khanaDisplay.this,"Could Not Refresh! Check Connection",Toast.LENGTH_SHORT).show();
                p.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile_menu:
                Intent intent = new Intent(this,profile.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void update(){
        Intent i = new Intent(this,update.class);
        startActivity(i);
    }

}
