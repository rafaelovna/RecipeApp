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

    @GetMapping("/")
    public ResponseEntity<String> getAllIngredient() {
        String allIngredient = ingredientService.getAllIngredient();
        return ResponseEntity.ok(allIngredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.editIngredient(id, ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
