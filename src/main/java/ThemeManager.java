import javafx.scene.Scene;

public class ThemeManager {
    private static boolean darkTheme = false;

    public static void setDarkTheme(boolean dark) {
        darkTheme = dark;
    }

    public static boolean isDarkTheme() {
        return darkTheme;
    }

    public static void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        String themeFile = darkTheme ? "/styles/dark-theme.css" : "/styles/light-theme.css";

        var resource = ThemeManager.class.getResource(themeFile);
        if (resource == null) {
            System.err.println("⚠️ Theme file not found: " + themeFile);
            return;
        }

        scene.getStylesheets().add(resource.toExternalForm());
    }
}