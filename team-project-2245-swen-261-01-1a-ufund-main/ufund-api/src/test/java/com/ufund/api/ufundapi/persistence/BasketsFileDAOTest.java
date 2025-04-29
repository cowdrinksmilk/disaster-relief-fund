// Authors: Bennett Griggs
// definitely not borrowed from whoever wrote the previous test files (why isn't that documented)

package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Basket;


public class BasketsFileDAOTest {
    BasketsFileDAO basketsFileDAO;
    Basket[] testBaskets;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupCupboardService() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testBaskets = new Basket[3];
        // there has to be a better way to do this
        ArrayList<Integer> testItems1 = new ArrayList<Integer>();
        ArrayList<Integer> testItems2 = new ArrayList<Integer>();
        ArrayList<Integer> testItems3 = new ArrayList<Integer>();
        ArrayList<Integer> testCounts1 = new ArrayList<Integer>();
        ArrayList<Integer> testCounts2 = new ArrayList<Integer>();
        ArrayList<Integer> testCounts3 = new ArrayList<Integer>();
        testItems1.add(4);
        testItems1.add(5);
        testItems2.add(5);
        testItems3.add(7);
        testCounts1.add(2);
        testCounts1.add(2);
        testCounts2.add(2);
        testCounts3.add(5);
        testBaskets[0] = new Basket(4,"name1",testItems1,testCounts1);
        testBaskets[1] = new Basket(5,"name2",testItems2,testCounts2);
        testBaskets[2] = new Basket(6,"name3",testItems3,testCounts3);

        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Basket[].class))
                .thenReturn(testBaskets);
        basketsFileDAO = new BasketsFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetBaskets() {
        // Invoke
        Basket[] baskets = basketsFileDAO.getBaskets();

        // Analyze
        assertEquals(baskets.length,testBaskets.length);
        for (int i = 0; i < testBaskets.length;++i)
            assertEquals(baskets[i],testBaskets[i]);
    }

    @Test
    public void testSearchBaskets() {
        // Invoke
        Basket[] baskets = basketsFileDAO.searchBaskets("1");

        // Analyze
        assertEquals(baskets.length,1);
        assertEquals(baskets[0],testBaskets[0]);
    }

    @Test
    public void testGetBasket() {
        // Invoke
        Basket basket = basketsFileDAO.getBasket(4);

        // Analzye
        assertEquals(basket,testBaskets[0]);
    }

    @Test
    public void testDeleteBasket() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> basketsFileDAO.deleteBasket(4),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        
        Basket[] baskets = basketsFileDAO.getBaskets();
        assertEquals(baskets.length, testBaskets.length-1);
    }

    @Test
    public void testCreateBasket() {
        // Setup
        ArrayList<Integer> testItems4 = new ArrayList<Integer>();
        ArrayList<Integer> testCounts4 = new ArrayList<Integer>();
        testItems4.add(8);
        testItems4.add(9);
        testItems4.add(10);
        testCounts4.add(8);
        testCounts4.add(9);
        testCounts4.add(8);
        Basket basket = new Basket(7,"name4", testItems4, testCounts4);

        // Invoke
        Basket result = assertDoesNotThrow(() -> basketsFileDAO.createBasket(basket),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Basket actual = basketsFileDAO.getBasket(basket.getId());
        assertEquals(actual.getId(),basket.getId());
        assertEquals(actual.getUsername(),basket.getUsername());
        assertEquals(actual.getItems(),basket.getItems());
        assertEquals(actual.getCounts(), basket.getCounts());
    }

    @Test
    public void testUpdateBasket() {
        // Setup
        ArrayList<Integer> testItems4 = new ArrayList<Integer>();
        ArrayList<Integer> testCounts4 = new ArrayList<Integer>();
        testItems4.add(8);
        testItems4.add(9);
        testItems4.add(10);
        testCounts4.add(8);
        testCounts4.add(9);
        testCounts4.add(8);
        Basket basket = new Basket(4,"name4", testItems4, testCounts4);

        // Invoke
        Basket result = assertDoesNotThrow(() -> basketsFileDAO.updateBasket(basket),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Basket actual = basketsFileDAO.getBasket(basket.getId());
        assertEquals(actual,basket);
    }

    /* @Test commented out because idk how this works and i don't want to make something faulty now
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Basket[].class));

        ArrayList<Integer> testItems4 = new ArrayList<Integer>();
        ArrayList<Integer> testCounts4 = new ArrayList<Integer>();
        testItems4.add(8);
        testItems4.add(9);
        testItems4.add(10);
        testCounts4.add(8);
        testCounts4.add(9);
        testCounts4.add(8);
        Basket basket = new Basket(7,"name4", testItems4, testCounts4);

        assertThrows(IOException.class,
                        () -> basketsFileDAO.createBasket(basket),
                        "IOException not thrown");
    } */

    @Test
    public void testGetBasketNotFound() {
        // Invoke
        Basket basket = basketsFileDAO.getBasket(20);

        // Analyze
        assertEquals(basket,null);
    }

    @Test
    public void testDeleteBasketNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> basketsFileDAO.deleteBasket(20),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(basketsFileDAO.getBaskets().length,testBaskets.length);
    }

    @Test
    public void testUpdateBasketNotFound() {
        // Setup
        ArrayList<Integer> testItems4 = new ArrayList<Integer>();
        ArrayList<Integer> testCounts4 = new ArrayList<Integer>();
        testItems4.add(8);
        testItems4.add(9);
        testItems4.add(10);
        testCounts4.add(8);
        testCounts4.add(9);
        testCounts4.add(8);
        Basket basket = new Basket(20,"name4", testItems4, testCounts4);

        // Invoke
        Basket result = assertDoesNotThrow(() -> basketsFileDAO.updateBasket(basket),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Basket[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new BasketsFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
