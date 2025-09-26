import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Popup window that initiates the patient check-in process.
 * This modal dialog displays a message asking staff to check in the next patient
 * and provides a Start button to begin the check-in workflow.
 */
public class CheckInPopup {
    
    private Stage popupStage;
    private boolean startButtonClicked;
    private Runnable onStartCallback;
    
    /**
     * Constructor to create the check-in popup
     * @param parentStage The parent stage (can be null)
     */
    public CheckInPopup(Stage parentStage) {
        initializePopup(parentStage);
    }
    
    /**
     * Constructor with callback for when Start button is clicked
     * @param parentStage The parent stage (can be null)
     * @param onStartCallback Callback to execute when Start is clicked
     */
    public CheckInPopup(Stage parentStage, Runnable onStartCallback) {
        this.onStartCallback = onStartCallback;
        initializePopup(parentStage);
    }
    
    private void initializePopup(Stage parentStage) {
        this.startButtonClicked = false;
        
        // Create the popup stage
        popupStage = new Stage();
        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Patient Check-In");
        popupStage.setResizable(false);
        
        // Set parent if provided
        if (parentStage != null) {
            popupStage.initOwner(parentStage);
        }
        
        // Create the main layout with modern styling
        VBox mainLayout = new VBox(25);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40, 50, 40, 50));
        mainLayout.getStyleClass().addAll("popup-main", "card");
        
        // Create modern header with icon
        Label headerLabel = new Label("🏥 Patient Check-In Ready");
    headerLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
    headerLabel.getStyleClass().add("popup-title");
        
        // Create the main message label
        Label messageLabel = new Label("Please check in your next patient");
    messageLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
    messageLabel.getStyleClass().add("popup-message");
        
        // Create instruction label with modern styling
        Label instructionLabel = new Label("Click 'Start' to begin the check-in process");
    instructionLabel.setFont(Font.font("Segoe UI", 14));
    instructionLabel.getStyleClass().add("popup-instruction");
        
        // Create modern Start button
        Button startButton = new Button("\ud83d\ude80 Start Check-In");
        startButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        startButton.setPrefSize(200, 50);
        startButton.getStyleClass().addAll("modern-button", "btn-primary");
        
        // Set button action
        startButton.setOnAction(e -> handleStartButtonClick());
        
        // Create modern Cancel button
        Button cancelButton = new Button("\u274c Cancel");
        cancelButton.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        cancelButton.setPrefSize(120, 40);
        cancelButton.getStyleClass().addAll("modern-button", "btn-danger");
        
        cancelButton.setOnAction(e -> handleCancelButtonClick());
        
        // Add components to layout with modern spacing
        mainLayout.getChildren().addAll(
            headerLabel,
            messageLabel,
            instructionLabel,
            startButton,
            cancelButton
        );
        
        // Create container for the rounded border effect
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20));
        container.getStyleClass().addAll("popup-container");
        container.getChildren().add(mainLayout);
        
    // Create and set the scene with modern dimensions
    Scene scene = new Scene(container, 450, 350);
    ThemeManager.registerScene(scene);
    scene.setFill(null); // Transparent background for rounded corners
    popupStage.setScene(scene);
    popupStage.setOnCloseRequest(ev -> ThemeManager.unregisterScene(scene));
        
        // Center the popup on screen
        popupStage.centerOnScreen();
    }
    
    /**
     * Handle Start button click
     */
    private void handleStartButtonClick() {
        startButtonClicked = true;
        
        // Execute callback if provided
        if (onStartCallback != null) {
            onStartCallback.run();
        }
        
        // Close the popup
        popupStage.close();
    }
    
    /**
     * Handle Cancel button click
     */
    private void handleCancelButtonClick() {
        startButtonClicked = false;
        popupStage.close();
    }
    
    /**
     * Show the popup and wait for user interaction
     * @return true if Start was clicked, false if Cancel was clicked or popup was closed
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
     * Check if the Start button was clicked
     * @return true if Start was clicked, false otherwise
     */
    public boolean wasStartButtonClicked() {
        return startButtonClicked;
    }
    
    /**
     * Set the callback to execute when Start button is clicked
     * @param callback The callback to execute
     */
    public void setOnStartCallback(Runnable callback) {
        this.onStartCallback = callback;
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
    public static boolean showCheckInPrompt(Stage parentStage) {
        CheckInPopup popup = new CheckInPopup(parentStage);
        return popup.showAndWait();
    }
    
    /**
     * Static convenience method to show popup with callback
     * @param parentStage The parent stage
     * @param onStartCallback Callback to execute when Start is clicked
     */
    public static void showCheckInPrompt(Stage parentStage, Runnable onStartCallback) {
        CheckInPopup popup = new CheckInPopup(parentStage, onStartCallback);
        popup.show();
    }
}