import javafx.scene.Scene;

public class ThemeManager {
    private static ThemeManager instance;  // singleton instance
    private String currentTheme = "/light-theme.css"; // default theme

    // private constructor (singleton pattern)
    private ThemeManager() {}

    // access point
    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    // toggle between light and dark
    public void toggleTheme(Scene scene) {
        if (currentTheme.equals("/light-theme.css")) {
            currentTheme = "/dark-theme.css";
        } else {
            currentTheme = "/light-theme.css";
        }
        applyTheme(scene);
    }

    // explicitly set theme
    public void setTheme(String theme, Scene scene) {
        currentTheme = theme;
        applyTheme(scene);
    }

    // apply current theme to a scene
    public void applyTheme(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(currentTheme).toExternalForm());
    }

    // get current theme (for combo box sync, etc.)
    public String getCurrentTheme() {
        return currentTheme;
    }
}

