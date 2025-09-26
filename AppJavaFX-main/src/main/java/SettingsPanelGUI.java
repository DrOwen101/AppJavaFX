import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * GUI class for displaying and managing patient form data
 */
public class SettingsPanelGUI {
    
    private static final Logger LOGGER = Logger.getLogger(PatientFormGUI.class.getName());
    private boolean darkMode;
    // optional reference to Main to propagate setting changes
    private Main mainApp = null;
    
    private Stage stage;
    ObservableList<String> viewModeList = FXCollections.observableArrayList(
        "Light Mode",
        "Dark Mode"
    );
    private ComboBox<String> viewModeBox;
    
    public SettingsPanelGUI(boolean mode) {
        loadSettings(mode);
        initializeGUI();
    }

    /**
     * Constructor that accepts the Main application instance so changes can be
     * propagated back to the main UI (e.g. toggling dark mode live).
     */
    public SettingsPanelGUI(Main mainApp) {
        this.mainApp = mainApp;
        loadSettings(mainApp != null ? mainApp.darkMode : false);
        initializeGUI();
    }
    
    private void initializeGUI() {
        stage = new Stage();
        stage.setTitle("HealthCare Pro - Settings");
        
        // Create main layout with modern styling
        VBox mainLayout = new VBox(0);
        mainLayout.getStyleClass().add("main-layout");
        
        // Create header section with save button
        HBox headerSection = createModernHeaderSection();
        
        // Create scroll pane for the form with modern styling
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-transparent");
        
        VBox formContent = new VBox(25);
        formContent.setPadding(new Insets(30));
        formContent.getChildren().addAll(
            createModernSettingsBox(),
            createModernButtonSection()
        );
        
        scrollPane.setContent(formContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(600);
        
        // Add header and form to main layout
        mainLayout.getChildren().addAll(headerSection, scrollPane);
        
        // Create scene with modern styling
    Scene scene = new Scene(mainLayout, 900, 750);
    // Register scene for theme updates
    ThemeManager.registerScene(scene);
    stage.setScene(scene);
    stage.setOnCloseRequest(ev -> ThemeManager.unregisterScene(scene));
        stage.centerOnScreen();
    }
    
    /**
     * Create modern header section with title and save button
     */
    private HBox createModernHeaderSection() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(25, 30, 25, 30));
        // Use CSS class for header background so ThemeManager can control it
        header.getStyleClass().add("header");
        
        // Title section
    VBox titleSection = new VBox(5);

    Label titleLabel = new Label("Settings");
    titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
    titleLabel.getStyleClass().add("title-label");

    Label subtitleLabel = new Label("Change settings to your liking");
    subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
    subtitleLabel.getStyleClass().add("subtitle-label");
        
        titleSection.getChildren().addAll(titleLabel, subtitleLabel);
        
        // Create modern "Save and Leave Patient" button
        Button saveAndLeaveButton = createModernSaveButton();
        
        // Add spacer to push save button to the right
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        
        header.getChildren().addAll(titleSection, spacer, saveAndLeaveButton);
        return header;
    }
    
    /**
     * Create modern save button
     */
    private Button createModernSaveButton() {
        Button saveButton = new Button("💾 Save and Close");
        saveButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        saveButton.setPrefSize(200, 45);
        saveButton.getStyleClass().addAll("modern-button", "btn-save");
        
        saveButton.setOnAction(e -> saveAndLeaveSettings());
        return saveButton;
    }
    
    private VBox createModernSettingsBox() {
        VBox section = new VBox(20);
        section.setPadding(new Insets(1));
        section.getStyleClass().addAll("card", "card-section");
        
        // Section header
    Label sectionHeader = new Label("\u2699 Settings");
    sectionHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
    sectionHeader.getStyleClass().add("section-header");
        
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(15, 0, 0, 0));
        
        // Row 0
    viewModeBox = new ComboBox<>(viewModeList);
        if (darkMode == true) {
        viewModeBox.setValue("Dark Mode");
        } else {
        viewModeBox.setValue("Light Mode");
        }
        viewModeBox.setVisibleRowCount(3);
        viewModeBox.getStyleClass().addAll("form-control");
        grid.add(viewModeBox, 0, 0);
        // When the user changes the combobox selection update the local flag
        // but DO NOT persist or propagate it until the user clicks Save.
        viewModeBox.setOnAction(e -> {
            String val = viewModeBox.getValue();
            boolean newDark = "Dark Mode".equals(val);
            this.darkMode = newDark;
            // For preview only: apply a non-persistent preview theme to the settings scene
            if (stage != null && stage.getScene() != null) {
                ThemeManager.previewTheme(stage.getScene(), newDark);
            }
            LOGGER.fine(() -> "Settings preview darkMode=" + newDark);
        });
        return section;
    }
    
    private VBox createModernButtonSection() {
    VBox section = new VBox(20);
    section.setAlignment(Pos.CENTER);
    section.setPadding(new Insets(30, 25, 25, 25));
        
        HBox settingsHBox = new HBox(20);
        settingsHBox.setAlignment(Pos.CENTER_LEFT);

        HBox saveResetBox = new HBox(50);
        saveResetBox.setAlignment(Pos.BOTTOM_LEFT);
        
        // Save Button
        Button saveButton = new Button("💾 Save Settings");
        saveButton.getStyleClass().addAll("modern-button", "btn-primary");
        saveButton.setOnAction(e -> saveSettings());
        
        // Clear Button
        Button clearButton = new Button("🗑️ Reset Settings");
        clearButton.getStyleClass().addAll("modern-button", "btn-clear");
        clearButton.setOnAction(e -> resetSettings());
        
        settingsHBox.getChildren().addAll(viewModeBox);
        saveResetBox.getChildren().addAll(saveButton, clearButton);
        section.getChildren().addAll(settingsHBox, saveResetBox);
        
        return section;
    }

    public boolean saveSettings() {
        // Save settings
        // propagate to global ThemeManager and main only when saving
        ThemeManager.setDarkMode(this.darkMode);
        if (mainApp != null) {
            mainApp.darkMode = this.darkMode;
        }
        showAlert("Success", "Settings saved!");
        LOGGER.info(() -> "Settings saved. darkMode=" + this.darkMode);
        return darkMode;
    }

    public void saveAndLeaveSettings() {
        // Save settings
        saveSettings();
        stage.close();
    }
    
    private void resetSettings() {
        // Reset the UI controls and local preview to defaults, do not persist until Save
        viewModeBox.setValue("Light Mode");
        this.darkMode = false;
        // Update preview only using preview API
        if (stage != null && stage.getScene() != null) {
            ThemeManager.previewTheme(stage.getScene(), false);
        }
        showAlert("Resetting...", "Reset settings to default (NOT SAVED).");
        LOGGER.info("Settings reset locally (not saved).");
    }
    
    private void loadSettings(boolean mode) {
        this.darkMode = mode;
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Create Settings Panel from current data
     */
    
    public void show() {
        stage.show();
    }
    
    public void close() {
        stage.close();
    }
}