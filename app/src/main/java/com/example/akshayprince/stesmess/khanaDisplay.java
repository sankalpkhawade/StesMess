package com.example.akshayprince.stesmess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class khanaDisplay extends AppCompatActivity {

    private TextView menuL, menuD;
    private Button updateM;
    private Button refreshM;
    private ProgressDialog p;
    private String menu1;
    private FirebaseDatabase database;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    private DatabaseReference refL, refD;
    private AdView adView;
    String userName;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khana_display);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance();
        refL = database.getReference();
        refD = database.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        menuL = (TextView) findViewById(R.id.khanaL);
        menuD = (TextView) findViewById(R.id.khanaD);
        updateM = (Button) findViewById(R.id.upadte_id);
        refreshM = (Button) findViewById(R.id.refresh_id);
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

        adView = (AdView) findViewById(R.id.add_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }


    public void refresh() {

        /*refL = refL.child("GaneshMess").child("todaymenu").child("launch");
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
        });*/

        new network().execute("https://stesmess.firebaseio.com/.json");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.profile_menu:
                finish();
                Intent intent = new Intent(this, profile.class);
                intent.putExtra("name_user",userName);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void update() {
        Intent i = new Intent(this, update.class);
        startActivity(i);
    }

    public class network extends AsyncTask<String, String, String> {
        String lunch, dinner;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p.setMessage("Refreshing...");
            p.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream stream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String jsonResponce = buffer.toString();


                return jsonResponce;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject parentObject = new JSONObject(s);

                JSONObject mess_name = parentObject.getJSONObject("GaneshMess");
                JSONObject menu = mess_name.getJSONObject("todaymenu");

                lunch = menu.getString("launch");
                dinner = menu.getString("dinner");

                JSONObject P_user_name = parentObject.getJSONObject("userInfo");
                JSONObject user_name = P_user_name.getJSONObject(user.getUid());

                userName = user_name.getString("name");

                menuL.setText(lunch);
                menuD.setText(dinner);


                p.dismiss();

            }
            catch(JSONException e){
                e.printStackTrace();
            }

        }
    }
}








