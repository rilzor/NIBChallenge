
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;

import java.io.IOException;

public class jsonReader {
    public static JsonStructure readJsonFile() throws IOException {

        ObjectMapper mapper = new ObjectMapper();


        File jsonFile = new File("./src/main/resources/data.json");

        InputStream is = new FileInputStream(jsonFile);

        JsonStructure fileObject = mapper.readValue(is, JsonStructure.class);

        return fileObject;
    }
}
