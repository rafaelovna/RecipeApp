package com.rafaelovna.recipeapp.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    private String name;
    private int cookingTimeInMin;
    private List<Ingredient> ingredients;
    private List<String> steps;


    @Override
    public String toString() {
        return name +
                "\n Время приготовления: " + cookingTimeInMin + " минут.";
    }
}
