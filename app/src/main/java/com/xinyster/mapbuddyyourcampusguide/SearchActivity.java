package com.xinyster.mapbuddyyourcampusguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.xinyster.mapbuddyyourcampusguide.DataBaseHelperPackage.DataBaseController;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    AutoCompleteTextView location_box ;
    AutoCompleteTextView destination_box ;
    Spinner items ;
    Spinner items2 ;

    ArrayList<String> places = new ArrayList<String>();

    private Object AdapterView;

    boolean fil ,fid ;

    public void initialise(){

        // Used Db Controller here
        DataBaseController DCS = new DataBaseController() ;
        places = DCS.getPacesList();

        fil = true ;
        fid = true ;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this , android.R.layout.simple_spinner_dropdown_item , places) ;
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SearchActivity.this , android.R.layout.simple_spinner_dropdown_item ,places ) ;

        location_box.setAdapter(adapter);
        items.setAdapter(adapter2);

        items.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(fil){
                    location_box.setHint("Enter your location...");
                    fil = false ;
                }
                else {
                    String text = items.getSelectedItem().toString();
                    location_box.setText(text);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        destination_box.setAdapter(adapter);
        items2.setAdapter(adapter2);

        items2.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(fid){
                    destination_box.setHint("Enter your Destination...");
                    fid = false ;
                }
                else {
                    String text = items2.getSelectedItem().toString();
                    destination_box.setText(text);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Button signOutButton = (Button) findViewById(R.id.signOutButton) ;

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(SearchActivity.this , MainActivity.class));
            }
        });

        location_box = (AutoCompleteTextView) findViewById(R.id.location ) ;
        destination_box = (AutoCompleteTextView) findViewById(R.id.destination ) ;
        items  = (Spinner) findViewById(R.id.items ) ;
        items2  = (Spinner) findViewById(R.id.items2 ) ;

        initialise();

    }

    public void gomethod(View view){

        String start = location_box.getText().toString();
        String end = destination_box.getText().toString();

        if(start.equals("") || end.equals("")){
            Toast.makeText(this, "Inappropriate location or destination", Toast.LENGTH_SHORT).show();
            return ;
        }

        Intent intent = new Intent(this, PathShowerActivity.class);
        intent.putExtra("start", start);
        intent.putExtra("end", end);
        startActivity(intent);

    }

}

;


