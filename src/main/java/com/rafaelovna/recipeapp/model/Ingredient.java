package com.rafaelovna.recipeapp.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    private String name;
    private int weight;
    private String measureUnite;

    @Override
    public String toString() {
        return name + " - " + weight + " " + measureUnite;
    }
}
