//options for language, accessibility, and date and time display. Also a reset and save button
//settings do not currently change anything in the app, this is all design and structure for future use
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class settings {

    private static final Logger LOGGER = Logger.getLogger(settings.class.getName());
    private static settings instance;
    private final Properties properties;
    private static final String SETTINGS_FILE = "healthcare_settings.properties";

    //language setting
    private String language = "English";

    // accessibility settings
    private boolean enableAccessibilityFeatures = true;
    private boolean largeTextMode = false;

    //display settings
    private String dateFormat = "MM/dd/yyyy";
    private String timeFormat = "12-hour";

    //private constructor 
    private settings() {
        properties = new Properties();
        loadSettings();
    }

    public static settings getInstance() {
        if (instance == null) {
            instance = new settings();
        }
        return instance;
    }

    public void loadSettings() {
        try (InputStream input = new FileInputStream(SETTINGS_FILE)) {
            properties.load(input);
            language = properties.getProperty("language", language);
            enableAccessibilityFeatures = Boolean.parseBoolean(properties.getProperty("enableAccessibilityFeatures", String.valueOf(enableAccessibilityFeatures)));
            largeTextMode = Boolean.parseBoolean(properties.getProperty("largeTextMode", String.valueOf(largeTextMode)));
            dateFormat = properties.getProperty("dateFormat", dateFormat);
            timeFormat = properties.getProperty("timeFormat", timeFormat);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Could not load settings, using defaults: {0}", e.getMessage());
        }
    }

    public void saveSettings() {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILE)) {
            properties.setProperty("language", language);
            properties.setProperty("enableAccessibilityFeatures", String.valueOf(enableAccessibilityFeatures));
            properties.setProperty("largeTextMode", String.valueOf(largeTextMode));
            properties.setProperty("dateFormat", dateFormat);
            properties.setProperty("timeFormat", timeFormat);
            properties.store(output, null);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not save settings: {0}", e.getMessage());
        }
    }

    // Language getters
    public String getLanguage() { return language; }

    // accessibility getters
    public boolean isEnableAccessibilityFeatures() { return enableAccessibilityFeatures; }
    public boolean isLargeTextMode() { return largeTextMode; }

    // UI getters
    public String getDateFormat() { return dateFormat; }
    public String getTimeFormat() { return timeFormat; }

    // Language setters
    public void setLanguage(String language) { this.language = language; }

    // accessibility setters
    public void setEnableAccessibilityFeatures(boolean enableAccessibilityFeatures) { this.enableAccessibilityFeatures = enableAccessibilityFeatures; }
    public void setLargeTextMode(boolean largeTextMode) { this.largeTextMode = largeTextMode; }

    // UI setters
    public void setDateFormat(String dateFormat) { this.dateFormat = dateFormat; }
    public void setTimeFormat(String timeFormat) { this.timeFormat = timeFormat; }

    public void resetToDefaults() {
        language = "English";
        enableAccessibilityFeatures = true;
        largeTextMode = false;
        dateFormat = "MM/dd/yyyy";
        timeFormat = "12-hour";
    }

    //language options
    public String[] getLanguageOptions() {
        return new String[] {"English", "Spanish", "French", "Pig Latin"};
    }
    //accessibility options are simple true/false toggles, no need for options list

    //date and time format options
    public String[] getDateFormatOptions() {
        return new String[] {"MM/dd/yyyy", "dd/MM/yyyy", "yyyy-MM-dd"};
    }

    public String[] getTimeFormatOptions() {
        return new String[] {"12-hour", "24-hour"};
    }
}
