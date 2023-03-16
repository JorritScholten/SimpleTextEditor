package simpletexteditor.app.ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** Dialog which displays information about project */
public class AboutDialog extends JDialog {
    private final String aboutText = "<html style=\"text-align:center;\">" +
            "This is a simple text editor with a Swing UI using Gradle as a build system.<br>" +
            "The goal of this project learn Java UI development.<br>" +
            "<br>" +
            "Author: <a href=\"https://github.com/JorritScholten\" target=\"_blank\">Jorrit Scholten</a><br>" +
            "Source: <a href=\"https://github.com/JorritScholten/SimpleTextEditor\" target=\"_blank\">https://github.com/JorritScholten/SimpleTextEditor</a><br>" +
            "</html>";
    private final JPanel contentPane;
    private final JButton closeButton;
    private final JTextPane aboutTextPane;

    /**
     * Creates dialog displaying information about project
     * @param owner Reference to parent frame needed so that parent can be locked
     */
    public AboutDialog(Frame owner) {
        super(owner, "About", ModalityType.APPLICATION_MODAL);

        aboutTextPane = new JTextPane();
        aboutTextPane.setContentType("text/html");
        aboutTextPane.setText(aboutText);
        aboutTextPane.setEditable(false);
        closeButton = new JButton("Close");
        contentPane = new JPanel(new BorderLayout(5, 5));
        contentPane.add(aboutTextPane, BorderLayout.CENTER);
        contentPane.add(closeButton, BorderLayout.PAGE_END);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(closeButton);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);

        // call dispose() when button is clicked
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        // call dispose() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call dispose() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
}
