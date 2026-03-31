package tsutsu.td5spingboot1.service;

import org.springframework.stereotype.Service;
import tsutsu.td5spingboot1.entity.Ingredient;
import tsutsu.td5spingboot1.entity.StockValue;
import tsutsu.td5spingboot1.entity.UnitType;
import tsutsu.td5spingboot1.repository.IngredientRepository;

import java.time.Instant;
import java.util.List;

/**
 * Service pour la ressource Ingredient.
 * Contient la logique métier liée aux ingrédients.
 * Le Controller appelle le Service, qui appelle le Repository.
 */
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Integer id) {
        return ingredientRepository.findById(id);
    }

    public StockValue getStockValue(Integer ingredientId, Instant at, UnitType unit) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId);
        if (ingredient == null) return null;
        return ingredientRepository.getStockValueAt(ingredientId, at, unit);
    }
}
