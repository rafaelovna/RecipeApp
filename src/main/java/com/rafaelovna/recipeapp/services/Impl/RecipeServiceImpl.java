package com.rafaelovna.recipeapp.services.Impl;

import com.rafaelovna.recipeapp.model.Recipe;
import com.rafaelovna.recipeapp.services.RecipeService;
import org.springframework.stereotype.Service;



import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private int id = 0;
    private final Map<Integer, Recipe> recipes = new TreeMap<>();

    @Override
    public Integer addNewRecipe(Recipe recipe) {
        recipes.put(id, recipe);
        return id++;
    }

    @Override
    public Recipe getRecipe(int id) {
        return recipes.get(id);
    }

    @Override
    public String getAllRecipe() {
//        Iterator<Map.Entry<Integer, Recipe>> iterator = recipes.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Integer, Recipe> entry = iterator.next();
//            Integer key = entry.getKey();
//            Recipe value = entry.getValue();
//            System.out.println(key + " " + value);
//        }
//        return iterator.toString();
//    }
 //       }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Recipe> entries : recipes.entrySet()) {
            stringBuilder.append(entries.getValue().getName());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    @Override
    public Recipe editRecipe(int id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (recipes.containsKey(id)) {
            recipes.remove(id);
            return true;
        }
        return false;
    }

}
