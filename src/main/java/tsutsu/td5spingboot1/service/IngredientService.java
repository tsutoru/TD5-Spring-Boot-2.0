package tsutsu.td5spingboot1.service;

import org.springframework.stereotype.Service;
import tsutsu.td5spingboot1.entity.*;
import tsutsu.td5spingboot1.repository.IngredientRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    public List<StockMovement> getStockMovements(Integer ingredientId, Instant from, Instant to) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId);
        if (ingredient == null) return null;
        return ingredientRepository.findStockMovementsByIngredientId(ingredientId, from, to);
    }
    public List<StockMovement> addStockMovements(Integer ingredientId, List<CreateStockMovement> creates) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId);
        if (ingredient == null) return null;

        List<StockMovement> created = new ArrayList<>();
        for (CreateStockMovement create : creates) {
            StockMovement saved = ingredientRepository.saveStockMovement(ingredientId, create);
            if (saved != null) created.add(saved);
        }
        return created;
    }
}
