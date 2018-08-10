package com.sam.testsecurityrest.Error;


import com.google.gson.Gson;
import lombok.*;

import com.google.gson.Gson;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Error {
    private Long timestamp;
    private String message;
    private int status;
    private String scope;

    public String toJson() {
        return new Gson().toJson(this);
    }

}
