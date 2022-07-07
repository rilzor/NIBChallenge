import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItem {
    @JsonProperty("orderId")
    int orderId;
    @JsonProperty("productId")
    int productId;

    @JsonProperty("quantity")
    int quantity;
    @JsonProperty("costPerItem")
    int costPerItem;

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCostPerItem() {
        return costPerItem;
    }
}
