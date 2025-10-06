| Feature / Design           | **Bella**                                                | **Jake**                                         | **Lucas**                                              |
| -------------------------- | -------------------------------------------------------- | ------------------------------------------------ | ------------------------------------------------------ |
| **Header Layout**          | VBox centered                                            | HBox top bar (header + settings)                 | VBox centered                                          |
| **Settings Entry**         | `View Settings` button in button stack                   | ⚙ Top-right button in HBox                       | `⚙️ Settings` button in button stack                   |
| **Settings Logic**         | Opens `SettingsGUI`, toggles dark mode, updates theme    | Opens `AppointmentManagerGUI` with dummy patient | Opens `SettingsPanel`, behavior unknown                |
| **ThemeManager**           | ✅ Yes (dark/light toggle + stylesheet reload)           | ❌ No                                           | ❌ No                                                   |
| **Number of Main Buttons** | 4 (Form, Check-in, View, Settings)                       | 3 (Form, Check-in, View)                         | 4 (Form, Check-in, View, Settings)                     |
| **Layout Style**           | Clean, structured, complete                              | Creative layout w/ HBox                          | Simple and balanced                                    |
| **Code Completeness**      | High - good modularity and styling                       | Moderate - less features, more layout variety    | Moderate - clean but minimal settings logic            |
| **Use of CSS**             | Applies theme-specific CSS                               | No mention                                       | No mention                                             |
| **Popup Use**              | ✅ Check-in popup                                         | ✅ Check-in popup                                 | ✅ Check-in popup                                       |
| **Settings Handling**      | Reads and applies preferences (dark mode, notifications) | None                                             | Opens settings panel, not integrated with theme system |
