package com.rafaelovna.recipeapp.services.Impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.rafaelovna.recipeapp.exception.ValidationException;
import com.rafaelovna.recipeapp.model.Ingredient;
import com.rafaelovna.recipeapp.model.Recipe;
import com.rafaelovna.recipeapp.services.FileService;
import com.rafaelovna.recipeapp.services.RecipeService;
import com.rafaelovna.recipeapp.services.ValidationService;
import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final FileService fileService;
    private static int id = 1;
    private Map<Integer, Recipe> recipes = new TreeMap<>();
    private final ValidationService validationService;

    @Value("${path.to.data.file}")
    public String dataFilePath;

    @Value("${name.of.data.file}")
    public String dataFileName;

    @Value("${name.of.recipes.txt.file}")
    private String dataFileNameTxt;

    private Path recipePath;

    @PostConstruct
    private void init() {
        recipePath = Path.of(dataFilePath, dataFileName);
        recipes = fileService.readFromFile(recipePath, new TypeReference<>() {
        });
    }

    @Override
    public Integer addNewRecipe(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        recipes.put(id++, recipe);
        fileService.saveToFile(recipes, recipePath);
        return id;
    }

    @Override
    public Optional<Recipe> getRecipe(int id) {
        return Optional.ofNullable(recipes.get(id));
    }


    @Override
    public Recipe editRecipe(int id, Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            fileService.saveToFile(recipes, recipePath);
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            fileService.saveToFile(recipes, recipePath);
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Recipe> getAll() {
        return recipes;
    }


    @Override
    public String getAllRecipe() {
        StringBuilder stringBuilder = new StringBuilder();
        String list = " • ";
        String list1 = " | ";

        for (Recipe recipe : recipes.values()) {
            stringBuilder.append("\n").append(recipe.toString()).append("\n");
            stringBuilder.append("\nИнгрединты: \n");

            for (Ingredient ingredient : recipe.getIngredients()) {
                stringBuilder.append(list).append(ingredient.toString()).append("\n");
            }

            stringBuilder.append("\nИнструкция приготовления:\n");

            for (String steps : recipe.getSteps()) {
                stringBuilder.append(list1).append("  ").append(steps).append("\n");
            }
        }
        return stringBuilder.append("\n").toString();
    }

    @Override
    public File readFile() {
        return recipePath.toFile();
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        fileService.uploadFile(file, recipePath);
        recipes = fileService.readFromFile(recipePath, new TypeReference<>() {
        });
    }

    @Override
    public File prepareRecipesTxt() throws IOException {
        return fileService
                .saveFile(getAllRecipe(), Path.of(dataFilePath, dataFileNameTxt))
                .toFile();
    }
}
