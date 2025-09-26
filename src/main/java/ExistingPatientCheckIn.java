import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main coordinator class for existing patient check-in process.
 * This class orchestrates the patient search, selection, and check-in workflow
 * using modular panel components.
 */
public class ExistingPatientCheckIn {
    
    private static final Logger LOGGER = Logger.getLogger(ExistingPatientCheckIn.class.getName());
    
    private Stage stage;
    private final PatientDataStorage storage;
    
    // Panel components
    private PatientSearchPanel searchPanel;
    private PatientSelectionPanel selectionPanel;
    private CheckInPanel checkInPanel;
    
    /**
     * Constructor
     */
    public ExistingPatientCheckIn() {
        this.storage = PatientDataStorage.getInstance();
        LOGGER.info("ExistingPatientCheckIn constructor called");
        initializeGUI();
    }
    
    /**
     * Initialize the GUI and coordinate panel interactions
     */
    private void initializeGUI() {
        stage = new Stage();
        stage.setTitle("Existing Patient Check-In");
        stage.initModality(Modality.APPLICATION_MODAL);
        
        // Main layout with modern styling
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(25));
        mainLayout.setStyle(UIStyleHelper.getMainBackgroundStyle());
        
        // Create header section
        VBox headerSection = createHeaderSection();
        
        // Create and wire up the panels
        createPanels();
        
        mainLayout.getChildren().addAll(
            headerSection, 
            searchPanel.getPanel(), 
            selectionPanel.getPanel(), 
            checkInPanel.getPanel()
        );
        
        // Wrap the main layout in a ScrollPane for scrolling
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // No horizontal scrollbar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scrollbar as needed
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        Scene scene = new Scene(scrollPane, 700, 650);
        stage.setScene(scene);
        stage.centerOnScreen();
        
        // Focus on search field when window opens
        stage.setOnShown(e -> searchPanel.focusOnNameField());
    }
    
    /**
     * Create header section
     */
    private VBox createHeaderSection() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        
        // Title
        Label titleLabel = new Label("🏥 Existing Patient Check-In");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #1b5e20;");
        
        // Subtitle
        Label subtitleLabel = new Label("Search for existing patients and check them in quickly");
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subtitleLabel.setStyle("-fx-text-fill: #388e3c;");
        
        header.getChildren().addAll(titleLabel, subtitleLabel);
        return header;
    }
    
    /**
     * Create and wire up all the panel components
     */
    private void createPanels() {
        LOGGER.info("Creating panels...");
        
        // Create search panel with callback for results
        searchPanel = new PatientSearchPanel(storage, results -> {
            LOGGER.info("Search callback triggered with " + results.size() + " results");
            selectionPanel.updateResults(results);
            checkInPanel.setCurrentPatient(null); // Clear any previous selection
        });
        
        // Create selection panel with callback for patient selection
        selectionPanel = new PatientSelectionPanel(selectedPatient -> {
            LOGGER.info("Selection callback triggered with patient: " + 
                (selectedPatient != null ? selectedPatient.getFullName() : "null"));
            checkInPanel.setCurrentPatient(selectedPatient);
        });
        
        // Create check-in panel with callbacks for completion and cancellation
        checkInPanel = new CheckInPanel(
            storage,
            this::handleCheckInComplete,  // On successful check-in
            this::handleCancel,           // On cancel
            stage                         // Owner window for dialogs
        );
        
        LOGGER.info("All panels created successfully");
    }
    
    /**
     * Handle successful check-in completion
     */
    private void handleCheckInComplete() {
        LOGGER.info("Check-in process completed successfully");
        stage.close();
    }
    
    /**
     * Handle cancellation
     */
    private void handleCancel() {
        LOGGER.info("Check-in process cancelled by user");
        stage.close();
    }
    
    /**
     * Show the dialog
     */
    public void show() {
        stage.show();
    }
    
    /**
     * Show the dialog and wait for user interaction
     */
    public void showAndWait() {
        stage.showAndWait();
    }
}