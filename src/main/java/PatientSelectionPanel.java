import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Panel component for displaying and selecting patients from search results
 */
public class PatientSelectionPanel {
    
    private static final Logger LOGGER = Logger.getLogger(PatientSelectionPanel.class.getName());
    
    private final Consumer<PatientDataObject> onPatientSelectedCallback;
    
    // UI components
    private VBox panel;
    private ListView<PatientDataObject> patientsListView;
    private PatientDataObject selectedPatient;
    
    /**
     * Constructor
     * @param onPatientSelectedCallback Callback when a patient is selected
     */
    public PatientSelectionPanel(Consumer<PatientDataObject> onPatientSelectedCallback) {
        this.onPatientSelectedCallback = onPatientSelectedCallback;
        initializePanel();
    }
    
    /**
     * Initialize the selection panel UI
     */
    private void initializePanel() {
        panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setStyle(UIStyleHelper.getCardStyle());
        
        // Reasonable panel dimensions
        panel.setPrefHeight(200);
        panel.setPrefWidth(450);
        
        LOGGER.info("PatientSelectionPanel: Panel dimensions set to 450x200");
        
        // Section title
        Label sectionTitle = new Label("📋 Search Results");
        sectionTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        sectionTitle.setStyle(UIStyleHelper.getSectionTitleStyle());
        
        // Patients list view
        patientsListView = new ListView<>();
        patientsListView.setPrefHeight(120); // Smaller but adequate height
        patientsListView.setMinHeight(120);
        patientsListView.setMaxHeight(200);  // Allow some growth
        patientsListView.setPrefWidth(380);
        patientsListView.setMinWidth(350);
        
        // Simple, clean styling
        patientsListView.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #c8e6c9; " +
            "-fx-border-width: 1px; " +
            "-fx-border-radius: 5px;"
        );
        
        // Simple cell factory for patient display
        patientsListView.setCellFactory(listView -> new javafx.scene.control.ListCell<PatientDataObject>() {
            @Override
            protected void updateItem(PatientDataObject patient, boolean empty) {
                super.updateItem(patient, empty);
                
                if (empty || patient == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String displayText = patient.getFullName() + " (DOB: " + 
                                       patient.getDateOfBirth() + ")";
                    setText(displayText);
                    setGraphic(null);
                    
                    // Clean text styling
                    setStyle("-fx-text-fill: #333; -fx-padding: 8px;");
                }
            }
        });
        
        LOGGER.info("PatientSelectionPanel: Created single ListView with optimal dimensions");
        
        // Selection listener
        patientsListView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                selectedPatient = newSelection;
                onPatientSelectedCallback.accept(selectedPatient);
            }
        );
        
        // Add only the necessary components to the panel
        panel.getChildren().addAll(sectionTitle, patientsListView);
    }
    
    /**
     * Update the list with new search results
     */
    public void updateResults(List<PatientDataObject> results) {
        LOGGER.info("PatientSelectionPanel.updateResults called with " + results.size() + " results");
        for (PatientDataObject patient : results) {
            LOGGER.info("  - Received patient: " + patient.getFullName());
        }
        
        // Ensure UI updates happen on JavaFX Application Thread
        javafx.application.Platform.runLater(() -> {
            patientsListView.getItems().clear();
            
            // Try adding simple strings first to test basic ListView functionality
            for (PatientDataObject patient : results) {
                patientsListView.getItems().add(patient);
            }
            
            // Force a refresh of the ListView
            patientsListView.refresh();
            
            selectedPatient = null;
            patientsListView.getSelectionModel().clearSelection();
            
            LOGGER.info("ListView updated with " + patientsListView.getItems().size() + " items");
            
            // Debug ListView properties
            LOGGER.info("ListView visible: " + patientsListView.isVisible());
            LOGGER.info("ListView managed: " + patientsListView.isManaged());
            LOGGER.info("ListView width: " + patientsListView.getWidth());
            LOGGER.info("ListView height: " + patientsListView.getHeight());
            LOGGER.info("ListView prefWidth: " + patientsListView.getPrefWidth());
            LOGGER.info("ListView prefHeight: " + patientsListView.getPrefHeight());
            
            // Debug: Check what's actually in the ListView
            for (int i = 0; i < patientsListView.getItems().size(); i++) {
                PatientDataObject item = patientsListView.getItems().get(i);
                LOGGER.info("ListView item " + i + ": " + item.getFullName());
            }
        });
    }
    
    /**
     * Clear all results
     */
    public void clearResults() {
        patientsListView.getItems().clear();
        selectedPatient = null;
    }
    
    /**
     * Get the currently selected patient
     */
    public PatientDataObject getSelectedPatient() {
        return selectedPatient;
    }
    
    /**
     * Get the panel component
     */
    public VBox getPanel() {
        return panel;
    }
}