package com.sam.menu.Repository;

import com.sam.menu.Model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;

public interface MenuRepository extends MongoRepository<Menu, String> {

    public Menu findMenuByDate(String date);

    public Menu insert(Menu menu);

    public Long deleteMenuById(String id);

    public Menu save(Menu menu);

}
