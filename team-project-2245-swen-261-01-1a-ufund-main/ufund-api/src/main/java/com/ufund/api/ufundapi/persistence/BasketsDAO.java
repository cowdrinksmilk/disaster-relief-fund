package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Basket;

//Author[s]: Emma Wheeler, 

//stores all baskets and allows for viewing, editing, etc.
//definitely NOT "borrowed" from the CupboardDAO file
//major credit to SWEN Faculty


public interface BasketsDAO {
    /**
     * Retrieves all {@linkplain Basket baskets}
     * 
     * @return An array of {@link Basket baskets} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Basket[] getBaskets() throws IOException;

    /**
     * Finds all {@linkplain Basket basket} whose username matches the one entered. 
     * Should only return one if I do my job right. 
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Basket baskets} whose usernames contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Basket[] searchBaskets(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Basket basket} with the given id
     * 
     * @param id The id of the {@link Basket basket} to get
     * 
     * @return a {@link Basket basket} object with the matching id
     * <br>
     * null if no {@link Basket basket} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
     Basket getBasket(int id) throws IOException;

    /**
     * Creates and saves a {@linkplain Basket basket}
     * 
     * @param basket {@linkplain Basket basket} object to be created and saved.
     * Should only be called when no username is found
     * <br>
     * The id of the need object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Basket basket} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Basket createBasket(Basket basket) throws IOException;

    /**
     * Updates and saves a {@linkplain Basket basket}
     * 
     * @param {@link Basket basket} object to be updated and saved
     * 
     * @return updated {@link Basket basket} if successful, null if
     * {@link Basket basket} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Basket updateBasket(Basket basket) throws IOException;



    /**
     * Deletes a {@linkplain Basket basket} with the given id
     * I don't know why we'd need this, save for freeing space or pruning duplicates
     * if I do my job badly
     * 
     * @param id The id of the {@link Basket basket}
     * 
     * @return true if the {@link Basket basket} was deleted
     * <br>
     * false if need with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteBasket(int id) throws IOException;
}
