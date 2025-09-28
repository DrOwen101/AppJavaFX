import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javafx.scene.Scene;

/**
 * Theme manager that keeps a registry of Scenes so theme changes can be
 * broadcast to all currently-open scenes/stages.
 */
public class ThemeManager {
    private static volatile boolean darkMode = false;
    // Thread-safe set of registered scenes
    private static final Set<Scene> registeredScenes = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static void setDarkMode(boolean mode) {
        darkMode = mode;
        // apply to all registered scenes immediately
        for (Scene s : registeredScenes) {
            applyTheme(s);
        }
    }

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void registerScene(Scene scene) {
        if (scene == null) return;
        registeredScenes.add(scene);
        // apply current theme when registering
        applyTheme(scene);
    }

    public static void unregisterScene(Scene scene) {
        if (scene == null) return;
        registeredScenes.remove(scene);
    }

    /**
     * Apply the appropriate stylesheet to the given Scene.
     * This will remove any previously applied theme stylesheet and add the new one.
     */
    public static void applyTheme(Scene scene) {
        if (scene == null) return;
        scene.getStylesheets().removeIf(s -> s.contains("light-theme.css") || s.contains("dark-theme.css"));
        String resourcePath = darkMode ? "/styles/dark-theme.css" : "/styles/light-theme.css";
        try {
            String css = ThemeManager.class.getResource(resourcePath).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            // If resources can't be found, do nothing - UIs already have inline styles
        }
    }

    /**
     * Apply a non-persistent preview theme to the given scene. This will not
     * change the global darkMode flag or registered scenes; useful for settings
     * previews.
     */
    public static void previewTheme(Scene scene, boolean previewDark) {
        if (scene == null) return;
        scene.getStylesheets().removeIf(s -> s.contains("light-theme.css") || s.contains("dark-theme.css"));
        String resourcePath = previewDark ? "/styles/dark-theme.css" : "/styles/light-theme.css";
        try {
            String css = ThemeManager.class.getResource(resourcePath).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            // Swallow - preview is best-effort
        }
    }
}
