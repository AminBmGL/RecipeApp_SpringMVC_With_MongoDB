package insat.gl.recipies.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import insat.gl.recipies.commands.RecipeCommand;
import insat.gl.recipies.converters.RecipeCommandToRecipe;
import insat.gl.recipies.converters.RecipeToRecipeCommand;
import insat.gl.recipies.domain.Recipe;
import insat.gl.recipies.exceptions.NotFoundException;
import insat.gl.recipies.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Primary
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}
	

	@Override
	public Set<Recipe> getRecipes() {
		 Set<Recipe> recipeSet =new HashSet<>();
		 
		 recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		 return recipeSet;
	}


	@Override
	public Recipe findById(Long l) {
		Optional<Recipe> recipeReturned =recipeRepository.findById(l);
		if (!recipeReturned.isPresent()) {
            throw new NotFoundException("Recipe Not Found for ID " + l.toString());
		}
		return recipeReturned.get();
	}

	 @Override
	    @Transactional
	    public RecipeCommand findCommandById(Long l) {
	        return recipeToRecipeCommand.convert(findById(l));
	    }


	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
	}
	
	@Override
    public void deleteById(Long idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }

}
