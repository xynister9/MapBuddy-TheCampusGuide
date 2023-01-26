package com.xinyster.mapbuddyyourcampusguide.DataBaseHelperPackage;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xinyster.mapbuddyyourcampusguide.MainPageActivity;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;

public class DataBaseController {

//    private static DataBaseController = null ;

    private FirebaseFirestore db ;

    public DataBaseController(){
        db = FirebaseFirestore.getInstance() ;
    }

    public ArrayList<String> getPacesList(){

        ArrayList<String> places = new ArrayList<>();

        db.collection("place nodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    // getting QueryDocumentSnapshot
                    for(QueryDocumentSnapshot document : task.getResult() ){
                        if(document.get("place_name")!=null){
                            places.add( (String) document.get("place_name").toString() );
                        }
                    }
                }
                else{
                    Log.i("Error" , "some error occured");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i("error" , "didn't get response");
            }
        });

        // Sorting the places list here
        Collections.sort(places) ;
        return places ;

    }
}
