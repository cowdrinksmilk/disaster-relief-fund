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
import com.ufund.api.ufundapi.model.Disaster;

public class DisastersFileDAOTest {
    DisastersFileDAO disastersFileDAO;
    Disaster[] testDisasters;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupCupboardService() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testDisasters = new Disaster[3];
        // there has to be a better way to do this
        ArrayList<Integer> testItems1 = new ArrayList<Integer>();
        ArrayList<Integer> testItems2 = new ArrayList<Integer>();
        ArrayList<Integer> testItems3 = new ArrayList<Integer>();
        testItems1.add(4);
        testItems1.add(5);
        testItems2.add(5);
        testItems3.add(7);
        testDisasters[0] = new Disaster(4,"name1","desc1",testItems1);
        testDisasters[1] = new Disaster(5,"name2","desc2",testItems2);
        testDisasters[2] = new Disaster(6,"name3","desc3",testItems3);

        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Disaster[].class))
                .thenReturn(testDisasters);
        disastersFileDAO = new DisastersFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetDisasters() {
        // Invoke
        Disaster[] disasters = disastersFileDAO.getDisasters();

        // Analyze
        assertEquals(disasters.length,testDisasters.length);
        for (int i = 0; i < testDisasters.length;++i)
            assertEquals(disasters[i],testDisasters[i]);
    }

    @Test
    public void testSearchDisasters() {
        // Invoke
        Disaster[] disasters = disastersFileDAO.searchDisasters("1");

        // Analyze
        assertEquals(disasters.length,1);
        assertEquals(disasters[0],testDisasters[0]);
    }

    @Test
    public void testGetDisaster() {
        // Invoke
        Disaster disaster = disastersFileDAO.getDisaster(4);

        // Analzye
        assertEquals(disaster,testDisasters[0]);
    }

    @Test
    public void testDeleteDisaster() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> disastersFileDAO.deleteDisaster(4),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        
        Disaster[] disasters = disastersFileDAO.getDisasters();
        assertEquals(disasters.length, testDisasters.length-1);
    }

    @Test
    public void testCreateDisaster() {
        // Setup
        ArrayList<Integer> testItems4 = new ArrayList<Integer>();
        testItems4.add(8);
        testItems4.add(9);
        testItems4.add(10);
        Disaster disaster = new Disaster(7,"name4","desc4", testItems4);

        // Invoke
        Disaster result = assertDoesNotThrow(() -> disastersFileDAO.createDisaster(disaster),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Disaster actual = disastersFileDAO.getDisaster(disaster.getId());
        assertEquals(actual.getId(),disaster.getId());
        assertEquals(actual.getName(),disaster.getName());
        assertEquals(actual.getDescription(),disaster.getDescription());
        assertEquals(actual.getItems(),disaster.getItems());
    }

    @Test
    public void testUpdateDisaster() {
        // Setup
        ArrayList<Integer> testItems4 = new ArrayList<Integer>();
        testItems4.add(8);
        testItems4.add(9);
        testItems4.add(10);
        Disaster disaster = new Disaster(4,"name4","desc4", testItems4);

        // Invoke
        Disaster result = assertDoesNotThrow(() -> disastersFileDAO.updateDisaster(disaster),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Disaster actual = disastersFileDAO.getDisaster(disaster.getId());
        assertEquals(actual,disaster);
    }

    @Test
    public void testGetDisasterNotFound() {
        // Invoke
        Disaster disaster = disastersFileDAO.getDisaster(20);

        // Analyze
        assertEquals(disaster,null);
    }

    @Test
    public void testDeleteDisasterNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> disastersFileDAO.deleteDisaster(20),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(disastersFileDAO.getDisasters().length,testDisasters.length);
    }

    @Test
    public void testUpdateDisasterNotFound() {
        // Setup
        ArrayList<Integer> testItems4 = new ArrayList<Integer>();
        testItems4.add(8);
        testItems4.add(9);
        testItems4.add(10);
        Disaster disaster = new Disaster(20,"name4","desc4", testItems4);

        // Invoke
        Disaster result = assertDoesNotThrow(() -> disastersFileDAO.updateDisaster(disaster),
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
                .readValue(new File("doesnt_matter.txt"),Disaster[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new DisastersFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
