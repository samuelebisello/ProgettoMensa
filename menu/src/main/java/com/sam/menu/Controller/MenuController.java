package com.sam.menu.Controller;

import com.sam.menu.Error.Error;
import com.sam.menu.Error.MenuException;
import com.sam.menu.Model.Menu;
import com.sam.menu.Repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/menuplan")
@CrossOrigin("*")
public class MenuController {

    @Autowired
    MenuRepository repository;


    @GetMapping()
    @CrossOrigin("*")
    public Menu getMenu(@RequestParam("date") String date) throws MenuException {
        if (repository.findMenuByDate(date) != null)
            return repository.findMenuByDate(date);
        else throw new MenuException("elemento non presente");

        // ritorna un oggetto lista con array di dinner ed un array lunch e i piatti hanno campi:
        // - name, id, type, description, checked.

    }

    @PostMapping
    @CrossOrigin("*")
    public Menu createDailyMenu(@RequestBody Menu menu) {
        //menu.setDate(LocalDate.now());

        // generate a unique id like UUID
        menu.setId(UUID.randomUUID().toString());
        return repository.insert(menu);
        // ricevo un oggetto lista con array di dinner ed un array lunch e i piatti hanno campi:
        // - name, id, type, description, checked.

    }

    @DeleteMapping("/{id}")
    @CrossOrigin("*")
    public boolean deleteMenuByDate(@PathVariable("id") String id) throws MenuException {
        if (repository.deleteMenuById(id).equals(new Long(1)))
            return true;
        else
            throw new MenuException("menù non presente, impossibile cancellare");
    }

    @PutMapping
    @CrossOrigin("*")
    public Menu updateMenuByDate(@RequestBody Menu menu) {
        return repository.save(menu);
    }

    @ExceptionHandler
    public ResponseEntity<Error> printError(MenuException e) {
        Error error = Error.builder().message(e.getMessage()).scope("db").status(404).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
