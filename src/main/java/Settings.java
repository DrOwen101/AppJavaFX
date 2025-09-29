import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Application-wide settings singleton.
 * Uses JavaFX properties so UI can bind to them directly.
 */
public class Settings {
    private static Settings instance = null;

    // Example settings
    private final BooleanProperty enableLogging = new SimpleBooleanProperty(true);
    private final StringProperty theme = new SimpleStringProperty("Light");

    private Settings() {}

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public BooleanProperty enableLoggingProperty() { return enableLogging; }
    public boolean isEnableLogging() { return enableLogging.get(); }
    public void setEnableLogging(boolean v) { enableLogging.set(v); }

    public StringProperty themeProperty() { return theme; }
    public String getTheme() { return theme.get(); }
    public void setTheme(String t) { theme.set(t); }
}
