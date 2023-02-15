package com.rafaelovna.recipeapp.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelovna.recipeapp.exception.ValidationException;
import com.rafaelovna.recipeapp.model.Ingredient;
import com.rafaelovna.recipeapp.services.IngredientFileService;
import com.rafaelovna.recipeapp.services.IngredientService;
import com.rafaelovna.recipeapp.services.ValidationService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientFileService ingredientFileService;

    private int id = 0;
    private Map<Integer, Ingredient> ingredients = new TreeMap<>();
    private final ValidationService validationService;

    public IngredientServiceImpl(IngredientFileService ingredientFileService, ValidationService validationService) {
        this.ingredientFileService = ingredientFileService;
        this.validationService = validationService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Integer addIngredient(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredients.put(id, ingredient);
        saveToFile();
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
            saveToFile();
            return ingredient;
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Ingredient> getAll() {
        return ingredients;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            ingredientFileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = ingredientFileService.readFromFile();
            if (json.isEmpty()) {
                System.out.println("Нет сохраненных ингредиентов!");
            }
            ingredients = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
