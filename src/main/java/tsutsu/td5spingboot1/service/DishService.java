package tsutsu.td5spingboot1.service;

import org.springframework.stereotype.Service;
import tsutsu.td5spingboot1.entity.Dish;
import tsutsu.td5spingboot1.entity.Ingredient;
import tsutsu.td5spingboot1.repository.DishRepository;

import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Dish updateDishIngredients(Integer dishId, List<Ingredient> ingredients) {
        Dish existing = dishRepository.findById(dishId);
        if (existing == null) return null;
        return dishRepository.updateIngredients(dishId, ingredients);
    }
}
