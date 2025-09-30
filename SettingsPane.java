import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A simple settings pane that allows toggling between light and dark theme
 * using ThemeManager. The pane can be embedded into existing layouts or
 * shown as a modal dialog using showAsDialog.
 */
public class SettingsPane {

    private final Scene scene; // The scene to apply theme changes to

    public SettingsPane(Scene scene) {
        this.scene = scene;
    }

    /**
     * Build and return the settings UI as a Node so callers can embed it.
     */
    public Node createContent() {
        VBox root = new VBox(12);
        root.setPadding(new Insets(16));
        root.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Appearance");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        CheckBox darkModeCheck = new CheckBox("Enable dark mode");
        // Initialize the checkbox based on ThemeManager state if possible
        darkModeCheck.setSelected(ThemeManager.isDarkMode());

        darkModeCheck.setOnAction(e -> {
            // Toggle theme across the provided scene
            ThemeManager.toggleTheme(scene);
            // Sync checkbox with the new state
            darkModeCheck.setSelected(ThemeManager.isDarkMode());
        });

        // Also provide a button to immediately toggle, useful for dialogs
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER_LEFT);

        Button toggleBtn = new Button("Toggle Theme");
        toggleBtn.setOnAction(e -> {
            ThemeManager.toggleTheme(scene);
            darkModeCheck.setSelected(ThemeManager.isDarkMode());
        });

        controls.getChildren().addAll(darkModeCheck, toggleBtn);

        root.getChildren().addAll(title, controls);

        return root;
    }

    /**
     * Show the settings pane in a modal dialog attached to the given owner stage.
     */
    public void showAsDialog(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        VBox content = new VBox();
        content.getChildren().add((Node) createContent());
    Scene dialogScene = new Scene(content);
    dialog.setScene(dialogScene);
    // Ensure the settings dialog uses the currently selected theme
    ThemeManager.applyCurrentTheme(dialogScene);
        dialog.setTitle("Settings");
        dialog.setResizable(false);
        dialog.showAndWait();
    }
}
