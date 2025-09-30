import java.net.URL;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ThemeManager {
    // Load resources safely - getResource may return null if file is missing.
    private static final URL LIGHT_URL = ThemeManager.class.getResource("/Resources/light-theme.css");
    private static final URL DARK_URL = ThemeManager.class.getResource("/Resources/dark-theme.css");

    public static final String LIGHT_THEME = LIGHT_URL != null ? LIGHT_URL.toExternalForm() : "";
    public static final String DARK_THEME = DARK_URL != null ? DARK_URL.toExternalForm() : "";

    private static boolean isDarkMode = false;

    /**
     * Toggle between light and dark themes
     */
    public static void toggleTheme(Scene scene) {
        if (scene == null) return;
        if (isDarkMode) {
            // currently dark -> switch to light
            if (!DARK_THEME.isEmpty()) scene.getStylesheets().remove(DARK_THEME);
            if (!LIGHT_THEME.isEmpty() && !scene.getStylesheets().contains(LIGHT_THEME)) scene.getStylesheets().add(LIGHT_THEME);
            applyLight(scene);
        } else {
            // currently light -> switch to dark
            if (!LIGHT_THEME.isEmpty()) scene.getStylesheets().remove(LIGHT_THEME);
            if (!DARK_THEME.isEmpty() && !scene.getStylesheets().contains(DARK_THEME)) scene.getStylesheets().add(DARK_THEME);
            applyDark(scene);
        }
        isDarkMode = !isDarkMode;
    }

    // Apply inline styles for dark appearance to nodes which otherwise have inline styles
    private static void applyDark(Scene scene) {
        Parent root = scene.getRoot();
        if (root != null) {
            // dark background for root
            root.setStyle("-fx-background-color: linear-gradient(to bottom, #2b2b2b, #1f1f1f);");
            traverseAndApply(root, true);
        }
    }

    private static void applyLight(Scene scene) {
        Parent root = scene.getRoot();
        if (root != null) {
            // light background for root
            root.setStyle("-fx-background-color: linear-gradient(to bottom, #e8f5e8, #f0f9f0);");
            traverseAndApply(root, false);
        }
    }

    private static void traverseAndApply(Node node, boolean dark) {
        // Apply to this node
        if (node instanceof Label) {
            Label lbl = (Label) node;
            if (dark) lbl.setStyle("-fx-text-fill: #e6e6e6;");
            else lbl.setStyle("-fx-text-fill: #1b5e20;");
        } else if (node instanceof Button) {
            Button btn = (Button) node;
            if (dark) btn.setStyle("-fx-background-color: linear-gradient(#444444, #222222); -fx-text-fill: white; -fx-background-radius: 12; -fx-border-radius: 12;");
            else btn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 12; -fx-border-radius: 12;");
        }

        // Recurse into children if parent
        if (node instanceof Parent) {
            ObservableList<Node> children = ((Parent) node).getChildrenUnmodifiable();
            for (Node child : children) {
                traverseAndApply(child, dark);
            }
        }
    }

    /**
     * Return whether dark mode is currently active.
     */
    public static boolean isDarkMode() {
        return isDarkMode;
    }

    /**
     * Apply the currently selected theme to the provided scene without changing the global state.
     */
    public static void applyCurrentTheme(Scene scene) {
        if (scene == null) return;
        // Ensure stylesheet is applied
        if (isDarkMode) {
            if (!DARK_THEME.isEmpty() && !scene.getStylesheets().contains(DARK_THEME)) scene.getStylesheets().add(DARK_THEME);
            if (!LIGHT_THEME.isEmpty()) scene.getStylesheets().remove(LIGHT_THEME);
            applyDark(scene);
        } else {
            if (!LIGHT_THEME.isEmpty() && !scene.getStylesheets().contains(LIGHT_THEME)) scene.getStylesheets().add(LIGHT_THEME);
            if (!DARK_THEME.isEmpty()) scene.getStylesheets().remove(DARK_THEME);
            applyLight(scene);
        }
    }
}