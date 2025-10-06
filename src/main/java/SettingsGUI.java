import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.logging.Logger;

public class SettingsGUI {
    private final Stage stage;
    private boolean saved = false;
    private boolean darkThemeSelected = false;
    private boolean notificationsEnabled = true;
    private String selectedLanguage = "English";

    public SettingsGUI(Stage owner) {
        stage = new Stage();
        stage.setTitle("Settings");
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);

        // === Theme Toggle ===
        Label themeLabel = new Label("Theme:");
        ToggleGroup themeGroup = new ToggleGroup();
        RadioButton lightTheme = new RadioButton("Light");
        RadioButton darkTheme = new RadioButton("Dark");
        lightTheme.setToggleGroup(themeGroup);
        darkTheme.setToggleGroup(themeGroup);
        lightTheme.setSelected(true);

        HBox themeBox = new HBox(10, themeLabel, lightTheme, darkTheme);
        themeBox.setAlignment(Pos.CENTER_LEFT);

        // === Notifications ===
        Label notifLabel = new Label("Notifications:");
        CheckBox notifCheckBox = new CheckBox("Enable Notifications");
        notifCheckBox.setSelected(true);

        HBox notifBox = new HBox(10, notifLabel, notifCheckBox);
        notifBox.setAlignment(Pos.CENTER_LEFT);

        // === Save / Cancel Buttons ===
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        saveButton.setOnAction(e -> {
            saved = true;
            darkThemeSelected = darkTheme.isSelected();
            notificationsEnabled = notifCheckBox.isSelected();
            stage.close();
        });

        cancelButton.setOnAction(e -> {
            saved = false;
            stage.close();
        });

        VBox layout = new VBox(20, themeBox, notifBox,  buttonBox);
        layout.setPadding(new Insets(20));
        layout.setPrefWidth(350);

        Scene scene = new Scene(layout, 300, 200);
        ThemeManager.applyTheme(scene);
        stage.setScene(scene);
    }

    public void show() {
        stage.showAndWait();
    }

    // === Getters ===
    public boolean isSaved() { return saved; }
    public boolean isDarkThemeSelected() { return darkThemeSelected; }
    public boolean isNotificationsEnabled() { return notificationsEnabled; }
}