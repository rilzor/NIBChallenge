import java.io.IOException;
import java.util.Arrays;

public class jsonProcess {
    //Ideally these would be read into a database so I have structured functions with that in mind, but for sample purposes I have just created them as global objects in this function.
    public static Product[] globalProduct = new Product[]{};
    public static Order[] globalOrder = new Order[]{};
    public static void main(String[] args) throws IOException {
        //read in the json file
        JsonStructure jsonData = jsonReader.readJsonFile();
        //if no orders or products by file being not found or not in the file, the function cannot run.
        if (jsonData.Orders == null || jsonData.Products == null){
            System.out.println("Could not find Orders or Products to process");
            return;
        }
        //set the global products & orders data
        globalProduct = jsonData.Products;
        globalOrder = jsonData.Orders;
        int i;

        //get the order ids and then pass them into the process order function
        int[] orderIds = getOrderIds(jsonData.Orders);
        int[] rejectedOrders = processOrders(orderIds);
        for (i=0;i < rejectedOrders.length;i++){
            int rejectOrderId = rejectedOrders[i];
            System.out.println();
            System.out.print("Order ");
            System.out.print(rejectOrderId);
            System.out.print(" Has been rejected due to low stock");
        }

    }

    //extracts an array of order ids from an array of Order objects
    public static int[] getOrderIds(Order[] Orders){
        int[] orderIds = new int[Orders.length];
        int i;
        for (i = 0;i < Orders.length;i++){
            Order order = Orders[i];
            int orderId = order.getOrderId();
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
            //assumption #3 order id cannot be 0 or I would rework to be empty object if nothing found
            //catch for order id not found, it would be rejected
            if (newOrder.getOrderId() == 0){
                rejectedOrders[i] = orderId;
                continue;
            }
            orderArray [i] = newOrder;
        }

        //now we have the order array we loop over each order
        for (i = 0;i < orderArray.length;i++){
            Order orderToProcess = orderArray[i];
            OrderItem[] orderItems = orderToProcess.getItems();
            // make sure there is enough stock to order these items
            // needs to check all together so nothing gets partially processed and needs to be undone in stock on hand
            // Would also add a reserve system to prevent concurrent orders checking stock and then potentially taking stock on hand to negative.
            if(!checkStockLevels(orderItems)){
                rejectedOrders[i] = orderToProcess.getOrderId();
                setOrderStatus(orderToProcess.getOrderId(),"Unfulfillable");
                continue;
            }
            //now loop over each item
            for (x = 0;x < orderItems.length;x++){
                OrderItem orderItem = orderItems[x];
                removeStockOnHand(orderItem.getProductId(), orderItem.getQuantity());
                setOrderStatus(orderToProcess.getOrderId(), "Fulfilled");
            }
            System.out.println();
            System.out.print("Order ");
            System.out.print(orderToProcess.getOrderId());
            System.out.print(" Has been Fulfilled");
        }
        //format array to have no blank entries
        rejectedOrders = Arrays.stream(rejectedOrders).filter(reject -> reject>0).toArray();
        return rejectedOrders;
    }

    //subtract stock for order from stock on hand and issue a purchase order if stock falls below reorder threshold
    public static void removeStockOnHand(int productId, int orderQuantity){
        Product productToUpdate = getProductByProductId(productId);
        int reorderThreshold = productToUpdate.getReorderThreshold();
        int curQuantity = productToUpdate.getQuantityOnHand();
        int newQuantity = curQuantity - orderQuantity;
        productToUpdate.setQuantityOnHand(newQuantity);
        //Assumption #2 if current quantity is already below threshold a Purchase Order should have been issued.
        if (curQuantity >= reorderThreshold && reorderThreshold > newQuantity){
            newPurchaseOrder(productToUpdate.getProductId(), productToUpdate.getReorderAmount());
        }
    }

    //takes an array of orderItems and checks to make sure stock levels will be enough
    //Assumption #1 there is only 1 copy of each product in an order
    public static boolean checkStockLevels(OrderItem[] orderItems){
        boolean validStockLevels = true;
        int i;
        for (i = 0;i < orderItems.length;i++){
            OrderItem curItem = orderItems[i];
            Product curItemProduct = getProductByProductId(curItem.getProductId());
            if (curItem.getQuantity() > curItemProduct.getQuantityOnHand() ){
                validStockLevels = false;
            }
        }
        return validStockLevels;
    }

    //product/order retrieval functions
    public static Product getProductByProductId(int productId){
        //should check for product not found/empty
        int i;
        Product foundProduct = new Product();
        for (i=0;i<globalProduct.length;i++){
            Product searchProduct = globalProduct[i];
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
            if (searchOrder.getOrderId() == orderId){
                foundOrder = searchOrder;
                break;
            }
        }
        return foundOrder;
    }

    //update order status
    public static void setOrderStatus(int orderId, String status){
        Order updateOrder = getOrderByOrderId(orderId);
        updateOrder.setStatus(status);
    }

    //generate purchase order for new stock
    public static void newPurchaseOrder(int productId, int orderQuantity){
        System.out.println();
        System.out.print("Generating Purchase Order for Product ID ");
        System.out.print(productId);
        System.out.print(" for quantity of ");
        System.out.print(orderQuantity);

        //empty example function
    }


}
