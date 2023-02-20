package com.rafaelovna.recipeapp.controller;


import com.rafaelovna.recipeapp.services.IngredientService;
import com.rafaelovna.recipeapp.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "ИМПОРТ И ЭКСПОРТ ФАЙЛОВ", description = "Импорт и экспорт ингредиентов и рецептов")
@RequiredArgsConstructor
public class FilesController {



    private final IngredientService ingredientService;
    private final RecipeService recipeService;


    @GetMapping(value = "/export")
    @Operation(summary = "Выгрузка файла рецептов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выгрузка файла рецептов прошла успешно"),
            @ApiResponse(responseCode = "400", description = "Неверный(плохой) запрос!")
    })
    public ResponseEntity<InputStreamResource> downloadDataFile() {
        try {
            File file = recipeService.readFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeLog.json\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла рецептов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Загрузка файла рецептов прошла успешно"),
            @ApiResponse(responseCode = "400", description = "Неверный(плохой) запрос!")
    })
    public ResponseEntity<String> uploadDatafile(@RequestParam MultipartFile file) {
        try {
            recipeService.uploadFile(file);
            return ResponseEntity.ok("Файл успешно импортирован.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при загрузке файла.");
        }
    }


    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла ингредиентов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Загрузка файла ингредиентов прошла успешно"),
            @ApiResponse(responseCode = "400", description = "Неверный(плохой) запрос!")
    })
    public ResponseEntity<String> uploadIngredient(@RequestParam MultipartFile dataFile) {
        try {
            ingredientService.uploadFile(dataFile);
            return ResponseEntity.ok("Файл успешно импортирован.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при загрузке файла.");
        }
    }


    @GetMapping(value = "/export/txt")
    @Operation(summary = "Выгрузка файла рецептов в формате текста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выгрузка файла рецептов прошла успешно"),
            @ApiResponse(responseCode = "400", description = "Неверный(плохой) запрос!")
    })
    public ResponseEntity<InputStreamResource> downloadRecipeTxtFile() {
        try {
            File file = recipeService.prepareRecipesTxt();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeLog.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
