package simpletexteditor.app.ui;

import simpletexteditor.app.document.TextDocument;
import simpletexteditor.app.ui.dialog.AboutDialog;
import simpletexteditor.app.ui.dialog.FileChooser;
import simpletexteditor.app.ui.menu.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class MainWindow implements ActionListener, WindowListener {
    /** JPanel used as top-level container of UI components */
    private final JPanel rootPanel;
    /** JSrollPane containing the text editor */
    private final EditorPane editorPane;
    /** Menu bar at the top of the window */
    private final MenuBar menuBar;
    /** JFrame containing the top-level window */
    private JFrame frame;
    private TextDocument textDocument;

    public MainWindow() {
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setPreferredSize(new Dimension(400, 400));
        rootPanel.setMinimumSize(new Dimension(200, 200));

        textDocument = new TextDocument(this);

        editorPane = new EditorPane(this, textDocument.document);
        rootPanel.add(editorPane, BorderLayout.CENTER);

        menuBar = new MenuBar(this);
    }

    /**
     * Create frame and start UI
     * @param title Window title
     */
    public void run(String title) {
        frame = new JFrame(textDocument.getName());
        frame.setContentPane(rootPanel);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
    }

    /** Try to close window, checks for unsaved changes and aborts if necessary */
    private void exit() {
        if (promptUnsavedChanges(promptUnsavedChangesSource.EXIT_PROGRAM))
            return;
        frame.dispose();
        System.exit(0);
    }

    /** Creates an open file chooser dialog and handles instantiating the new document */
    private void createOpenDialog() {
        JFileChooser fileChooser = new FileChooser();
        fileChooser.setDialogTitle("Open file");
        int state = fileChooser.showOpenDialog(frame);
        switch (state) {
            case JFileChooser.CANCEL_OPTION:
                System.out.println("pressed cancel");
                break;
            case JFileChooser.APPROVE_OPTION:
                try {
                    textDocument = new TextDocument(this, fileChooser.getSelectedFile());
                    editorPane.inputPane.setStyledDocument(textDocument.document);
                    frame.setTitle(textDocument.getName());
                } catch (FileNotFoundException | AccessDeniedException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), ex.toString(), JOptionPane.ERROR_MESSAGE);
                }
                break;
            default:
            case JFileChooser.ERROR_OPTION:
                System.out.println("dialog dismissed");
        }
    }

    /** Creates a save file chooser dialog and handles saving the document */
    private void createSaveDialog(File file) {
        JFileChooser fileChooser = new FileChooser(file);
        fileChooser.setDialogTitle("Save file");
        int state = fileChooser.showSaveDialog(frame);
        if (state == JFileChooser.APPROVE_OPTION) {
            File f = new File(fileChooser.getSelectedFile().getAbsolutePath());
            if (f.exists()) {
                int choice = JOptionPane.showOptionDialog(frame,
                        "File already exists, please choose different name.",
                        "Overwrite file?",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new String[]{"Overwrite", "Save as", "Cancel"},
                        "Save as");
                switch (choice) {
                    case JOptionPane.YES_OPTION:
                        try {
                            textDocument.saveAs(f);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(frame, ex.getMessage());
                            createSaveDialog(f);
                        }
                        break;
                    case JOptionPane.NO_OPTION:
                        createSaveDialog(f);
                        break;
                    default:
                        break;
                }
            } else {
                try {
                    textDocument.saveAs(f);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                    createSaveDialog(f);
                }
            }
            frame.setTitle(textDocument.getName());
        }
    }

    /**
     * Function prompts user to save unsaved changes if there are any, cannot be called recursively.
     * @param calledFrom enum specifying where this function called from so that the prompted messages match the
     *                   context
     * @return true if calling method should return immediately without continuing
     */
    private boolean promptUnsavedChanges(promptUnsavedChangesSource calledFrom) {
        String[][] options = null;
        String[] message = null;
        String title = "Unsaved changes";
        switch (calledFrom) {
            case NEW_FILE:
                message = new String[]{"Create new without saving changes?",
                        "Create new without saving untitled document?"};
                options = new String[][]{{"Create new", "Save changes", "Cancel"},
                        {"Save as", "Create new", "Cancel"}};
                break;
            case OPEN_FILE:
                message = new String[]{"Open new document without saving changes?",
                        "Open new document without saving untitled document?"};
                options = new String[][]{{"Continue", "Save changes", "Cancel"},
                        {"Save as", "Continue", "Cancel"}};
                break;
            case EXIT_PROGRAM:
                message = new String[]{"Exit without saving changes?",
                        "Exit without saving untitled document?"};
                options = new String[][]{{"Exit", "Save changes", "Cancel"},
                        {"Save as", "Exit", "Cancel"}};
                break;
        }
        if (textDocument.isModified() & !textDocument.isUnsaved()) {
            int choice = JOptionPane.showOptionDialog(frame,
                    message[0],
                    title,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options[0],
                    options[0][1]);
            switch (choice) {
                case JOptionPane.NO_OPTION -> textDocument.save();
                case JOptionPane.CANCEL_OPTION, JOptionPane.CLOSED_OPTION -> {
                    return true;
                }
            }
        } else if (textDocument.isModified() & textDocument.isUnsaved()) {
            int choice = JOptionPane.showOptionDialog(frame,
                    message[1],
                    title,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options[1],
                    options[1][0]);
            switch (choice) {
                case JOptionPane.YES_OPTION -> createSaveDialog(null);
                case JOptionPane.CANCEL_OPTION, JOptionPane.CLOSED_OPTION -> {
                    return true;
                }
            }
        }
        return false;
    }

    /** Invoked when the UI is interacted with */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == menuBar.fileMenu.newItem) {
            if (promptUnsavedChanges(promptUnsavedChangesSource.NEW_FILE))
                return;
            textDocument = new TextDocument(this);
            editorPane.inputPane.setStyledDocument(textDocument.document);
            frame.setTitle(textDocument.getName());
        } else if (source == menuBar.fileMenu.openItem) {
            if (promptUnsavedChanges(promptUnsavedChangesSource.OPEN_FILE))
                return;
            createOpenDialog();
        } else if (source == menuBar.fileMenu.saveItem) {
            try {
                textDocument.save();
            } catch (NullPointerException ex) {
                createSaveDialog(null);
            }
        } else if (source == menuBar.fileMenu.saveAsItem) {
            createSaveDialog(textDocument.getFile());
        } else if (source == menuBar.fileMenu.exitItem) {
            exit();
        } else if (source == menuBar.helpMenu.aboutItem) {
            AboutDialog about = new AboutDialog(frame);
            about.setVisible(true);
        } else if (source == textDocument) {
            if (textDocument.isModified())
                frame.setTitle(textDocument.getName() + "*");
            else
                frame.setTitle(textDocument.getName());
        } else {
//            System.out.println("other source");
        }
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        exit();
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    private enum promptUnsavedChangesSource {
        NEW_FILE,
        OPEN_FILE,
        EXIT_PROGRAM
    }
}
