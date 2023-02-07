package com.rafaelovna.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.ArrayList;


@Data
@AllArgsConstructor
public class Recipe {

    private String name;
    private int cookingTimeInMin;
    private Ingredient ingredients;
    private ArrayList<String> steps;



}
