package gui;

import core.FileUtils;
import core.UniversalDataFormat;
import formats.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Map;

public class AppController {

    @FXML
    private Label inputLabel;

    @FXML
    private ComboBox<String> formatCombo;

    @FXML
    private TextArea logArea;

    private File selectedFile;

    @FXML
    public void initialize() {
        formatCombo.getItems().addAll("csv", "json", "txt", "xml");
    }

    @FXML
    public void onChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите входной файл");
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            inputLabel.setText(selectedFile.getName());
            log("Файл выбран: " + selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void onConvert(ActionEvent event) {
        if (selectedFile == null || formatCombo.getValue() == null) {
            log("Выберите файл и формат для конвертации");
            return;
        }

        String inputExt = FileUtils.getExtension(selectedFile.getName());
        String outputFormat = formatCombo.getValue();
        String outputPath = selectedFile.getParent() + File.separator + "converted." + outputFormat;

        UniversalDataFormat data = switch (inputExt) {
            case "csv" -> CSVConverter.read(selectedFile.getAbsolutePath());
            case "json" -> JSONConverter.read(selectedFile.getAbsolutePath());
            case "txt" -> TXTConverter.read(selectedFile.getAbsolutePath());
            case "xml" -> XmlConverter.read(selectedFile.getAbsolutePath());
            default -> null;
        };

        if (data == null || data.getRows().isEmpty()) {
            log("Не удалось прочитать данные из файла");
            return;
        }

        switch (outputFormat) {
            case "csv" -> CSVConverter.write(data, outputPath);
            case "json" -> JSONConverter.write(data, outputPath);
            case "txt" -> TXTConverter.write(data, outputPath);
            case "xml" -> XmlConverter.write(outputPath, data);
            default -> log("Неподдерживаемый формат вывода");
        }

        log("Готово! Сохранено в: " + outputPath);
    }

    private void log(String text) {
        logArea.appendText(text + "\n");
    }
}
