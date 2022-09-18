package com.order.manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.order.manager.config.security.User;


@Data
@Entity
@NoArgsConstructor
@Table(name = "Taco_Order")//if name is ommited , jpa will automatically use the class name as table name
public class Order {
    /**
     *note: @Enumerated must be added if it's an enum field, or when ingredient.findAll()is called, the data in DB will be mapped to int,
     * which cause the「DataIntegrityViolationException」; Moreover, @NotNull is optional, but better to be added if
     * corresponding field has the constraint「not null」when create table, and @Column can be used to change the column name(or the field
     * name will be used as the column name), and mark it nuulable nor not */

    private static final double ORDER_ID_LENGTH = 8;

    @Id
    @Column(name = "ID", nullable = false)
    private String id = orderIdGenerator();

    @Column(name = "PLACEDAT", nullable = false)
    private Date placedAt;

    @NotBlank(message="Delivery name is required")
    @Column(name = "DELIVERYNAME", nullable = false)
    private String deliveryName;

    @NotBlank(message="Street is required")
    @Column(name = "DELIVERYSTREET", nullable = false)
    private String deliveryStreet;

    @NotBlank(message="City is required")
    @Column(name = "DELIVERYCITY", nullable = false)
    private String deliveryCity;

    @NotBlank(message="State is required")
    @Size(max=2, message = "character of deliveryState should not greater than 2")
    @Column(name = "DELIVERYSTATE", nullable = false)
    private String deliveryState;

    @NotBlank(message="Zip code is required")
    @Size(max=10, message = "character of deliveryZip should not greater than 10")
    @Column(name = "DELIVERYZIP", nullable = false)
    private String deliveryZip;

    @CreditCardNumber(message="Not a valid credit card number")
    @Column(name = "CCNUMBER", nullable = false)
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
          message="Must be formatted MM/YY")
    @Column(name = "CCEXPIRATION", nullable = false)
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    @Column(name = "CCCVV", nullable = false)
    private String ccCVV;

    /** CascadeType.ALL,orphanRemoval, mappedBy, fetch and @JoinColumn:
     * note: mappedBy and @JoinColumn cannot be defined in the same field.
     *       However, for OneToOne, mappedBy and @PrimaryKeyJoinColumn can be used in the same dield.
     *   !!mappedBy should be fulfilled with field name from ManyToOne side(in our case it is parentOrder, it's Case Sensitive)
     * note: if only use @JoinColumn in Taco, then from Taco, Order can be got, but from Order, cannot get tacos,
     *       if use @JoinColumn in Taco and mappedBy in Order, then bidirectional association between Taco and Order
     * learning about orphanRemoval: https://www.baeldung.com/jpa-cascade-remove-vs-orphanremoval
     * learning about JPA cascade: https://www.baeldung.com/jpa-cascade-types
     * learning about JPA joinColumn: https://www.baeldung.com/jpa-join-column
     * learning about OneToOne: https://www.baeldung.com/jpa-one-to-one
     * learning about ManyToMany: https://www.baeldung.com/jpa-many-to-many -> translated to Chinese:https://blog.csdn.net/neweastsun/article/details/103216107
     * OneToMany learning materials:
     * https://stackoverflow.com/questions/13027214/what-is-the-meaning-of-the-cascadetype-all-for-a-manytoone-jpa-association
     * https://www.objectdb.com/java/jpa/persistence/delete#Orphan_Removal
     * difference between mappedBy and @JoinColumn: https://www.baeldung.com/jpa-joincolumn-vs-mappedby
     *                                              https://www.cnblogs.com/chiangchou/p/mappedby.html*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tacoOrder", orphanRemoval = true, fetch = FetchType.EAGER)
    @Size(min=1, message = "You must choose at least 1 ingredient")
//    @JsonManagedReference("tacos")
    private List<Taco> tacos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

    private String orderIdGenerator(){
        return String.valueOf( (long)((Math.random()*9+1)*Math.pow(10,ORDER_ID_LENGTH-1)) );
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
