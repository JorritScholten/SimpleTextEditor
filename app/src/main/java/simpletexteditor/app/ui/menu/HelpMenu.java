package simpletexteditor.app.ui.menu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class HelpMenu extends JMenu {
    public JMenuItem aboutItem;

    public HelpMenu(ActionListener listener) {
        // set menu name & description
        super("Help");
        getAccessibleContext().setAccessibleDescription("Help menu");
        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(listener);
        add(aboutItem);
    }
}
