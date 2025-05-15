package converter;

import core.FileUtils;
import core.UniversalDataFormat;
import formats.CSVConverter;
import formats.JSONConverter;
import formats.TXTConverter;
import formats.XmlConverter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите путь к исходному файлу: ");
        String inputPath = scanner.nextLine();
        String inputExt = FileUtils.getExtension(inputPath);

        UniversalDataFormat data = switch(inputExt){
            case "csv" -> CSVConverter.read(inputPath);
            case "txt" -> TXTConverter.read(inputPath);
            case "json" -> JSONConverter.read(inputPath);
            case "xml" -> XmlConverter.read(inputPath);

            default -> {
                System.out.println("Неподдерживаемый формат: " + inputExt);
                yield null;
            }
        };

        if (data == null) return;

        System.out.println("Выберите формат для конвертирования: CSV/JSON/TXT/XML ");
        String outputFormat = scanner.nextLine().trim().toLowerCase();

        System.out.println("Выберите путь для сохранения результата: ");
        String outputPath = scanner.nextLine().trim();

        if (!outputPath.toLowerCase().endsWith("." + outputFormat)) {
            outputPath += "." + outputFormat;
        }


        switch(outputFormat) {
            case "csv" -> CSVConverter.write(data, outputPath);
            case "json" -> JSONConverter.write(data, outputPath);
            case "txt" -> TXTConverter.write(data, outputPath);
            case "xml" -> XmlConverter.write(outputPath, data);
            default -> System.out.println("Неподдерживаемый выходной формат: " + outputFormat);
        }

        scanner.close();
    }
}