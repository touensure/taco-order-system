package com.order.manager.repository;

import org.springframework.data.repository.CrudRepository;
import com.order.manager.model.Order;

public interface OrderRepository 
         extends CrudRepository<Order, String> {

}
