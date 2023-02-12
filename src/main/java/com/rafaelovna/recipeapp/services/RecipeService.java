package com.rafaelovna.recipeapp.services;
import com.rafaelovna.recipeapp.model.Recipe;


public interface RecipeService {

    Integer addNewRecipe(Recipe recipe);

    Recipe getRecipe(int id);

    String getAllRecipe();

    Recipe editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

}
