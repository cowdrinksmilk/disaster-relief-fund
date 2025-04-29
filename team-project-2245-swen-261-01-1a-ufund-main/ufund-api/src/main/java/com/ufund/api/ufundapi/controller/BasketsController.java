package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.persistence.BasketsDAO;
import com.ufund.api.ufundapi.model.Basket;


//Handles the REST API requests for the Basket resource
//Definitely not "borrowed" from the Cupboard implementation
//Author[s] Emma Wheeler, 
//major credit to SWEN faculty

@RestController
@RequestMapping("baskets")
public class BasketsController {
    private static final Logger LOG = Logger.getLogger(BasketsController.class.getName());
    private BasketsService basketService;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param basketDao The {@link BasketsDAO Basket Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public BasketsController(BasketsDAO basketDAO) {
        //Streamlined to remove global dao file since it's out of use
        basketService = new BasketsService(basketDAO);
    }

    /**
     * Responds to the GET request for a {@linkplain Basket basket} for the given id
     * 
     * @param id The id used to locate the {@link Basket basket}
     * 
     * @return ResponseEntity with {@link Basket basket} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasket(@PathVariable int id) {
        LOG.info("GET /basket/" + id);
        try {
            Basket basket = basketService.getBasket(id);
            if (basket != null)
                return new ResponseEntity<Basket>(basket,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Basket baskets}
     * 
     * @return ResponseEntity with array of {@link Basket baskets} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Basket[]> getBaskets() {
        LOG.info("GET /baskets");
        try{
            Basket[] baskets = basketService.getBaskets();
            if(baskets != null){
                return new ResponseEntity<Basket[]>(baskets,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Basket basket} whose name contains
     * the text in description
     * 
     * @param username The username parameter which contains the text used to find the {@link Basket baskets}
     * 
     * @return ResponseEntity with array of {@link Basket basket} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all baskets that contain the text "ma"
     * GET http://localhost:8080/baskets/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Basket[]> searchBaskets(@RequestParam String username) {
        LOG.info("GET /baskets/?username="+username);
        try {
            Basket[] baskets = basketService.searchBaskets(username);
            if (baskets != null)
                return new ResponseEntity<Basket[]>(baskets,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Basket basket} with the provided basket object
     * 
     * @param basket - The {@link Basket basket} to create
     * 
     * @return ResponseEntity with created {@link Basket basket} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Basket basket} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Basket> createBasket(@RequestBody Basket basket) {
        LOG.info("POST /baskets " + basket);
        try {
            Basket newBasket = basketService.createBasket(basket);
            if (newBasket != null)
                return new ResponseEntity<Basket>(newBasket,HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Basket basket} with the provided {@linkplain Basket basket} object, if it exists
     * 
     * @param basket The {@link Basket basket} to update
     * 
     * @return ResponseEntity with updated {@link Basket basket} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Basket> updateBasket(@RequestBody Basket basket) {
        LOG.info("PUT /baskets " + basket);
        try {
            Basket newBasket = basketService.updateBasket(basket);
            if (newBasket != null)
                return new ResponseEntity<Basket>(newBasket,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Basket basket} with the given id
     * 
     * @param id The id of the {@link Basket basket} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Basket> deleteBasket(@PathVariable int id) {
        LOG.info("DELETE /baskets/" + id);
        try {
            boolean deleted = basketService.deleteBasket(id);
            if (deleted)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
