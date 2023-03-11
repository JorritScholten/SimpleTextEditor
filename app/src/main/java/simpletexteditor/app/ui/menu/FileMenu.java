package simpletexteditor.app.ui.menu;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * File menu definition
 */
public class FileMenu extends JMenu {
    public JMenuItem newItem;
    public JMenuItem openItem;
    public JMenuItem exitItem;

    /**
     * Creates and populates the File menu as part of the menu bar
     *
     * @param listener ActionListener needed to pass Actions back to parent
     */
    public FileMenu(ActionListener listener) {
        // set menu name & description
        super("File");
        getAccessibleContext().setAccessibleDescription("File menu");

        newItem = new JMenuItem("New");
        newItem.addActionListener(listener);
        add(newItem);

        openItem = new JMenuItem("Open");
        openItem.addActionListener(listener);
        add(openItem);
        addSeparator();

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(listener);
        add(exitItem);
    }
}
