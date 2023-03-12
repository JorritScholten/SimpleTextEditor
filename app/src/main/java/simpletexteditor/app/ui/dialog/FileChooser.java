package simpletexteditor.app.ui.dialog;

import javax.swing.*;
import java.awt.*;

public class FileChooser extends JFileChooser {
    @Override
    protected JDialog createDialog(Component parent) throws HeadlessException {
        JDialog dialog = super.createDialog(parent);
        dialog.pack();
        // prevent file chooser window from getting too small
        dialog.setMinimumSize(dialog.getSize());
        return dialog;
    }
}
