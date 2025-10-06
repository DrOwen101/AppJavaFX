
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class SettingsManager 
{
    private static SettingsManager instance;

    private BooleanProperty dyslexiaFontEnabled = new SimpleBooleanProperty(false);
    private BooleanProperty dateTimeVisible = new SimpleBooleanProperty(false);
    private BooleanProperty colorblindModeEnabled = new SimpleBooleanProperty(false);

    private SettingsManager() {}

    public static synchronized SettingsManager getInstance()
    {
        if (instance == null)
        {
            instance = new SettingsManager();
        }
        return instance;
    }
    public BooleanProperty dyslexiaFontProperty()
    {
        return dyslexiaFontEnabled;
    }
    public BooleanProperty dateTimeVisibleProperty()
    {
        return dateTimeVisible;
    }
    public BooleanProperty colorblindModeProperty()
    {
        return colorblindModeEnabled;
    }
    public boolean isDyslexiaFontEnabled()
    {
        return dyslexiaFontEnabled.get();
    }
    public void setDyslexiaFontEnabled(boolean enabled)
    {
        this.dyslexiaFontEnabled.set(enabled);
    }
    public boolean isDateTimeVisible()
    {
        return dateTimeVisible.get();
    }
    public void setDateTimeVisible(boolean enabled)
    {
        this.dateTimeVisible.set(enabled);
    }
    public boolean isColorblindModeEnabled()
    {
        return colorblindModeEnabled.get();
    }
    public void setColorblindModeEnabled(boolean enabled)
    {
        this.colorblindModeEnabled.set(enabled);
    }
}
