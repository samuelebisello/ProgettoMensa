package com.sam.reservation.Controller;

import com.sam.reservation.Error.Error;
import com.sam.reservation.Error.ReservationException;
import com.sam.reservation.Model.*;
import com.sam.reservation.Repository.RepositoryReservation;
import com.sam.reservation.Service.ReservationService;
import org.omg.CORBA.INTERNAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class ReservationController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ReservationService service;

    @Autowired
    RepositoryReservation repositoryReservation;


    @GetMapping("/api/reservations/users")
    @CrossOrigin("*")
    public List<UserMealsReservation> getReservationUser(@RequestParam("date") String date, @RequestParam("moment") String moment) throws ReservationException {
        try {
            return service.getMelasforUser(date, moment);
        } catch (Exception e) {
            throw new ReservationException("errore data, moment o elemento non presente");
        }

    }

    @CrossOrigin("*")
    @GetMapping("/api/reservations/meals")
    public List<MealUsersReservation> getReservationMeal(@RequestParam("date") String date, @RequestParam("moment") String moment) throws ReservationException {
        // parsare string to localdate
        log.info("out");
        try {
            return service.getUserforMeals(date, moment);
        } catch (Exception e) {
            throw new ReservationException("errore data, moment o elemento non presente");
        }
    }


    @PostMapping("/api/reservations")
    @CrossOrigin("*")
    Reservation createReservation(@RequestBody Reservation reservation) {
        String date = reservation.getDate();
        String moment = reservation.getMoment();
        User user = reservation.getUser();
        List<Reservation> res = repositoryReservation.findReservationByMomentAndDate(moment, date);
        Reservation aux = null;
        for(Reservation r: res) {
            log.info(r.getUser().getName());
            if(r.getUser().getId().equals(user.getId()))
                aux = r;
        }

        if (aux != null) {
            log.info(aux.getUser().getName());
            throw new ReservationException("Prenotazione di questo utente già inserita. Prima rimuovi qulla già esistente");

        } else {
            log.info("è su null");
            reservation.setId(UUID.randomUUID().toString());
            // recuperare id utente per ruolo non null;
            return repositoryReservation.insert(reservation);
        }
    }


    @DeleteMapping("/api/reservations/{id}")
    @CrossOrigin("*")
    boolean deleteReservation(@PathVariable("id") String id) throws ReservationException {

        if (repositoryReservation.deleteReservationById(id).equals(new Long(1)))
            return true;
        else
            throw new ReservationException("non è possibile eliminare la prenotazione");
    }


    @ExceptionHandler
    public ResponseEntity<Error> printError(ReservationException e) {
        Error error = Error.builder().message(e.getMessage()).status(404).timestamp(System.currentTimeMillis()).scope("db").build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
