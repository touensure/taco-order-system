package com.order.manager.repository;

import org.springframework.data.repository.CrudRepository;
import com.order.manager.model.Ingredient;

public interface IngredientRepository 
         extends CrudRepository<Ingredient, String> {

}
