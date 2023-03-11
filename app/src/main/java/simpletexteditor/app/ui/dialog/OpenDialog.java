package simpletexteditor.app.ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenDialog extends JDialog {
    private final JPanel contentPane;
    private final JFileChooser fileChooser;
    private final LayoutManager layoutManager;
    private final File userHome = new File(System.getProperty("user.home"));

    public OpenDialog(Frame owner, ActionListener listener) {
        super(owner, "Open file", ModalityType.APPLICATION_MODAL);
        layoutManager = new BorderLayout();
        contentPane = new JPanel(layoutManager);
        fileChooser = new JFileChooser(userHome);

        contentPane.add(fileChooser, BorderLayout.CENTER);
        setContentPane(contentPane);
        pack();
        setPreferredSize(getSize());
        setMinimumSize(getSize());
    }
}
