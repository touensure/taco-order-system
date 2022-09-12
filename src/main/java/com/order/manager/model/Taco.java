package com.order.manager.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class Taco {

    private static final double TACO_ID_LENGTH = 8;

    private String id = tacoIdGenerator();

    @NotNull
    @Size(min=5, message = "Name must be at least 5 characters long")
    private String name;

    @Size(min=1, message = "You must choose at least 1 ingredient")
    @NotNull(message = "You must choose at least 1 ingredient")
    private List<String> ingredients;

    private Date createdAt;

    private String tacoIdGenerator(){
        return String.valueOf( (long)((Math.random()*9+1)*Math.pow(10,TACO_ID_LENGTH-1)) );
    }
}
