package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Disaster;

//Author[s]: Emma Wheeler, 

//stores all disasters and allows for viewing, editing, etc.
//definitely NOT "borrowed" from the CupboardDAO file
//major credit to SWEN Faculty


public interface DisastersDAO {
    /**
     * Retrieves all {@linkplain Disaster disasters}
     * 
     * @return An array of {@link Disaster disasters} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Disaster[] getDisasters() throws IOException;

    /**
     * Finds all {@linkplain Disaster disaster} whose username matches the one entered. 
     * Should only return one if I do my job right. 
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Disaster disasters} whose usernames contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Disaster[] searchDisasters(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Disaster disaster} with the given id
     * 
     * @param id The id of the {@link Disaster disaster} to get
     * 
     * @return a {@link Disaster disaster} object with the matching id
     * <br>
     * null if no {@link Disaster disaster} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
     Disaster getDisaster(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Disaster disaster}
     * 
     * @param disaster {@linkplain Disaster disaster} object to be created and saved.
     * Should only be called when no username is found
     * <br>
     * The id of the need object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Disaster disaster} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Disaster createDisaster(Disaster disaster) throws IOException;

    /**
     * Updates and saves a {@linkplain Disaster disaster}
     * 
     * @param {@link Disaster disaster} object to be updated and saved
     * 
     * @return updated {@link Disaster disaster} if successful, null if
     * {@link Disaster disaster} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Disaster updateDisaster(Disaster disaster) throws IOException;



    /**
     * Deletes a {@linkplain Disaster disaster} with the given id
     * I don't know why we'd need this, save for freeing space or pruning duplicates
     * if I do my job badly
     * 
     * @param id The id of the {@link Disaster disaster}
     * 
     * @return true if the {@link Disaster disaster} was deleted
     * <br>
     * false if need with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteDisaster(int id) throws IOException;
}
