//Author[s] Emma Wheeler, 
//Grab stuff from DAO for controller.
//Definitely not borrowed from Demitri's cupboard implementation.

package com.ufund.api.ufundapi.controller;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;

import com.ufund.api.ufundapi.persistence.DisastersDAO;
import com.ufund.api.ufundapi.model.Disaster;


@RequestMapping("disasters")
public class DisastersService {
    
    private DisastersDAO dao;

    public DisastersService(DisastersDAO disasterDAO){
        this.dao = disasterDAO;
    }

    public Disaster getDisaster(int id) throws IOException{
        return dao.getDisaster(id);
    }

    public Disaster[] getDisasters() throws IOException{
        return dao.getDisasters();
    }

    public Disaster[] searchDisasters(String desc) throws IOException{
        return dao.searchDisasters(desc);
    }

    public Disaster createDisaster(Disaster disaster) throws IOException{
        return dao.createDisaster(disaster);
    }

    public Disaster updateDisaster(Disaster disaster) throws IOException{
        return dao.updateDisaster(disaster);
    }

    public boolean deleteDisaster(int id) throws IOException{
        return dao.deleteDisaster(id);
    }

}
