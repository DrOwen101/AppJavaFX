import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SettingsPanel {
	private final Stage stage;
	private final AppSetting settings;

	private ComboBox<String> fontFamilyBox;
	private ComboBox<String> fontColorBox;
	private Spinner<Double> fontSizeSpinner;
	private Label previewLabel;

	public SettingsPanel() {
		this(AppSetting.getInstance());
	}

	public SettingsPanel(AppSetting settings) {
		this.settings = settings;
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Settings");
		buildUI();
	}

	private void buildUI() {
		VBox root = new VBox(12);
		root.setPadding(new Insets(16));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f6f6f6);");

		// Font family selector
		fontFamilyBox = new ComboBox<>();
		// Add a short, useful set first, then all installed fonts to give choices
		fontFamilyBox.getItems().addAll("Segoe UI", "Arial", "Verdana", "Times New Roman", "Courier New");
		// Add installed fonts (avoid duplicates)
		for (String fam : Font.getFamilies()) {
			if (!fontFamilyBox.getItems().contains(fam)) {
				fontFamilyBox.getItems().add(fam);
			}
		}
		fontFamilyBox.setPrefWidth(300);
		fontFamilyBox.setValue(settings.getFontStyle() == null ? "Segoe UI" : settings.getFontStyle());

		// Font color selector (names map to CSS color values)
		fontColorBox = new ComboBox<>();
		fontColorBox.getItems().addAll("Black", "Dark Green", "Blue", "Red", "Gray", "Purple");
		fontColorBox.setValue(mapColorToName(settings.getFontColor()));

		// Font size spinner
		SpinnerValueFactory.DoubleSpinnerValueFactory vf = new SpinnerValueFactory.DoubleSpinnerValueFactory(8.0, 72.0, Math.max(8.0, settings.getFontSize()), 1.0);
		fontSizeSpinner = new Spinner<>(vf);
		fontSizeSpinner.setEditable(true);

		// Preview
		previewLabel = new Label("This is a preview of the selected font settings.");
		previewLabel.setWrapText(true);
		previewLabel.setPrefWidth(360);
		updatePreview();

		// Controls layout
		HBox famRow = new HBox(10, new Label("Font:"), fontFamilyBox);
		famRow.setAlignment(Pos.CENTER_LEFT);
		HBox colorRow = new HBox(10, new Label("Color:"), fontColorBox);
		colorRow.setAlignment(Pos.CENTER_LEFT);
		HBox sizeRow = new HBox(10, new Label("Size:"), fontSizeSpinner);
		sizeRow.setAlignment(Pos.CENTER_LEFT);

		// Buttons
		Button applyBtn = new Button("Apply");
		Button saveCloseBtn = new Button("Save & Close");
		Button cancelBtn = new Button("Cancel");

		HBox buttons = new HBox(10, applyBtn, saveCloseBtn, cancelBtn);
		buttons.setAlignment(Pos.CENTER_RIGHT);

		root.getChildren().addAll(famRow, colorRow, sizeRow, previewLabel, buttons);

		// Listeners
		fontFamilyBox.setOnAction(e -> updatePreview());
		fontColorBox.setOnAction(e -> updatePreview());
		fontSizeSpinner.valueProperty().addListener((obs, oldV, newV) -> updatePreview());

		applyBtn.setOnAction(e -> applyToSettings());
		saveCloseBtn.setOnAction(e -> { applyToSettings(); stage.close(); });
		cancelBtn.setOnAction(e -> stage.close());

		Scene scene = new Scene(root, 420, 260);
		stage.setScene(scene);
	}

	private void updatePreview() {
		String family = fontFamilyBox.getValue();
		double size = 12.0;
		try {
			size = fontSizeSpinner.getValue();
		} catch (Exception ex) {
			// ignore and fallback to default
		}
		previewLabel.setFont(Font.font(family, size));
		String cssColor = mapNameToColor(fontColorBox.getValue());
		previewLabel.setStyle("-fx-text-fill: " + cssColor + ";");
	}

	private void applyToSettings() {
		String family = fontFamilyBox.getValue();
		double size = fontSizeSpinner.getValue();
		String colorCss = mapNameToColor(fontColorBox.getValue());

		settings.setFontStyle(family);
		settings.setFontSize(size);
		settings.setFontColor(colorCss);
	}

	/**
	 * Show the settings panel window.
	 */
	public void show() {
		stage.show();
		stage.toFront();
	}

	private static String mapNameToColor(String name) {
		if (name == null) return "#000000";
		switch (name) {
			case "Dark Green": return "#1b5e20";
			case "Blue": return "#0d47a1";
			case "Red": return "#b71c1c";
			case "Gray": return "#616161";
			case "Purple": return "#6f42c1";
			case "Black":
			default: return "#000000";
		}
	}

	private static String mapColorToName(String css) {
		if (css == null) return "Black";
		switch (css.toLowerCase()) {
			case "#1b5e20": return "Dark Green";
			case "#0d47a1": return "Blue";
			case "#b71c1c": return "Red";
			case "#616161": return "Gray";
			case "#6f42c1": return "Purple";
			case "#000000":
			default: return "Black";
		}
	}
}
