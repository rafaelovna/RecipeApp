package com.rafaelovna.recipeapp.services;
import com.rafaelovna.recipeapp.model.Ingredient;

public interface IngredientService {

    Integer addIngredient(Ingredient ingredient);

    Ingredient getIngredientId(int id);
}
