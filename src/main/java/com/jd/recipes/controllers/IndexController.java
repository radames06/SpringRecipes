package com.jd.recipes.controllers;

import com.jd.recipes.domain.Category;
import com.jd.recipes.domain.UnitOfMeasure;
import com.jd.recipes.repositories.CategoryRepository;
import com.jd.recipes.repositories.UnitOfMeasureRepository;
import com.jd.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@Slf4j
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model) {
        log.debug("getIndexPage method");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
