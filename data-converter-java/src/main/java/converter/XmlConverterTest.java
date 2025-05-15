package converter;

import core.UniversalDataFormat;
import formats.XmlConverter;

import java.io.File;

public class XmlConverterTest {
    public static void main(String[] args) {
        String path = "./src/main/resources/example_valid.xml"; // или абсолютный путь
        System.out.println("Чтение XML-файла: " + path);

        File file = new File(path);
        System.out.println("Файл существует? " + file.exists());
        System.out.println("Размер файла: " + file.length());

        UniversalDataFormat data = XmlConverter.read(path);

        if (data.getRows().isEmpty()) {
            System.out.println("Нет данных после чтения. Что-то пошло не так.");
        } else {
            System.out.println("Прочитано строк: " + data.getRows().size());
            for (var row : data.getRows()) {
                System.out.println(row);
            }
        }
    }
}