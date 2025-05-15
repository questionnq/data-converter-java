package converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;

public class JSONConverter {
    public static void convertToCsv(String inputJsonPath, String outputCsvPath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, String>> list = mapper.readValue(
                    new File(inputJsonPath),
                    mapper.getTypeFactory().constructCollectionType(List.class, Map.class)
            );

            if (list.isEmpty()) {
                System.out.println("JSON файл пустой.");
                return;
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter(outputCsvPath))) {
                Set<String> headers = list.get(0).keySet();
                writer.writeNext(headers.toArray(new String[0]));

                for (Map<String, String> obj : list) {
                    List<String> row = new ArrayList<>();
                    for (String key : headers) {
                        row.add(obj.getOrDefault(key, ""));
                    }
                    writer.writeNext(row.toArray(new String[0]));
                }
            }

            System.out.println("Файл успешно преобразован в CSV.");
        } catch (Exception e) {
            System.err.println("Ошибка при конвертации: " + e.getMessage());
        }
    }
}