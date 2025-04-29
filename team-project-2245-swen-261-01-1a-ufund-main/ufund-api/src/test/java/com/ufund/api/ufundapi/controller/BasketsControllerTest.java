package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.persistence.BasketsDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Basket Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class BasketsControllerTest {
    private BasketsController cupboardController;
    private BasketsDAO mockBasketsDAO;

    /**
     * Before each test, create a new BasketsController object and inject
     * a mock Basket DAO
     */
    @BeforeEach
    public void setupBasketsController() {
        mockBasketsDAO = mock(BasketsDAO.class);
        cupboardController = new BasketsController(mockBasketsDAO);
    }

    @Test
    public void testGetBasket() throws IOException {  // getBasket may throw IOException
        // Setup
        Basket need = new Basket(99,"Testname 1", new ArrayList<Integer>(), new ArrayList<Integer>());
        // When the same id is passed in, our mock Basket DAO will return the Basket object
        when(mockBasketsDAO.getBasket(need.getId())).thenReturn(need);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.getBasket(need.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testGetBasketNotFound() throws Exception { // createBasket may throw IOException
        // Setup
        int needId = 99;
        // When the same id is passed in, our mock Basket DAO will return null, simulating
        // no need found
        when(mockBasketsDAO.getBasket(needId)).thenReturn(null);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.getBasket(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetBasketHandleException() throws Exception { // createBasket may throw IOException
        // Setup
        int needId = 99;
        // When getBasket is called on the Mock Basket DAO, throw an IOException
        doThrow(new IOException()).when(mockBasketsDAO).getBasket(needId);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.getBasket(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all BasketsController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateBasket() throws IOException {  // createBasket may throw IOException
        // Setup
        Basket need = new Basket(99,"Testname 1", new ArrayList<Integer>(), new ArrayList<Integer>());
        // when createBasket is called, return true simulating successful
        // creation and save
        when(mockBasketsDAO.createBasket(need)).thenReturn(need);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.createBasket(need);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testCreateBasketFailed() throws IOException {  // createBasket may throw IOException
        // Setup
        Basket need = new Basket(99,"Testname 1", new ArrayList<Integer>(), new ArrayList<Integer>());
        // when createBasket is called, return false simulating failed
        // creation and save
        when(mockBasketsDAO.createBasket(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.createBasket(need);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateBasketHandleException() throws IOException {  // createBasket may throw IOException
        // Setup
        Basket need = new Basket(99,"Testname 1", new ArrayList<Integer>(), new ArrayList<Integer>());

        // When createBasket is called on the Mock Basket DAO, throw an IOException
        doThrow(new IOException()).when(mockBasketsDAO).createBasket(need);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.createBasket(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateBasket() throws IOException { // updateBasket may throw IOException
        // Setup
        Basket need = new Basket(99,"Testname 1", new ArrayList<Integer>(), new ArrayList<Integer>());
        // when updateBasket is called, return true simulating successful
        // update and save
        when(mockBasketsDAO.updateBasket(need)).thenReturn(need);
        ResponseEntity<Basket> response = cupboardController.updateBasket(need);
        need.setUsername("Testname 2");

        // Invoke
        response = cupboardController.updateBasket(need);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(need,response.getBody());
    }

    @Test
    public void testUpdateBasketFailed() throws IOException { // updateBasket may throw IOException
        // Setup
        Basket need = new Basket(99,"Testname 1", new ArrayList<Integer>(), new ArrayList<Integer>());
        // when updateBasket is called, return true simulating successful
        // update and save
        when(mockBasketsDAO.updateBasket(need)).thenReturn(null);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.updateBasket(need);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateBasketHandleException() throws IOException { // updateBasket may throw IOException
        // Setup
        Basket need = new Basket(99,"Testname 1", new ArrayList<Integer>(), new ArrayList<Integer>());
        // When updateBasket is called on the Mock Basket DAO, throw an IOException
        doThrow(new IOException()).when(mockBasketsDAO).updateBasket(need);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.updateBasket(need);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetBasketes() throws IOException { // getBasketes may throw IOException
        // Setup
        Basket[] needs = new Basket[2];
        needs[0] = new Basket(99,"Testname 1", new ArrayList<Integer>(), new ArrayList<Integer>());
        needs[1] = new Basket(100,"Testname 2", new ArrayList<Integer>(), new ArrayList<Integer>());
        // When getBasketes is called return the needes created above
        when(mockBasketsDAO.getBaskets()).thenReturn(needs);

        // Invoke
        ResponseEntity<Basket[]> response = cupboardController.getBaskets();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testGetBasketesHandleException() throws IOException { // getBasketes may throw IOException
        // Setup
        // When getBasketes is called on the Mock Basket DAO, throw an IOException
        doThrow(new IOException()).when(mockBasketsDAO).getBaskets();

        // Invoke
        ResponseEntity<Basket[]> response = cupboardController.getBaskets();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchBasketes() throws IOException { // findBasketes may throw IOException
        // Setup
        String searchString = "la";
        Basket[] needs = new Basket[2];
        needs[0] = new Basket(99,"Testname 1", new ArrayList<Integer>(), new ArrayList<Integer>());
        needs[1] = new Basket(100,"Testname 2", new ArrayList<Integer>(), new ArrayList<Integer>());
        // When findBasketes is called with the search string, return the two
        /// needes above
        when(mockBasketsDAO.searchBaskets(searchString)).thenReturn(needs);

        // Invoke
        ResponseEntity<Basket[]> response = cupboardController.searchBaskets(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(needs,response.getBody());
    }

    @Test
    public void testSearchBasketesHandleException() throws IOException { // findBasketes may throw IOException
        // Setup
        String searchString = "1";
        // When createBasket is called on the Mock Basket DAO, throw an IOException
        doThrow(new IOException()).when(mockBasketsDAO).searchBaskets(searchString);

        // Invoke
        ResponseEntity<Basket[]> response = cupboardController.searchBaskets(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteBasket() throws IOException { // deleteBasket may throw IOException
        // Setup
        int needId = 99;
        // when deleteBasket is called return true, simulating successful deletion
        when(mockBasketsDAO.deleteBasket(needId)).thenReturn(true);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.deleteBasket(needId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteBasketNotFound() throws IOException { // deleteBasket may throw IOException
        // Setup
        int needId = 99;
        // when deleteBasket is called return false, simulating failed deletion
        when(mockBasketsDAO.deleteBasket(needId)).thenReturn(false);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.deleteBasket(needId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteBasketHandleException() throws IOException { // deleteBasket may throw IOException
        // Setup
        int needId = 99;
        // When deleteBasket is called on the Mock Basket DAO, throw an IOException
        doThrow(new IOException()).when(mockBasketsDAO).deleteBasket(needId);

        // Invoke
        ResponseEntity<Basket> response = cupboardController.deleteBasket(needId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
