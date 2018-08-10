package com.sam.dishes.Controller;

import com.sam.dishes.Error.DishException;
import com.sam.dishes.Error.Error;
import com.sam.dishes.Model.Dish;
import com.sam.dishes.Repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    DishRepository repository;


    // return all dishes
    @GetMapping
    @CrossOrigin("*")
    List<Dish> getAllDishes() throws DishException {
        // riflettere
        return repository.findAll();
    }


    // create a dish
    @PostMapping
    @CrossOrigin("*")
    Dish createDishes(@RequestBody Dish dish) {
        dish.setId(UUID.randomUUID().toString());

        return repository.insert(dish);

    }

    // create the dish find by id
    @DeleteMapping("/{id}")
    @CrossOrigin("*")
    boolean deleteDish(@PathVariable("id") String id) throws DishException {
        if (repository.deleteDishById(id).equals(1L))
            return true;
        else
            throw new DishException("menù non presente, impossibile cancellare");
    }


    @ExceptionHandler
    public ResponseEntity<Error> printError(DishException e) {
        Error error = Error.builder().message(e.getMessage()).status(404).timestamp(System.currentTimeMillis()).scope("db").build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
