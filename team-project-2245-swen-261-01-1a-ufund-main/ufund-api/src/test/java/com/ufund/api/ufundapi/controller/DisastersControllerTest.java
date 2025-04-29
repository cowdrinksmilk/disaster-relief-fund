package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.ufund.api.ufundapi.model.Disaster;
import com.ufund.api.ufundapi.persistence.DisastersDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Disaster Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class DisastersControllerTest {
    private DisastersController cupboardController;
    private DisastersDAO mockDisastersDAO;

    /**
     * Before each test, create a new DisastersController object and inject
     * a mock Disaster DAO
     */
    @BeforeEach
    public void setupDisastersController() {
        mockDisastersDAO = mock(DisastersDAO.class);
        cupboardController = new DisastersController(mockDisastersDAO);
    }

    @Test
    public void testGetDisaster() throws IOException {  // getDisaster may throw IOException
        // Setup
        Disaster need = new Disaster(99,"Testname 1", "Testdesc 1", new ArrayList<Integer>());
        // When the same id is passed in, our mock Disaster DAO will return the Disaster object
        when(mockDisastersDAO.getDisaster(need.getId())).thenReturn(need);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.getDisaster(need.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testGetDisasterNotFound() throws Exception { // createDisaster may throw IOException
        // Setup
        int needId = 99;
        // When the same id is passed in, our mock Disaster DAO will return null, simulating
        // no need found
        when(mockDisastersDAO.getDisaster(needId)).thenReturn(null);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.getDisaster(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetDisasterHandleException() throws Exception { // createDisaster may throw IOException
        // Setup
        int needId = 99;
        // When getDisaster is called on the Mock Disaster DAO, throw an IOException
        doThrow(new IOException()).when(mockDisastersDAO).getDisaster(needId);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.getDisaster(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all DisastersController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateDisaster() throws IOException {  // createDisaster may throw IOException
        // Setup
        Disaster need = new Disaster(99,"Testname 1", "Testdesc 1", new ArrayList<Integer>());
        // when createDisaster is called, return true simulating successful
        // creation and save
        when(mockDisastersDAO.createDisaster(need)).thenReturn(need);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.createDisaster(need);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testCreateDisasterFailed() throws IOException {  // createDisaster may throw IOException
        // Setup
        Disaster need = new Disaster(99,"Testname 1", "Testdesc 1", new ArrayList<Integer>());
        // when createDisaster is called, return false simulating failed
        // creation and save
        when(mockDisastersDAO.createDisaster(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.createDisaster(need);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateDisasterHandleException() throws IOException {  // createDisaster may throw IOException
        // Setup
        Disaster need = new Disaster(99,"Testname 1", "Testdesc 1", new ArrayList<Integer>());

        // When createDisaster is called on the Mock Disaster DAO, throw an IOException
        doThrow(new IOException()).when(mockDisastersDAO).createDisaster(need);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.createDisaster(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateDisaster() throws IOException { // updateDisaster may throw IOException
        // Setup
        Disaster need = new Disaster(99,"Testname 1", "Testdesc 1", new ArrayList<Integer>());
        // when updateDisaster is called, return true simulating successful
        // update and save
        when(mockDisastersDAO.updateDisaster(need)).thenReturn(need);
        ResponseEntity<Disaster> response = cupboardController.updateDisaster(need);
        need.setName("Testname 2");

        // Invoke
        response = cupboardController.updateDisaster(need);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testUpdateDisasterFailed() throws IOException { // updateDisaster may throw IOException
        // Setup
        Disaster need = new Disaster(99,"Testname 1", "Testdesc 1", new ArrayList<Integer>());
        // when updateDisaster is called, return true simulating successful
        // update and save
        when(mockDisastersDAO.updateDisaster(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.updateDisaster(need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateDisasterHandleException() throws IOException { // updateDisaster may throw IOException
        // Setup
        Disaster need = new Disaster(99,"Testname 1", "Testdesc 1", new ArrayList<Integer>());
        // When updateDisaster is called on the Mock Disaster DAO, throw an IOException
        doThrow(new IOException()).when(mockDisastersDAO).updateDisaster(need);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.updateDisaster(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetDisasteres() throws IOException { // getDisasteres may throw IOException
        // Setup
        Disaster[] needs = new Disaster[2];
        needs[0] = new Disaster(99,"Testname 1", "Testdesc 1", new ArrayList<Integer>());
        needs[1] = new Disaster(100,"Testname 2", "Testdesc 1", new ArrayList<Integer>());
        // When getDisasteres is called return the needes created above
        when(mockDisastersDAO.getDisasters()).thenReturn(needs);

        // Invoke
        ResponseEntity<Disaster[]> response = cupboardController.getDisasters();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testGetDisasteresHandleException() throws IOException { // getDisasteres may throw IOException
        // Setup
        // When getDisasteres is called on the Mock Disaster DAO, throw an IOException
        doThrow(new IOException()).when(mockDisastersDAO).getDisasters();

        // Invoke
        ResponseEntity<Disaster[]> response = cupboardController.getDisasters();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchDisasteres() throws IOException { // findDisasteres may throw IOException
        // Setup
        String searchString = "la";
        Disaster[] needs = new Disaster[2];
        needs[0] = new Disaster(99,"Testname 1", "Testdesc 1", new ArrayList<Integer>());
        needs[1] = new Disaster(100,"Testname 2", "Testdesc 1", new ArrayList<Integer>());
        // When findDisasteres is called with the search string, return the two
        /// needes above
        when(mockDisastersDAO.searchDisasters(searchString)).thenReturn(needs);

        // Invoke
        ResponseEntity<Disaster[]> response = cupboardController.searchDisasters(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testSearchDisasteresHandleException() throws IOException { // findDisasteres may throw IOException
        // Setup
        String searchString = "1";
        // When createDisaster is called on the Mock Disaster DAO, throw an IOException
        doThrow(new IOException()).when(mockDisastersDAO).searchDisasters(searchString);

        // Invoke
        ResponseEntity<Disaster[]> response = cupboardController.searchDisasters(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteDisaster() throws IOException { // deleteDisaster may throw IOException
        // Setup
        int needId = 99;
        // when deleteDisaster is called return true, simulating successful deletion
        when(mockDisastersDAO.deleteDisaster(needId)).thenReturn(true);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.deleteDisaster(needId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteDisasterNotFound() throws IOException { // deleteDisaster may throw IOException
        // Setup
        int needId = 99;
        // when deleteDisaster is called return false, simulating failed deletion
        when(mockDisastersDAO.deleteDisaster(needId)).thenReturn(false);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.deleteDisaster(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteDisasterHandleException() throws IOException { // deleteDisaster may throw IOException
        // Setup
        int needId = 99;
        // When deleteDisaster is called on the Mock Disaster DAO, throw an IOException
        doThrow(new IOException()).when(mockDisastersDAO).deleteDisaster(needId);

        // Invoke
        ResponseEntity<Disaster> response = cupboardController.deleteDisaster(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
