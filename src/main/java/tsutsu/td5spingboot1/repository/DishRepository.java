package tsutsu.td5spingboot1.repository;

import org.springframework.stereotype.Repository;
import tsutsu.td5spingboot1.entity.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepository {

    private final DataSource dataSource;
    private final IngredientRepository ingredientRepository;

    public DishRepository(DataSource dataSource, IngredientRepository ingredientRepository) {
        this.dataSource = dataSource;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Dish> findAll() {
        String sql = "SELECT id, name, dish_type, selling_price FROM dish ORDER BY id";
        List<Dish> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Dish dish = mapDish(rs);
                dish.setIngredients(ingredientRepository.findByDishId(dish.getId()));
                list.add(dish);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll dishes", e);
        }
        return list;
    }

    public Dish findById(Integer id) {
        String sql = "SELECT id, name, dish_type, selling_price FROM dish WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Dish dish = mapDish(rs);
                dish.setIngredients(ingredientRepository.findByDishId(dish.getId()));
                return dish;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById dish", e);
        }
    }

    public Dish updateIngredients(Integer dishId, List<Ingredient> requestedIngredients) {
        List<Integer> validIds = new ArrayList<>();
        for (Ingredient req : requestedIngredients) {
            if (req.getId() != null) {
                Ingredient existing = ingredientRepository.findById(req.getId());
                if (existing != null) validIds.add(req.getId());
            }
        }
        String deleteSql = "DELETE FROM dish_ingredient WHERE id_dish = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(deleteSql)) {

            ps.setInt(1, dishId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur delete dish_ingredient", e);
        }

        String insertSql = "INSERT INTO dish_ingredient (id_dish, id_ingredient) VALUES (?, ?) ON CONFLICT DO NOTHING";
        for (Integer ingredientId : validIds) {
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(insertSql)) {

                ps.setInt(1, dishId);
                ps.setInt(2, ingredientId);
                ps.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException("Erreur insert dish_ingredient", e);
            }
        }
        return findById(dishId);
    }

    private Dish mapDish(ResultSet rs) throws SQLException {
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