package com.rafaelovna.recipeapp.services;
import com.rafaelovna.recipeapp.model.Recipe;

import java.util.Map;
import java.util.Optional;


public interface RecipeService {

    Integer addNewRecipe(Recipe recipe);

    Optional<Recipe> getRecipe(int id);

    String getAllRecipe();

    Recipe editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    Map<Integer, Recipe> getAll();

}
