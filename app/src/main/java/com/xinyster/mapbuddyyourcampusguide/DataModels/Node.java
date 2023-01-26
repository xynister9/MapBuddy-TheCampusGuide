package com.xinyster.mapbuddyyourcampusguide.DataModels;

import java.util.Map;

public class Node {
    public int from ;
    public int val ;
    public String direction ;
    public int weight ;

    public Node(int from, int val, int weight, String direction){
        this.from = from ;
        this.val = val ;
        this.weight = weight ;
        this.direction = direction ;
    }

    public Node( Map<String,Object> docMap ){

        try {
            this.from = Integer.parseInt(docMap.get("start").toString());
            this.val = Integer.parseInt(docMap.get("end").toString());
            this.weight = Integer.parseInt(docMap.get("weight").toString());
            this.direction = docMap.get("path direction").toString();
        } catch (Exception e ){

        }

    }
}
