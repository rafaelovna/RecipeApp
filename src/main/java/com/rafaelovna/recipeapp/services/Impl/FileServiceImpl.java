package com.rafaelovna.recipeapp.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelovna.recipeapp.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final ObjectMapper objectMapper;


    @Override
    public <T> void saveToFile(Map<Integer, T> map, Path path) {
        try {
            cleanDataFile(path);
            String json = objectMapper.writeValueAsString(map);
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> Map<Integer, T> readFromFile(Path path, TypeReference<HashMap<Integer, T>> typeReference) {
        try {
            String json = Files.readString(path);
            if (json.isEmpty()) {
                return new HashMap<>();
            }
            return objectMapper.readValue(json, typeReference);
        } catch (NoSuchFileException e) {
            return new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadFile(MultipartFile file, Path path) throws IOException {
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);
        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            bis.transferTo(bos);
        }
    }


    @Override
    public void cleanDataFile(Path path) {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Path saveFile(String content, Path path) throws IOException {
        cleanDataFile(path);
        return Files.writeString(path, content);
    }

}



