package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * unit tests suite for the Basket class
 *  */
@Tag("Model-tier")
public class BasketTest {

    @Test
    public void testCreation() {
        //Can it be created properly?

        // Setup
        int expected_id = 99;
        String expected_name = "username2";
        ArrayList<Integer> expected_items = new ArrayList<Integer>();
        ArrayList<Integer> expected_counts = new ArrayList<Integer>();
        expected_items.add(1);
        expected_counts.add(1);
        
        // Invoke
        Basket basket = new Basket(expected_id, expected_name, expected_items, expected_counts);

        // Analyze
        assertEquals(expected_id, basket.getId());
        assertEquals(expected_name, basket.getUsername());
        assertEquals(expected_items, basket.getItems());
        assertEquals(expected_counts, basket.getCounts());

    }

    @Test
    public void testName() {
        //Can naming errors be fixed properly?

        // Setup
        int id = 99;
        String name = "username2";
        ArrayList<Integer> items = new ArrayList<Integer>();
        ArrayList<Integer> counts = new ArrayList<Integer>();
        items.add(1);
        counts.add(1);
        Basket basket = new Basket(id, name, items, counts);

        String expected_name = "username2";

        // Invoke
        basket.setUsername(expected_name);

        // Analyze
        assertEquals(expected_name, basket.getUsername());
    }

    @Test
    public void testItemsAndCounts() {
        // Setup
        int id = 99;
        String name = "username2";
        ArrayList<Integer> items = new ArrayList<Integer>();
        ArrayList<Integer> counts = new ArrayList<Integer>();
        items.add(1);
        counts.add(1);
        Basket basket = new Basket(id, name, items, counts);

        ArrayList<Integer> expected_items = new ArrayList<Integer>();
        ArrayList<Integer> expected_count = new ArrayList<Integer>();
        expected_items.add(1);
        expected_count.add(1);
        expected_items.add(4);
        expected_count.add(1);

        // Invoke
        basket.addItem(4, 1);

        // Analyze
        assertEquals(expected_items, basket.getItems());
        assertEquals(expected_count, basket.getCounts());

    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "username2";
        ArrayList<Integer> items = new ArrayList<Integer>();
        ArrayList<Integer> counts = new ArrayList<Integer>();
        items.add(1);
        counts.add(1);
        Basket basket = new Basket(id, name, items, counts);
        String expected_string = String.format(Basket.STRING_FORMAT,id,name, items, counts);

        // Invoke
        String actual_string = basket.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

}