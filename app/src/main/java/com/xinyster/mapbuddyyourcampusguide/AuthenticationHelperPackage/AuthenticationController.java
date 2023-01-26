package com.xinyster.mapbuddyyourcampusguide.AuthenticationHelperPackage;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationController {

    FirebaseAuth mAuth ;
    final static String successMessage = "Success" ;

    AuthenticationController(){
        mAuth = FirebaseAuth.getInstance();
    }

    public String logInUser(String email , String password ){

        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(email,password) ;

        if(task.isSuccessful()){
            return successMessage ;
        }
        else{
            return task.getException().getMessage() ;
        }

    }

    public String signInNewUser(String email , String password ){

        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email,password) ;

        if(task.isSuccessful()){
            return successMessage ;
        }
        else{
            return task.getException().getMessage() ;
        }

    }

    public void signOutUser(){
        mAuth.signOut();
    }
}
