import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDateTime;

public class Order {
    @JsonProperty("orderId")
    int orderID;
    @JsonProperty("status")
    String status;
    @JsonProperty("dateCreated")
    String dateCreated;
    @JsonProperty("items")
    OrderItems[] items;

    public int getOrderID() {
        return orderID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public OrderItems[] getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
