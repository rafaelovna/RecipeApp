package com.rafaelovna.recipeapp.controller;

import com.rafaelovna.recipeapp.model.Recipe;
import com.rafaelovna.recipeapp.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/recipe")
@Tag(name = "РЕЦЕПТЫ", description = "CRUD операции для работы с рецептами")

public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/")
    @Operation(
            summary = "Добавление рецепта"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт добавлен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует, либо не работает."
            )
    })
    @Parameter(
                    name = "Название",
                    example = "Цезарь"
            )
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) {
        Integer integer = recipeService.addNewRecipe(recipe);
        return ResponseEntity.ok(integer);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение рецепта",
            description = "Получение рецепта по id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт получен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует, либо не работает."
            )
    })
            @Parameter(
                    name = "Идентификатор",
                    example = "1"
            )
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Optional<Recipe> recipe = recipeService.getRecipe(id);
        return ResponseEntity.of(recipe);
    }

    @GetMapping("/")
    @Operation(
            summary = "Получение списка всех рецептов"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список рецептов получен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует, либо не работает."
            )
    })
    public ResponseEntity<String> getAllRecipe() {
        String allRecipe = recipeService.getAllRecipe();
        return ResponseEntity.ok(allRecipe);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование рецепта",
            description = "Редактирование рецепта по id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт отредактирован"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует, либо не работает."
            )
    })
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.editRecipe(id, recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe1);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта",
            description = "Удаление рецепта по id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удален"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует, либо не работает."
            )
    })
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {

        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получение списка рецептов с идентификатором."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список рецептов с идентификатором получен."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует, либо не работает."
            )
    })
    public ResponseEntity<Map<Integer, Recipe>> getAll() {
        return ResponseEntity.ok(recipeService.getAll());
    }
}
