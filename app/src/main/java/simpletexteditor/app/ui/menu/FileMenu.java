package simpletexteditor.app.ui.menu;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FileMenu extends JMenu {
    public JMenuItem newItem;
    public JMenuItem exitItem;

    public FileMenu(ActionListener listener) {
        // set menu name & description
        super("File");
        getAccessibleContext().setAccessibleDescription("File menu");

        newItem = new JMenuItem("New");
        newItem.addActionListener(listener);
        add(newItem);
        addSeparator();

        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(listener);
        add(exitItem);
    }
}
