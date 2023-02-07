package com.rafaelovna.recipeapp.controller;

import com.rafaelovna.recipeapp.model.Ingredient;
import com.rafaelovna.recipeapp.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/")
    public ResponseEntity<Integer> addIngredient(@RequestBody Ingredient ingredient) {
        Integer id = ingredientService.addIngredient(ingredient);
        if (id == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable int id) {
        Ingredient ingredientId = ingredientService.getIngredientId(id);
        if (ingredientId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredientId);
    }

}
