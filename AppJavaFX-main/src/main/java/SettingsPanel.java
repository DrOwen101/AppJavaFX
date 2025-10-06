/**
 * Abstract class for settings panel
 */
public abstract class SettingsPanel {
    
    // Boolean for light or dark mode
    protected boolean darkMode;
    
    /**
     * Constructor for SettingsPanel
     */
    public SettingsPanel(boolean mode) {
       this.darkMode = mode; 
    }
    
    // Getters and Setters for light/dark mode
    public boolean getDarkMode() { return darkMode; }
    public void setDarkMode(boolean mode) { this.darkMode = mode; }
}