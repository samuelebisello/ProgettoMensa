package com.sam.reservation.Model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MealUsersReservation {

    private String id; // id dish
    private String name;
    private int type;
    private List<User> reslist;

}
