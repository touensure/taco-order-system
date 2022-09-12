package com.order.manager.repository.interfaces;

import com.order.manager.model.Order;


public interface OrderRepository {
	Order save(Order order);
}
