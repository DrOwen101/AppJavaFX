//other imports

//standard GUI imports
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/*
 * This class will present all saved Patients in a GUI where a receptionist can see:
 * The patient's full name, ID number, and open a second alert tab to see a larger summary of the patient
 */

public class SearchPatientGUI {
    private Stage stage;

    //Performed on GUI creation
    public SearchPatientGUI() {
        initializeGUI();
    }

    //set up GUI here
    private void initializeGUI() {
        stage = new Stage();
        
        //Set name
        stage.setTitle("HealthCare Pro - Patient Search");

        //Main VBox
        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.TOP_LEFT);
        mainLayout.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #e8f5e8, #f0f9f0);" // Light green gradient
        );


        //Header
        HBox header = new HBox();
        header.setAlignment(Pos.TOP_LEFT);
        header.setPadding(new Insets(15, 30, 15, 30));
        header.setStyle(
            "-fx-background-color: linear-gradient(to right, #1b5e20, #2e7d32);" // Dark green gradient
        );

        Label topLabel = new Label("     Patient Search");
        topLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        topLabel.setStyle("-fx-text-fill: white;");

        header.getChildren().add(topLabel);

        //Scroll pane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle(
            "-fx-background: transparent;" +
            "-fx-background-color: transparent;"
        );

        GridPane patientsList = getPatientsList(); 

        scrollPane.setContent(patientsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(600);

        //Add Children
        mainLayout.getChildren().addAll(header, scrollPane);

        //Set up scene
        // Create scene with modern styling
        Scene scene = new Scene(mainLayout, 900, 550);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    //Add all patients to GRIDPANE to make visible 
    public GridPane getPatientsList() {
        /* 

        *       EXAMPLE LABEL

        Label testLabel1 = new Label("Testing");
        testLabel1.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        testLabel1.setStyle("-fx-text-fill: black;");
        */
        GridPane list = new GridPane();
        list.setHgap(100);
        list.setVgap(15);
        list.setPadding(new Insets(15, 0, 0, 10));

        PatientDataStorage storage = PatientDataStorage.getInstance();
        List<PatientDataObject> savedPatients = storage.getAllPatients();

        Label checkLabel = new Label("There are no saved patients");
        checkLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 14));
        checkLabel.setStyle("-fx-text-fill: black;");
        list.add(checkLabel, 0, 0);

        if (savedPatients.isEmpty()) {
            return list;
        } else {
            list.getChildren().clear();

            Label numLabel = new Label("There are " + savedPatients.size() + " saved patients.");
            numLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 10));
            numLabel.setStyle("-fx-text-fill: grey;");
            list.add(numLabel, 0, 0);
        }

        //Go through patients list to present as names
        for (int i = 0; i < savedPatients.size(); i++) {

            PatientDataObject currentPatient = savedPatients.get(i);

            //Left Label
            Label nameLabel = new Label("   " + currentPatient.getFullName());
            nameLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 14));
            nameLabel.setStyle("-fx-text-fill: black;");
            nameLabel.setAlignment(Pos.BASELINE_LEFT);
            list.add(nameLabel, 0, i + 1);


            //Patient ID
            Label patientIDLabel = new Label("   " + currentPatient.getPatientId());
            patientIDLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 14));
            patientIDLabel.setStyle("-fx-text-fill: black;");
            patientIDLabel.setAlignment(Pos.BASELINE_CENTER);
            list.add(patientIDLabel, 1, i + 1);


            //Button
            Button seePatientButton = createModernButton(
            "view summary",
            "#6f42c1", "#563d7c", // Purple accent for variety
            150, 30
            );
            seePatientButton.setOnAction(e -> {
                showPatientSummaryDialogue(currentPatient);
            });
            seePatientButton.setAlignment(Pos.BASELINE_RIGHT);
            list.add(seePatientButton, 2, i + 1);
        }

        return list;
    }

    //Gives a patient summary in a new window
    private void showPatientSummaryDialogue(PatientDataObject patient) {
        //Initiate alert
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setTitle("Patient summary");
        alert.setHeaderText(patient.getFullName());

        //print summary
        StringBuilder content = new StringBuilder();
        content.append(patient.toJsonString());  
        alert.setContentText(content.toString());

        // Make the dialog resizable and larger for better readability
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(600, 400);
        
        alert.showAndWait();
    }


    //Show and close window
    public void show() {
        stage.show();
    }
    
    public void close() {
        stage.close();
    }

    //Button
    private Button createModernButton(String text, String primaryColor, String hoverColor, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
        
        // Base styling
        button.setStyle(
            "-fx-background-color: " + primaryColor + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
            "-fx-cursor: hand;"
        );
        
        // Hover effects
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: " + hoverColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 4);" +
                "-fx-cursor: hand;" +
                "-fx-scale-x: 1.02;" +
                "-fx-scale-y: 1.02;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: " + primaryColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
                "-fx-cursor: hand;" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;"
            )
        );
        
        return button;
    }
}
