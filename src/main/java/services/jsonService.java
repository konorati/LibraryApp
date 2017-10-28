package services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.StringWriter;

public abstract class jsonService {

    public String serializeObject(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw,obj);
            return sw.toString();
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }

    abstract Object mapJsonToObject(String json)throws IOException;

    abstract Boolean isValid(Object obj);
}
