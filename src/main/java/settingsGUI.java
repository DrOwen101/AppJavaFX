import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class settingsGUI {
    private final settings setting;
    private Stage stage;

    public settingsGUI() {
        setting = settings.getInstance();
        initializeGUI();
    }

    private void initializeGUI() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Settings");
        stage.setResizable(false);

    
        VBox mainSettings = new VBox(20);
        mainSettings.setPadding(new Insets(20));
        mainSettings.setStyle("-fx-background-color: #f0f9f0;");

        Label titleLabel = new Label("✨Settings :D ✨");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #333;");
        
     
        VBox settingsBox = new VBox(15);
        settingsBox.setPadding(new Insets(20));
        settingsBox.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 5);" +
            "-fx-border-color: #d4edda;" +
            "-fx-border-width: 1;"
        );

        settingsBox.getChildren().add(createSettingsSection());
        HBox buttonBox = createButtonBox();
        mainSettings.getChildren().addAll(titleLabel, settingsBox, buttonBox);

        Scene scene = new Scene(mainSettings, 400, 500);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    private VBox createSettingsSection() {
        VBox section = new VBox(8);
        
        //language settings
        Label langLabel = new Label("🗪Language Options:");
        langLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        ComboBox<String> languageBox = new ComboBox<>();
        languageBox.getItems().addAll(setting.getLanguageOptions());
        languageBox.setValue(setting.getLanguage());
        languageBox.setOnAction(e -> setting.setLanguage(languageBox.getValue()));
        languageBox.setPrefWidth(200);
        languageBox.setStyle("-fx-base: #bbecddff; -fx-background-radius: 8; -fx-border-radius: 8;");

        //accessibility settings
        Label accessLabel = new Label("🚹Accessibility Options:");
        accessLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        CheckBox accessibilityBox = new CheckBox("Enable Accessibility Features");
        accessibilityBox.setSelected(setting.isEnableAccessibilityFeatures());
        accessibilityBox.setOnAction(e -> setting.setEnableAccessibilityFeatures(accessibilityBox.isSelected()));
        accessibilityBox.setStyle("-fx-base: #bbecddff;");
        CheckBox largeTextBox = new CheckBox("Large Text Mode");
        largeTextBox.setSelected(setting.isLargeTextMode());
        largeTextBox.setOnAction(e -> {setting.setLargeTextMode(largeTextBox.isSelected());});
        largeTextBox.setStyle("-fx-base: #bbecddff;");
        
     
        //date and time settings
        Label dateTimeLabel = new Label("🐀Date and Time Format Options:");
        dateTimeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Date format
        HBox dateBox = new HBox(10);
        dateBox.setAlignment(Pos.CENTER_LEFT);
        Label dateLabel = new Label("🗓Date:");
        ComboBox<String> dateCombo = new ComboBox<>();
        dateCombo.getItems().addAll(setting.getDateFormatOptions());
        dateCombo.setValue(setting.getDateFormat());
        dateCombo.setOnAction(e -> setting.setDateFormat(dateCombo.getValue()));
        dateCombo.setPrefWidth(150);
        dateCombo.setStyle("-fx-base: #bbecddff; -fx-background-radius: 8; -fx-border-radius: 8;");
        dateBox.getChildren().addAll(dateLabel, dateCombo);
        
        // Time format
        HBox timeBox = new HBox(10);
        timeBox.setAlignment(Pos.CENTER_LEFT);
        Label timeLabel = new Label("⏰Time:");
        ComboBox<String> timeCombo = new ComboBox<>();
        timeCombo.getItems().addAll(setting.getTimeFormatOptions());
        timeCombo.setValue(setting.getTimeFormat());
        timeCombo.setOnAction(e -> setting.setTimeFormat(timeCombo.getValue()));
        timeCombo.setPrefWidth(150);
        timeCombo.setStyle("-fx-base: #bbecddff; -fx-background-radius: 8; -fx-border-radius: 8;");
        timeBox.getChildren().addAll(timeLabel, timeCombo);

        section.getChildren().addAll(
            langLabel,
            languageBox,
            accessLabel,
            accessibilityBox,
            largeTextBox,
            dateTimeLabel,
            dateLabel,
            dateBox,
            timeLabel,
            timeBox
        );
        return section;
    }

    private HBox createButtonBox() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> stage.close());
        cancelBtn.setStyle("-fx-background-color: #ccc; -fx-text-fill: black;");

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> resetSettings());
        resetBtn.setStyle("-fx-background-color: #db4906ff; -fx-text-fill: white;");

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> saveSettings());
        saveBtn.setStyle("-fx-background-color: #e8a908ff; -fx-text-fill: white;");
        //consistent with the "Orange for save actions, red for cancel/delete" from the color scheme in the README

        buttonBox.getChildren().addAll(cancelBtn, resetBtn, saveBtn);
        return buttonBox;
    }

    private void resetSettings() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Settings");
        alert.setHeaderText("Reset all settings to defaults?");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            setting.resetToDefaults();
            stage.close();
            new settingsGUI().show();
        }
    }

    private void saveSettings() {
        try {
            setting.saveSettings();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Saved");
            alert.setHeaderText("Settings saved successfully!!!");
            alert.showAndWait();
            
            stage.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to save settings: " + e.getMessage());
            alert.showAndWait();
        }
    }
    public void show() {
        stage.show();
    }
}
