package com.rafaelovna.recipeapp.services;
import com.rafaelovna.recipeapp.model.Ingredient;

public interface IngredientService {

    Integer addIngredient(Ingredient ingredient);

    Ingredient getIngredientId(int id);

    String getAllIngredient();

    Ingredient editIngredient(int id, Ingredient ingredient);

    boolean deleteIngredient(int id);

}
