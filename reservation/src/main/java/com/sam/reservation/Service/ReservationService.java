package com.sam.reservation.Service;

import com.sam.reservation.Model.*;
import com.sam.reservation.Repository.RepositoryReservation;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service

public class ReservationService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RepositoryReservation repository;


    // logic method.
    public List<UserMealsReservation> getMelasforUser(String date, String moment) throws Exception {

        List<UserMealsReservation> userMealsReservationList = new ArrayList<>();

        List<Reservation> reservation = repository.findReservationByMomentAndDate(moment, date);


        for(Reservation res: reservation ) {
            UserMealsReservation userMealsReservation = UserMealsReservation.builder()
                    .hour(res.getHour())
                    .id(res.getId())
                    .user(res.getUser())
                    .build();
            userMealsReservationList.add(userMealsReservation);
        }

        return userMealsReservationList;
    }



    public List<MealUsersReservation> getUserforMeals(String date, String moment) throws Exception {

        log.info("*");

        List<Reservation> reservation = repository.findReservationByMomentAndDate(moment, date);

        // ALL CONTIENE LE RESERVATIONS DAL GIORNO DATE E DEL MOMENT MOMENT

        Map<Dish, List<User>> map = new HashMap<>();
        for (Reservation res : reservation) {
            User user = res.getUser();
            user.setHour(res.getHour());

            for (Dish dish : res.getMeals()) {
                List<User> users = new ArrayList<>();
                if (map.containsKey(dish)) {
                    map.get(dish).add(user);
                } else {
                    users.add(user);
                    map.put(dish, users);
                }
            }
        }

        List<MealUsersReservation> usersReservations = new ArrayList<>();
        for (Map.Entry<Dish, List<User>> maps : map.entrySet()) {
            usersReservations.add(
                    MealUsersReservation.builder()
                            .id(maps.getKey().getId())
                            .name(maps.getKey().getName())
                            .type(maps.getKey().getType())
                            .reslist(maps.getValue())
                            .build()
            );
        }
        return usersReservations;
    }

    // db -> get all reservations by date. for each reservations fetch dishes -> create map dishes-occurences.
    public Map<Dish, Integer> getDataForReport(String YearAndMonth) {

        List<Reservation> reservationList = repository.findAll();
        log.info(YearAndMonth);
        reservationList = reservationList.stream().filter(reservation -> reservation.getDate().startsWith(YearAndMonth)).collect(Collectors.toList());


        List<Dish> dishList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            log.info(reservation.getDate());
            dishList.addAll(reservation.getMeals());
        }

        Map<Dish, Integer> dishIntegerMap = new HashMap<>();
        for (Dish dish : dishList) {
            Integer counter = Collections.frequency(dishList, dish);
            if (!dishIntegerMap.containsKey(dish))
                dishIntegerMap.put(dish, counter);
        }

        return dishIntegerMap;
    }



    // verifica prenotazione esistente per momento e data di un determinato utente -> ritorna errore se presente


}
