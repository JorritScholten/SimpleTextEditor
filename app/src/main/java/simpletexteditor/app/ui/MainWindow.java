package simpletexteditor.app.ui;

import simpletexteditor.app.document.TextDocument;
import simpletexteditor.app.ui.dialog.AboutDialog;
import simpletexteditor.app.ui.dialog.FileChooser;
import simpletexteditor.app.ui.menu.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

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
     *
     * @param title Window title
     */
    public void run(String title) {
        frame = new JFrame(textDocument.getName());
        frame.setContentPane(rootPanel);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
    }

    /**
     * Try to close window, checks for unsaved changes and aborts if necessary
     */
    private void exit() {
        if (textDocument.isModified() & !textDocument.isUnsaved()) {
            int choice = JOptionPane.showOptionDialog(frame,
                    "Exit without saving changes?",
                    "Unsaved changes",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Exit", "Save changes", "Cancel"},
                    "Save changes");
            switch (choice) {
                case JOptionPane.NO_OPTION -> textDocument.save();
                case JOptionPane.CANCEL_OPTION -> {
                    return;
                }
            }
        } else if (textDocument.isModified() & textDocument.isUnsaved()) {
            int choice = JOptionPane.showOptionDialog(frame,
                    "Exit without saving untitled document?",
                    "Unsaved changes",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Save as", "Exit"},
                    "Save as");
            if (choice == JOptionPane.YES_OPTION)
                createSaveDialog(null);
        }
        frame.dispose();
    }

    /**
     * Creates an open file chooser dialog and handles instantiating the new document
     */
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

    /**
     * Creates a save file chooser dialog and handles saving the document
     */
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
     * Invoked when the UI is interacted with
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == menuBar.fileMenu.newItem) {
            // TODO: add safety checks
            if (textDocument.isModified() | textDocument.isUnsaved()) {
                // TODO: prompt user to save document
            }
            textDocument = new TextDocument(this);
            editorPane.inputPane.setStyledDocument(textDocument.document);
            frame.setTitle(textDocument.getName());
        } else if (source == menuBar.fileMenu.openItem) {
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
}
