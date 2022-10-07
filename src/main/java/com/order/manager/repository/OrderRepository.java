package com.order.manager.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.order.manager.config.security.User;
import com.order.manager.model.Order;

public interface OrderRepository 
         extends CrudRepository<Order, String> {
	List<Order> findByUserOrderByPlacedAtDesc(
			User user, Pageable pageable);
}
