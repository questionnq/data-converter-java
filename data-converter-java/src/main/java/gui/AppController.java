package gui;

import core.FileUtils;
import core.UniversalDataFormat;
import formats.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
public class AppController {

    @FXML
    private Label inputLabel;

    @FXML
    private ComboBox<String> formatCombo;

    @FXML
    private VBox rootVBox;

    @FXML
    private TextArea outputNameField;

    @FXML
    private TextArea logArea;

    private File selectedFile;
    private File saveDirectory;

    @FXML
    public void initialize() {
        formatCombo.getItems().addAll("csv", "json", "txt", "xml");

        // Drag-and-drop
        rootVBox.setOnDragOver(event -> {
            if (event.getGestureSource() != rootVBox && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        rootVBox.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                selectedFile = db.getFiles().get(0);
                inputLabel.setText(selectedFile.getName());
                log("📥 Файл загружен через drag-and-drop: " + selectedFile.getAbsolutePath());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
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

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Выберите папку для сохранения");
        saveDirectory = chooser.showDialog(null);
        if (saveDirectory == null) {
            log("⚠ Папка для сохранения не выбрана");
            return;
        }

        String outputName = outputNameField.getText().trim();
        if (outputName.isEmpty()) {
            log("Введите имя выходного файла");
            return;
        }

        String outputPath = saveDirectory.getAbsolutePath() + File.separator + outputName + "." + outputFormat;

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

        log("[" + LocalDateTime.now() + "]" + "Конвертация: " + inputExt + " -> " + outputFormat);
        log("Готово! Сохранено в: " + outputPath);
    }

    private void log(String text) {
        logArea.appendText(text + "\n");
    }
}
