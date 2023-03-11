package simpletexteditor.app.ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
//        fileChooser.addActionListener(listener); // this sends all Action events to parent
        fileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                    dispose();
                } else if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                    System.out.println("Open:" + fileChooser.getSelectedFile());
                    dispose();
                }
            }
        });

        contentPane.add(fileChooser, BorderLayout.CENTER);
        setContentPane(contentPane);
        pack();
        setMinimumSize(getSize());
    }
}
