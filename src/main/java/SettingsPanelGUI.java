
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SettingsPanelGUI
{
    
    private Stage stage;
    private VBox mainLayout;
    private VBox contentArea;
    private Label titleLabel;
    private Stage primaryStage;
    private Text dateTimeText;
    private VBox root;
    private Font defaultTitleFont = Font.font("Segoe UI", 24);
    private Font dyslexicTitleFont = Font.font("Verdana", 28);
    private Font defaultFont = Font.font("Segoe UI", 12);
    private Font dyslexiaFont = Font.font("Verdana", 16);
    private CheckBox btnToggleDyslexiaFont;
    private CheckBox btnToggleDateTime;
    private CheckBox btnToggleColorblind;

    public SettingsPanelGUI()
    {
        root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background: linear-gradient(to bottom, #e8f5e8, #f0f9f0);");

        VBox card = new VBox(20);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 5);" +
            "-fx-border-color: #d4edda;" +
            "-fx-border-width: 1;"
        );

        titleLabel = new Label("Settings");
        titleLabel.setFont(defaultTitleFont);
        titleLabel.setStyle("-fx-text-fill: #1b5e20");
        
        btnToggleDyslexiaFont = new CheckBox("Toggle Dyslexia Display");
        btnToggleDateTime = new CheckBox("Toggle Date/Time Display");
        btnToggleColorblind = new CheckBox("Toggle Colorblind Mode");

        styleCheckbox(btnToggleColorblind);
        styleCheckbox(btnToggleDateTime);
        styleCheckbox(btnToggleDyslexiaFont);

        SettingsManager settings = SettingsManager.getInstance();
        btnToggleDyslexiaFont.selectedProperty().bindBidirectional(settings.dyslexiaFontProperty());
        btnToggleColorblind.selectedProperty().bindBidirectional(settings.colorblindModeProperty());
        btnToggleDateTime.selectedProperty().bindBidirectional(settings.dateTimeVisibleProperty());

        dateTimeText = new Text();
        dateTimeText.setVisible(false);
        dateTimeText.setFont(defaultFont);
        dateTimeText.setStyle("-fx-fill: #21232eff");

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> updateDateTime())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        settings.dyslexiaFontProperty().addListener((obs, oldVal, newVal) -> toggleDyslexiaFont());
        settings.colorblindModeProperty().addListener((obs, oldVal, newVal) -> toggleColorblindMode());
        settings.dateTimeVisibleProperty().addListener((obs, oldVal, newVal) -> dateTimeText.setVisible(newVal));
        card.getChildren().addAll(
                titleLabel,
                btnToggleDyslexiaFont,
                btnToggleColorblind,
                btnToggleDateTime,
                dateTimeText
        );

        root.getChildren().add(card);
    }

    public VBox getContent()
    {
        return root;
    }

    private void toggleDyslexiaFont()
    {
        boolean enabled = SettingsManager.getInstance().dyslexiaFontProperty().get();
        Font font = enabled ? dyslexiaFont : defaultFont;
        Font f = enabled ? dyslexicTitleFont : defaultTitleFont;
        applyFont(font);
        applyTitleFont(f);
    }
    private void toggleColorblindMode()
    {
        boolean enabled = SettingsManager.getInstance().colorblindModeProperty().get();
        if (enabled)
        {
            root.setStyle("-fx-background-color: #0d0a46ff;");
            applyTextColor("#00000");
            
        }
        else
        {
            root.setStyle("-fx-background: linear-gradient(to bottom, #e8f5e8, #f0f9f0); -fx-text-fill: #0d180eff;");
        }

    }
    private void applyTextColor(String color)
    {
        titleLabel.setStyle("-fx-text-fill: " + color);
        dateTimeText.setStyle("-fx-fill: " + color);
        btnToggleColorblind.setStyle("-fx-text-fill: " + color);
        btnToggleDateTime.setStyle("-fx-text-fill: " + color);
        btnToggleDyslexiaFont.setStyle("-fx-text-fill: " + color);

    }
    private void updateDateTime()
    {
        boolean show = SettingsManager.getInstance().dateTimeVisibleProperty().get();
        if (show)
        {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTimeText.setText("Currently: " + now.format(formatter));
        }
    }
    private void applyFont(Font font)
    {
        dateTimeText.setFont(font);
        btnToggleColorblind.setFont(font);
        btnToggleDateTime.setFont(font);
        btnToggleDyslexiaFont.setFont(font);
    }
    private void applyTitleFont(Font font)
    {
        titleLabel.setFont(font);
    }
    private void styleCheckbox(CheckBox checkbox)
    {
        checkbox.setFont(defaultFont);
        checkbox.setStyle("-fx-text-fill: #21232eff");
    }
}
