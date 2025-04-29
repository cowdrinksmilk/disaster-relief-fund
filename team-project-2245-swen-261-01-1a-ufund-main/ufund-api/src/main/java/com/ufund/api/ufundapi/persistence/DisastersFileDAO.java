//Author[s]: Emma Wheeler, Mary Almazan
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

import com.ufund.api.ufundapi.model.Disaster;

@Component
public class DisastersFileDAO implements DisastersDAO {

    private static final Logger LOG = Logger.getLogger(DisastersFileDAO.class.getName()); // Added logging
    Map<Integer,Disaster> disasters;   // Provides a local cache of the need objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Need
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new Need
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Disaster File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public DisastersFileDAO(@Value("${disasters.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the disasters from the file
        LOG.info("Loaded disasters from file: " + filename);
    }

    /**
     * Generates the next id for a new {@linkplain Disaster disaster}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Disaster disasters} from the tree map
     * 
     * @return  The array of {@link Disaster disaster}, may be empty
     */
    private Disaster[] getDisastersArray() {
        return getDisastersArray(null);
    }

    /**
     * Generates an array of {@linkplain Disaster disasters} from the tree map for any
     * {@linkplain Disaster disasters} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Disaster disasters}
     * in the tree map
     * 
     * @return  The array of {@link Disaster disasters}, may be empty
     */
    private Disaster[] getDisastersArray(String containsText) { // if containsText == null, no filter
        ArrayList<Disaster> disasterArrayList = new ArrayList<>();

        for (Disaster disaster : disasters.values()) {
            if (containsText == null || disaster.getName().contains(containsText)) {
                disasterArrayList.add(disaster);
            }
        }

        Disaster[] disasterArray = new Disaster[disasterArrayList.size()];
        disasterArrayList.toArray(disasterArray);
        return disasterArray;
    }

    /**
     * Saves the {@linkplain Disaster disasters} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Disaster disasters} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Disaster[] disasterArray = getDisastersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), disasterArray);
        return true;
    }

    /**
     * Loads {@linkplain Disaster disasters} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        disasters = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of disasters
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Disaster[] disasterArray = objectMapper.readValue(new File(filename),Disaster[].class);

        // Add each need to the tree map and keep track of the greatest id
        for (Disaster disaster : disasterArray) {
            disasters.put(disaster.getId(),disaster);
            if (disaster.getId() > nextId)
                nextId = disaster.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Disaster[] getDisasters() {
        synchronized(disasters) {
            return getDisastersArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Disaster[] searchDisasters(String containsText) {
        synchronized(disasters) {
            return getDisastersArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Disaster getDisaster(int id) {
        synchronized(disasters) {
            if (disasters.containsKey(id))
                return disasters.get(id);
            else
                return null;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Disaster createDisaster(Disaster disaster) throws IOException {
        synchronized(disasters) {
            // We create a new need object because the id field is immutable
            // and we need to assign the next unique id
            Disaster newDisaster = new Disaster(nextId(),disaster.getName(), disaster.getDescription(), disaster.getItems());
            disasters.put(newDisaster.getId(),newDisaster);
            save(); // may throw an IOException
            return newDisaster;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Disaster updateDisaster(Disaster disaster) throws IOException {
        synchronized(disasters) {
            if (disasters.containsKey(disaster.getId()) == false)
                return null;  // need does not exist

            disasters.put(disaster.getId(),disaster);
            save(); // may throw an IOException
            return disaster;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteDisaster(int id) throws IOException {
        synchronized(disasters) {
            if (disasters.containsKey(id)) {
                disasters.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}