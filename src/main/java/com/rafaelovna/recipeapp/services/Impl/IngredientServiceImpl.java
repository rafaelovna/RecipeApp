package com.rafaelovna.recipeapp.services.Impl;

import com.rafaelovna.recipeapp.exception.ValidationException;
import com.rafaelovna.recipeapp.model.Ingredient;
import com.rafaelovna.recipeapp.services.IngredientService;
import com.rafaelovna.recipeapp.services.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {

    private int id = 0;
    private final Map<Integer, Ingredient> ingredients = new TreeMap<>();
    private final ValidationService validationService;

    public IngredientServiceImpl(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Integer addIngredient(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredients.put(id, ingredient);
        return id++;
    }

    @Override
    public Optional<Ingredient> getIngredientId(int id) {
        return Optional.ofNullable(ingredients.get(id));
    }

    @Override
    public String getAllIngredient() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Ingredient> entries : ingredients.entrySet()) {
            stringBuilder.append(entries.getValue().getName());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public Ingredient editIngredient(int id, Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
        }
        return ingredient;
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Ingredient> getAll() {
        return ingredients;
    }

}
