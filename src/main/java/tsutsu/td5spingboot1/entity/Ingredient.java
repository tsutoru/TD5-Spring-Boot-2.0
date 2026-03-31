package tsutsu.td5spingboot1.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ingredient {

    private Integer id;
    private String name;
    private Double price;
    private CategoryEnum category;
    private List<StockMovement> stockMovementList = new ArrayList<>();

    public Ingredient() {}

    public Ingredient(Integer id, String name, Double price, CategoryEnum category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Ingredient(Integer id, String name, Double price, CategoryEnum category,
                      List<StockMovement> stockMovementList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        if (stockMovementList != null) this.stockMovementList = stockMovementList;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public CategoryEnum getCategory() { return category; }
    public void setCategory(CategoryEnum category) { this.category = category; }

    public List<StockMovement> getStockMovementList() { return stockMovementList; }
    public void setStockMovementList(List<StockMovement> list) { this.stockMovementList = list; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Ingredient{id=" + id + ", name='" + name + "', price=" + price + ", category=" + category + '}';
    }
}
