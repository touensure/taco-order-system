package com.order.manager.config;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;


@Data
@Validated
@Component
@ConfigurationProperties(prefix = "taco.orders")//fields in this class will get properties defined in application.yaml -> taco.orders according to fields' name(pageSize)
public class OrderProps {
	@Min(value=5, message="must be between 5 and 25")
	@Max(value=25, message="must be between 5 and 25")
	private int pageSize = 20;

}
