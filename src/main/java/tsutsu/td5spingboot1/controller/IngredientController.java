package tsutsu.td5spingboot1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsutsu.td5spingboot1.entity.Ingredient;
import tsutsu.td5spingboot1.entity.StockValue;
import tsutsu.td5spingboot1.entity.UnitType;
import tsutsu.td5spingboot1.service.IngredientService;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Integer id) {
        Ingredient ingredient = ingredientService.getIngredientById(id);

        if (ingredient == null) {
            return ResponseEntity
                    .status(404)
                    .body("Ingredient.id=" + id + " is not found");
        }

        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<?> getStock(
            @PathVariable Integer id,
            @RequestParam(required = false) String at,
            @RequestParam(required = false) String unit
    ) {

        if (at == null || unit == null) {
            return ResponseEntity
                    .status(400)
                    .body("Either mandatory query parameter `at` or `unit` is not provided.");
        }

        Instant instant;
        try {
            instant = Instant.parse(at);
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body("Le paramètre `at` doit être au format ISO-8601, ex: 2026-01-02T12:00:00Z");
        }

        UnitType unitType;
        try {
            unitType = UnitType.valueOf(unit.toUpperCase());
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body("Le paramètre `unit` doit être l'une des valeurs : KG, PCS, L");
        }

        StockValue stockValue = ingredientService.getStockValue(id, instant, unitType);
        if (stockValue == null) {
            return ResponseEntity
                    .status(404)
                    .body("Ingredient.id=" + id + " is not found");
        }

        return ResponseEntity.ok(stockValue);
    }
}
