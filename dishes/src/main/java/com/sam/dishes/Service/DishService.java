package com.sam.dishes.Service;

import com.sam.dishes.Model.Dish;
import com.sam.dishes.Repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    @Autowired
    DishRepository repository;


}
