// Authors: Bennett Griggs
// definitely not borrowed from whoever wrote the previous test files (why isn't that documented)

package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * unit tests suite for the Disaster class
 *  */
@Tag("Model-tier")
public class DisasterTest {

    @Test
    public void testCreation() {
        //Can it be created properly?

        // Setup
        int expected_id = 99;
        String expected_name = "name2";
        String expected_desc = "desc2";
        ArrayList<Integer> expected_items = new ArrayList<Integer>();
        expected_items.add(1);
        
        // Invoke
        Disaster disaster = new Disaster(expected_id, expected_name, expected_desc, expected_items);

        // Analyze
        assertEquals(expected_id, disaster.getId());
        assertEquals(expected_name, disaster.getName());
        assertEquals(expected_desc, disaster.getDescription());
        assertEquals(expected_items, disaster.getItems());

    }

    @Test
    public void testName() {
        //Can naming errors be fixed properly?

        // Setup
        int id = 99;
        String name = "name2";
        String desc = "desc2";
        ArrayList<Integer> items = new ArrayList<Integer>();
        items.add(1);
        Disaster disaster = new Disaster(id, name, desc, items);

        String expected_name = "name3";

        // Invoke
        disaster.setName(expected_name);

        // Analyze
        assertEquals(expected_name, disaster.getName());
    }

    @Test
    public void testDesc() {
        //Can description errors be fixed properly?

        // Setup
        int id = 99;
        String name = "name2";
        String desc = "desc2";
        ArrayList<Integer> items = new ArrayList<Integer>();
        items.add(1);
        Disaster disaster = new Disaster(id, name, desc, items);

        String expected_desc = "desc3";

        // Invoke
        disaster.setDescription(expected_desc);

        // Analyze
        assertEquals(expected_desc, disaster.getDescription());
    }

    @Test
    public void testItems() {
        // Setup
        int id = 99;
        String name = "name2";
        String desc = "desc2";
        ArrayList<Integer> items = new ArrayList<Integer>();
        items.add(1);
        Disaster disaster = new Disaster(id, name, desc, items);

        ArrayList<Integer> expected_items = new ArrayList<Integer>();
        expected_items.add(1);
        expected_items.add(4);

        // Invoke
        disaster.addItem(4);

        // Analyze
        assertEquals(expected_items, disaster.getItems());

    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "name2";
        String desc = "desc2";
        ArrayList<Integer> items = new ArrayList<Integer>();
        items.add(1);
        Disaster disaster = new Disaster(id, name, desc, items);
        String expected_string = String.format(Disaster.STRING_FORMAT,id,name, desc, items);

        // Invoke
        String actual_string = disaster.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

}