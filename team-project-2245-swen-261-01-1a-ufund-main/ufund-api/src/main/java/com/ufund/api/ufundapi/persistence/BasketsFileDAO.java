// Implements the functionality for JSON file-based peristance for Baskets
//Definitely not "borrowed" from Cupboard implementation
//Author[s]: Emma Wheeler, 
//Major credit to SWEN Faculty

package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Basket;

@Component
public class BasketsFileDAO implements BasketsDAO {

    private static final Logger LOG = Logger.getLogger(BasketsFileDAO.class.getName()); // Added logging
    Map<Integer,Basket> baskets;   // Provides a local cache of the need objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Need
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new Need
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Basket File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public BasketsFileDAO(@Value("${baskets.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the baskets from the file
        LOG.info("Loaded baskets from file: " + filename);
    }

    /**
     * Generates the next id for a new {@linkplain Basket basket}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Basket baskets} from the tree map
     * 
     * @return  The array of {@link Basket basket}, may be empty
     */
    private Basket[] getBasketsArray() {
        return getBasketsArray(null);
    }

    /**
     * Generates an array of {@linkplain Basket baskets} from the tree map for any
     * {@linkplain Basket baskets} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Basket baskets}
     * in the tree map
     * 
     * @return  The array of {@link Basket baskets}, may be empty
     */
    private Basket[] getBasketsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Basket> basketArrayList = new ArrayList<>();

        for (Basket basket : baskets.values()) {
            if (containsText == null || basket.getUsername().contains(containsText)) {
                basketArrayList.add(basket);
            }
        }

        Basket[] basketArray = new Basket[basketArrayList.size()];
        basketArrayList.toArray(basketArray);
        return basketArray;
    }

    /**
     * Saves the {@linkplain Basket baskets} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Basket baskets} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Basket[] basketArray = getBasketsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), basketArray);
        return true;
    }

    /**
     * Loads {@linkplain Basket baskets} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        baskets = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of baskets
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Basket[] basketArray = objectMapper.readValue(new File(filename),Basket[].class);

        // Add each need to the tree map and keep track of the greatest id
        for (Basket basket : basketArray) {
            baskets.put(basket.getId(),basket);
            if (basket.getId() > nextId)
                nextId = basket.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Basket[] getBaskets() {
        synchronized(baskets) {
            return getBasketsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Basket[] searchBaskets(String containsText) {
        synchronized(baskets) {
            return getBasketsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Basket getBasket(int id) {
        synchronized(baskets) {
            if (baskets.containsKey(id))
                return baskets.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Basket createBasket(Basket basket) throws IOException {
        synchronized(baskets) {
            // We create a new need object because the id field is immutable
            // and we need to assign the next unique id
            Basket newBasket = new Basket(nextId(),basket.getUsername(), basket.getItems(), basket.getCounts());
            baskets.put(newBasket.getId(),newBasket);
            save(); // may throw an IOException
            return newBasket;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Basket updateBasket(Basket basket) throws IOException {
        synchronized(baskets) {
            if (baskets.containsKey(basket.getId()) == false)
                return null;  // need does not exist

            baskets.put(basket.getId(),basket);
            save(); // may throw an IOException
            return basket;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteBasket(int id) throws IOException {
        synchronized(baskets) {
            if (baskets.containsKey(id)) {
                baskets.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
