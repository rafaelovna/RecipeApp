package com.rafaelovna.recipeapp.controller;

import com.rafaelovna.recipeapp.model.Ingredient;
import com.rafaelovna.recipeapp.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "ИНГРЕДИЕНТЫ", description = "CRUD операции для работы с ингредиентами")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;


    @PostMapping("/")
    @Operation(
            summary = "Редактирование ингредиентов по id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты отредактированы"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный запрос!"
            )
    })
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredient ingredient) {
        Integer id = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Получение ингредиентов по id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты получены"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует, либо не работает."
            )
    })
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable int id) {
        Optional<Ingredient> ingredientId = ingredientService.getIngredientId(id);
        if (ingredientId.isPresent()) {
            return ResponseEntity.of(ingredientId);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/")
    @Operation(
            summary = "Получение всех ингредиентов."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты получены"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует, либо не работает."
            )
    })
    public ResponseEntity<String> getAllIngredient() {
        String allIngredient = ingredientService.getAllIngredient();
        if (!allIngredient.isEmpty()) {
            return ResponseEntity.ok(allIngredient);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование ингредиентов."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты отредактированы"
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
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.editIngredient(id, ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient1);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиентов по id."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты удалены"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует, либо не работает."
            )
    })
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/all")
    @Operation(
            summary = "Получение ингредиентов с идентификаторами."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Ингредиенты получены"
    )
    public ResponseEntity<Map<Integer, Ingredient>> getAll() {
        return ResponseEntity.ok(ingredientService.getAll());
    }
}
