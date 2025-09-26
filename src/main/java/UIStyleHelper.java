import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Utility class for consistent UI styling across the application
 */
public class UIStyleHelper {
    
    // Color constants
    public static final String PRIMARY_GREEN = "#2e7d32";
    public static final String LIGHT_GREEN_BG = "#e8f5e8";
    public static final String VERY_LIGHT_GREEN_BG = "#f1f8e9";
    public static final String WHITE = "white";
    public static final String BORDER_GREEN = "#c8e6c9";
    public static final String LIGHT_GRAY_BG = "#fafafa";
    
    // Button colors
    public static final String SEARCH_BUTTON_PRIMARY = "#2196f3";
    public static final String SEARCH_BUTTON_HOVER = "#1976d2";
    public static final String SUCCESS_BUTTON_PRIMARY = "#4caf50";
    public static final String SUCCESS_BUTTON_HOVER = "#388e3c";
    public static final String CANCEL_BUTTON_PRIMARY = "#f44336";
    public static final String CANCEL_BUTTON_HOVER = "#d32f2f";
    
    /**
     * Create a styled button with consistent appearance
     */
    public static Button createStyledButton(String text, String primaryColor, String hoverColor) {
        Button button = new Button(text);
        button.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
        button.setPrefWidth(180);
        button.setPrefHeight(45);
        
        // Base styling
        button.setStyle(
            "-fx-background-color: " + primaryColor + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 22;" +
            "-fx-border-radius: 22;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
            "-fx-cursor: hand;"
        );
        
        // Hover effects
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: " + hoverColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 22;" +
                "-fx-border-radius: 22;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 4);" +
                "-fx-cursor: hand;" +
                "-fx-scale-x: 1.02;" +
                "-fx-scale-y: 1.02;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: " + primaryColor + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 22;" +
                "-fx-border-radius: 22;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
                "-fx-cursor: hand;" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;"
            )
        );
        
        return button;
    }
    
    /**
     * Get the standard card container style
     */
    public static String getCardStyle() {
        return "-fx-background-color: " + WHITE + ";" +
               "-fx-background-radius: 15;" +
               "-fx-border-radius: 15;" +
               "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);";
    }
    
    /**
     * Get the standard input field style
     */
    public static String getInputFieldStyle() {
        return "-fx-padding: 10;" +
               "-fx-background-radius: 8;" +
               "-fx-border-radius: 8;" +
               "-fx-border-color: " + BORDER_GREEN + ";" +
               "-fx-border-width: 2;";
    }
    
    /**
     * Get the standard list view style
     */
    public static String getListViewStyle() {
        return "-fx-background-color: " + LIGHT_GRAY_BG + ";" +
               "-fx-background-radius: 8;" +
               "-fx-border-radius: 8;" +
               "-fx-border-color: " + BORDER_GREEN + ";" +
               "-fx-border-width: 1;";
    }
    
    /**
     * Get the standard section title style
     */
    public static String getSectionTitleStyle() {
        return "-fx-text-fill: " + PRIMARY_GREEN + ";";
    }
    
    /**
     * Get the main background gradient style
     */
    public static String getMainBackgroundStyle() {
        return "-fx-background-color: linear-gradient(to bottom, " + LIGHT_GREEN_BG + ", " + VERY_LIGHT_GREEN_BG + ");";
    }
}