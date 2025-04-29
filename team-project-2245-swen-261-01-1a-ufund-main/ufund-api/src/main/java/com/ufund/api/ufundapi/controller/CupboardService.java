//Author[s] Demitri Clark, 
//Grab stuff from DAO for controller.

package com.ufund.api.ufundapi.controller;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;

import com.ufund.api.ufundapi.persistence.CupboardDAO;
import com.ufund.api.ufundapi.model.Need;


@RequestMapping("needs")
public class CupboardService {
    
    private CupboardDAO dao;

    public CupboardService(CupboardDAO cupboardDAO){
        this.dao = cupboardDAO;
    }

    public Need getNeed(int id) throws IOException{
        return dao.getNeed(id);
    }

    public Need[] getNeeds() throws IOException{
        return dao.getNeeds();
    }

    public Need[] searchNeeds(String desc) throws IOException{
        return dao.searchNeeds(desc);
    }

    public Need createNeed(Need need) throws IOException{
        return dao.createNeed(need);
    }

    public Need updateNeed(Need need) throws IOException{
        return dao.updateNeed(need);
    }

    public boolean deleteNeed(int id) throws IOException{
        return dao.deleteNeed(id);
    }

}
