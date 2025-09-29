Settings panel added

Files added:
- src/main/java/Settings.java  - Application-wide settings singleton (JavaFX properties)
- src/main/java/SettingsPanel.java - A modal settings UI with a clean layout, Toggle, ChoiceBox, and Save/Cancel buttons

Changes:
- `Main.java` now includes a "⚙️ Settings" button on the main screen that opens the `SettingsPanel`.

Design notes:
- Visual hierarchy: header (title + subtitle), separator, content area (rows), action row.
- Interactive controls: ToggleButton for logging, ChoiceBox for theme selection, Save/Cancel buttons.
- Event handling: Save button writes values into `Settings` singleton. Toggle updates label on click.
- Best practices: Settings are centralized in a singleton using JavaFX properties so other UI can bind to them.

How to test manually:
1. Run the application (Main).
2. Click the Settings button on the main screen.
3. Toggle logging, choose a theme, click Save.
4. Observe that the settings are stored in memory; other parts of the app can read `Settings.getInstance()`.

Further improvements (optional):
- Persist settings to disk (preferences file).
- Expose settings to UI via bindings so the app updates immediately.
- Add unit tests for Settings model.
