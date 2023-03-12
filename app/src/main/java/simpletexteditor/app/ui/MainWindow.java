package simpletexteditor.app.ui;

import simpletexteditor.app.ui.dialog.AboutDialog;
import simpletexteditor.app.ui.menu.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements ActionListener {
    /**
     * JPanel used as top-level container of UI components
     */
    private final JPanel rootPanel;
    /**
     * JSrollPane containing the text editor
     */
    private final EditorPane editorPane;
    /**
     * Menu bar at the top of the window
     */
    private final MenuBar menuBar;
    /**
     * JFrame containing the top-level window
     */
    private JFrame frame;
    /**
     * View-only pane to render the line numbers
     */
    private JEditorPane lineNumberPane;

    public MainWindow() {
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setPreferredSize(new Dimension(400, 400));
        rootPanel.setMinimumSize(new Dimension(200, 200));

        editorPane = new EditorPane(this);
        rootPanel.add(editorPane, BorderLayout.CENTER);

        menuBar = new MenuBar(this);
    }

    /**
     * Create frame and start UI
     *
     * @param title Window title
     */
    public void run(String title) {
        frame = new JFrame(title);
        frame.setContentPane(rootPanel);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Try to close window, checks for unsaved changes and aborts if necessary
     */
    private void exit() {
        // TODO: add checks for unsaved changes and such.
        frame.dispose();
    }

    private void createOpenDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open file");
        // prevent file chooser window from getting too small
        fileChooser.setMinimumSize(fileChooser.getSize()); //doesn't work, need to extend JFileChooser class
        int state = fileChooser.showOpenDialog(frame);
        switch (state) {
            case JFileChooser.CANCEL_OPTION:
                System.out.println("pressed cancel");
                break;
            case JFileChooser.APPROVE_OPTION:
                System.out.println("Open:" + fileChooser.getSelectedFile());
                break;
            default:
            case JFileChooser.ERROR_OPTION:
                System.out.println("dialog dismissed");
        }
    }

    /**
     * Invoked when the UI is interacted with.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == menuBar.fileMenu.newItem) {
            editorPane.inputPane.setText("");
        } else if (source == menuBar.fileMenu.openItem) {
//            OpenDialog open = new OpenDialog(frame, this);
//            open.setVisible(true);
            createOpenDialog();
        } else if (source == menuBar.fileMenu.exitItem) {
            exit();
        } else if (source == menuBar.helpMenu.aboutItem) {
            AboutDialog about = new AboutDialog(frame);
            about.setVisible(true);
        } else {
            System.out.println("other source");
        }
    }
}
