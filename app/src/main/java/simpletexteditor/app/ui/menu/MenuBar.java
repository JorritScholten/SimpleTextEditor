package simpletexteditor.app.ui.menu;

import javax.swing.*;
import java.awt.event.ActionListener;

/** Menu bar at the top of the window */
public class MenuBar extends JMenuBar {
    /** Menu with file options */
    public final FileMenu fileMenu;
    /** Menu with help options */
    public final HelpMenu helpMenu;

    /**
     * Creates and populates menu bar at the top of the window
     * @param listener ActionListener needed to pass Actions back to parent
     */
    public MenuBar(ActionListener listener) {
        fileMenu = new FileMenu(listener);
        add(fileMenu);
        helpMenu = new HelpMenu(listener);
        add(helpMenu);
    }
}
