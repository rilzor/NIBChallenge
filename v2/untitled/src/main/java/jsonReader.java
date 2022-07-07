
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class jsonReader {
    public static JsonStructure readJsonFile() throws IOException {

        ObjectMapper mapper = new ObjectMapper();


        File jsonFile = new File("./src/main/resources/data.json");

        InputStream is = new FileInputStream(jsonFile);

        JsonStructure fileObject = mapper.readValue(is, JsonStructure.class);

        return fileObject;
    }
}
