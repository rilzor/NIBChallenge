import com.fasterxml.jackson.databind.ObjectMapper;
public class jsonReader {
    public static void readJsonFile(){
        //JSONParser parser = new JSONParser
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Test.class.getResourceAsStream("/test.json");
        testObj = mapper.readValue(is, Test.class);

        System.out.println("Hello JSON");
    }
}
