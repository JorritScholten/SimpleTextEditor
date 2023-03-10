package simpletexteditor.app.ui.menu;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Help menu definition
 */
public class HelpMenu extends JMenu {
    public JMenuItem aboutItem;

    /**
     * Creates and populates the Help menu as part of the menu bar
     *
     * @param listener ActionListener needed to pass Actions back to parent
     */
    public HelpMenu(ActionListener listener) {
        // set menu name & description
        super("Help");
        getAccessibleContext().setAccessibleDescription("Help menu");
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(listener);
        add(aboutItem);
    }
}
