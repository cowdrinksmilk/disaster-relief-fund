//Model for Need objects
//Author[s]: Emma Wheeler, Mary Almazan, Demitri Clark

package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Need {
    //This was here in the Heroes API code, I don't know what it does nor why we need it.
    // It logs all the info so we can debug better - Mary
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    static final String STRING_FORMAT = "Need [id=%d, name=%s, description=%s, quantity=%d, cost=%d]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name; //do we need this? all the search stuff is done on the description.
    @JsonProperty("description") private String description; //alternatively, do we get rid of the description and run searches based on the name?
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("cost") private int cost;
    //add associated disaster later once we have time to implement non-bare minimum features

    /* make a new need
     * @param id: the id, for internal use. [generate w/o user input? maybe based on # of needs in system]
     * @param name: the name. [generate with user input]
     * @param description: the description. [generate with user input]
     * @param money_needed: some int value of how much money a need needs. may change to double for cent values.
     */
    // Mary -> im going to edit this a bit
    // public Need(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("money_needed") int money_needed){
        // this.id = id;
        // this.name = name;
        // this.description = description;
        // this.money_needed = money_needed;
     // }
     public Need(@JsonProperty("id") int id, 
        @JsonProperty("name") String name, 
        @JsonProperty("description") String description, 
        @JsonProperty("quantity") int quantity,
        @JsonProperty("cost") int cost
        )
         {
            this.id = id;
            this.name = name;
            this.description = description;
            this.quantity = quantity;
            this.cost = cost;

            LOG.info("New Need created: " + this.toString());
            }


    //functions to pull data from a need
    public int getId() {return id;}

    public void setName(String newName){this.name = newName;}

    public String getName(){return name;}

    public void setDescription(String newDescription){this.description = newDescription;}

    public String getDescription(){return description;}

    public void setQuantity(int newquantity){this.quantity = newquantity;}

    public int getQuantity(){return quantity;}

    public int getCost() {return cost;}

    public void setCost(int newcost){this.cost = newcost;}

    
    
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, 
            id, 
            name != null ? name : "N/A", 
            description != null ? description : "N/A", 
            getQuantity(),
            getCost()
        );
    }


}
