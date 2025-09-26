import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Panel component for patient search functionality
 */
public class PatientSearchPanel {
    
    private static final Logger LOGGER = Logger.getLogger(PatientSearchPanel.class.getName());
    
    private final PatientDataStorage storage;
    private final Consumer<List<PatientDataObject>> onSearchResultsCallback;
    
    // UI components
    private VBox panel;
    private TextField searchNameField;
    private DatePicker dateOfBirthPicker;
    private Button searchButton;
    
    /**
     * Constructor
     * @param storage The patient data storage instance
     * @param onSearchResultsCallback Callback to handle search results
     */
    public PatientSearchPanel(PatientDataStorage storage, Consumer<List<PatientDataObject>> onSearchResultsCallback) {
        this.storage = storage;
        this.onSearchResultsCallback = onSearchResultsCallback;
        LOGGER.info("PatientSearchPanel created with callback: " + (onSearchResultsCallback != null ? "present" : "null"));
        initializePanel();
    }
    
    /**
     * Initialize the search panel UI
     */
    private void initializePanel() {
        panel = new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setStyle(UIStyleHelper.getCardStyle());
        
        // Section title
        Label sectionTitle = new Label("🔍 Patient Search");
        sectionTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        sectionTitle.setStyle(UIStyleHelper.getSectionTitleStyle());
        
        // Search form
        GridPane searchGrid = createSearchForm();
        
        // Search button
        searchButton = UIStyleHelper.createStyledButton(
            "🔍 Search Patients", 
            UIStyleHelper.SEARCH_BUTTON_PRIMARY, 
            UIStyleHelper.SEARCH_BUTTON_HOVER
        );
        searchButton.setOnAction(e -> performSearch());
        
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().add(searchButton);
        
        panel.getChildren().addAll(sectionTitle, searchGrid, buttonContainer);
    }
    
    /**
     * Create the search form grid
     */
    private GridPane createSearchForm() {
        GridPane searchGrid = new GridPane();
        searchGrid.setHgap(15);
        searchGrid.setVgap(15);
        searchGrid.setAlignment(Pos.CENTER_LEFT);
        
        // Name search field
        Label nameLabel = new Label("Patient Name:");
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
        searchNameField = new TextField();
        searchNameField.setPromptText("Enter first or last name");
        searchNameField.setStyle(UIStyleHelper.getInputFieldStyle());
        
        // Date of birth picker
        Label dobLabel = new Label("Date of Birth:");
        dobLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
        dateOfBirthPicker = new DatePicker();
        dateOfBirthPicker.setPromptText("Select date of birth");
        dateOfBirthPicker.setStyle(
            "-fx-padding: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-border-radius: 8;" +
            "-fx-border-color: " + UIStyleHelper.BORDER_GREEN + ";" +
            "-fx-border-width: 2;"
        );
        
        searchGrid.add(nameLabel, 0, 0);
        searchGrid.add(searchNameField, 1, 0);
        searchGrid.add(dobLabel, 0, 1);
        searchGrid.add(dateOfBirthPicker, 1, 1);
        
        return searchGrid;
    }
    
    /**
     * Perform patient search based on input criteria
     */
    private void performSearch() {
        String searchName = searchNameField.getText().trim();
        LocalDate dateOfBirth = dateOfBirthPicker.getValue();
        
        LOGGER.info("Performing search with name: '" + searchName + "' and DOB: " + dateOfBirth);
        
        if (searchName.isEmpty() && dateOfBirth == null) {
            AlertHelper.showWarning("Search Error", "Please enter a name or select a date of birth to search.");
            return;
        }
        
        List<PatientDataObject> results = searchPatients(searchName, dateOfBirth);
        
        LOGGER.info("Search completed. Found " + results.size() + " patients:");
        for (PatientDataObject patient : results) {
            LOGGER.info("  - " + patient.getFullName() + " (DOB: " + patient.getDateOfBirth() + ")");
        }
        
        if (results.isEmpty()) {
            AlertHelper.showWarning("No Results", 
                "No patients found matching your search criteria.\n\n" +
                "Please verify the name and date of birth, or register the patient first using " +
                "'Patient Information Form'.");
        }
        
        // Notify callback with results
        onSearchResultsCallback.accept(results);
        
        LOGGER.info("Search results sent to callback");
    }
    
    /**
     * Search for patients based on criteria
     */
    private List<PatientDataObject> searchPatients(String searchName, LocalDate dateOfBirth) {
        List<PatientDataObject> results;
        
        if (!searchName.isEmpty() && dateOfBirth != null) {
            // Search by both name and DOB
            List<PatientDataObject> nameResults = storage.findPatientsByName(searchName);
            results = nameResults.stream()
                .filter(patient -> dateOfBirth.equals(patient.getDateOfBirth()))
                .collect(java.util.stream.Collectors.toList());
        } else if (!searchName.isEmpty()) {
            // Search by name only
            results = storage.findPatientsByName(searchName);
        } else {
            // Search by DOB only
            results = storage.findPatientsByDateOfBirth(dateOfBirth);
        }
        
        return results;
    }
    
    /**
     * Clear search fields
     */
    public void clearSearch() {
        searchNameField.clear();
        dateOfBirthPicker.setValue(null);
    }
    
    /**
     * Get the panel component
     */
    public VBox getPanel() {
        return panel;
    }
    
    /**
     * Focus on the name search field
     */
    public void focusOnNameField() {
        searchNameField.requestFocus();
    }
}