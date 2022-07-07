
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

import java.io.IOException;

public class jsonReader {
    public static JsonStructure readJsonFile() throws IOException {
        JsonStructure fileObject = new JsonStructure();
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("./src/main/resources/data.json");
        if (jsonFile.exists()){
            InputStream is = new FileInputStream(jsonFile);
            fileObject = mapper.readValue(is, JsonStructure.class);
        }
        else {
            System.out.println("Could not find file");
        }
        return fileObject;
    }
}
