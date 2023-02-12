package com.rafaelovna.recipeapp.services.Impl;

import com.rafaelovna.recipeapp.model.Ingredient;
import com.rafaelovna.recipeapp.model.Recipe;
import com.rafaelovna.recipeapp.services.ValidationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validate(Recipe recipe) {
        return recipe != null
                && recipe.getName() != null
                && !StringUtils.isEmpty(recipe.getName())
                && recipe.getSteps() != null
                && recipe.getIngredients() != null
                && !recipe.getIngredients().isEmpty()
                && !recipe.getSteps().isEmpty();
    }

    @Override
    public boolean validate(Ingredient ingredient) {
        return ingredient != null
                && ingredient.getName() != null
                && !StringUtils.isEmpty(ingredient.getName())
                && ingredient.getMeasureUnite() != null
                && ingredient.getName().isEmpty()
                && ingredient.getMeasureUnite().isEmpty();
    }
}
