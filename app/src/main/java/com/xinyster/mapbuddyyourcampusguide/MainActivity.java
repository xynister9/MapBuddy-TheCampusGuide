package com.xinyster.mapbuddyyourcampusguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xinyster.mapbuddyyourcampusguide.AuthenticationHelperPackage.AuthenticationController;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        AuthenticationController ACS = new AuthenticationController();
        FirebaseUser user = ACS.getCurrentUser() ;

        if(user==null){
            startActivity(new Intent(MainActivity.this , LoginActivity.class ));
        }
        else{
            startActivity(new Intent(MainActivity.this , SearchActivity.class));
        }
    }
}