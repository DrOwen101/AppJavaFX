import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.util.Objects;

/**
 * Simple singleton to manage application-wide styling (theme + font size).
 * Keeps things minimal: switches between two CSS files and sets root font-size inline.
 */
public class AppStyleManager {
    private static final AppStyleManager INSTANCE = new AppStyleManager();

    private boolean darkMode = false;
    public enum SizeVariant { SMALL, NORMAL, LARGE }

    private SizeVariant sizeVariant = SizeVariant.NORMAL;

    private final String lightCss;
    private final String darkCss;
    private final String sizeSmallCss;
    private final String sizeNormalCss;
    private final String sizeLargeCss;

    private AppStyleManager() {
        // load styles from resources if available
        String l = null;
        String d = null;
        String sSmall = null;
        String sNormal = null;
        String sLarge = null;
        try {
            if (getClass().getResource("/styles/light.css") != null) {
                l = getClass().getResource("/styles/light.css").toExternalForm();
            }
            if (getClass().getResource("/styles/dark.css") != null) {
                d = getClass().getResource("/styles/dark.css").toExternalForm();
            }
            if (getClass().getResource("/styles/size-small.css") != null) {
                sSmall = getClass().getResource("/styles/size-small.css").toExternalForm();
            }
            if (getClass().getResource("/styles/size-normal.css") != null) {
                sNormal = getClass().getResource("/styles/size-normal.css").toExternalForm();
            }
            if (getClass().getResource("/styles/size-large.css") != null) {
                sLarge = getClass().getResource("/styles/size-large.css").toExternalForm();
            }
        } catch (Exception e) {
            // ignore, resources may not exist yet
        }
        lightCss = l;
        darkCss = d;
        sizeSmallCss = sSmall;
        sizeNormalCss = sNormal;
        sizeLargeCss = sLarge;
        // Apply to any existing windows at startup
        try {
            applyGlobally();
        } catch (Exception ignored) {}

        // Listen for new windows being added so we can auto-apply styles
        try {
            Window.getWindows().addListener((javafx.collections.ListChangeListener<Window>) change -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Window w : change.getAddedSubList()) {
                            if (w instanceof Stage) {
                                Stage s = (Stage) w;
                                // apply when scene is available or later when set
                                if (s.getScene() != null) applyToScene(s.getScene());
                                else {
                                    s.sceneProperty().addListener((obs, oldScene, newScene) -> {
                                        if (newScene != null) applyToScene(newScene);
                                    });
                                }
                            }
                        }
                    }
                }
            });
        } catch (Throwable ignored) {
            // Some JavaFX runtimes may not allow listening early; ignore safely
        }
    }

    public static AppStyleManager getInstance() {
        return INSTANCE;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean dark) {
        this.darkMode = dark;
    }

    public SizeVariant getSizeVariant() {
        return sizeVariant;
    }

    public void setSizeVariant(SizeVariant variant) {
        if (variant != null) this.sizeVariant = variant;
    }

    /**
     * Apply styles to all open windows (global effect).
     */
    public void applyGlobally() {
        for (Window w : Window.getWindows()) {
            if (w instanceof Stage) {
                Stage s = (Stage) w;
                Scene sc = s.getScene();
                if (sc != null) applyToScene(sc);
            }
        }
    }

    public void applyToStage(Stage stage) {
        if (stage == null) return;
        applyToScene(stage.getScene());
    }

    public void applyToScene(Scene scene) {
        if (scene == null) return;

        // Remove theme styles
        try {
            if (lightCss != null) scene.getStylesheets().remove(lightCss);
            if (darkCss != null) scene.getStylesheets().remove(darkCss);
        } catch (Exception ignored) {}

        // Add selected theme
        if (darkMode && darkCss != null) scene.getStylesheets().add(darkCss);
        else if (!darkMode && lightCss != null) scene.getStylesheets().add(lightCss);

        // Remove size styles
        try {
            if (sizeSmallCss != null) scene.getStylesheets().remove(sizeSmallCss);
            if (sizeNormalCss != null) scene.getStylesheets().remove(sizeNormalCss);
            if (sizeLargeCss != null) scene.getStylesheets().remove(sizeLargeCss);
        } catch (Exception ignored) {}

        // Add selected size stylesheet
        switch (Objects.requireNonNull(sizeVariant)) {
            case SMALL:
                if (sizeSmallCss != null) scene.getStylesheets().add(sizeSmallCss);
                break;
            case NORMAL:
                if (sizeNormalCss != null) scene.getStylesheets().add(sizeNormalCss);
                break;
            case LARGE:
                if (sizeLargeCss != null) scene.getStylesheets().add(sizeLargeCss);
                break;
        }

        // Also set a small inline style fallback for root (keeps compatibility)
        try {
            int px = 14;
            switch (sizeVariant) {
                case SMALL: px = 12; break;
                case NORMAL: px = 14; break;
                case LARGE: px = 18; break;
            }
            scene.getRoot().setStyle("-fx-font-size: " + px + "px;");
        } catch (Exception ignored) {}
    }
}
