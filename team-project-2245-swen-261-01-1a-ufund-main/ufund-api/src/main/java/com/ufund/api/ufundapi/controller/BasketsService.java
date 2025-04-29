//Author[s] Emma Wheeler, 
//Grab stuff from DAO for controller.
//Definitely not borrowed from Demitri's cupboard implementation.

package com.ufund.api.ufundapi.controller;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;

import com.ufund.api.ufundapi.persistence.BasketsDAO;
import com.ufund.api.ufundapi.model.Basket;


@RequestMapping("baskets")
public class BasketsService {
    
    private BasketsDAO dao;

    public BasketsService(BasketsDAO basketDAO){
        this.dao = basketDAO;
    }

    public Basket getBasket(int id) throws IOException{
        return dao.getBasket(id);
    }

    public Basket[] getBaskets() throws IOException{
        return dao.getBaskets();
    }

    public Basket[] searchBaskets(String desc) throws IOException{
        return dao.searchBaskets(desc);
    }

    public Basket createBasket(Basket basket) throws IOException{
        return dao.createBasket(basket);
    }

    public Basket updateBasket(Basket basket) throws IOException{
        return dao.updateBasket(basket);
    }

    public boolean deleteBasket(int id) throws IOException{
        return dao.deleteBasket(id);
    }

}
