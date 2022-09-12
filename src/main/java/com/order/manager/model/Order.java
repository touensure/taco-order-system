package com.order.manager.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Order {

    private static final double ORDER_ID_LENGTH = 8;

    private String id = orderIdGenerator();

    private Date placedAt;

    //end::newFields[]

    @NotBlank(message="Delivery name is required")
    private String deliveryName;

    @NotBlank(message="Street is required")
    private String deliveryStreet;

    @NotBlank(message="City is required")
    private String deliveryCity;

    @NotBlank(message="State is required")
    @Size(max=2, message = "character of deliveryState should not greater than 2")
    private String deliveryState;

    @NotBlank(message="Zip code is required")
    @Size(max=10, message = "character of deliveryZip should not greater than 10")
    private String deliveryZip;

    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
          message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

    private String orderIdGenerator(){
        return String.valueOf( (long)((Math.random()*9+1)*Math.pow(10,ORDER_ID_LENGTH-1)) );
    }
}
