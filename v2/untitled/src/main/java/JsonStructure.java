import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonStructure
{
    @JsonProperty("orders")
    Order[] Orders;
    @JsonProperty("products")
    Products[] Products;
}
