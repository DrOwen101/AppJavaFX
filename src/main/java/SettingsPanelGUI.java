import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SettingsPanelGUI {
    
    private static final Logger LOGGER = Logger.getLogger(SettingsPanelGUI.class.getName());

    private Stage stage;

    public SettingsPanelGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        stage = new Stage();
        stage.setTitle("HealthCare Pro - Settings");

        VBox mainLayout = new VBox(0);
        mainLayout.setStyle(
            "-fx-background: linear-gradient(to bottom, #e8f5e8, #f0f9f0);" // Light green gradient
        );

        HBox headerSection = createModernHeaderSection();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle(
            "-fx-background: transparent;" +
            "-fx-background-color: transparent;"
        );

        VBox formContent = new VBox(25);
        formContent.setPadding(new Insets(30));
        formContent.getChildren().addAll(
            createModernSettings()
        );

        scrollPane.setContent(formContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(600);

        mainLayout.getChildren().addAll(headerSection, scrollPane);

        Scene scene = new Scene(mainLayout, 900, 750);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    private HBox createModernHeaderSection() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(25, 30, 25, 30));
        header.setStyle(
            "-fx-background: linear-gradient(to right, #1b5e20, #2e7d32);" // Dark green gradient
        );
        
        // Create modern "Save and Leave Patient" button
        Button saveAndLeaveButton = createModernSaveButton();
        
        // Add spacer to push save button to the right
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        
        header.getChildren().addAll(spacer, saveAndLeaveButton);
        return header;
    }

    private Button createModernSaveButton() {
        Button saveButton = new Button("💾 Save and Leave Patient");
        saveButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        saveButton.setPrefSize(200, 45);
        saveButton.setStyle(
            "-fx-background-color: #ffc107;" +
            "-fx-text-fill: #1b5e20;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);" +
            "-fx-cursor: hand;"
        );
        
        // Hover effects
        saveButton.setOnMouseEntered(e -> 
            saveButton.setStyle(
                "-fx-background-color: #ffcd39;" +
                "-fx-text-fill: #1b5e20;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 12, 0, 0, 4);" +
                "-fx-cursor: hand;" +
                "-fx-scale-x: 1.02;" +
                "-fx-scale-y: 1.02;"
            )
        );
        
        saveButton.setOnMouseExited(e -> 
            saveButton.setStyle(
                "-fx-background-color: #ffc107;" +
                "-fx-text-fill: #1b5e20;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);" +
                "-fx-cursor: hand;" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;"
            )
        );
        
        saveButton.setOnAction(e -> LOGGER.info("Saving settings..."));
        return saveButton;
    }

    private VBox createModernSettings() {
        VBox section = new VBox(20);
        section.setPadding(new Insets(25));
        section.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 3);" +
            "-fx-border-color: #e8f5e8;" +
            "-fx-border-width: 1;"
        );

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(15, 0, 0, 0));

        String labelStyle = "-fx-text-fill: #2e7d32; -fx-font-weight: bold; -fx-font-size: 14px;";
        
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle(labelStyle);
        grid.add(usernameLabel, 0, 0);
        grid.add(createStyledTextField("Update username"), 1, 0);

        section.getChildren().addAll(grid, createModernPictureSection());
        return section;
    }

    private TextField createStyledTextField(String promptText) {
        TextField field = new TextField();
        field.setPromptText(promptText);
        styleComponent(field);
        return field;
    }

    private void styleComponent(javafx.scene.control.Control component) {
        component.setStyle(
            "-fx-background-color: #f8f9fa;" +
            "-fx-border-color: #c8e6c9;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 10;" +
            "-fx-font-size: 14px;" +
            "-fx-text-fill: #2e7d32;"
        );
        
        // Focus styling
        component.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (newFocus) {
                component.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #4caf50;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-radius: 8;" +
                    "-fx-background-radius: 8;" +
                    "-fx-padding: 10;" +
                    "-fx-font-size: 14px;" +
                    "-fx-text-fill: #2e7d32;" +
                    "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.3), 8, 0, 0, 2);"
                );
            } else {
                component.setStyle(
                    "-fx-background-color: #f8f9fa;" +
                    "-fx-border-color: #c8e6c9;" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 8;" +
                    "-fx-background-radius: 8;" +
                    "-fx-padding: 10;" +
                    "-fx-font-size: 14px;" +
                    "-fx-text-fill: #2e7d32;"
                );
            }
        });
    }

    private VBox createModernPictureSection() {
        VBox section = new VBox(20);
        section.setPadding(new Insets(25));
        section.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 3);" +
            "-fx-border-color: #e8f5e8;" +
            "-fx-border-width: 1;"
        );
        
        // Section header
        Label sectionHeader = new Label("📷 Profile Picture");
        sectionHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        sectionHeader.setStyle("-fx-text-fill: #1b5e20;");
        
        // Picture container
        VBox pictureContainer = new VBox(15);
        pictureContainer.setAlignment(Pos.CENTER);
        
        // Upload button
        Button uploadButton = new Button("📁 Choose Photo");
        uploadButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #66bb6a, #4caf50);" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 12 24;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
        );
        
        // Button hover effect
        uploadButton.setOnMouseEntered(e -> uploadButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #5cb85c, #449d44);" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 12 24;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 12, 0, 0, 4);" +
            "-fx-scale-x: 1.05;" +
            "-fx-scale-y: 1.05;"
        ));
        
        uploadButton.setOnMouseExited(e -> uploadButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #66bb6a, #4caf50);" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 12 24;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);" +
            "-fx-scale-x: 1.0;" +
            "-fx-scale-y: 1.0;"
        ));
        
        uploadButton.setOnAction(e -> LOGGER.info("Selecting profile picture..."));
        
        // Photo info label
        Label photoInfo = new Label("📝 Upload a clear, recent photo (JPG, PNG)");
        photoInfo.setStyle(
            "-fx-text-fill: #6b7280;" +
            "-fx-font-size: 12px;" +
            "-fx-font-style: italic;"
        );
        
        pictureContainer.getChildren().addAll(uploadButton, photoInfo);
        section.getChildren().addAll(sectionHeader, pictureContainer);
        
        return section;
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }
}
