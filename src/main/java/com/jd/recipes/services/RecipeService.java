package com.jd.recipes.services;

import com.jd.recipes.commands.RecipeCommand;
import com.jd.recipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe findById(Long l);
    void deleteById(long l);

    RecipeCommand findCommandById(Long l);
    RecipeCommand saveRecipeCommand(RecipeCommand command);

}
