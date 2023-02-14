package com.rafaelovna.recipeapp.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelovna.recipeapp.exception.ValidationException;
import com.rafaelovna.recipeapp.model.Recipe;
import com.rafaelovna.recipeapp.services.FileService;
import com.rafaelovna.recipeapp.services.RecipeService;
import com.rafaelovna.recipeapp.services.ValidationService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final FileService fileService;

    private int id = 0;
    private static Map<Integer, Recipe> recipes = new TreeMap<>();
    private final ValidationService validationService;

    public RecipeServiceImpl(FileService fileService, ValidationService validationService) {
        this.fileService = fileService;
        this.validationService = validationService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Integer addNewRecipe(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        recipes.put(id, recipe);
        saveToFile();
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
            saveToFile();
            return recipe;
        }
        return null;
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


    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
