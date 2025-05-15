package formats;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.UniversalDataFormat;

import java.io.*;
import java.util.*;

public class JSONConverter {

    public static UniversalDataFormat read(String inputJsonPath){
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, String>> data = mapper.readValue(
                    new File(inputJsonPath),
                    mapper.getTypeFactory().constructCollectionType(List.class, Map.class)
            );
            return new UniversalDataFormat(data);
        } catch (Exception e) {
            System.err.println("Ошибка при чтении JSON: " + e.getMessage());
            return new UniversalDataFormat(List.of());
        }
    }

    public static void write(UniversalDataFormat data, String outputJsonPath){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputJsonPath), data.getRows());
            System.out.println("JSON-Файл успешно записан.");
        } catch (Exception e) {
            System.err.println("Ошибка записи JSON: " + e.getMessage());
        }
    }
}

