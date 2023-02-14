package com.rafaelovna.recipeapp.services;

public interface IngredientFileService {

    boolean saveToFile(String json);

    String readFromFile();
}
