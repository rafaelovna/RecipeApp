package com.rafaelovna.recipeapp.services;
import com.rafaelovna.recipeapp.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;


public interface RecipeService {

    Integer addNewRecipe(Recipe recipe);

    Optional<Recipe> getRecipe(int id);

    Map<Integer, Recipe> getAll();


    String getAllRecipe();

    Recipe editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    File readFile();

    void uploadFile(MultipartFile file) throws IOException;

    File prepareRecipesTxt() throws IOException;
}
