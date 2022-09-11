package com.order.manager.repository;

import com.order.manager.model.Ingredient;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientJDBCRepository {
    Iterable<Ingredient> findAll();

    Ingredient findById(String id);

    void deleteById(String id);

    Ingredient save(Ingredient ingredient);
}
