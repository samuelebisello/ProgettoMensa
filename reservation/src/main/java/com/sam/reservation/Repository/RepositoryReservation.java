package com.sam.reservation.Repository;

import com.sam.reservation.Model.Dish;
import com.sam.reservation.Model.Reservation;
import com.sam.reservation.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RepositoryReservation extends MongoRepository<Reservation, String> {

    public Reservation insert(Reservation reservation);

    public Long deleteReservationById(String id);

    public List<Dish> findAllDishesById(String id); // ritorna tutti i piatti relativi all'utente con Id id;

    public List<Reservation> findAllByMoment(String moment);
    public List<Reservation>findReservationByMomentAndDate(String moment, String date);
    public Reservation findReservationByMomentAndDateAndUser(String moment, String date, User user);

    //public List<Dish> findAllDishes();
    public List<Reservation> findAllByDate(String date);

    public List<User> findAllUserByDate(String date);

    public List<Reservation> findAllByUser(User user);

    // cerca le reservation di una data
    // dal risultato guarda gli id degloi utenti -> sono quelli ok di oggi. -> per ogni utenti cerca i piatti.
    // poi ritorni un json formattato con utent + piatti


}
