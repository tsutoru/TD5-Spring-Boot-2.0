package tsutsu.td5spingboot1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tsutsu.td5spingboot1.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository pour la ressource Dish.
 * Contient toutes les requêtes SQL liées aux plats.
 * Remplace les méthodes de plats de l'ancienne classe DataRetriever.
 */
@Repository
public class DishRepository {

    private final JdbcTemplate jdbcTemplate;
    private final IngredientRepository ingredientRepository;

    public DishRepository(JdbcTemplate jdbcTemplate, IngredientRepository ingredientRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Dish> findAll() {
        String sql = "SELECT id, name, dish_type, selling_price FROM dish ORDER BY id";

        return jdbcTemplate.execute((Connection conn) -> {
            List<Dish> list = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Dish dish = mapRow(rs);
                    dish.setIngredients(ingredientRepository.findByDishId(dish.getId()));
                    list.add(dish);
                }
            }
            return list;
        });
    }

    // ---------------------------------------------------------------
    // Recherche un plat par son id (utilisé en interne)
    // ---------------------------------------------------------------
    public Dish findById(Integer id) {
        String sql = "SELECT id, name, dish_type, selling_price FROM dish WHERE id = ?";

        return jdbcTemplate.execute((Connection conn) -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) return null;
                    Dish dish = mapRow(rs);
                    dish.setIngredients(ingredientRepository.findByDishId(dish.getId()));
                    return dish;
                }
            }
        });
    }

    // ---------------------------------------------------------------
    // PUT /dishes/{id}/ingredients  →  remplace les ingrédients du plat
    // ---------------------------------------------------------------
    public Dish updateIngredients(Integer dishId, List<Ingredient> requestedIngredients) {

        // 1. Garder uniquement les ingrédients qui existent réellement en base
        List<Integer> validIds = new ArrayList<>();
        for (Ingredient req : requestedIngredients) {
            if (req.getId() != null) {
                Ingredient existing = ingredientRepository.findById(req.getId());
                if (existing != null) {
                    validIds.add(req.getId());
                }
                // Si l'ingrédient n'existe pas en base → on l'ignore silencieusement
            }
        }

        // 2. Supprimer toutes les associations actuelles du plat
        jdbcTemplate.execute((Connection conn) -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM dish_ingredient WHERE id_dish = ?")) {
                ps.setInt(1, dishId);
                ps.executeUpdate();
            }
            return null;
        });

        // 3. Insérer les nouvelles associations (ids valides uniquement)
        for (Integer ingredientId : validIds) {
            jdbcTemplate.execute((Connection conn) -> {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO dish_ingredient (id_dish, id_ingredient) VALUES (?, ?) ON CONFLICT DO NOTHING")) {
                    ps.setInt(1, dishId);
                    ps.setInt(2, ingredientId);
                    ps.executeUpdate();
                }
                return null;
            });
        }

        // 4. Retourner le plat mis à jour avec sa nouvelle liste d'ingrédients
        return findById(dishId);
    }

    // ---------------------------------------------------------------
    // Mapper ResultSet → Dish
    // ---------------------------------------------------------------
    private Dish mapRow(ResultSet rs) throws SQLException {
        BigDecimal sp = rs.getBigDecimal("selling_price");
        return new Dish(
                rs.getInt("id"),
                rs.getString("name"),
                DishtypeEnum.valueOf(rs.getString("dish_type")),
                sp == null ? null : sp.doubleValue(),
                new ArrayList<>()
        );
    }
}
