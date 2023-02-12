package com.rafaelovna.recipeapp.services.Impl;

import com.rafaelovna.recipeapp.exception.ValidationException;
import com.rafaelovna.recipeapp.model.Recipe;
import com.rafaelovna.recipeapp.services.RecipeService;
import com.rafaelovna.recipeapp.services.ValidationService;
import org.springframework.stereotype.Service;



import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private int id = 0;
    private final Map<Integer, Recipe> recipes = new TreeMap<>();
    private final ValidationService validationService;

    public RecipeServiceImpl(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Integer addNewRecipe(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        recipes.put(id, recipe);
        return id++;
    }

    @Override
    public Optional<Recipe> getRecipe(int id) {
        return Optional.ofNullable(recipes.get(id));
    }

    @Override
    public String getAllRecipe() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Recipe> entries : recipes.entrySet()) {
            stringBuilder.append(entries.getValue().getName());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    @Override
    public Recipe editRecipe(int id, Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
        }
        return recipe;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Recipe> getAll() {
        return recipes;
    }

}
