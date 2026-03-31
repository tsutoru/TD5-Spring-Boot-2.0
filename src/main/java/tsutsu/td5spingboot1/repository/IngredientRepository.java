package tsutsu.td5spingboot1.repository;

import org.springframework.stereotype.Repository;
import tsutsu.td5spingboot1.entity.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientRepository {

    private final DataSource dataSource;

    public IngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Ingredient> findAll() {
        String sql = "SELECT id, name, price, category FROM ingredient ORDER BY id";
        List<Ingredient> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapIngredient(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll ingredients", e);
        }
        return list;
    }

    public Ingredient findById(Integer id) {
        String sql = "SELECT id, name, price, category FROM ingredient WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapIngredient(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById ingredient", e);
        }
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

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
                return new StockValue(0.0, unit);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur getStockValueAt", e);
        }
    }

    public List<StockMovement> findStockMovementsByIngredientId(Integer ingredientId, Instant from, Instant to) {
        String sql = """
            SELECT id, quantity, type, unit, creation_datetime
            FROM stock_movement
            WHERE id_ingredient = ?
              AND creation_datetime >= ?
              AND creation_datetime <= ?
            ORDER BY creation_datetime
        """;
        List<StockMovement> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ingredientId);
            ps.setTimestamp(2, Timestamp.from(from));
            ps.setTimestamp(3, Timestamp.from(to));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StockValue value = new StockValue(
                            rs.getBigDecimal("quantity").doubleValue(),
                            UnitType.valueOf(rs.getString("unit"))
                    );
                    list.add(new StockMovement(
                            rs.getInt("id"),
                            ingredientId,
                            value,
                            MouvementType.valueOf(rs.getString("type")),
                            rs.getTimestamp("creation_datetime").toInstant()
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findStockMovements", e);
        }
        return list;
    }

    public StockMovement saveStockMovement(Integer ingredientId, CreateStockMovement create) {
        String sql = """
            INSERT INTO stock_movement (id_ingredient, quantity, type, unit, creation_datetime)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id, quantity, type, unit, creation_datetime
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ingredientId);
            ps.setBigDecimal(2, BigDecimal.valueOf(create.getQuantity()));
            ps.setObject(3, create.getType().name(), Types.OTHER);
            ps.setObject(4, create.getUnit().name(), Types.OTHER);
            ps.setTimestamp(5, Timestamp.from(Instant.now()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    StockValue value = new StockValue(
                            rs.getBigDecimal("quantity").doubleValue(),
                            UnitType.valueOf(rs.getString("unit"))
                    );
                    return new StockMovement(
                            rs.getInt("id"),
                            ingredientId,
                            value,
                            MouvementType.valueOf(rs.getString("type")),
                            rs.getTimestamp("creation_datetime").toInstant()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur saveStockMovement", e);
        }
        return null;
    }

    public List<Ingredient> findByDishId(Integer dishId) {
        String sql = """
            SELECT i.id, i.name, i.price, i.category
            FROM dish_ingredient di
            JOIN ingredient i ON i.id = di.id_ingredient
            WHERE di.id_dish = ?
            ORDER BY i.id
        """;
        List<Ingredient> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dishId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapIngredient(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDishId", e);
        }
        return list;
    }

    private Ingredient mapIngredient(ResultSet rs) throws SQLException {
        BigDecimal p = rs.getBigDecimal("price");
        return new Ingredient(
                rs.getInt("id"),
                rs.getString("name"),
                p == null ? null : p.doubleValue(),
                CategoryEnum.valueOf(rs.getString("category"))
        );
    }
}