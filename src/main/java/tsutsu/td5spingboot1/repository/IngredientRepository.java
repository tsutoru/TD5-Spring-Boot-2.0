package tsutsu.td5spingboot1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tsutsu.td5spingboot1.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository pour la ressource Ingredient.
 * Contient toutes les requêtes SQL liées aux ingrédients.
 * Remplace les méthodes d'ingrédients de l'ancienne classe DataRetriever.
 */
@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    public IngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Ingredient> findAll() {
        String sql = "SELECT id, name, price, category FROM ingredient ORDER BY id";

        return jdbcTemplate.execute((Connection conn) -> {
            List<Ingredient> list = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
            return list;
        });
    }

    public Ingredient findById(Integer id) {
        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";

        return jdbcTemplate.execute((Connection conn) -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) return null;
                    return mapRow(rs);
                }
            }
        });
    }

    public StockValue getStockValueAt(Integer ingredientId, Instant at, UnitType unit) {
        String sql = """
            SELECT
                sm.unit,
                COALESCE(
                    SUM(CASE WHEN sm.type = 'OUT' THEN -sm.quantity ELSE sm.quantity END),
                    0
                ) AS actual_quantity
            FROM stock_movement sm
            WHERE sm.id_ingredient = ?
              AND sm.creation_datetime <= ?
              AND sm.unit = ?
            GROUP BY sm.unit
        """;

        return jdbcTemplate.execute((Connection conn) -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, ingredientId);
                ps.setTimestamp(2, Timestamp.from(at));
                ps.setObject(3, unit.name(), Types.OTHER);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new StockValue(
                                rs.getDouble("actual_quantity"),
                                UnitType.valueOf(rs.getString("unit"))
                        );
                    }
                    // Aucun mouvement trouvé → stock 0
                    return new StockValue(0.0, unit);
                }
            }
        });
    }

    public List<Ingredient> findByDishId(Integer dishId) {
        String sql = """
            SELECT i.id, i.name, i.price, i.category
            FROM dish_ingredient di
            JOIN ingredient i ON i.id = di.id_ingredient
            WHERE di.id_dish = ?
            ORDER BY i.id
        """;

        return jdbcTemplate.execute((Connection conn) -> {
            List<Ingredient> list = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, dishId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapRow(rs));
                    }
                }
            }
            return list;
        });
    }

    private Ingredient mapRow(ResultSet rs) throws SQLException {
        BigDecimal p = rs.getBigDecimal("price");
        return new Ingredient(
                rs.getInt("id"),
                rs.getString("name"),
                p == null ? null : p.doubleValue(),
                CategoryEnum.valueOf(rs.getString("category"))
        );
    }
}
