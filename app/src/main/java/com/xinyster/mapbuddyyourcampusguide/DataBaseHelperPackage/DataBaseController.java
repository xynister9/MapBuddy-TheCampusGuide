package com.xinyster.mapbuddyyourcampusguide.DataBaseHelperPackage;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xinyster.mapbuddyyourcampusguide.DataModels.Node;
import com.xinyster.mapbuddyyourcampusguide.SignInUserActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class DataBaseController {

    // DataBase Instance
    private FirebaseFirestore db ;

    // Get Data'S
    private static ArrayList<String> places = null ;
    private static ArrayList<ArrayList<Node>> adjList = null ;
    private static Map<Integer ,String> NameOf = null ;
    private static Map<String,Integer> IdOf = null ;

    // Default Constructor
    public DataBaseController(){
        db = FirebaseFirestore.getInstance();
    }

    public void addUserData( Map<String,Object>  user ){
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i( "Erro" , "here 2") ;
            }
        });
    }
    public ArrayList<String> getPacesList(){

        if(places!=null){
            return places ;
        }

        places = new ArrayList<>();

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

    public ArrayList<ArrayList<Node>> getAdjacencyList(){
        if(adjList!=null){
            return adjList ;
        }

        adjList = new ArrayList<>() ;

        for(int i = 0 ; i<36 ; i++ ){
            adjList.add(new ArrayList<>()) ;
        }

        db.collection("adjacency list").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document : task.getResult()){

                        Map<String, Object> listmap = document.getData();
                        String SfromNode = "" ;

                        for( String s :  listmap.keySet() ){
                            SfromNode = s ;
                        }
                        Map<String, Map<String,Object> > mapActualList = (Map<String, Map<String,Object>> ) listmap.get(SfromNode) ;

                        for(String to : mapActualList.keySet() ){

                            Node createNode = new Node(mapActualList.get(to)) ;
                            adjList.get(createNode.from).add(createNode) ;
                        }
                    }

                }
            }
        });
        return  adjList ;
    }

    public Map<Integer,String> getNameOf(){

        if(NameOf!=null){
            return NameOf ;
        }

        NameOf  = new HashMap<>();

        db.collection("place nodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    // getting QueryDocumentSnapshot
                    for(QueryDocumentSnapshot document : task.getResult() ){
                        if(document.get("place_name")!=null){
                            NameOf.put((Integer)document.get("id"), (String)document.get("place_name").toString() );
                        }
                    }
                }
                else{
                    Log.i("Error" , "some error occured");
                }
            }
        });
        return  NameOf ;
    }

    public Map<String,Integer> getIdOf(){

        if(IdOf==null){
            return IdOf ;
        }

        IdOf  = new HashMap<>();

        db.collection("place nodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    // getting QueryDocumentSnapshot
                    for(QueryDocumentSnapshot document : task.getResult() ){
                        if(document.get("place_name")!=null){
                            IdOf.put((String)document.get("place_name").toString()  ,(Integer)document.get("id"));
                        }
                    }
                }
                else{
                    Log.i("Error" , "some error occured");
                }
            }
        });
        return IdOf  ;
    }
}
