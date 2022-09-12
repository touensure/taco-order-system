package com.order.manager.repository.interfaces;

import com.order.manager.model.Ingredient;
import org.springframework.stereotype.Repository;

public interface IngredientRepository
{
    Iterable<Ingredient> findAll();

    Ingredient findById(String id);

    void deleteById(String id);

    Ingredient save(Ingredient ingredient);
}
