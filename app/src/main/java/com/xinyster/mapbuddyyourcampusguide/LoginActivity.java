package com.xinyster.mapbuddyyourcampusguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.xinyster.mapbuddyyourcampusguide.AuthenticationHelperPackage.AuthenticationController;

import java.util.concurrent.Future;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mail= findViewById(R.id.editTextTextEmailAddress);
        final EditText pass= findViewById(R.id.editTextTextPassword);
        final Button signInButton =findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = mail.getText().toString();
                final String password = pass.getText().toString();

                if( email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter your details :( ", Toast.LENGTH_SHORT).show();
                }
                else {

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(LoginActivity.this, SearchActivity.class) );
                            } else{
                                Toast.makeText(LoginActivity.this, "Login Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }

        });

    }

    public void switchToSignIn(View view){
        startActivity(new Intent(LoginActivity.this , SignInUserActivity.class) );
    }
}