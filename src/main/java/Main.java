import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
  private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

  @Override
  public void start(Stage primaryStage) {
    // Create main layout
    VBox mainLayout = new VBox(20);
    mainLayout.setAlignment(Pos.CENTER);
    mainLayout.setPadding(new Insets(40));

    // Create header section
    VBox headerSection = createHeaderSection();

    // Create button container
    VBox buttonContainer = new VBox(15);
    buttonContainer.setAlignment(Pos.CENTER);
    buttonContainer.setPadding(new Insets(30));
    buttonContainer.getStyleClass().add("section");

    // Buttons
    Button patientFormBtn = createModernButton("📋 Patient Information Form", 200, 55);
    patientFormBtn.setOnAction(e -> {
      LOGGER.info("Opening Patient Form...");
      PatientFormGUI patientFormGUI = new PatientFormGUI();
      patientFormGUI.show();
    });

    Button checkInBtn = createModernButton("🏥 Start Patient Check-In", 200, 55);
    checkInBtn.setOnAction(e -> {
      LOGGER.info("Starting Patient Check-In workflow...");
      startCheckInWorkflow(primaryStage);
    });

    Button viewPatientsBtn = createModernButton("👥 View Saved Patients", 200, 55);
    viewPatientsBtn.setOnAction(e -> {
      LOGGER.info("Opening saved patients view...");
      showSavedPatientsDialog(primaryStage);
    });

    buttonContainer.getChildren().addAll(patientFormBtn, checkInBtn, viewPatientsBtn);

    mainLayout.getChildren().addAll(headerSection, buttonContainer);

    // Scene setup
    Scene scene = new Scene(mainLayout, 450, 550);
    ThemeManager.getInstance().applyTheme(scene); // apply light/dark mode

    primaryStage.setTitle("HealthCare Pro - Patient Management System");
    primaryStage.setScene(scene);
    primaryStage.centerOnScreen();
    primaryStage.show();
  }

  /**
   * Create modern header section with settings button
   */
  private VBox createHeaderSection() {
    VBox header = new VBox(10);
    header.setAlignment(Pos.CENTER);
    header.setPadding(new Insets(20));

    Label titleLabel = new Label("HealthCare Pro");
    titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
    titleLabel.getStyleClass().add("title-label");

    Label subtitleLabel = new Label("Patient Management System");
    subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
    subtitleLabel.getStyleClass().add("subtitle-label");

    Label versionLabel = new Label("v2.0 • Professional Edition");
    versionLabel.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 12));
    versionLabel.getStyleClass().add("version-label");

    // Settings button (dark mode toggle)
    Button settingsBtn = new Button("⚙️");
    settingsBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
    settingsBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

    settingsBtn.setOnAction(e -> {
      Scene scene = settingsBtn.getScene();
      ThemeManager.getInstance().toggleTheme(scene);
    });

    VBox container = new VBox(10, titleLabel, subtitleLabel, versionLabel, settingsBtn);
    container.setAlignment(Pos.CENTER);

    return container;
  }

  private Button createModernButton(String text, double width, double height) {
    Button button = new Button(text);
    button.setPrefSize(width, height);
    button.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));

    // assign style class
    button.getStyleClass().add("modern-button");

    return button;
  }

  private void startCheckInWorkflow(Stage parentStage) {
    CheckInPopup popup = new CheckInPopup(parentStage, () -> {
      LOGGER.info("Check-in popup Start button clicked - opening check-in GUI");
      PatientCheckInGUI checkInGUI = new PatientCheckInGUI();
      checkInGUI.show();
    });

    boolean startClicked = popup.showAndWait();

    if (startClicked) {
      LOGGER.info("Check-in workflow initiated successfully");
    } else {
      LOGGER.info("Check-in workflow cancelled by user");
    }
  }

  private void showSavedPatientsDialog(Stage parentStage) {
    PatientDataStorage storage = PatientDataStorage.getInstance();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.initOwner(parentStage);
    alert.setTitle("Saved Patients");
    alert.setHeaderText("Patient Data Storage");

    if (storage.isEmpty()) {
      alert.setContentText("No patients have been saved yet.\n\n" +
          "Use 'Open Patient Form' or 'Start Patient Check-In' to add patients, " +
          "then click 'Save and Leave Patient' to save them to storage.");
    } else {
      StringBuilder content = new StringBuilder();
      content.append(storage.getStorageStatistics()).append("\n");
      content.append(storage.getAllPatientsSummary());
      alert.setContentText(content.toString());
    }

    alert.setResizable(true);
    alert.getDialogPane().setPrefSize(600, 400);
    alert.showAndWait();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

