package formats;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import core.UniversalDataFormat;

import java.io.*;
import java.util.*;

public class CSVConverter {
    public static UniversalDataFormat read(String inputCsvPath) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(inputCsvPath))) {
            List<String[]> allRows = reader.readAll();

            if (allRows.isEmpty()) {
                System.out.println("CSV-Файл пуст.");
                return new UniversalDataFormat(dataList);
            }

            String[] headers = allRows.get(0);
            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                Map<String, String> map = new LinkedHashMap<>();
                for (int j = 0; j < headers.length && j < row.length; j++) {
                    map.put(headers[j], row[j]);
                }
                dataList.add(map);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при чтении CSV: " + e.getMessage());
        }

        return new UniversalDataFormat(dataList);
    }

    public static void write(UniversalDataFormat data, String outputCsvPath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputCsvPath))) {
            List<Map<String, String>> rows = data.getRows();

            if (rows.isEmpty()) {
                System.out.println("Нет данных для записи. ");
                return;
            }

            Set<String> headers = rows.get(0).keySet();
            writer.writeNext(headers.toArray(new String[0]));

            for (Map<String, String> row : rows) {
                List<String> values = new ArrayList<>();
                for (String key : headers) {
                    values.add(row.getOrDefault(key, ""));
                }
                writer.writeNext(values.toArray(new String[0]));
            }

            System.out.println("CSV-Файл успехно сохранен.");
        } catch (Exception e) {
            System.err.println("Ошибка при записи CSV: " + e.getMessage());
        }
    }
}