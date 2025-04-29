//Model for Basket objects
//Author[s]: Emma Wheeler, 

package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Basket {
    private static final Logger LOG = Logger.getLogger(Basket.class.getName());

    static final String STRING_FORMAT = "Basket [id=%d, username=%s, items=%s, counts=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username; 
    @JsonProperty("items") private ArrayList<Integer> items; //IDs of needs in basket
    @JsonProperty("counts") private ArrayList<Integer> counts; //Arrary of number of items in basket, matches up with ID of associated need
                                                               // ***not yet in use***

    /* make a new basket
     * @param id: the id, for internal use. [generate w/o user input? maybe based on # of needs in system]
     * @param username: the username of the person it belongs to.
     * @param items: the ids of the needs in the basket
     * @param counts: the counts per each need. index is shared w/ associated needs [not yet in use.]
     */
     public Basket(@JsonProperty("id") int id, 
        @JsonProperty("username") String username, 
        @JsonProperty("items") ArrayList<Integer> items,
        @JsonProperty("counts") ArrayList<Integer> counts
        )
         {
            this.id = id;
            this.username = username;
            this.items = items;
            this.counts = counts;

            LOG.info("New Basket created: " + this.toString());
            }


    //functions to pull data from a basket
    public int getId() {return id;}

    public void setUsername(String newUsername){this.username = newUsername;}

    public String getUsername(){return username;}

    public void updateCount(int newCount, int index){
        if(newCount == 0){
            counts.remove(index);
            items.remove(index);
        }
        else{
            counts.set(index, newCount);
        }
    } 

    public ArrayList<Integer> getCounts(){return counts;}

    public ArrayList<Integer> getItems(){return items;}

    public void addItem (int needId, int count){
        counts.add(count);
        items.add(needId);
    }

    public void removeItem (int index){
        counts.remove(index);
        items.remove(index);
    }
    
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, 
            id, 
            username != null ? username : "N/A", 
            items != null ? items : new ArrayList<Integer>(),
            counts != null ? counts: new ArrayList<Integer>()
        );
    }

}
