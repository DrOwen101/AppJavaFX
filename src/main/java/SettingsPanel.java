import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A small, self-contained Settings panel that demonstrates clean layout,
 * visual hierarchy, interactive controls, and proper event handling.
 *
 * - Clean layout: header, content area, action row
 * - Interactive elements: ToggleButton, ChoiceBox, Save button
 * - Event handling: binds to Settings singleton and writes to it on Save
 */
public class SettingsPanel {
    private final Stage stage;
    private final Settings settings = Settings.getInstance();

    public SettingsPanel() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Application Settings");

        VBox root = new VBox(16);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f7f9fb);");

        // Header
        Label title = new Label("Settings");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));

        Label subtitle = new Label("Adjust application preferences");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        subtitle.setStyle("-fx-text-fill: #555555;");

        VBox header = new VBox(4, title, subtitle);

        Separator sep = new Separator();

        // Content area
        VBox content = new VBox(12);
        content.setPadding(new Insets(10, 0, 10, 0));

        // Logging toggle
        HBox loggingRow = new HBox(10);
        loggingRow.setAlignment(Pos.CENTER_LEFT);
        Label loggingLabel = new Label("Enable detailed logging");
        loggingLabel.setFont(Font.font("Segoe UI", 14));
        ToggleButton loggingToggle = new ToggleButton();
        loggingToggle.setText(settings.isEnableLogging() ? "On" : "Off");
        loggingToggle.setSelected(settings.isEnableLogging());
        loggingToggle.setOnAction(e -> {
            boolean selected = loggingToggle.isSelected();
            loggingToggle.setText(selected ? "On" : "Off");
        });
        loggingRow.getChildren().addAll(loggingLabel, loggingToggle);

        // Theme choice
        HBox themeRow = new HBox(10);
        themeRow.setAlignment(Pos.CENTER_LEFT);
        Label themeLabel = new Label("Theme");
        themeLabel.setFont(Font.font("Segoe UI", 14));
        ChoiceBox<String> themeChoice = new ChoiceBox<>();
        themeChoice.getItems().addAll("Light", "Dark", "System");
        themeChoice.setValue(settings.getTheme());
        themeRow.getChildren().addAll(themeLabel, themeChoice);

        content.getChildren().addAll(loggingRow, themeRow);

        // Actions
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Button saveBtn = new Button("Save");
        saveBtn.setStyle(
            "-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 8;"
        );
        saveBtn.setOnAction(e -> {
            // Apply changes to Settings singleton (event handling)
            settings.setEnableLogging(loggingToggle.isSelected());
            settings.setTheme(themeChoice.getValue());
            stage.close();
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> stage.close());

        actions.getChildren().addAll(cancelBtn, saveBtn);

        root.getChildren().addAll(header, sep, content, actions);

        Scene scene = new Scene(root, 420, 220);
        stage.setScene(scene);
    }

    public void show() {
        stage.showAndWait();
    }
}
