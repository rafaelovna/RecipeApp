package com.rafaelovna.recipeapp.services;
import com.rafaelovna.recipeapp.model.Ingredient;

import java.util.Map;
import java.util.Optional;

public interface IngredientService {

    Integer addIngredient(Ingredient ingredient);

    Optional<Ingredient> getIngredientId(int id);

    String getAllIngredient();

    Ingredient editIngredient(int id, Ingredient ingredient);

    boolean deleteIngredient(int id);

    Map<Integer, Ingredient> getAll();

}
