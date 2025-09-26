
public class AppSetting extends Setting {
    private static AppSetting instance = new AppSetting();

    private String fontColor = "#000000";
    private String fontStyle = "Segoe UI";
    private double fontSize = 12.0;

    private AppSetting() {}

    public static AppSetting getInstance() {
        return instance;
    }

    public String getFontColor() {
        return fontColor;
    }
    public void setFontColor(String color) {
        this.fontColor = color;
    }
    public String getFontStyle() {
        return fontStyle;
    }
    public void setFontStyle(String style) {
        this.fontStyle = style;
    }
    public double getFontSize() {
        return fontSize;
    }
    public void setFontSize(double size) {
        this.fontSize = size;
    }
}
