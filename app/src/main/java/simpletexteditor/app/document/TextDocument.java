package simpletexteditor.app.document;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
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
    /**
     * ActionListener of parent class so that events may be passed up the hierarchy
     */
    private ActionListener actionListener;

    /**
     * Construct empty TextDocument class
     */
    public TextDocument(ActionListener listener) {
        document = new DefaultStyledDocument();
        document.addDocumentListener(new MyDocumentListener());
        actionListener = listener;
    }

    /**
     * Construct TextDocument class from file
     *
     * @param file a File object specifying a text document to populate the class with at initialisation
     */
    public TextDocument(ActionListener listener, File file) throws FileNotFoundException, AccessDeniedException {
        document = new DefaultStyledDocument();
        document.addDocumentListener(new MyDocumentListener());
        actionListener = listener;

        if (!file.isFile())
            throw new FileNotFoundException(file + " is not a file.");
        if (!file.canRead())
            throw new AccessDeniedException(file.getAbsolutePath());
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
        modified = false;
    }

    /**
     * Saves document to new file, this changes this.file
     *
     * @param file a File object specifying the text document to write to
     */
    public void saveAs(File file) throws IOException {
        if (file.isDirectory())
            throw new IOException(file.getAbsolutePath() + " needs to be a file, not a directory.");
        if (file.exists() & !file.canWrite())
            throw new AccessDeniedException(file.getAbsolutePath());
        this.file = new File(file.getAbsolutePath());
        save();
        name = this.file.getName();
    }

    /**
     * Saves document to file, throws exception when file == null
     */
    public void save() throws NullPointerException {
        if (file == null)
            throw new NullPointerException("file is undefined, call saveAs() instead.");
        //TODO: implement file saving
        documentModified(false);
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public boolean isModified() {
        return modified;
    }

    public boolean isUnsaved() {
        return file == null;
    }

    protected void documentModified(boolean isModified) {
        // only fire event if there is a change
        if (modified != isModified) {
            modified = isModified;
            actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, (String) null));
        }
    }

    //This one listens for any changes to the document.
    protected class MyDocumentListener implements DocumentListener {
        public void insertUpdate(DocumentEvent e) {
            documentModified(true);
        }

        public void removeUpdate(DocumentEvent e) {
            documentModified(true);
        }

        public void changedUpdate(DocumentEvent e) {
        }
    }
}
