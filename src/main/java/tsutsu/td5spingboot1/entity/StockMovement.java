package tsutsu.td5spingboot1.entity;

import java.time.Instant;

public class StockMovement {

    private Integer id;
    private Integer ingredientId;
    private StockValue value;
    private MouvementType type;
    private Instant creationDatetime;

    public StockMovement() {}

    public StockMovement(Integer id, Integer ingredientId, StockValue value,
                         MouvementType type, Instant creationDatetime) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.value = value;
        this.type = type;
        this.creationDatetime = creationDatetime;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIngredientId() { return ingredientId; }
    public void setIngredientId(Integer ingredientId) { this.ingredientId = ingredientId; }

    public StockValue getValue() { return value; }
    public void setValue(StockValue value) { this.value = value; }

    public MouvementType getType() { return type; }
    public void setType(MouvementType type) { this.type = type; }

    public Instant getCreationDatetime() { return creationDatetime; }
    public void setCreationDatetime(Instant creationDatetime) { this.creationDatetime = creationDatetime; }
}
