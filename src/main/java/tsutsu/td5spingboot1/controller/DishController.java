package tsutsu.td5spingboot1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tsutsu.td5spingboot1.entity.Dish;
import tsutsu.td5spingboot1.entity.Ingredient;
import tsutsu.td5spingboot1.service.DishService;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        List<Dish> dishes = dishService.getAllDishes();
        return ResponseEntity.ok(dishes);
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateDishIngredients(
            @PathVariable Integer id,
            @RequestBody(required = false) List<Ingredient> ingredients
    ) {
        if (ingredients == null) {
            return ResponseEntity
                    .status(400)
                    .body("Le corps de la requête est obligatoire et doit contenir une liste d'ingrédients.");
        }

        Dish updatedDish = dishService.updateDishIngredients(id, ingredients);

        if (updatedDish == null) {
            return ResponseEntity
                    .status(404)
                    .body("Dish.id=" + id + " is not found");
        }

        return ResponseEntity.ok(updatedDish);
    }
}
