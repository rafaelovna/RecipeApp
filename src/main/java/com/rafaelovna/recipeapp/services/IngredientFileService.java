package com.rafaelovna.recipeapp.services;

import java.io.File;

public interface IngredientFileService {

    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();

    File getDataFile();
}
