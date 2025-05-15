package converter;
import com.opencsv.bean.CsvConverter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите действие: ");
        System.out.println("1. - CSV -> JSON ");
        System.out.println("2. - JSON -> CSV ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Выберите путь к исходному файлу: ");
        String inputPath = scanner.nextLine();

        System.out.println("Выберите путь для сохранения результата: ");
        String outputPath = scanner.nextLine();

        switch (choice) {
            case 1 -> CSVConverter.convertToJson(inputPath, outputPath);
            case 2 -> JSONConverter.convertToCsv(inputPath, outputPath);
            default -> System.out.println("Неверный выбор. ");
        }

        scanner.close();
    }
}