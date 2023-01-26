package com.xinyster.mapbuddyyourcampusguide.AuthenticationHelperPackage;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Future;

public class AuthenticationController {

    FirebaseAuth mAuth ;

    public static String successMessage = "Success" ;

    public AuthenticationController(){
        mAuth = FirebaseAuth.getInstance();
    }

    public String logInUser(String email , String password ){

        Task<AuthResult> task =  mAuth.signInWithEmailAndPassword(email,password) ;

        while (task.isComplete()==false )

        if(task.isSuccessful()) {
            return successMessage ;
        }
        else{
            return task.getException().getMessage();
        }
        return "General Error" ;

    }

    public String signInNewUser(String email , String password ){

        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email,password) ;

        while (task.isComplete()==false) ;
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

    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }
}
