package com.rafaelovna.recipeapp.services.Impl;

import com.rafaelovna.recipeapp.model.Ingredient;
import com.rafaelovna.recipeapp.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static int id = 0;
    private static Map<Integer, Ingredient> ingredients = new TreeMap<>();


    @Override
    public Integer addIngredient(Ingredient ingredient) {
        ingredients.put(id, ingredient);
        return id++;
    }

    @Override
    public Ingredient getIngredientId(int id) {
        return ingredients.get(id);
    }
}
