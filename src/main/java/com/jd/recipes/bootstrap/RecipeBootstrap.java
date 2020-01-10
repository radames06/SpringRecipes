package com.jd.recipes.bootstrap;

import com.jd.recipes.domain.*;
import com.jd.recipes.repositories.CategoryRepository;
import com.jd.recipes.repositories.RecipeRepository;
import com.jd.recipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("onApplicationEvent method");
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByUom("Tablespoon");
        if (!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Tablespoon not found");
        }
        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByUom("Teaspoon");
        if (!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Teaspoon not found");
        }
        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByUom("Dash");
        if (!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Dash not found");
        }
        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByUom("Pint");
        if (!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Pint not found");
        }
        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByUom("Cup");
        if (!cupUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM not found");
        }

        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();

        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if (!americanCategoryOptional.isPresent()) {
            throw  new RuntimeException("Expected Category not found");
        }
        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if (!mexicanCategoryOptional.isPresent()) {
            throw  new RuntimeException("Expected Category not found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("blablabla");
        Notes guacNotes = new Notes();
        guacNotes.setNotes("notes de guac");
        guacRecipe.setNotes(guacNotes);
        //guacNotes.setRecipe(guacRecipe);

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), dashUom));
        guacRecipe.addIngredient(new Ingredient("ingr 2", new BigDecimal(5), teaSpoonUom));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);
        recipes.add(guacRecipe);

        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Recette des Tacos");
        tacosRecipe.setPrepTime(40);
        tacosRecipe.setCookTime(4);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setDirections("blabal du Tacos");
        Notes tacosNotes = new Notes();
        tacosNotes.setNotes("Mes notes sur le tacos");
        tacosRecipe.setNotes(tacosNotes);
        //tacosNotes.setRecipe(tacosRecipe);

        tacosRecipe.addIngredient(new Ingredient("farine", new BigDecimal(4), pintUom));
        tacosRecipe.addIngredient(new Ingredient("eau", new BigDecimal(55), tableSpoonUom));

        tacosRecipe.getCategories().add(mexicanCategory);
        recipes.add(tacosRecipe);

        return recipes;

    }
}
