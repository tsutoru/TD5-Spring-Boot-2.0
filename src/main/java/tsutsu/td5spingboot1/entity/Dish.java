package tsutsu.td5spingboot1.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dish {

    private Integer id;
    private String name;
    private DishtypeEnum dishtype;
    private Double sellingPrice;
    private List<Ingredient> ingredients = new ArrayList<>();

    public Dish() {}

    public Dish(Integer id, String name, DishtypeEnum dishtype, Double sellingPrice, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.dishtype = dishtype;
        this.sellingPrice = sellingPrice;
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public DishtypeEnum getDishtype() { return dishtype; }
    public void setDishtype(DishtypeEnum dishtype) { this.dishtype = dishtype; }

    public Double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(Double sellingPrice) { this.sellingPrice = sellingPrice; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish dish)) return false;
        return Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Dish{id=" + id + ", name='" + name + "', sellingPrice=" + sellingPrice + '}';
    }
}
