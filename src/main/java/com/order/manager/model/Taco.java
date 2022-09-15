package com.order.manager.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Data
@Entity
@Table(name = "Taco")//if name is ommited , jpa will automatically use the class name as table name
public class Taco {
    /**
     *note: @Enumerated must be added if it's an enum field, or when ingredient.findAll()is called, the data in DB will be mapped to int,
     * which cause the「DataIntegrityViolationException」; Moreover, @NotNull is optional, but better to be added if
     * corresponding field has the constraint「not null」when create table, and @Column can be used to change the column name(or the field
     * name will be used as the column name), and mark it nuulable nor not */

    private static final double TACO_ID_LENGTH = 8;

    @Id
    @NotNull
    @Column(name = "ID", nullable = false)
    private String id = tacoIdGenerator();

    @NotNull
    @Size(min=5, message = "Name must be at least 5 characters long")
    @Column(name = "NAME", nullable = false)
    private String name;

    @Size(min=1, message = "You must choose at least 1 ingredient")
    @NotNull(message = "You must choose at least 1 ingredient")
    @ManyToMany(targetEntity=Ingredient.class)
    private List<Ingredient> ingredients;

    @Column(name = "CREATEDAT", nullable = false)
    private Date createdAt;

    /* for add multiple columns:
     * @JoinColumns({
            @JoinColumn(name="ADDR_ID", referencedColumnName="ID"),
            @JoinColumn(name="ADDR_ZIP", referencedColumnName="ZIP")
        })
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERID")
    private Order tacoOrder;

    private String tacoIdGenerator(){
        return String.valueOf( (long)((Math.random()*9+1)*Math.pow(10,TACO_ID_LENGTH-1)) );
    }

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}
