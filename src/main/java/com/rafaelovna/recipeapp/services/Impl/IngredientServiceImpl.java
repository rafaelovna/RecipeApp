package com.rafaelovna.recipeapp.services.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rafaelovna.recipeapp.exception.ValidationException;
import com.rafaelovna.recipeapp.model.Ingredient;

import com.rafaelovna.recipeapp.services.FileService;
import com.rafaelovna.recipeapp.services.IngredientService;
import com.rafaelovna.recipeapp.services.ValidationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Path;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final FileService fileService;

    private static int id = 1;
    private Map<Integer, Ingredient> ingredients = new TreeMap<>();
    private final ValidationService validationService;


    @Value("${path.to.data.file}")
    private String dataFilePath;


    @Value("${name.of.data.file.ingredient}")
    private String dataFileName;

    private Path ingredientPath;


    @PostConstruct
    private void init() {
        ingredientPath = Path.of(dataFilePath, dataFileName);
        ingredients = fileService.readFromFile(ingredientPath, new TypeReference<>() {
        });
    }

    @Override
    public Integer addIngredient(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredients.put(id++, ingredient);
        fileService.saveToFile(ingredients, ingredientPath);
        return id;
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
            fileService.saveToFile(ingredients, ingredientPath);
            return ingredient;
        }
        return null;
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (ingredients.containsKey(id)) {
            ingredients.remove(id);
            fileService.saveToFile(ingredients, ingredientPath);
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Ingredient> getAll() {
        return ingredients;
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        fileService.uploadFile(file, ingredientPath);
        ingredients = fileService.readFromFile(ingredientPath, new TypeReference<>() {
        });
    }


}
