import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDateTime;

public class Order {
    @JsonProperty("orderId")
    int orderId;
    @JsonProperty("status")
    String status;
    @JsonProperty("dateCreated")
    String dateCreated;
    @JsonProperty("items")
    OrderItem[] items;

    public int getOrderId() {
        return orderId;
    }

    public LocalDateTime getDateCreated() {
        return LocalDateTime.parse(dateCreated);
    }

    public OrderItem[] getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
