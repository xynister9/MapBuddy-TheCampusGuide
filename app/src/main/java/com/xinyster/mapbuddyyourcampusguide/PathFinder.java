package com.xinyster.mapbuddyyourcampusguide;


import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class PathFinder {
    //(1) Data structure
    String[] names_of_nodes = new String[]{"","Main Gate" , "Research Lab" , "Boys Hostel" , "Library" , "Shakuntalam Hall" , "Canteen" , "Royal Enfield Workshop" ,
            "Mechanical Engineering Department" , "India Post Office" , "Mother Dairy" ,"CV Raman Block" , "Back Gate" , "ATM" , "Civil Engineering Department"
            ,"Reception" , "Lal Chowk" , "Health Dispensary" ,"Basket Ball Court" , "Main Ground" ,"VC Office" ,"Management Department" , "Girls Hostel",
            "Electrical Engineering Department" , "Computer Department" , "MBA Park" , "Electronics Engineering Department" , "Accounts Section" , "Auditorium" , "University Computer Centre" ,
            "Student Window" , "Administration Block" ,"Ground" , "Photocopy Shop" ,"Sports Office" ,"Registrar Office" ,"Main Stage" ,"Srijan's Quarter" ,"" ,"" ,"" ,"" } ;



    //(2) Data structure
    HashMap< String , Integer > nodeNumber = new HashMap<>();
    //(3) Data structure

    String[][] pathname = new String[40][40] ;
    //(4) Data Structure
    int dist[][] = new int[40][40] ;
    int[] distance =new int[40] ;
    int[] parent =new int[40] ;

    //------------------------------------------
    static class Edge {
        int start;
        int end ;
        int weight ;
        String path_direction ;

        Edge(int start, int end, int weight , String direction ) {
            this.start = start;
            this.end = end;
            this.weight = weight;
            this.path_direction = direction ;
        }

        Map<String,Object> getMap(){
            Map<String,Object> edgeDoc = new HashMap<>();
            edgeDoc.put( "start",  start ) ;
            edgeDoc.put("end" , end ) ;
            edgeDoc.put("weight" , weight) ;
            edgeDoc.put("path direction" , path_direction ) ;
            return edgeDoc ;
        }

    }

    //(4) For graph Data structure / adjancency list
    int vtces = 40;

    ArrayList<Map<String , Object>> graph = new ArrayList<>() ;

    public void connectEgde( int u , int v , int dis , String utov , String vtou ){
        Edge edu_to_v = new Edge(u, v, dis ,utov) ;
        Edge edv_to_u = new Edge(v, u, dis ,vtou) ;

        if(graph.size()>u ){
            Map<String , Object> alist = graph.get(u) ;
            alist.put( Integer.toString(v) , edu_to_v.getMap() );
        }
        if(graph.size()>v ){
            Map<String , Object> alist = graph.get(v) ;
            alist.put( Integer.toString(u) , edv_to_u.getMap() );
        }
//        graph[u].add(new Edge(u, v, dis ,utov) );
//        graph[v].add(new Edge(v, u, dis ,vtou) );

//        pathname[u][v] = utov ;
//        pathname[v][u] = vtou ;
//        dist[u][v] = dist[v][u] = dis;
    }
    String sauce= "sauce" ;

    public PathFinder(){
//        FirebaseFirestore db =  FirebaseFirestore.getInstance();

        for(int i=1; i<=35; i++){
            Map<String,Object> dummy = new HashMap<>();
            graph.add(dummy) ;
//            Map<String,Object> place_node = new HashMap<>();
//
//            place_node.put("id", i) ;
//            place_node.put("place_name" , names_of_nodes[i] ) ;

//            db.collection("place nodes").add(place_node);

//            nodeNumber.put(names_of_nodes[i] , i ) ;
////            graph[i] = new ArrayList<>();
//            distance[i]=100000000;
//            parent[i]=i;
        }
        connectEgde(1,2,50 ,"Go straight" , "Go straight");
        connectEgde(2,3,30 ,"Turn right" , "Go straight");
        connectEgde(3,4,20 ,"Turn left" , "Turn Right" );
        connectEgde(4,5,20,"take right" , "Go right");
        connectEgde(4,6,20,"take right" , "Go left");
        connectEgde(5,6,20,"take left" , "Go right");
        connectEgde(7,8 , 60 , "take right " , "take left");
        connectEgde(8,9 ,20 , " take right " , "take right");
        connectEgde(9,10 ,10 , " take right " , "take left");
        connectEgde(1,10,100, "turn right" , "turn left" );
        connectEgde(7,11,5, "Go Straigt" , "Turn left");
        connectEgde(11,12,60, "Head right -> take right" , "");
        connectEgde(11,13,10,"take right" , "then take left" );
        connectEgde(13,14,60 ,"Go straight" , "Go straight beside Shakuntalam");
        connectEgde(14,15,15 ,"Go straight" , "Go straight beside Shakuntalam");
        connectEgde(15,16,20 ,"Go left " , "Go right beside boys washroom");
        connectEgde(16,17 ,15 ,"Head Out" , "Go inside Administrative block");
        connectEgde(17,18,5,"Take left ","Turn right");
        connectEgde(17,19,10,"Take left go straight ","Turn right");
        connectEgde(17,20,20,"Go straight turn right" , "Go straight take left");
        connectEgde(2,20,2,"Turn left" , "Go straight ");
        connectEgde(17,21,10,"turn right" , "turn left");
        connectEgde(17,22 , 10 ,"Turn left" , "Take right" );
        connectEgde(17,23,100,"Head Straight take right" , "Head Straight take left");
        connectEgde(2,24,10,"head Staight take right" , "take left" );
        connectEgde(24,25,10,"take right" , "take left" );
        connectEgde(25,26,10,"take right" , "take left" );

        connectEgde(27,8,10,"take stairs go down" , "take stairs to 2nd floor turn right" );
        connectEgde(22,28,10,"Take stairs , On first floor " , "Take stairs,down ");
        connectEgde(25,29,15,"Take lift/stairs , first floor turn left" , "go straight take stairs , down");
        connectEgde(8,30,2,"On the left besides president office" , "Take right , go straight");
        connectEgde(17,31,5,"in right" , "go out straight");

        for (int i = 1 ; i<=31 ; i++){
            Map<String,Object> alist = new HashMap<>();
            alist.put(Integer.toString(i) , graph.get(i));
            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            try {
//                db.collection("adjacency list").add(alist);
//            } catch (Exception e ){
//                sauce = e.getMessage() ;
//            }
        }
    }

    public ArrayList<Integer> dijkstra_bfs(int src , int end ){

//        Log.i("selected" , src+" "+ end ) ;
//        PriorityQueue<Pair> queue = new PriorityQueue<>();
//
//        queue.add(new Pair(src, 0));
//        boolean[] visited = new boolean[vtces];
//
//        while(queue.size() > 0){
//            Pair curr = queue.remove();
//
////            System.out.println(rem.v + " via " + rem.psf.toString() + " @ " + rem.wsf);
//
//            for (Edge e : graph[curr.node]) {
//                if (curr.dis + e.wt < distance[e.nbr]) {
//                    distance[e.nbr] = curr.dis + e.wt ;
//                    parent[e.nbr] = curr.node ;
//                    queue.add( new Pair(e.nbr , distance[e.nbr] ) ) ;
//                }
//            }
//        }
        ArrayList<Integer> res = new ArrayList<>() ;
//        while(parent[end]!=src){
//            res.add(end) ;
//            end = parent[end];
//        }
//        res.add(end) ;
//        res.add(src) ;
//        Collections.reverse(res);
        return res ;
    }

    static class Pair implements Comparable<Pair> {
        int node;
        int dis;

        Pair(int node, int currdis){
            this.node = node;
            this.dis = currdis;
        }

        public int compareTo(Pair o){
            return this.dis - o.dis;
        }
    }

    public ArrayList<String> givePath( String start , String end ) {

        int pt = 1 ;
        ArrayList<String> shortestPath = new ArrayList<>() ;

        if(start.equals(end)) {
            shortestPath.add("You are here only !!");
            shortestPath.add(sauce) ;
            return shortestPath;
        }

        int ids , ide ;

        try{
            ids = nodeNumber.get(start)  ;
            ide = nodeNumber.get(end)  ;
        }
        catch (Exception  e){
            shortestPath.add("+"+start+"+") ;
            shortestPath.add("+"+end+"+") ;
            e.printStackTrace() ;
            return shortestPath ;
        }

        ArrayList<Integer> pathInNode =  dijkstra_bfs(ids , ide );

        for(int i =0 ; i<pathInNode.size()-1; i++){
            shortestPath.add( (pt++) + ". "+ pathname[pathInNode.get(i)][pathInNode.get(i+1)]
                    +" to " + names_of_nodes[pathInNode.get(i+1)]  + " -" + dist[pathInNode.get(i)][pathInNode.get(i+1)]+"ms" ) ;
        }
        return shortestPath ;
    }

}