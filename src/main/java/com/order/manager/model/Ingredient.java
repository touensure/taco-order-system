package com.order.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
//@RequiredArgsConstructor
public class Ingredient {
    private String id;
    private String name;
    private Type type;

    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
