package com.rafaelovna.recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


public interface FileService {

    <T> void saveToFile(Map<Integer, T> map, Path path);

    <T> Map<Integer, T> readFromFile(Path path, com.fasterxml.jackson.core.type.TypeReference<HashMap<Integer, T>> typeReference);

    void uploadFile(MultipartFile file, Path path) throws IOException;

    void cleanDataFile(Path path);

    Path saveFile(String content, Path path) throws IOException;
}
