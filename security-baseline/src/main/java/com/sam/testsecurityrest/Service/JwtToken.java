package com.sam.testsecurityrest.Service;

import com.sam.testsecurityrest.Model.User;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class JwtToken {
    private String username;
    private String id;
    private String name;
    private String exp;
    private boolean admin;
}
