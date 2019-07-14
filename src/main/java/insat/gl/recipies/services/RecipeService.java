package insat.gl.recipies.services;

import java.util.Set;

import insat.gl.recipies.commands.RecipeCommand;
import insat.gl.recipies.domain.Recipe;

public interface RecipeService {
	Recipe findById(Long l);
	Set<Recipe> getRecipes();
    RecipeCommand findCommandById(Long l);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
    void deleteById(Long idToDelete);

	
}
