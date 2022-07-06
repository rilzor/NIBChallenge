

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;


public class jsonProcess {

    public static void main(String[] args) throws IOException {
        JsonStructure jsonData = jsonReader.readJsonFile();
        System.out.println("Hello World");

        System.out.println(jsonData.Orders.length);
        //System.out.println(jsonData.Products);


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

    public static int[] processOrders(int[] orderIds){
        int[] returnValue = new int[]{};



        return returnValue;
    }

    public static void getOrderDetails(int[] orderId){

    }


}
