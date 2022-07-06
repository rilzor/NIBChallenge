import com.fasterxml.jackson.annotation.JsonProperty;

public class Products {
    @JsonProperty("productId")
    int productId;
    @JsonProperty("description")
    String description;
    @JsonProperty("quantityOnHand")
    int quantityOnHand;
    @JsonProperty("reorderThreshold")
    int reorderThreshold;
    @JsonProperty("reorderAmount")
    int reorderAmount;
    @JsonProperty("deliveryLeadTime")
    int deliveryLeadTime;

    public int getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public int getReorderThreshold() {
        return reorderThreshold;
    }

    public int getReorderAmount() {
        return reorderAmount;
    }

    public int getDeliveryLeadTime() {
        return deliveryLeadTime;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }
}
