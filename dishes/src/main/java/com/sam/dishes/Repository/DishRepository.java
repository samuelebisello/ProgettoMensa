package com.sam.dishes.Repository;


import com.sam.dishes.Model.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DishRepository extends MongoRepository<Dish, String> {

    Dish insert(Dish dish);

    Long deleteDishById(String id);

    List<Dish> findAll();

}
