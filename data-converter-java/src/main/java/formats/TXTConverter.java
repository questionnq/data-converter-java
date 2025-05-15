package formats;

import core.UniversalDataFormat;

import java.io.*;
import java.util.*;

public class TXTConverter {

    /**
     * Чтение TXT-файла в универсальный формат.
     */
    public static UniversalDataFormat read(String inputTxtPath) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputTxtPath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                System.out.println("Файл пустой.");
                return new UniversalDataFormat(dataList);
            }

            String[] headers = headerLine.trim().split("\\s+");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.trim().split("\\s+");
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    row.put(headers[i], values[i]);
                }
                dataList.add(row);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при чтении TXT: " + e.getMessage());
        }

        return new UniversalDataFormat(dataList);
    }

    public static void write(UniversalDataFormat data, String outputTxtPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputTxtPath))) {
            List<Map<String, String>> rows = data.getRows();
            if (rows.isEmpty()) {
                System.out.println("Нет данных для записи. ");
                return;
            }

            Set<String> headers = rows.get(0).keySet();
            writer.write(String.join(" ", headers));
            writer.newLine();

            for (Map<String, String> row : rows) {
                List<String> values = new ArrayList<>();
                for (String key : headers) {
                    values.add(row.getOrDefault(key, ""));
                }
                writer.write(String.join(" ", values));
                writer.newLine();
            }

            System.out.println("Данные успешно записаны в TXT.");
        } catch (Exception e) {
            System.err.println("Ошибка записи в TXT: " + e.getMessage());
        }
    }
}
