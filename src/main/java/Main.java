import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private ThemeManager themeManager;
    
    // UI References for theme updates
    private VBox mainLayout;
    private VBox buttonContainer;
    private VBox headerSection;

    @Override
    public void start(Stage primaryStage) {
        // Initialize theme manager
        themeManager = ThemeManager.getInstance();
        // Create main layout with modern styling
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40));
        
        // Apply theme-aware background
        updateMainLayoutStyles(mainLayout);
        
        // Create header section
        VBox headerSection = createHeaderSection();
        
        // Create button container with modern card styling
        VBox buttonContainer = new VBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(30));
        
        // Apply theme-aware card styles
        updateButtonContainerStyles(buttonContainer);
        
        // Create Dark Mode Toggle button
        Button darkModeToggleBtn = createDarkModeToggleButton();
        
        // Create Patient Form button with modern green styling
        Button patientFormBtn = createModernButton(
            "📋 Patient Information Form",
            "#28a745", "#1e7e34", // Primary green colors
            200, 55
        );
        patientFormBtn.setOnAction(e -> {
            LOGGER.info("Opening Patient Form...");
            PatientFormGUI patientFormGUI = new PatientFormGUI();
            patientFormGUI.show();
        });
        
        // Create Patient Check-In button with accent green
        Button checkInBtn = createModernButton(
            "🏥 Start Patient Check-In",
            "#20c997", "#17a085", // Teal green colors
            200, 55
        );
        checkInBtn.setOnAction(e -> {
            LOGGER.info("Starting Patient Check-In workflow...");
            startCheckInWorkflow(primaryStage);
        });
        
        // Create View Saved Patients button with secondary green
        Button viewPatientsBtn = createModernButton(
            "👥 View Saved Patients",
            "#6f42c1", "#563d7c", // Purple accent for variety
            200, 55
        );
        viewPatientsBtn.setOnAction(e -> {
            LOGGER.info("Opening saved patients view...");
            showSavedPatientsDialog(primaryStage);
        });
        
        buttonContainer.getChildren().addAll(darkModeToggleBtn, patientFormBtn, checkInBtn, viewPatientsBtn);
        
        mainLayout.getChildren().addAll(headerSection, buttonContainer);
        
        Scene scene = new Scene(mainLayout, 450, 600); // Increased height for toggle button
        primaryStage.setTitle("HealthCare Pro - Patient Management System");
        primaryStage.setScene(scene);
        
        // Register scene with theme manager
        themeManager.registerScene(scene);
        
        // Store references for theme updates
        storeUIReferences(mainLayout, buttonContainer, headerSection);
        
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    
    /**
     * Create modern header section with theme support
     */
    private VBox createHeaderSection() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        
        // Main title
        Label titleLabel = new Label("HealthCare Pro");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        titleLabel.setStyle(themeManager.getHeaderTextStyles());
        
        // Subtitle
        Label subtitleLabel = new Label("Patient Management System");
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        subtitleLabel.setStyle(themeManager.getSubtitleTextStyles());
        
        // Version/status indicator
        Label versionLabel = new Label("v2.0 • Professional Edition");
        versionLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 12));
        versionLabel.setStyle(themeManager.getVersionTextStyles());
        
        header.getChildren().addAll(titleLabel, subtitleLabel, versionLabel);
        return header;
    }
    
    /**
     * Create a modern styled button with theme support
     */
    private Button createModernButton(String text, String primaryColor, String hoverColor, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
        
        // Apply theme-aware styling
        updateButtonStyles(button, primaryColor, hoverColor);
        
        return button;
    }
    
    /**
     * Update button styles based on current theme
     */
    private void updateButtonStyles(Button button, String primaryColor, String hoverColor) {
        // Base styling
        button.setStyle(themeManager.getButtonStyles(primaryColor, hoverColor));
        
        // Hover effects
        button.setOnMouseEntered(e -> 
            button.setStyle(themeManager.getButtonHoverStyles(hoverColor))
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(themeManager.getButtonStyles(primaryColor, hoverColor))
        );
    }
    
    /**
     * Create dark mode toggle button
     */
    private Button createDarkModeToggleButton() {
        Button toggleBtn = new Button(themeManager.isDarkMode() ? "🔆 Light Mode" : "🌙 Dark Mode");
        toggleBtn.setPrefSize(200, 45);
        toggleBtn.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        
        // Apply theme-aware styling
        String primaryColor = "#6c757d";
        String hoverColor = "#5a6268";
        updateButtonStyles(toggleBtn, primaryColor, hoverColor);
        
        // Toggle action
        toggleBtn.setOnAction(e -> {
            themeManager.toggleTheme();
            toggleBtn.setText(themeManager.isDarkMode() ? "🔆 Light Mode" : "🌙 Dark Mode");
            updateAllUIElements();
        });
        
        return toggleBtn;
    }
    
    /**
     * Update main layout styles based on current theme
     */
    private void updateMainLayoutStyles(VBox layout) {
        if (themeManager.isDarkMode()) {
            layout.setStyle(themeManager.getDarkModeStyles());
        } else {
            layout.setStyle(themeManager.getLightModeStyles());
        }
    }
    
    /**
     * Update button container styles based on current theme
     */
    private void updateButtonContainerStyles(VBox container) {
        container.setStyle(themeManager.getCardStyles());
    }
    
    /**
     * Store UI references for theme updates
     */
    private void storeUIReferences(VBox mainLayout, VBox buttonContainer, VBox headerSection) {
        this.mainLayout = mainLayout;
        this.buttonContainer = buttonContainer;
        this.headerSection = headerSection;
    }
    
    /**
     * Update all UI elements when theme changes
     */
    private void updateAllUIElements() {
        if (mainLayout != null) {
            updateMainLayoutStyles(mainLayout);
        }
        if (buttonContainer != null) {
            updateButtonContainerStyles(buttonContainer);
        }
        if (headerSection != null) {
            updateHeaderStyles();
        }
    }
    
    /**
     * Update header styles based on current theme
     */
    private void updateHeaderStyles() {
        if (headerSection != null) {
            headerSection.getChildren().forEach(node -> {
                if (node instanceof Label) {
                    Label label = (Label) node;
                    String text = label.getText();
                    if (text.contains("HealthCare Pro")) {
                        label.setStyle(themeManager.getHeaderTextStyles());
                    } else if (text.contains("Patient Management System")) {
                        label.setStyle(themeManager.getSubtitleTextStyles());
                    } else if (text.contains("v2.0")) {
                        label.setStyle(themeManager.getVersionTextStyles());
                    }
                }
            });
        }
    }
    
    /**
     * Starts the patient check-in workflow with popup
     */
    private void startCheckInWorkflow(Stage parentStage) {
        // Show the check-in popup first
        CheckInPopup popup = new CheckInPopup(parentStage, () -> {
            // This callback is executed when the Start button is clicked
            LOGGER.info("Check-in popup Start button clicked - opening check-in GUI");
            
            // Create and show the patient check-in GUI
            PatientCheckInGUI checkInGUI = new PatientCheckInGUI();
            checkInGUI.show();
        });
        
        // Show the popup and wait for user interaction
        boolean startClicked = popup.showAndWait();
        
        if (startClicked) {
            LOGGER.info("Check-in workflow initiated successfully");
        } else {
            LOGGER.info("Check-in workflow cancelled by user");
        }
    }
    
    /**
     * Show a dialog with all saved patients
     */
    private void showSavedPatientsDialog(Stage parentStage) {
        PatientDataStorage storage = PatientDataStorage.getInstance();
        
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.initOwner(parentStage);
        alert.setTitle("Saved Patients");
        alert.setHeaderText("Patient Data Storage");
        
        if (storage.isEmpty()) {
            alert.setContentText("No patients have been saved yet.\n\n" +
                "Use 'Open Patient Form' or 'Start Patient Check-In' to add patients, " +
                "then click 'Save and Leave Patient' to save them to storage.");
        } else {
            StringBuilder content = new StringBuilder();
            content.append(storage.getStorageStatistics()).append("\n");
            content.append(storage.getAllPatientsSummary());
            alert.setContentText(content.toString());
        }
        
        // Make the dialog resizable and larger for better readability
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 400);
        
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
