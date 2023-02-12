package com.rafaelovna.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Recipe {

    private String name;
    private int cookingTimeInMin;
    private List<Ingredient> ingredients;
    private List<String> steps;

}
