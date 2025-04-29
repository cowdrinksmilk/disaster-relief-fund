//Model for Disaster objects
//Author[s]: Emma Wheeler, Mary Almazan

package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Disaster {
    private static final Logger LOG = Logger.getLogger(Disaster.class.getName());

    static final String STRING_FORMAT = "Disaster [id=%d, name=%s, description=%s, items=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name; 
    @JsonProperty("description") private String description; 
    @JsonProperty("items") private ArrayList<Integer> items; //IDs of needs associated with disaster

    /* make a new basket
     * @param id: the id, for internal use. [generate w/o user input? maybe based on # of needs in system]
     * @param username: the username of the person it belongs to.
     * @param items: the ids of the needs in the basket
     * @param counts: the counts per each need. index is shared w/ associated needs [not yet in use.]
     */
     public Disaster(@JsonProperty("id") int id, 
        @JsonProperty("name") String name, 
        @JsonProperty("description") String description, 
        @JsonProperty("items") ArrayList<Integer> items
        )
         {
            this.id = id;
            this.name = name;
            this.description = description;
            this.items = items;

            LOG.info("New Disaster created: " + this.toString());
            }


    //functions to pull data from a basket
    public int getId() {return id;}

    public void setName(String newName){this.name = newName;}

    public String getName(){return name;}

    public void setDescription(String newDesc){this.description = newDesc;}

    public String getDescription(){return description;}

    public ArrayList<Integer> getItems(){return items;}

    public void addItem (int needId){
        items.add(needId);
    }

    public void removeItem (int index){
        items.remove(index);
    }
    
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, 
            id, 
            name != null ? name : "N/A", 
            description != null ? description : "N/A",
            items != null ? items : new ArrayList<Integer>()
        );
    }
    
}