package com.rafaelovna.recipeapp.services;

import com.rafaelovna.recipeapp.model.Ingredient;
import com.rafaelovna.recipeapp.model.Recipe;

public interface ValidationService {

 boolean validate(Recipe recipe);

 boolean validate(Ingredient ingredient);

}
