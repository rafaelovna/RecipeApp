package com.rafaelovna.recipeapp.services.Impl;

import com.rafaelovna.recipeapp.model.Recipe;
import com.rafaelovna.recipeapp.services.RecipeService;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static int id = 0;
    private static Map<Integer, Recipe> recipes = new TreeMap<>();

    @Override
    public Integer addNewRecipe(Recipe recipe) {
        recipes.put(id, recipe);
        return id++;
    }

    @Override
    public Recipe getRecipe(int id) {
        return recipes.get(id);
    }
}
