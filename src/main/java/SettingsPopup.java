import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Popup window that contains settings options.
 * This will allow the user to control various cosmetic settings in the application
 * such as theme and font size.
 */
public class SettingsPopup {
    
    private Stage popupStage;
    private boolean startButtonClicked;
    
    /**
     * Constructor to create the settings popup
     * @param parentStage The parent stage (can be null)
     */
    public SettingsPopup(Stage parentStage) {
        initializePopup(parentStage);
    }
    
    private void initializePopup(Stage parentStage) {

        // Create the popup stage
        popupStage = new Stage();
        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Settings");
        popupStage.setResizable(false);
        
        // Set parent if provided
        if (parentStage != null) {
            popupStage.initOwner(parentStage);
        }
        
        // Create the main layout with modern styling
        VBox mainLayout = new VBox(25);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40, 50, 40, 50));
        mainLayout.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 20;" +
            "-fx-border-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 25, 0, 0, 8);" +
            "-fx-border-color: #c8e6c9;" +
            "-fx-border-width: 2;"
        );
        
        // Create modern header with icon
        Label headerLabel = new Label("⚙ Settings");
        headerLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        headerLabel.setStyle("-fx-text-fill: #1b5e20;");
    headerLabel.getStyleClass().add("header-label");
        
        // Create the main message label
        Label messageLabel = new Label("Please check in your next patient");
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        messageLabel.setStyle("-fx-text-fill: #2e7d32;");
        
        // Create instruction label with modern styling
        Label instructionLabel = new Label("Click 'Start' to begin the check-in process");
        instructionLabel.setFont(Font.font("Segoe UI", 14));
        instructionLabel.setStyle("-fx-text-fill: #66bb6a;");
        
        // Create modern Close button
        Button closeButton = new Button("Close");
        closeButton.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        closeButton.setPrefSize(120, 40);
        closeButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #5d5d5dff, #3e3e3eff);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-border-radius: 20;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
        );
        
        closeButton.setOnMouseEntered(e -> {
            closeButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #383838ff, #323232ff);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 12, 0, 0, 3);" +
                "-fx-scale-x: 1.05;" +
                "-fx-scale-y: 1.05;"
            );
        });
        
        closeButton.setOnMouseExited(e -> {
            closeButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #5d5d5dff, #3e3e3eff);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;"
            );
        });
        
        closeButton.setOnAction(e -> handleCloseButtonClick());

        // Theme toggle (Light / Dark)
        ToggleButton themeToggle = new ToggleButton("Dark Mode");
        themeToggle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        themeToggle.setSelected(AppStyleManager.getInstance().isDarkMode());
        themeToggle.setOnAction(e -> {
            boolean dark = themeToggle.isSelected();
            AppStyleManager.getInstance().setDarkMode(dark);
            AppStyleManager.getInstance().applyToScene(popupStage.getScene());
        });

        // Font size choice box (small/normal/large mapped to size variants)
        ChoiceBox<String> fontChoice = new ChoiceBox<>();
        fontChoice.getItems().addAll("Small", "Normal", "Large");
        AppStyleManager.SizeVariant currentVariant = AppStyleManager.getInstance().getSizeVariant();
        switch (currentVariant) {
            case SMALL: fontChoice.setValue("Small"); break;
            case LARGE: fontChoice.setValue("Large"); break;
            default: fontChoice.setValue("Normal"); break;
        }
        fontChoice.setOnAction(e -> {
            String v = fontChoice.getValue();
            AppStyleManager.SizeVariant variant = AppStyleManager.SizeVariant.NORMAL;
            if ("Small".equals(v)) variant = AppStyleManager.SizeVariant.SMALL;
            else if ("Large".equals(v)) variant = AppStyleManager.SizeVariant.LARGE;
            AppStyleManager.getInstance().setSizeVariant(variant);
            // apply live to this popup scene for immediate feedback
            AppStyleManager.getInstance().applyToScene(popupStage.getScene());
        });

        // Apply button
        Button applyButton = new Button("Apply");
        applyButton.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        applyButton.setPrefSize(120, 40);
        applyButton.setOnAction(e -> {
            // Apply settings to the whole app
            AppStyleManager.getInstance().applyGlobally();
        });
        applyButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #5d5d5dff, #3e3e3eff);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-border-radius: 20;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
        );
        applyButton.setOnMouseEntered(e -> {
            applyButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #383838ff, #323232ff);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 12, 0, 0, 3);" +
                "-fx-scale-x: 1.05;" +
                "-fx-scale-y: 1.05;"
            );
        });
        applyButton.setOnMouseExited(e -> {
            applyButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #5d5d5dff, #3e3e3eff);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;"
            );
        });
        
        // Add components to layout with modern spacing
        mainLayout.getChildren().addAll(
            headerLabel,
            messageLabel,
            instructionLabel,
            themeToggle,
            fontChoice,
            applyButton,
            closeButton
        );
        
        // Create container for the rounded border effect
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));
        container.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #e8f5e8, #f1f8e9);" +
            "-fx-background-radius: 25;"
        );
        container.getChildren().add(mainLayout);
        
        // Create and set the scene with modern dimensions
        Scene scene = new Scene(container, 450, 450);
        scene.setFill(null); // Transparent background for rounded corners
        popupStage.setScene(scene);
        
        // Center the popup on screen
        popupStage.centerOnScreen();
    }
    
    
    /**
     * Handle Close button click
     */
    private void handleCloseButtonClick() {
        popupStage.close();
    }
    
    /**
     * Show the popup and wait for user interaction
     * @return true if Start was clicked, false if Close was clicked or popup was closed
     */
    public boolean showAndWait() {
        popupStage.showAndWait();
        return startButtonClicked;
    }
    
    /**
     * Show the popup without blocking
     */
    public void show() {
        popupStage.show();
    }
    
    /**
     * Close the popup
     */
    public void close() {
        popupStage.close();
    }
    
    /**
     * Get the popup stage (useful for positioning or additional configuration)
     * @return The popup stage
     */
    public Stage getPopupStage() {
        return popupStage;
    }
    
    /**
     * Set custom message text
     * @param message The message to display
     */
    public void setMessage(String message) {
        // This would require keeping a reference to the label
        // For now, the message is fixed during initialization
        // Could be enhanced to support dynamic messages
    }
    
    /**
     * Static convenience method to show popup and get result
     * @param parentStage The parent stage
     * @return true if Start was clicked, false otherwise
     */
    public static boolean showSettingsPrompt(Stage parentStage) {
        SettingsPopup popup = new SettingsPopup(parentStage);
        return popup.showAndWait();
    }

}