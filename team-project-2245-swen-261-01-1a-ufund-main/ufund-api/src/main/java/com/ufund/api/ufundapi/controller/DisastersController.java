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

import com.ufund.api.ufundapi.persistence.DisastersDAO;
import com.ufund.api.ufundapi.model.Disaster;


//Handles the REST API requests for the Disaster resource
//Definitely not "borrowed" from the Cupboard implementation
//Author[s] Emma Wheeler, 
//major credit to SWEN faculty

@RestController
@RequestMapping("disasters")
public class DisastersController {
    private static final Logger LOG = Logger.getLogger(DisastersController.class.getName());
    private DisastersService disasterService;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param disasterDao The {@link DisastersDAO Disaster Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public DisastersController(DisastersDAO disasterDAO) {
        //Streamlined to remove global dao file since it's out of use
        disasterService = new DisastersService(disasterDAO);
    }

    /**
     * Responds to the GET request for a {@linkplain Disaster disaster} for the given id
     * 
     * @param id The id used to locate the {@link Disaster disaster}
     * 
     * @return ResponseEntity with {@link Disaster disaster} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Disaster> getDisaster(@PathVariable int id) {
        LOG.info("GET /disaster/" + id);
        try {
            Disaster disaster = disasterService.getDisaster(id);
            if (disaster != null)
                return new ResponseEntity<Disaster>(disaster,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Disaster disasters}
     * 
     * @return ResponseEntity with array of {@link Disaster disasters} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Disaster[]> getDisasters() {
        LOG.info("GET /disasters");
        try{
            Disaster[] disasters = disasterService.getDisasters();
            if(disasters != null){
                return new ResponseEntity<Disaster[]>(disasters,HttpStatus.OK);
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
     * Responds to the GET request for all {@linkplain Disaster disaster} whose name contains
     * the text in description
     * 
     * @param name The name parameter which contains the text used to find the {@link Disaster disasters}
     * 
     * @return ResponseEntity with array of {@link Disaster disaster} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all disasters that contain the text "ma"
     * GET http://localhost:8080/disasters/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Disaster[]> searchDisasters(@RequestParam String name) {
        LOG.info("GET /disasters/?name="+name);
        try {
            Disaster[] disasters = disasterService.searchDisasters(name);
            if (disasters != null)
                return new ResponseEntity<Disaster[]>(disasters,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Disaster disaster} with the provided disaster object
     * 
     * @param disaster - The {@link Disaster disaster} to create
     * 
     * @return ResponseEntity with created {@link Disaster disaster} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Disaster disaster} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Disaster> createDisaster(@RequestBody Disaster disaster) {
        LOG.info("POST /disasters " + disaster);
        try {
            Disaster newDisaster = disasterService.createDisaster(disaster);
            if (newDisaster != null)
                return new ResponseEntity<Disaster>(newDisaster,HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Disaster disaster} with the provided {@linkplain Disaster disaster} object, if it exists
     * 
     * @param disaster The {@link Disaster disaster} to update
     * 
     * @return ResponseEntity with updated {@link Disaster disaster} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Disaster> updateDisaster(@RequestBody Disaster disaster) {
        LOG.info("PUT /disasters " + disaster);
        try {
            Disaster newDisaster = disasterService.updateDisaster(disaster);
            if (newDisaster != null)
                return new ResponseEntity<Disaster>(newDisaster,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Disaster disaster} with the given id
     * 
     * @param id The id of the {@link Disaster disaster} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Disaster> deleteDisaster(@PathVariable int id) {
        LOG.info("DELETE /disasters/" + id);
        try {
            boolean deleted = disasterService.deleteDisaster(id);
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
