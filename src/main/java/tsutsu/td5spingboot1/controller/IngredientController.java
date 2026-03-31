package tsutsu.td5spingboot1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsutsu.td5spingboot1.entity.*;
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
                    .body("Ingredient.id=" + id + " n'as pas pue etre trouver");
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
                    .body("vous avez oublier les parametres `at` or `unit` .");
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
                    .body("Ingredient.id=" + id + " est introuvable");
        }

        return ResponseEntity.ok(stockValue);
    }

    @GetMapping("/{id}/stockMovements")
    public ResponseEntity<?> getStockMovements(
            @PathVariable Integer id,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        if (from == null || to == null) {
            return ResponseEntity
                    .status(400)
                    .body("vous avez oublier les parametres `from` or `to`.");
        }
        Instant fromInstant;
        Instant toInstant;
        try {
            fromInstant = Instant.parse(from);
            toInstant   = Instant.parse(to);
        } catch (Exception e) {
            return ResponseEntity
                    .status(400)
                    .body("Les paramètres `from` et `to` doivent être au format ISO-8601, ex: 2026-01-02T12:00:00Z");
        }

        List<StockMovement> movements = ingredientService.getStockMovements(id, fromInstant, toInstant);

        if (movements == null) {
            return ResponseEntity
                    .status(404)
                    .body("Ingredient.id=" + id + " n'as pas pue etre trouver");
        }

        return ResponseEntity.ok(movements);
    }

    @PostMapping("/{id}/stockMovements")
    public ResponseEntity<?> addStockMovements(
            @PathVariable Integer id,
            @RequestBody(required = false) List<CreateStockMovement> creates
    ) {
        if (creates == null || creates.isEmpty()) {
            return ResponseEntity
                    .status(400)
                    .body("Le corps de la requête est obligatoire et doit contenir une liste de mouvements.");
        }

        List<StockMovement> saved = ingredientService.addStockMovements(id, creates);

        if (saved == null) {
            return ResponseEntity
                    .status(404)
                    .body("Ingredient.id=" + id + " est introuvable");
        }

        return ResponseEntity.status(201).body(saved);
    }
}
