package tsutsu.td5spingboot1.entity;

public class CreateStockMovement {

    private UnitType unit;
    private Double quantity;
    private MouvementType type;

    public CreateStockMovement() {}

    public UnitType getUnit() { return unit; }
    public void setUnit(UnitType unit) { this.unit = unit; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public MouvementType getType() { return type; }
    public void setType(MouvementType type) { this.type = type; }
}