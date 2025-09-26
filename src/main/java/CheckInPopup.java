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
        mainLayout.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 20;" +
            "-fx-border-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 25, 0, 0, 8);" +
            "-fx-border-color: #c8e6c9;" +
            "-fx-border-width: 2;"
        );
        
        // Create modern header with icon
        Label headerLabel = new Label("🏥 Patient Check-In");
        headerLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        headerLabel.setStyle("-fx-text-fill: #1b5e20;");
        
        // Create the main message label
        Label messageLabel = new Label("Choose your check-in option:");
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        messageLabel.setStyle("-fx-text-fill: #2e7d32;");
        
        // Create instruction label with modern styling
        Label instructionLabel = new Label("Select whether this is a new patient or an existing patient");
        instructionLabel.setFont(Font.font("Segoe UI", 14));
        instructionLabel.setStyle("-fx-text-fill: #66bb6a;");
        
        // Create New Patient button
        Button newPatientButton = new Button("👤 New Patient");
        newPatientButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        newPatientButton.setPrefSize(200, 50);
        newPatientButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #66bb6a, #4caf50);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 4);"
        );
        
        // Create Existing Patient button
        Button existingPatientButton = new Button("✅ Existing Patient");
        existingPatientButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        existingPatientButton.setPrefSize(200, 50);
        existingPatientButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #ff9800, #f57c00);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 4);"
        );
        
        // Add hover effects for New Patient button
        newPatientButton.setOnMouseEntered(e -> {
            newPatientButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #5cb85c, #449d44);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 25;" +
                "-fx-border-radius: 25;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 18, 0, 0, 6);" +
                "-fx-scale-x: 1.05;" +
                "-fx-scale-y: 1.05;"
            );
        });
        
        newPatientButton.setOnMouseExited(e -> {
            newPatientButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #66bb6a, #4caf50);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 25;" +
                "-fx-border-radius: 25;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 4);" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;"
            );
        });
        
        // Add hover effects for Existing Patient button  
        existingPatientButton.setOnMouseEntered(e -> {
            existingPatientButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #f57c00, #ef6c00);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 25;" +
                "-fx-border-radius: 25;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 18, 0, 0, 6);" +
                "-fx-scale-x: 1.05;" +
                "-fx-scale-y: 1.05;"
            );
        });
        
        existingPatientButton.setOnMouseExited(e -> {
            existingPatientButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff9800, #f57c00);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 25;" +
                "-fx-border-radius: 25;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 4);" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;"
            );
        });
        
        // Set button actions
        newPatientButton.setOnAction(e -> handleNewPatientButtonClick());
        existingPatientButton.setOnAction(e -> handleExistingPatientButtonClick());
        
        // Create modern Cancel button
        Button cancelButton = new Button("❌ Cancel");
        cancelButton.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        cancelButton.setPrefSize(120, 40);
        cancelButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #ef5350, #e53935);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-border-radius: 20;" +
            "-fx-cursor: hand;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
        );
        
        cancelButton.setOnMouseEntered(e -> {
            cancelButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #e53935, #c62828);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 12, 0, 0, 3);" +
                "-fx-scale-x: 1.05;" +
                "-fx-scale-y: 1.05;"
            );
        });
        
        cancelButton.setOnMouseExited(e -> {
            cancelButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ef5350, #e53935);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 20;" +
                "-fx-border-radius: 20;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;"
            );
        });
        
        cancelButton.setOnAction(e -> handleCancelButtonClick());
        
        // Add components to layout with modern spacing
        mainLayout.getChildren().addAll(
            headerLabel,
            messageLabel,
            instructionLabel,
            newPatientButton,
            existingPatientButton,
            cancelButton
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
        
        // Create and set the scene with modern dimensions (increased height for two buttons)
        Scene scene = new Scene(container, 450, 420);
        scene.setFill(null); // Transparent background for rounded corners
        popupStage.setScene(scene);
        
        // Center the popup on screen
        popupStage.centerOnScreen();
    }
    
    /**
     * Handle New Patient button click
     */
    private void handleNewPatientButtonClick() {
        startButtonClicked = true;
        
        // Execute callback if provided (this will open new patient form)
        if (onStartCallback != null) {
            onStartCallback.run();
        }
        
        // Close the popup
        popupStage.close();
    }
    
    /**
     * Handle Existing Patient button click  
     */
    private void handleExistingPatientButtonClick() {
        startButtonClicked = true;
        
        // Open existing patient check-in directly
        ExistingPatientCheckIn existingCheckIn = new ExistingPatientCheckIn();
        existingCheckIn.show();
        
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