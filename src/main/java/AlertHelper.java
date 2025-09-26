import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * Utility class for showing consistent alert dialogs
 */
public class AlertHelper {
    
    /**
     * Show a warning alert dialog
     */
    public static void showWarning(String title, String message) {
        showAlert(Alert.AlertType.WARNING, title, message, null);
    }
    
    /**
     * Show a warning alert dialog with owner window
     */
    public static void showWarning(String title, String message, Window owner) {
        showAlert(Alert.AlertType.WARNING, title, message, owner);
    }
    
    /**
     * Show an information alert dialog
     */
    public static void showInformation(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message, null);
    }
    
    /**
     * Show an information alert dialog with owner window
     */
    public static void showInformation(String title, String message, Window owner) {
        showAlert(Alert.AlertType.INFORMATION, title, message, owner);
    }
    
    /**
     * Show an error alert dialog
     */
    public static void showError(String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message, null);
    }
    
    /**
     * Show an error alert dialog with owner window
     */
    public static void showError(String title, String message, Window owner) {
        showAlert(Alert.AlertType.ERROR, title, message, owner);
    }
    
    /**
     * Generic method to show alert dialog
     */
    private static void showAlert(Alert.AlertType type, String title, String message, Window owner) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        if (owner != null) {
            alert.initOwner(owner);
        }
        
        alert.showAndWait();
    }
}