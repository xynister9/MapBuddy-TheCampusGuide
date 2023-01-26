package com.xinyster.mapbuddyyourcampusguide.RoutingAlgorithmPackage;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.xinyster.mapbuddyyourcampusguide.DataModels.Node;
import com.xinyster.mapbuddyyourcampusguide.DataBaseHelperPackage.DataBaseController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class PathFinderAStar{
    static ArrayList<ArrayList<Node>> adjList = null ;
    static Map<String , Integer> numId = null ;
    static Map<Integer , String> nameOf = null ;
    static Map<Integer,Map<Integer,Integer>> heuristic = null ;

    static class Pair implements Comparable<Pair> {
        Node node;
        int dis;
        int heuristic ;

        Pair( int dis , Node node , int heuristic){
            this.node = node;
            this.dis = dis;
            this.heuristic = heuristic ;
        }

        public int compareTo(Pair o){
            return this.dis + this.heuristic - o.dis + o.heuristic ;
        }
    }

    public PathFinderAStar(){
        DataBaseController DBC = new DataBaseController() ;
        if(adjList==null){
            adjList = DBC.getAdjacencyList();
            numId = DBC.getIdOf();
            nameOf = DBC.getNameOf() ;
        }
    }

    ArrayList<String> AStarBFS( int start , int destination  ){

        // metrics Data
        int []dist = new int[36] ;
        Node []parent = new Node[36] ;
        Arrays.fill(dist , 1000000000);

        // StartNode
        Node startNode = new Node(-1,start,-1,"");

        // PriorityQueue
        PriorityQueue<Pair> pq = new PriorityQueue<>() ;

        // Initialize
        dist[startNode.val] = 0 ;
        pq.add(new Pair(0,startNode,0))  ;

        // Start Bfs
        while(pq.size()>0){

            Pair curr = pq.remove() ;
            int nodeNum = curr.node.val ;
            int currDis = curr.dis ;

            if(nodeNum==destination){
                break ;
            }
            if(dist[nodeNum]<currDis){
                continue;
            }

            for( Node node : adjList.get(nodeNum) ){
                if(dist[node.val] > currDis+node.weight ){
                    dist[node.val] = currDis+node.weight ;
                    parent[node.val] = node ;
                    pq.add(new Pair(dist[node.val] , node , heuristic.get(nodeNum).get(node.val))) ;
                }
            }

        }

        ArrayList<String> res = new ArrayList<>() ;

        while (destination!=start){
            String theDirection = "From " + nameOf.get(parent[destination].from)
                    + " " + parent[destination].direction
                    + " " + nameOf.get(parent[destination].val)
                    + "for : " + parent[destination].weight
                    + " ms." ;
            res.add(theDirection);
            //res.add(parent[destination].direction) ;

            destination = parent[destination].from ;
        }

        if(res.isEmpty()){
            res.add("Sorry we didn't find any path...");
        }else{
            Collections.reverse(res);
        }
        return  res ;
    }

    public ArrayList<String> givePath( String start , String end ) {
        ArrayList<String> ans = new ArrayList<>() ;

        if(start.equals(end)){
            ans.add("You are already here") ;
        }
        else if(!numId.containsKey(start) || !numId.containsKey(end)){
            ans.add("Invalid start or destination") ;
        }
        else{
            ans = AStarBFS(numId.get(start) , numId.get(end)) ;
        }
        return ans ;
    }
}
