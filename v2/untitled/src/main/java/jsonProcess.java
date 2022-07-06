

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;


public class jsonProcess {
    public static Products[] globalProducts = new Products[]{};
    public static Order[] globalOrder = new Order[]{};
    public static void main(String[] args) throws IOException {
        //read in the json file
        JsonStructure jsonData = jsonReader.readJsonFile();
        System.out.println("Hello World");

        System.out.println(jsonData.Orders.length);
        System.out.println(jsonData.Products.length);
        //set the global products & orders data
        globalProducts = jsonData.Products;
        globalOrder = jsonData.Orders;
        System.out.println(globalProducts);


        int[] orderIds = getOrderIds(jsonData.Orders);
        processOrders(orderIds);
        //read JSON file
        //return order ids
        // check stock
        //reorder
    }

    public static int[] getOrderIds(Order[] Orders){
        int[] orderIds = new int[Orders.length];
        System.out.println(Orders);
        int i;
        for (i = 0;i < Orders.length;i++){
            Order order = Orders[i];
            int orderId = order.getOrderID();
            orderIds[i] = orderId;
        }
        return orderIds;
    }

    //reorder Orders by date to ensure most recent orders go through first.
    public static int[] processOrders(int[] orderIds){
        int[] rejectedOrders = new int[orderIds.length];
        Order[] orderArray = new Order[orderIds.length];
        int i, x;
        for (i = 0;i < orderIds.length;i++){
            int orderId = orderIds[i];
            Order newOrder = getOrderByOrderId(orderId);
            //check if new order is valid?
            orderArray [i] = newOrder;
        }

        //now we have the order array we loop over each order
        for (i = 0;i < orderArray.length;i++){
            Order orderToProcess = orderArray[i];
            OrderItems[] orderItems = orderToProcess.getItems();
            //make sure there is enough stock to order these items
            if(!checkStockLevels(orderItems)){
                //should find a way to order this
                rejectedOrders[i] = orderToProcess.getOrderID();
                setOrderStatus(orderToProcess.getOrderID(),"Unfulfillable");
                continue;
            };
            //now loop over each item
            for (x = 0;x < orderItems.length;x++){
                OrderItems orderItem = orderItems[x];
                removeStockOnHand(orderItem.getProductId(), orderItem.getQuantity());
                setOrderStatus(orderToProcess.getOrderID(), "Fulfilled");
            }
            System.out.println(orderToProcess.getItems());
        }
        System.out.println("custom built order array");

        return rejectedOrders;
    }

    public static void setOrderStatus(int orderId, String status){
        Order updateOrder = getOrderByOrderId(orderId);
        updateOrder.setStatus(status);
    }

    public static void removeStockOnHand(int productId, int quantity){
        Products productToUpdate = getProductByProductId(productId);
        int curQuantity = productToUpdate.getQuantityOnHand();
        int newQuantity = curQuantity - quantity;
        productToUpdate.setQuantityOnHand(newQuantity);
        if (productToUpdate.getReorderThreshold() > newQuantity){
            newPurchaseOrder(productToUpdate.getProductId(), productToUpdate.getReorderAmount());
        }
    }

    //takes an array of orderItems and checks to make sure stock levels will be enough
    //Assumption #1 there is only 1 copy of each product in an order
    public static boolean checkStockLevels(OrderItems[] orderItems){
        boolean validStockLevels = true;
        int i;
        for (i = 0;i < orderItems.length;i++){
            OrderItems curItem = orderItems[i];
            Products curItemProduct = getProductByProductId(curItem.getProductId());
            if (curItem.getQuantity() > curItemProduct.getQuantityOnHand() ){
                validStockLevels = false;
            }
        }
        return validStockLevels;
    }

    public static Products getProductByProductId(int productId){
        //should check for product not found/empty
        int i;
        Products foundProduct = new Products();
        for (i=0;i<globalProducts.length;i++){
            Products searchProduct = globalProducts[i];
            if (searchProduct.getProductId() == productId){
                foundProduct = searchProduct;
                break;
            }
        }
        return foundProduct;
    }

    public static Order getOrderByOrderId(int orderId){
        //should check for order not found/empty
        int i;
        Order foundOrder = new Order();
        for (i=0;i<globalOrder.length;i++){
            Order searchOrder = globalOrder[i];
            if (searchOrder.getOrderID() == orderId){
                foundOrder = searchOrder;
                break;
            }
        }
        return foundOrder;
    }

    public static void newPurchaseOrder(int productId, int orderQuantity){
        //empty example function
    }


}
