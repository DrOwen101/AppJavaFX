
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ThemeManager {
    private static final Logger LOGGER = Logger.getLogger(ThemeManager.class.getName());
    private static boolean darkMode = false;

    // ThemeManager can store ANY GUI that implements Darkmode
    private static List<Darkmode> registeredGuis = new ArrayList<>();
    
    public static void registerGui(Darkmode gui) {
        registeredGuis.add(gui);
        LOGGER.info("GUI registered: " + gui.getClass().getSimpleName() + " (Total: " + registeredGuis.size() + ")");
        
        // Apply current theme to the newly registered GUI
        applyCurrentThemeToNewGui(gui);
    }

    public static void toggleTheme() {
        darkMode = !darkMode;
        for (Darkmode gui : registeredGuis) {
            if (darkMode) {
                gui.applyDarkMode();
            } else {
                gui.applyLightMode();
            }
        }
    }

    public static boolean isDarkMode() {
        return darkMode;
    }

    public static void setDarkMode(boolean darkModeValue) {
        ThemeManager.darkMode = darkModeValue;
        LOGGER.info("Setting theme to " + (darkModeValue ? "DARK" : "LIGHT") + " mode for " + registeredGuis.size() + " GUIs");
        
        for (Darkmode gui : registeredGuis) {
            try {
                if (darkModeValue) {
                    gui.applyDarkMode();
                } else {
                    gui.applyLightMode();
                }
                LOGGER.info("Theme applied to: " + gui.getClass().getSimpleName());
            } catch (Exception e) {
                LOGGER.warning("Failed to apply theme to " + gui.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    public static void unregisterGui(Darkmode gui) {
        boolean removed = registeredGuis.remove(gui);
        if (removed) {
            LOGGER.info("GUI unregistered: " + gui.getClass().getSimpleName() + " (Remaining: " + registeredGuis.size() + ")");
        } else {
            LOGGER.warning("Attempted to unregister GUI that wasn't registered: " + gui.getClass().getSimpleName());
        }
    }

    public static void applyCurrentThemeToNewGui(Darkmode gui) {
        if (darkMode) {
            gui.applyDarkMode();
        } else {
            gui.applyLightMode();
        }
    }
}