import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Region;

/**
 * Simple theme helper to enable a dark mode across the app.
 *
 * Usage: call Theme.enableDarkMode(true) and then Theme.applyScene(scene)
 * for any scenes you create. This will set backgrounds to #0d0d0d and
 * adjust basic text/input colors to be readable on dark backgrounds.
 */
public class Theme {
    private static boolean darkMode = false;
    private static final String BG = "#0d0d0d"; // requested background
    private static final String INPUT_BG = "#141414";
    private static final String TEXT = "#e6e6e6";

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void enableDarkMode(boolean enable) {
        darkMode = enable;
    }

    /**
     * Apply the current theme to a Scene. If dark mode is disabled this is a no-op.
     */
    public static void applyScene(Scene scene) {
        if (!darkMode || scene == null) return;
        Parent root = scene.getRoot();
        if (root != null) {
            applyToNode(root);
        }
        try {
            scene.setFill(javafx.scene.paint.Color.web(BG));
        } catch (Exception ignored) {}
    }

    private static void applyToNode(Node node) {
        if (node == null) return;

        // Preserve Button styling entirely (keep colors as in light mode)
        if (node instanceof Button) {
            return;
        }

        // For layout Regions (Panes, VBox, HBox, etc.) set a dark background
        if (node instanceof Region) {
            Region r = (Region) node;
            String old = r.getStyle() == null ? "" : r.getStyle();
            r.setStyle(old + (old.endsWith(";") || old.isEmpty() ? "" : ";") + "-fx-background-color: " + BG + ";");
        }

        // For labeled controls (Label, Button, etc.) set text color
        if (node instanceof Labeled) {
            Labeled l = (Labeled) node;
            String old = l.getStyle() == null ? "" : l.getStyle();
            l.setStyle(old + (old.endsWith(";") || old.isEmpty() ? "" : ";") + "-fx-text-fill: " + TEXT + ";");
        }

        // For text inputs, set inner background and text color
        if (node instanceof TextInputControl) {
            TextInputControl t = (TextInputControl) node;
            String old = t.getStyle() == null ? "" : t.getStyle();
            t.setStyle(old + (old.endsWith(";") || old.isEmpty() ? "" : ";") +
                "-fx-control-inner-background: " + INPUT_BG + "; -fx-text-fill: " + TEXT + ";");
        }

        // Recurse into children if Parent
        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                applyToNode(child);
            }
        }
    }
}
