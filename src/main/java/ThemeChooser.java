import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Simple modal that asks the user which theme they prefer before the app starts.
 * Returns true for dark mode, false for light mode.
 */
public class ThemeChooser {
    public static boolean showThemeChooser(Stage owner) {
        final boolean[] choice = new boolean[1];

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle("Choose Theme");

    VBox root = new VBox(18);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(20));
    // Set the chooser background to the requested color and ensure good contrast
    root.setStyle("-fx-background-color: #006633;");

    Label prompt = new Label("What theme would you prefer?");
    prompt.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
    prompt.setStyle("-fx-text-fill: white;");

        Button lightBtn = new Button("Light Mode");
        Button darkBtn = new Button("Dark Mode");

    lightBtn.setPrefWidth(120);
    darkBtn.setPrefWidth(120);
    // Buttons: white background with green text for contrast on the green pane
    lightBtn.setStyle("-fx-background-color: white; -fx-text-fill: #006633; -fx-font-weight: bold; -fx-cursor: hand;");
    darkBtn.setStyle("-fx-background-color: white; -fx-text-fill: #006633; -fx-font-weight: bold; -fx-cursor: hand;");

        HBox btns = new HBox(12, lightBtn, darkBtn);
        btns.setAlignment(Pos.CENTER);

        root.getChildren().addAll(prompt, btns);

        Scene scene = new Scene(root, 360, 140);
        dialog.setScene(scene);

        // Button actions
        lightBtn.setOnAction(e -> {
            choice[0] = false;
            dialog.close();
        });

        darkBtn.setOnAction(e -> {
            choice[0] = true;
            dialog.close();
        });

        // Show and wait
        dialog.centerOnScreen();
        dialog.showAndWait();

        return choice[0];
    }
}
