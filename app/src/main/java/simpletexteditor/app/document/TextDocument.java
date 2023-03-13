package simpletexteditor.app.document;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Scanner;

public class TextDocument {
    public DefaultStyledDocument document;
    /**
     * Location of file on system
     */
    private File file = null;
    /**
     * Name of document
     */
    private String name = "Untitled";
    /**
     * Tracks whether document has unsaved changes
     */
    private boolean modified = false;
    private ActionListener actionListener;

    /**
     * Construct empty TextDocument class
     */
    public TextDocument(ActionListener listener) {
        document = new DefaultStyledDocument();
        document.addDocumentListener(new MyDocumentListener());
        actionListener = listener;
    }

//    /**
//     * Construct TextDocument class from file
//     *
//     * @param file a File object specifying a text document to populate the class with at initialisation
//     */
//    public TextDocument(File file) {
//        super();
//        this.file = file;
//    }

    public void openFile(File file) throws FileNotFoundException, AccessDeniedException {
        if (!file.isFile())
            throw new FileNotFoundException(file + " is not a file.");
        if (!file.canRead())
            throw new AccessDeniedException(file.getAbsolutePath());
        System.out.println("Opening:" + file);
        try {
            FileReader in = new FileReader(file);
            Scanner scan = new Scanner(in);
            while (scan.hasNextLine())
                document.insertString(document.getLength(), scan.nextLine() + "\n", null);
            in.close();
        } catch (IOException | BadLocationException ex) {
            System.out.println(ex);
        }
        this.file = new File(file.getAbsolutePath());
        name = this.file.getName();
        documentModified(false);
    }

    public String getName() {
        return name;
    }

    public boolean isModified() {
        return modified;
    }

    protected void documentModified(boolean isModified) {
        modified = isModified;
        actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, (String) null));
    }

    //This one listens for any changes to the document.
    protected class MyDocumentListener implements DocumentListener {
        public void insertUpdate(DocumentEvent e) {
            documentModified(true);
//            displayEditInfo(e);
        }

        public void removeUpdate(DocumentEvent e) {
            documentModified(true);
//            displayEditInfo(e);
        }

        public void changedUpdate(DocumentEvent e) {
            documentModified(true);
//            displayEditInfo(e);
        }

        private void displayEditInfo(DocumentEvent e) {
            Document document = e.getDocument();
            int changeLength = e.getLength();
            System.out.println(e.getType().toString() + ": " +
                    changeLength + " character" +
                    ((changeLength == 1) ? ". " : "s. ") +
                    " Text length = " + document.getLength() +
                    "." + "\n");
        }
    }
}
