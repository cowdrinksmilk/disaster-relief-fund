package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * unit tests suite for the Need class
 *  */
@Tag("Model-tier")
public class NeedTest {

    @Test
    public void testCreation() {
        //Can it be created properly?

        // Setup
        int expected_id = 99;
        String expected_name = "Blankets";
        String expected_desc = "We need blankets for [reason]";
        int expected_quantity = 30;
        int expected_cost = 100;

        // Invoke
        Need need = new Need(expected_id, expected_name, expected_desc, expected_quantity, expected_cost);

        // Analyze
        assertEquals(expected_id, need.getId());
        assertEquals(expected_name, need.getName());
        assertEquals(expected_desc, need.getDescription());
        assertEquals(expected_quantity, need.getQuantity());
        assertEquals(expected_cost, need.getCost());
    }

    @Test
    public void testName() {
        //Can naming errors be fixed properly?

        // Setup
        int id = 99;
        String name = "Blankes";
        String desc = "We need blankets for [reason]";
        int quantity = 30;
        int cost = 100;
        Need need = new Need(id, name, desc, quantity, cost);

        String expected_name = "Blankets";

        // Invoke
        need.setName(expected_name);

        // Analyze
        assertEquals(expected_name, need.getName());
    }

    @Test
    public void testDesc() {
        //Can description errors be fixed properly?

        // Setup
        int id = 99;
        String name = "Blankets";
        String desc = "We nee blankets for [reason]";
        int quantity = 30;
        int cost = 100;
        Need need = new Need(id, name, desc, quantity, cost);

        String expected_desc = "We need blankets for [reason]";

        // Invoke
        need.setDescription(expected_desc);

        // Analyze
        assertEquals(expected_desc, need.getDescription());
    }

    @Test
    public void testQuantity() {
        //Can description errors be fixed properly?

        // Setup
        int id = 99;
        String name = "Blankets";
        String desc = "We nee blankets for [reason]";
        int quantity = 30;
        int cost = 100;
        Need need = new Need(id, name, desc, quantity, cost);

        int expected_quantity = 50;

        // Invoke
        need.setQuantity(expected_quantity);

        // Analyze
        assertEquals(expected_quantity, need.getQuantity());
    }

    @Test
    public void testCost() {
        //Can description errors be fixed properly?

        // Setup
        int id = 99;
        String name = "Blankets";
        String desc = "We nee blankets for [reason]";
        int quantity = 30;
        int cost = 100;
        Need need = new Need(id, name, desc, quantity, cost);

        int expected_cost = 150;

        // Invoke
        need.setCost(expected_cost);

        // Analyze
        assertEquals(expected_cost, need.getCost());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Blankets";
        String desc = "We nee blankets for [reason]";
        int quantity = 30;
        int cost = 100;
        String expected_string = String.format(Need.STRING_FORMAT, id, name, desc, quantity, cost);
        Need need = new Need(id,name,desc,quantity,cost);

        // Invoke
        String actual_string = need.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

}