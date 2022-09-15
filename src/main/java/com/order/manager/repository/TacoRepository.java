package com.order.manager.repository;

import org.springframework.data.repository.CrudRepository;
import com.order.manager.model.Taco;

public interface TacoRepository 
         extends CrudRepository<Taco, String> {

}
