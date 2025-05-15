package converter;

import com.opencsv.CSVReader;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class CSVConverter {
    public static void convertToJson(String inputCsvPath, String outputJsonPath) {
        try {
            File file = new File(inputCsvPath);
            if (!file.exists() || !file.isFile()) {
                System.err.println("Файл не существует или не является обычным файлом");
                return;
            }

            try (CSVReader reader = new CSVReader(new FileReader(file))) {
                List<String[]> allRows = reader.readAll();

                if (allRows.isEmpty()) {
                    System.out.println("CSV Файл пустой.");
                    return;
                }

                String[] headers = allRows.get(0);
                List<Map<String, String>> datalist = new ArrayList<>();

                for (int i = 1; i < allRows.size(); i++) {
                    String[] row = allRows.get(i);
                    Map<String, String> obj = new LinkedHashMap<>();
                    for (int j = 0; j < headers.length && j < row.length; j++) {
                        obj.put(headers[j], row[j]);
                    }
                    datalist.add(obj);
                }
                ObjectMapper mapper = new ObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputJsonPath), datalist);

                System.out.println("Файл успешно преобразован в JSON. ");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка при конвертации: " + e.getMessage());
        }
    }
}