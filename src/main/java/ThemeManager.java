import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.scene.Scene;

/**
 * Singleton class that manages application themes (light and dark mode).
 * Provides centralized theme switching and style management for all application windows.
 */
public class ThemeManager {
    
    private static final Logger LOGGER = Logger.getLogger(ThemeManager.class.getName());
    
    // Singleton instance
    private static ThemeManager instance;
    
    // Theme state
    private boolean isDarkMode = false;
    
    // List of active scenes to apply theme changes to
    private List<Scene> managedScenes = new ArrayList<>();
    
    // Theme change listeners
    private List<Runnable> themeChangeListeners = new ArrayList<>();
    
    /**
     * Private constructor for singleton pattern
     */
    private ThemeManager() {
        LOGGER.info("ThemeManager initialized with light theme");
    }
    
    /**
     * Get singleton instance
     */
    public static synchronized ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }
    
    /**
     * Check if dark mode is currently active
     */
    public boolean isDarkMode() {
        return isDarkMode;
    }
    
    /**
     * Toggle between light and dark mode
     */
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        applyThemeToAllScenes();
        notifyThemeChangeListeners();
        LOGGER.info("Theme toggled to: " + (isDarkMode ? "Dark Mode" : "Light Mode"));
    }
    
    /**
     * Set theme explicitly
     */
    public void setDarkMode(boolean darkMode) {
        if (this.isDarkMode != darkMode) {
            this.isDarkMode = darkMode;
            applyThemeToAllScenes();
            notifyThemeChangeListeners();
            LOGGER.info("Theme set to: " + (isDarkMode ? "Dark Mode" : "Light Mode"));
        }
    }
    
    /**
     * Register a scene to be managed by the theme manager
     */
    public void registerScene(Scene scene) {
        if (scene != null && !managedScenes.contains(scene)) {
            managedScenes.add(scene);
            applyThemeToScene(scene);
            LOGGER.fine("Scene registered with ThemeManager");
        }
    }
    
    /**
     * Unregister a scene from theme management
     */
    public void unregisterScene(Scene scene) {
        managedScenes.remove(scene);
        LOGGER.fine("Scene unregistered from ThemeManager");
    }
    
    /**
     * Add a listener that will be notified when theme changes
     */
    public void addThemeChangeListener(Runnable listener) {
        if (listener != null) {
            themeChangeListeners.add(listener);
        }
    }
    
    /**
     * Remove a theme change listener
     */
    public void removeThemeChangeListener(Runnable listener) {
        themeChangeListeners.remove(listener);
    }
    
    /**
     * Apply current theme to all managed scenes
     */
    private void applyThemeToAllScenes() {
        managedScenes.forEach(this::applyThemeToScene);
    }
    
    /**
     * Apply current theme to a specific scene
     */
    private void applyThemeToScene(Scene scene) {
        if (scene != null) {
            scene.getStylesheets().clear();
            if (isDarkMode) {
                scene.getRoot().setStyle(getDarkModeStyles());
            } else {
                scene.getRoot().setStyle(getLightModeStyles());
            }
        }
    }
    
    /**
     * Notify all theme change listeners
     */
    private void notifyThemeChangeListeners() {
        themeChangeListeners.forEach(listener -> {
            try {
                listener.run();
            } catch (Exception e) {
                LOGGER.warning("Error notifying theme change listener: " + e.getMessage());
            }
        });
    }
    
    /**
     * Get light mode styles
     */
    public String getLightModeStyles() {
        return "-fx-base: #f4f4f4; " +
               "-fx-background: linear-gradient(to bottom, #e8f5e8, #f0f9f0); " +
               "-fx-control-inner-background: white; " +
               "-fx-text-fill: #333333; " +
               "-fx-text-base-color: #333333;";
    }
    
    /**
     * Get dark mode styles
     */
    public String getDarkModeStyles() {
        return "-fx-base: #2b2b2b; " +
               "-fx-background: linear-gradient(to bottom, #1e1e1e, #2d2d2d); " +
               "-fx-control-inner-background: white; " + // Keep text fields white
               "-fx-text-fill: #e0e0e0; " +
               "-fx-text-base-color: #333333; " + // Keep text field text dark
               "-fx-background-color: #2b2b2b;";
    }
    
    /**
     * Get button styles for current theme
     */
    public String getButtonStyles(String primaryColor, String hoverColor) {
        if (isDarkMode) {
            return "-fx-background-color: " + adjustColorForDarkMode(primaryColor) + ";" +
                   "-fx-text-fill: #e0e0e0;" +
                   "-fx-background-radius: 12;" +
                   "-fx-border-radius: 12;" +
                   "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 2);" +
                   "-fx-cursor: hand;";
        } else {
            return "-fx-background-color: " + primaryColor + ";" +
                   "-fx-text-fill: white;" +
                   "-fx-background-radius: 12;" +
                   "-fx-border-radius: 12;" +
                   "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
                   "-fx-cursor: hand;";
        }
    }
    
    /**
     * Get hover button styles for current theme
     */
    public String getButtonHoverStyles(String hoverColor) {
        if (isDarkMode) {
            return "-fx-background-color: " + adjustColorForDarkMode(hoverColor) + ";" +
                   "-fx-text-fill: #e0e0e0;" +
                   "-fx-background-radius: 12;" +
                   "-fx-border-radius: 12;" +
                   "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 12, 0, 0, 4);" +
                   "-fx-cursor: hand;" +
                   "-fx-scale-x: 1.02;" +
                   "-fx-scale-y: 1.02;";
        } else {
            return "-fx-background-color: " + hoverColor + ";" +
                   "-fx-text-fill: white;" +
                   "-fx-background-radius: 12;" +
                   "-fx-border-radius: 12;" +
                   "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 4);" +
                   "-fx-cursor: hand;" +
                   "-fx-scale-x: 1.02;" +
                   "-fx-scale-y: 1.02;";
        }
    }
    
    /**
     * Get card/container styles for current theme
     */
    public String getCardStyles() {
        if (isDarkMode) {
            return "-fx-background-color: #3c3c3c;" +
                   "-fx-background-radius: 15;" +
                   "-fx-border-radius: 15;" +
                   "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);" +
                   "-fx-border-color: #555555;" +
                   "-fx-border-width: 1;";
        } else {
            return "-fx-background-color: white;" +
                   "-fx-background-radius: 15;" +
                   "-fx-border-radius: 15;" +
                   "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 5);" +
                   "-fx-border-color: #d4edda;" +
                   "-fx-border-width: 1;";
        }
    }
    
    /**
     * Get header text styles for current theme
     */
    public String getHeaderTextStyles() {
        if (isDarkMode) {
            return "-fx-text-fill: #81c784;"; // Light green for dark mode
        } else {
            return "-fx-text-fill: #1b5e20;"; // Dark green for light mode
        }
    }
    
    /**
     * Get subtitle text styles for current theme
     */
    public String getSubtitleTextStyles() {
        if (isDarkMode) {
            return "-fx-text-fill: #a5d6a7;"; // Medium light green for dark mode
        } else {
            return "-fx-text-fill: #388e3c;"; // Medium green for light mode
        }
    }
    
    /**
     * Get version text styles for current theme
     */
    public String getVersionTextStyles() {
        if (isDarkMode) {
            return "-fx-text-fill: #c8e6c9;"; // Very light green for dark mode
        } else {
            return "-fx-text-fill: #81c784;"; // Light green for light mode
        }
    }
    
    /**
     * Get text field styles - keeps them light in both themes
     */
    public String getTextFieldStyles() {
        // Always return light theme styles for text fields
        return "-fx-control-inner-background: white; " +
               "-fx-text-fill: #333333; " +
               "-fx-background-color: white; " +
               "-fx-border-color: #cccccc;";
    }
    
    /**
     * Adjust color brightness for dark mode compatibility
     */
    private String adjustColorForDarkMode(String color) {
        // Simple color adjustment - in a more complex implementation,
        // you might parse the hex color and adjust brightness
        switch (color.toLowerCase()) {
            case "#28a745": return "#4caf50"; // Green
            case "#1e7e34": return "#388e3c"; // Dark green
            case "#20c997": return "#26a69a"; // Teal
            case "#17a085": return "#00897b"; // Dark teal
            case "#6f42c1": return "#9c27b0"; // Purple
            case "#563d7c": return "#7b1fa2"; // Dark purple
            default: return color;
        }
    }
}