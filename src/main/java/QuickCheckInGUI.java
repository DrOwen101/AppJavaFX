

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Small JavaFX dialog that performs a quick check-in using QuickCheckIn logic.
 * Intended to be launched when the "Quick Check-In" button in the main menu is pressed.
 *
 * Usage from main menu button:
 *   QuickCheckInGUI.showWindow(ownerStage);
 */
public class QuickCheckInGUI extends VBox {

    private static final Logger LOGGER = Logger.getLogger(QuickCheckInGUI.class.getName());

    private final TextField lookupField = new TextField();
    private final Button searchBtn = new Button("Search");
    private final ListView<PatientDataObject> results = new ListView<>();
    private final Button quickInBtn = new Button("Quick Check-In");
    private final Label status = new Label();

    public QuickCheckInGUI() {
        build();
        attachHandlers();
    }

    /**
     * Create a QuickCheckInGUI that knows how to navigate back to a previous scene.
     * Adds a small Back button at the top that restores the provided scene on the given stage.
     */
    public QuickCheckInGUI(Stage primaryStage, Scene previousScene) {
        this();
        Button back = new Button("← Back");
        back.setOnAction(e -> {
            if (previousScene != null) {
                primaryStage.setScene(previousScene);
                primaryStage.setTitle("HealthCare Pro - Patient Management System");
                primaryStage.centerOnScreen();
            }
        });
        // Insert back button at the top
        getChildren().add(0, back);
    }

    private void build() {
        // Use spacing/padding for the outer container
        setSpacing(8);
        setPadding(new Insets(10));

        lookupField.setPromptText("Enter patient ID or name fragment");
        HBox searchRow = new HBox(8, lookupField, searchBtn);
        HBox.setHgrow(lookupField, Priority.ALWAYS);

        results.setPlaceholder(new Label("No matches"));
        results.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PatientDataObject p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null) setText(null);
                else {
                    String name = p.getFullName() == null ? "(no name)" : p.getFullName();
                    String id = p.getPatientId() == null ? "(no id)" : p.getPatientId();
                    String flag = p.isCheckInComplete() ? "✓" : "•";
                    setText(name + "  [" + id + "]  " + flag);
                }
            }
        });

        quickInBtn.setDisable(true);

        // Header
        Label title = new Label("Quick Check-In");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #1b5e20;");

        // Create modern-styled search button and quick-check button
        Button styledSearch = createModernButton("Search", "#20c997", "#17a085", 90, 36);
        styledSearch.setOnAction(e -> doSearch());
        // Replace searchBtn in the row with the styled button
        searchRow.getChildren().set(1, styledSearch);

        Button styledQuick = createModernButton("" + quickInBtn.getText(), "#28a745", "#1e7e34", 160, 42);
        // Fire the original quickInBtn action when styled button is pressed (attachHandlers will set the real action)
        styledQuick.setOnAction(e -> quickInBtn.fire());
        styledQuick.disableProperty().bind(quickInBtn.disableProperty());

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox card = new VBox(12, title, searchRow, results, styledQuick, status);
        card.setPadding(new Insets(18));
        card.setMaxWidth(640);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 16, 0, 0, 6);"
        );

        StackPane wrapper = new StackPane(card);
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setPadding(new Insets(24));
        wrapper.setStyle("-fx-background: linear-gradient(to bottom, #e8f5e8, #f0f9f0);");

        getChildren().add(wrapper);
    }

    // Small helper to create modern styled buttons similar to Main
    private Button createModernButton(String text, String primaryColor, String hoverColor, double width, double height) {
        Button button = new Button(text);
        button.setPrefSize(width, height);
        button.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));

        button.setStyle(
            "-fx-background-color: " + primaryColor + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 8, 0, 0, 2);"
        );

        button.setOnMouseEntered(e ->
            button.setStyle(
                "-fx-background-color: " + hoverColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 12, 0, 0, 4);" +
                "-fx-scale-x: 1.02; -fx-scale-y: 1.02;"
            )
        );

        button.setOnMouseExited(e ->
            button.setStyle(
                "-fx-background-color: " + primaryColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 8, 0, 0, 2);" +
                "-fx-scale-x: 1.0; -fx-scale-y: 1.0;"
            )
        );

        return button;
    }

    private void attachHandlers() {
        searchBtn.setOnAction(e -> doSearch());
        lookupField.setOnAction(e -> doSearch());

        results.getSelectionModel().selectedItemProperty().addListener((o, oldV, newV) -> {
            quickInBtn.setDisable(newV == null);
            status.setText("");
        });

        quickInBtn.setOnAction(e -> {
            PatientDataObject selected = results.getSelectionModel().getSelectedItem();
            if (selected == null) {
                status.setText("Select a patient first.");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Quick check-in " + selected.getFullName() + " [" + selected.getPatientId() + "]?",
                    ButtonType.OK, ButtonType.CANCEL);
            confirm.setHeaderText("Confirm Quick Check-In");
            confirm.initModality(Modality.APPLICATION_MODAL);

            Optional<ButtonType> res = confirm.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.OK) {
                // Open details dialog to collect non-obvious answers (reason, temperature, waiting room)
                showDetailsDialog(selected);
            }
        });
    }

    // Show a small modal dialog that collects reason, temperature, and waiting room.
    private void showDetailsDialog(PatientDataObject patient) {
        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle("Quick Check-In Details: " + (patient.getFullName() == null ? "(no name)" : patient.getFullName()));

        // Root with gradient background so the modal matches app style
        StackPane root = new StackPane();
        root.setStyle("-fx-background: linear-gradient(to bottom, #e8f5e8, #f0f9f0);");
        root.setPadding(new Insets(12));

        // Card container
        VBox card = new VBox(12);
        card.setPadding(new Insets(16));
        card.setMaxWidth(520);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 12, 0, 0, 6);"
        );

        Label info = new Label("Provide a few quick details before completing check-in:");
        info.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));

        TextField reasonField = new TextField();
        reasonField.setPromptText("Reason for visit (e.g. follow-up, urgent, routine)");

        TextField tempField = new TextField();
        tempField.setPromptText("Temperature (°C) e.g. 36.7");

        ComboBox<String> waitingRoom = new ComboBox<>(FXCollections.observableArrayList(
            "Waiting Room A", "Waiting Room B", "Triage", "Virtual Waiting"
        ));
        waitingRoom.setPromptText("Select waiting room");

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        Button save = createModernButton("Save & Check-In", "#28a745", "#1e7e34", 140, 38);
        Button cancel = createModernButton("Cancel", "#6c757d", "#5a6268", 100, 38);
        buttons.getChildren().addAll(cancel, save);

        card.getChildren().addAll(info,
            new Label("Reason:"), reasonField,
            new Label("Temperature (°C):"), tempField,
            new Label("Waiting Room:"), waitingRoom,
            buttons);

        // Actions
        save.setOnAction(evt -> {
            String reason = reasonField.getText();
            String tempText = tempField.getText();
            String room = waitingRoom.getValue();

            double tempVal = Double.NaN;
            if (tempText != null && !tempText.trim().isEmpty()) {
                try {
                    tempVal = Double.parseDouble(tempText.trim());
                } catch (NumberFormatException nfe) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Temperature must be a number like 36.7", ButtonType.OK);
                    a.initModality(Modality.APPLICATION_MODAL);
                    a.showAndWait();
                    return;
                }
            }

            if (reason != null && !reason.trim().isEmpty()) {
                patient.setSpecialInstructions(reason.trim());
            }
            if (!Double.isNaN(tempVal)) {
                patient.setTemperature(tempVal);
            }
            if (room != null && !room.trim().isEmpty()) {
                patient.setWaitingAreaAssignment(room);
            }

            boolean ok = QuickCheckIn.quickCheckInObject(patient, PatientDataStorage.getInstance());
            if (ok) {
                status.setText("Quick check-in successful.");
                results.refresh();
                dlg.close();
            } else {
                status.setText("Quick check-in failed. See logs.");
                Alert a = new Alert(Alert.AlertType.ERROR, "Failed to persist quick check-in. See logs.", ButtonType.OK);
                a.initModality(Modality.APPLICATION_MODAL);
                a.showAndWait();
            }
        });

        cancel.setOnAction(evt -> dlg.close());

        root.getChildren().add(card);
        StackPane.setAlignment(card, Pos.TOP_CENTER);

        dlg.setScene(new Scene(root, 520, 360));
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.showAndWait();
    }

    private void doSearch() {
        String q = lookupField.getText();
        if (q == null || q.trim().isEmpty()) {
            results.setItems(FXCollections.observableArrayList());
            status.setText("Enter ID or name.");
            return;
        }

        PatientDataStorage storage = PatientDataStorage.getInstance();

        // Try ID first
        Optional<PatientDataObject> byId = storage.findPatientById(q.trim());
        if (byId.isPresent()) {
            results.setItems(FXCollections.observableArrayList(byId.get()));
            results.getSelectionModel().selectFirst();
            status.setText("Found by ID.");
            return;
        }

        // Fallback to name search
        List<PatientDataObject> matches = storage.findPatientsByName(q.trim());
        results.setItems(FXCollections.observableArrayList(matches));
        if (matches.isEmpty()) status.setText("No matches.");
        else if (matches.size() == 1) {
            results.getSelectionModel().selectFirst();
            status.setText("One match.");
        } else status.setText(matches.size() + " matches.");
    }

    /**
     * Show the QuickCheckIn dialog as a modal window. Call this from the main menu button handler.
     */
    public static void showWindow(Stage owner) {
        Platform.runLater(() -> {
            QuickCheckInGUI view = new QuickCheckInGUI();
            Stage stage = new Stage();
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Quick Check-In");
            stage.setScene(new Scene(view, 480, 360));
            stage.show();
        });
    }
}