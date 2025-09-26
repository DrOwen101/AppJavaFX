import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;

/**
 * Panel component for patient check-in functionality
 */
public class CheckInPanel {
    
    private static final Logger LOGGER = Logger.getLogger(CheckInPanel.class.getName());
    
    private final PatientDataStorage storage;
    private final Runnable onCheckInCompleteCallback;
    private final Runnable onCancelCallback;
    private final Window ownerWindow;
    
    // UI components
    private VBox panel;
    private TextArea reasonForVisitArea;
    private Button checkInButton;
    private Button cancelButton;
    
    // Current patient
    private PatientDataObject currentPatient;
    
    /**
     * Constructor
     * @param storage The patient data storage instance
     * @param onCheckInCompleteCallback Callback when check-in is completed
     * @param onCancelCallback Callback when operation is cancelled
     * @param ownerWindow The owner window for dialogs
     */
    public CheckInPanel(PatientDataStorage storage, 
                       Runnable onCheckInCompleteCallback, 
                       Runnable onCancelCallback,
                       Window ownerWindow) {
        this.storage = storage;
        this.onCheckInCompleteCallback = onCheckInCompleteCallback;
        this.onCancelCallback = onCancelCallback;
        this.ownerWindow = ownerWindow;
        initializePanel();
    }
    
    /**
     * Initialize the check-in panel UI
     */
    private void initializePanel() {
        panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setStyle(UIStyleHelper.getCardStyle());
        
        // Section title
        Label sectionTitle = new Label("✅ Check-In");
        sectionTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        sectionTitle.setStyle(UIStyleHelper.getSectionTitleStyle());
        
        // Reason for visit section
        VBox reasonSection = createReasonForVisitSection();
        
        // Buttons section
        HBox buttonContainer = createButtonSection();
        
        panel.getChildren().addAll(sectionTitle, reasonSection, buttonContainer);
        
        // Initially disable the panel
        setEnabled(false);
    }
    
    /**
     * Create the reason for visit section
     */
    private VBox createReasonForVisitSection() {
        VBox reasonSection = new VBox(10);
        
        Label reasonLabel = new Label("Reason for Visit:");
        reasonLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
        
        reasonForVisitArea = new TextArea();
        reasonForVisitArea.setPromptText("Please describe the reason for today's visit...");
        reasonForVisitArea.setPrefRowCount(3);
        reasonForVisitArea.setWrapText(true);
        reasonForVisitArea.setStyle(UIStyleHelper.getInputFieldStyle());
        
        reasonSection.getChildren().addAll(reasonLabel, reasonForVisitArea);
        return reasonSection;
    }
    
    /**
     * Create the button section
     */
    private HBox createButtonSection() {
        // Check-in button
        checkInButton = UIStyleHelper.createStyledButton(
            "🏥 Complete Check-In", 
            UIStyleHelper.SUCCESS_BUTTON_PRIMARY, 
            UIStyleHelper.SUCCESS_BUTTON_HOVER
        );
        checkInButton.setOnAction(e -> performCheckIn());
        
        // Cancel button
        cancelButton = UIStyleHelper.createStyledButton(
            "❌ Cancel", 
            UIStyleHelper.CANCEL_BUTTON_PRIMARY, 
            UIStyleHelper.CANCEL_BUTTON_HOVER
        );
        cancelButton.setOnAction(e -> onCancelCallback.run());
        
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(checkInButton, cancelButton);
        
        return buttonContainer;
    }
    
    /**
     * Set the current patient for check-in
     */
    public void setCurrentPatient(PatientDataObject patient) {
        this.currentPatient = patient;
        setEnabled(patient != null);
        
        if (patient != null) {
            reasonForVisitArea.requestFocus();
            LOGGER.info("Selected patient for check-in: " + patient.getFullName());
        } else {
            reasonForVisitArea.clear();
        }
    }
    
    /**
     * Enable or disable the check-in panel
     */
    private void setEnabled(boolean enabled) {
        checkInButton.setDisable(!enabled);
        reasonForVisitArea.setDisable(!enabled);
        
        if (!enabled) {
            reasonForVisitArea.clear();
        }
    }
    
    /**
     * Perform the check-in process
     */
    private void performCheckIn() {
        if (currentPatient == null) {
            AlertHelper.showWarning("Selection Error", "Please select a patient first.", ownerWindow);
            return;
        }
        
        String reasonForVisit = reasonForVisitArea.getText().trim();
        if (reasonForVisit.isEmpty()) {
            AlertHelper.showWarning("Input Error", "Please provide a reason for the visit.", ownerWindow);
            return;
        }
        
        try {
            // Update the patient's data with check-in information
            currentPatient.setCurrentSymptoms(reasonForVisit);  // Use current symptoms field
            currentPatient.setAppointmentType("Walk-in Check-in");  // Mark as walk-in
            currentPatient.setAppointmentDateTime(LocalDateTime.now());
            currentPatient.setCheckInComplete(true);
            
            // Save the updated patient data
            boolean saved = storage.savePatientData(currentPatient);
            
            if (saved) {
                String successMessage = String.format(
                    "✅ Check-in completed successfully!\n\n" +
                    "Patient: %s\n" +
                    "Reason for Visit: %s\n" +
                    "Check-in Time: %s", 
                    currentPatient.getFullName(),
                    reasonForVisit,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"))
                );
                
                AlertHelper.showInformation("Check-In Complete", successMessage, ownerWindow);
                
                LOGGER.info("Check-in completed for patient: " + currentPatient.getPatientId());
                onCheckInCompleteCallback.run();
            } else {
                AlertHelper.showError("Save Error", "Failed to save check-in data. Please try again.", ownerWindow);
            }
            
        } catch (Exception e) {
            LOGGER.severe("Error completing check-in: " + e.getMessage());
            AlertHelper.showError("Error", "An error occurred during check-in. Please try again.", ownerWindow);
        }
    }
    
    /**
     * Clear the reason for visit field
     */
    public void clearReasonForVisit() {
        reasonForVisitArea.clear();
    }
    
    /**
     * Get the panel component
     */
    public VBox getPanel() {
        return panel;
    }
}