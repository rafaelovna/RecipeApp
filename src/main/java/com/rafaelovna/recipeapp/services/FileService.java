package com.rafaelovna.recipeapp.services;

public interface FileService {

    boolean saveToFile(String json);

    String readFromFile();
}
