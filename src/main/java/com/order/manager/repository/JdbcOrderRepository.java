package com.order.manager.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.manager.model.Order;
import com.order.manager.model.Taco;
import com.order.manager.repository.interfaces.OrderRepository;


@Repository
public class JdbcOrderRepository implements OrderRepository
{
	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;

	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order");
//		      .usingGeneratedKeyColumns("id");

		this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
				.withTableName("Taco_Order_Tacos");

		this.objectMapper = new ObjectMapper();
	}
	@Override
	public Order save(final Order order) {
		order.setPlacedAt(new Date());
		saveOrderDetails(order);

		for (Taco taco : order.getTacos()) {
			saveTacoToOrder(order.getId(), taco.getId());
		}

		return order;
	}

	private void saveOrderDetails(Order order) {
		@SuppressWarnings("unchecked")
		Map<String, Object> values =
				objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());

		orderInserter.execute(values);
	}

	private void saveTacoToOrder(String orderId, String tacoId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrderId", orderId);
		values.put("tacoId", tacoId);
		orderTacoInserter.execute(values);
	}
}
