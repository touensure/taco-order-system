package com.order.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ingredient")//if name is ommited , jpa will automatically use the class name as table name
public class Ingredient {
    /**
     *note: @Enumerated must be added if it's an enum field, or when ingredient.findAll()is called, the data in DB will be mapped to int,
     * which cause the「DataIntegrityViolationException」; Moreover, @NotNull is optional, but better to be added if
     * corresponding field has the constraint「not null」when create table, and @Column can be used to change the column name(or the field
     * name will be used as the column name), and mark it nuulable nor not */
    @Id
    @NotNull
    @Column(name = "ID", unique = true, nullable = false)
    private String id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)//must be added if the field is an enum
    @Column(name = "TYPE", nullable = false)
    private Type type;

    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
