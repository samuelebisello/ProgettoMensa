package com.sam.dishes.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document
public class Dish {

    @Id
    private String id;
    private int type;
    private String description;
    private String name;

}
