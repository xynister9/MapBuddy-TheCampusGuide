package com.xinyster.mapbuddyyourcampusguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xinyster.mapbuddyyourcampusguide.AuthenticationHelperPackage.AuthenticationController;
import com.xinyster.mapbuddyyourcampusguide.DataBaseHelperPackage.DataBaseController;

import java.util.HashMap;
import java.util.Map;


public class SignInUserActivity extends AppCompatActivity {
    FirebaseAuth mAuth ;
    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_user);

        final EditText name= findViewById(R.id.editTextTextPersonName);
        final EditText mail= findViewById(R.id.editTextTextEmailAddress);
        final EditText password= findViewById(R.id.editTextTextPassword);
        final Button signInButton =findViewById(R.id.signInButton);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String newname = name.getText().toString();
                final String newmail = mail.getText().toString();
                final String newpassword = password.getText().toString();

                Map<String , Object > user = new HashMap<>();
                user.put("email" , newmail ) ;
                user.put("name" , newname ) ;

                if(newname.isEmpty() || newmail.isEmpty() || newpassword.isEmpty()){
                    Toast.makeText(SignInUserActivity.this, "Please enter your details :( ", Toast.LENGTH_SHORT).show();
                }
                else {

//                    AuthenticationController ATC = new AuthenticationController();
//                    String message = ATC.signInNewUser(newmail,newpassword) ;
//
//                    DataBaseController DTC = new DataBaseController() ;
//                    DTC.addUserData(user);

//                    if(message.equals(AuthenticationController.successMessage)){
//                        startActivity(new Intent(LoginActivity.this, SearchActivity.class) );
//                    }
//                    else{
//                        Toast.makeText(LoginActivity.this, "Login Error : " + message,  Toast.LENGTH_LONG).show();
//                    }

                    mAuth.createUserWithEmailAndPassword(newmail,newpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                add_info(user);
                                logInUser(newmail,newpassword);
                            }else{
                                Toast.makeText(SignInUserActivity.this, "Registration Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }

    private void logInUser(String mail , String password ){
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(SignInUserActivity.this, SearchActivity.class) );
                } else{
                    Toast.makeText(SignInUserActivity.this, "Login Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void add_info(Map<String,Object> user ){
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SignInUserActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i( "Erro" , "here 2") ;
            }
        });
    }
    private  void switchToLogIn(View view){
        startActivity(new Intent(SignInUserActivity.this , LoginActivity.class) );
    }

}