import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
// ButtonType/ButtonBar no longer used after switching to a simple informational alert
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simple menu that appears after clicking Start Patient Check-In.
 * Provides: New Patient, Existing Patient (search), Cancel.
 */
public class PatientCheckInMenu {
    private static final Logger LOGGER = Logger.getLogger(PatientCheckInMenu.class.getName());

    private Stage stage;

    public PatientCheckInMenu() {
        initialize();
    }

    private void initialize() {
        stage = new Stage();
        stage.setTitle("Patient Check In");

        VBox root = new VBox(18);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #e8f5e8, #f1f8e9);");

    Label iconLabel = new Label("🏥");
    iconLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 28));

    Label title = new Label("Patient Check-In");
    title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
    title.setStyle("-fx-text-fill: #1b5e20;");

    HBox titleBox = new HBox(10, iconLabel, title);
    titleBox.setAlignment(Pos.CENTER);

    Label subtitle = new Label("Please choose your check-in option:");
    subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
    subtitle.setStyle("-fx-text-fill: #33691e;");

    VBox buttons = new VBox(10);
    buttons.setAlignment(Pos.CENTER);

    Button newPatientBtn = new Button();
    newPatientBtn.setPrefSize(180, 45);
    newPatientBtn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, #66bb6a, #4caf50);" +
        "-fx-text-fill: white;" +
        "-fx-background-radius: 22;" +
        "-fx-border-radius: 22;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
        "-fx-cursor: hand;"
    );
    // Use inline emoji to match main menu orientation (heavy plus emoji)
    newPatientBtn.setText("➕  New Patient");
    newPatientBtn.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
    newPatientBtn.setOnAction(e -> handleNewPatient());
    // Hover effect for New
    newPatientBtn.setOnMouseEntered(e -> newPatientBtn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, #5cb85c, #449d44);" +
        "-fx-text-fill: white;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 22;" +
        "-fx-border-radius: 22;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 3);" +
        "-fx-cursor: hand;" +
        "-fx-scale-x: 1.03;" +
        "-fx-scale-y: 1.03;"
    ));
    newPatientBtn.setOnMouseExited(e -> newPatientBtn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, #66bb6a, #4caf50);" +
        "-fx-text-fill: white;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 22;" +
        "-fx-border-radius: 22;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
        "-fx-cursor: hand;"
    ));

        Button existingBtn = new Button("🔎  Existing Patient");
    existingBtn.setPrefSize(180, 45);
    existingBtn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, #29b6f6, #0288d1);" +
        "-fx-text-fill: white;" +
        "-fx-background-radius: 22;" +
        "-fx-border-radius: 22;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
        "-fx-cursor: hand;"
    );
    existingBtn.setOnMouseEntered(e -> existingBtn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, #03a9f4, #0288d1);" +
        "-fx-text-fill: white;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 22;" +
        "-fx-border-radius: 22;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 3);" +
        "-fx-cursor: hand;" +
        "-fx-scale-x: 1.03;" +
        "-fx-scale-y: 1.03;"
    ));
    existingBtn.setOnMouseExited(e -> existingBtn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, #29b6f6, #0288d1);" +
        "-fx-text-fill: white;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 22;" +
        "-fx-border-radius: 22;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
        "-fx-cursor: hand;"
    ));
    existingBtn.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
        existingBtn.setOnAction(e -> showExistingPatientSearch());

        Button cancelBtn = new Button("✖  Cancel");
    cancelBtn.setPrefSize(140, 40);
    cancelBtn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, #ff8a80, #ff5252);" +
        "-fx-text-fill: white;" +
        "-fx-background-radius: 22;" +
        "-fx-border-radius: 22;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
        "-fx-cursor: hand;"
    );
    cancelBtn.setOnAction(e -> stage.close());
    cancelBtn.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
    cancelBtn.setOnMouseEntered(e -> cancelBtn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, #ff7070, #ff1744);" +
        "-fx-text-fill: white;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 22;" +
        "-fx-border-radius: 22;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 3);" +
        "-fx-cursor: hand;" +
        "-fx-scale-x: 1.02;" +
        "-fx-scale-y: 1.02;"
    ));
    cancelBtn.setOnMouseExited(e -> cancelBtn.setStyle(
        "-fx-background-color: linear-gradient(to bottom, #ff8a80, #ff5252);" +
        "-fx-text-fill: white;" +
        "-fx-font-weight: bold;" +
        "-fx-background-radius: 22;" +
        "-fx-border-radius: 22;" +
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
        "-fx-cursor: hand;"
    ));

    // Add spacer so cancel is slightly lower than the other buttons
    VBox spacer = new VBox();
    spacer.setMinHeight(8);

    buttons.getChildren().addAll(newPatientBtn, existingBtn, spacer, cancelBtn);

    // Center title and buttons
    root.setAlignment(Pos.TOP_CENTER);
    root.setSpacing(24);

    // Add title (with emoji), subtitle and buttons
    root.getChildren().addAll(titleBox, subtitle, buttons);

    Scene scene = new Scene(root, 420, 340);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    private void handleNewPatient() {
        // Open a blank PatientCheckInGUI which will create a new NewPatient in workflow
        LOGGER.info("Starting new patient check-in");
        PatientCheckInGUI gui = new PatientCheckInGUI();
        gui.show();
        stage.close();
    }

    private void showExistingPatientSearch() {
        // Build a search UI in a new stage (or reuse same stage content)
        Stage searchStage = new Stage();
        searchStage.setTitle("Search Existing Patients");

        VBox root = new VBox(12);
        root.setPadding(new Insets(14));

        // Use a grid so each input has a visible label next to it
        GridPane fields = new GridPane();
        fields.setHgap(10);
        fields.setVgap(8);

        Label fnLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First name");
        fields.add(fnLabel, 0, 0);
        fields.add(firstNameField, 1, 0);

        Label lnLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last name");
        fields.add(lnLabel, 0, 1);
        fields.add(lastNameField, 1, 1);

        Label dobLabel = new Label("Date of Birth:");
        DatePicker dobPicker = new DatePicker();
        dobPicker.setPromptText("DOB");
        fields.add(dobLabel, 0, 2);
        fields.add(dobPicker, 1, 2);

    // Reason textarea with a label and continue button on the right
    Label reasonLabel = new Label("Reason for Visit:");
    TextArea reasonArea = new TextArea();
    reasonArea.setPromptText("Enter details of the reason for the visit");
    reasonArea.setPrefRowCount(3);

        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-background-color: #29b6f6; -fx-text-fill: white;");

        TableView<PatientDataObject> resultsTable = new TableView<>();
        resultsTable.setPrefHeight(260);

        // Highlight selected row with a custom background color
        resultsTable.setRowFactory(tv -> {
            javafx.scene.control.TableRow<PatientDataObject> row = new javafx.scene.control.TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    row.setStyle("-fx-background-color: #dcedc8;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });

        TableColumn<PatientDataObject, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameCol.setPrefWidth(220);

        TableColumn<PatientDataObject, String> dobCol = new TableColumn<>("DOB");
        dobCol.setCellValueFactory(cell -> {
            LocalDate dob = cell.getValue().getDateOfBirth();
            String formatted = dob != null ? dob.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) : "";
            return new javafx.beans.property.SimpleStringProperty(formatted);
        });
        dobCol.setPrefWidth(120);

        TableColumn<PatientDataObject, String> idCol = new TableColumn<>("Patient ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        idCol.setPrefWidth(180);

    resultsTable.getColumns().addAll(Arrays.asList(nameCol, dobCol, idCol));

    Button selectBtn = new Button("Select");
        selectBtn.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white;");
        selectBtn.setDisable(true);
        // Enable Select button only when a row is selected
        resultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selectBtn.setDisable(newSel == null);
        });
        selectBtn.setOnAction(e -> {
            PatientDataObject selected = resultsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                LOGGER.log(Level.INFO, "Selected patient: {0}", selected.getFullName());
                // Create NewPatient from PatientDataObject and start check-in GUI with it
                NewPatient np = convertToNewPatient(selected);
                PatientCheckInGUI gui = new PatientCheckInGUI(np, true);
                // Pre-fill today's reason into the workflow/session as a note
                if (reasonArea.getText() != null && !reasonArea.getText().trim().isEmpty()) {
                    gui.getCheckInWorkflow().getCurrentSession().addSessionNote("Reason: " + reasonArea.getText().trim());
                }
                gui.show();
                searchStage.close();
                stage.close();
            }
        });

        // Wire search action
        searchBtn.setOnAction(e -> {
            resultsTable.getItems().clear();

            PatientDataStorage storage = PatientDataStorage.getInstance();

            String fn = firstNameField.getText() != null ? firstNameField.getText().trim() : "";
            String ln = lastNameField.getText() != null ? lastNameField.getText().trim() : "";

            // If both names provided, search by combined name term first
            if (!fn.isEmpty() || !ln.isEmpty()) {
                String combined = (fn + " " + ln).trim();
                List<PatientDataObject> byName = storage.findPatientsByName(combined);
                // If specific last name provided and first name empty, also include by last name search
                resultsTable.getItems().addAll(byName);
            }

            // If DOB provided, filter by DOB and merge results
            if (dobPicker.getValue() != null) {
                List<PatientDataObject> byDob = storage.findPatientsByDateOfBirth(dobPicker.getValue());
                for (PatientDataObject p : byDob) {
                    if (!resultsTable.getItems().contains(p)) {
                        resultsTable.getItems().add(p);
                    }
                }
            }

            // If nothing matched, show a simple informational dialog
            if (resultsTable.getItems().isEmpty()) {
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("No matches found");
                info.setHeaderText("No existing patient with this information found");
                info.setContentText("Please verify the search fields or create a new patient from the main menu.");
                info.showAndWait();
            }
        });

    // Add a Cancel button next to Search and Select
    Button cancelSearchBtn = new Button("Cancel");
    cancelSearchBtn.setStyle("-fx-background-color: #ff8a80; -fx-text-fill: white;");
    cancelSearchBtn.setOnAction(e -> {
        searchStage.close();
    });

    HBox actions = new HBox(10);
    // Spacer pushes the cancel button to the right
    javafx.scene.layout.Region actionSpacer = new javafx.scene.layout.Region();
    HBox.setHgrow(actionSpacer, javafx.scene.layout.Priority.ALWAYS);
    actions.getChildren().addAll(searchBtn, selectBtn, actionSpacer, cancelSearchBtn);

    // Create an HBox for the reason area with a Continue button on the right
    Button continueBtn = new Button("Continue");
    continueBtn.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white;");

    // Update button label depending on whether a patient row is selected
    resultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
        if (newSel != null) {
            continueBtn.setText("Confirm Check-In");
        } else {
            continueBtn.setText("Continue");
        }
    });

    continueBtn.setOnAction(e -> {
        PatientDataObject selected = resultsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Confirm check-in for selected existing patient
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Check-In");
            confirm.setHeaderText("Confirm check-in for: " + selected.getFullName());
            confirm.setContentText("Proceed with check-in for this existing patient?");
            Optional<javafx.scene.control.ButtonType> answer = confirm.showAndWait();
                if (answer.isPresent() && answer.get() == javafx.scene.control.ButtonType.OK) {
                NewPatient np = convertToNewPatient(selected);
                PatientCheckInGUI gui = new PatientCheckInGUI(np, true);
                if (reasonArea.getText() != null && !reasonArea.getText().trim().isEmpty()) {
                    gui.getCheckInWorkflow().getCurrentSession().addSessionNote("Reason: " + reasonArea.getText().trim());
                }
                gui.show();
                searchStage.close();
                stage.close();
            }
        } else {
            // Start a new patient check-in prefilled with search fields and reason
            NewPatient np = new NewPatient();
            String fn = firstNameField.getText() != null ? firstNameField.getText().trim() : "";
            String ln = lastNameField.getText() != null ? lastNameField.getText().trim() : "";
            if (!fn.isEmpty()) np.setFirstName(fn);
            if (!ln.isEmpty()) np.setLastName(ln);
            if (dobPicker.getValue() != null) np.setDateOfBirth(dobPicker.getValue());
            PatientCheckInGUI gui = new PatientCheckInGUI(np);
            if (reasonArea.getText() != null && !reasonArea.getText().trim().isEmpty()) {
                gui.getCheckInWorkflow().getCurrentSession().addSessionNote("Reason: " + reasonArea.getText().trim());
            }
            gui.show();
            searchStage.close();
            stage.close();
        }
    });

    HBox reasonRow = new HBox(8);
    reasonRow.getChildren().addAll(reasonArea, continueBtn);
    reasonRow.setAlignment(Pos.CENTER_RIGHT);

    VBox container = new VBox(10);
    // Place fields, actions, results, and finally the reason row (with continue button)
    container.getChildren().addAll(fields, actions, resultsTable, reasonLabel, reasonRow);
        container.setPadding(new Insets(8));

        ScrollPane sp = new ScrollPane(container);
        sp.setFitToWidth(true);

        Scene scene = new Scene(sp, 700, 420);
        searchStage.setScene(scene);
        searchStage.centerOnScreen();
        searchStage.show();
    }

    private NewPatient convertToNewPatient(PatientDataObject pdo) {
        NewPatient np = new NewPatient();
        if (pdo.getFirstName() != null) np.setFirstName(pdo.getFirstName());
        if (pdo.getLastName() != null) np.setLastName(pdo.getLastName());
        if (pdo.getDateOfBirth() != null) np.setDateOfBirth(pdo.getDateOfBirth());
        if (pdo.getGender() != null) np.setGender(pdo.getGender());
        np.setAddress(pdo.getAddress());
        np.setPhoneNumber(pdo.getPhoneNumber());
        np.setEmail(pdo.getEmail());
        np.setEmergencyContact(pdo.getEmergencyContact());
        np.setInsuranceProvider(pdo.getInsuranceProvider());
        np.setInsurancePolicyNumber(pdo.getInsurancePolicyNumber());
        // Note: appointments, medical history are not fully reconstructed here
        return np;
    }

    public void show() {
        stage.show();
    }
}
