package simpletexteditor.app.document;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.Scanner;

public class TextDocument {
    /**
     * ActionListener of parent class so that events may be passed up the hierarchy
     */
    private final ActionListener actionListener;
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
        FileReader in = null;
        try {
            in = new FileReader(file);
            Scanner scan = new Scanner(in);
            while (scan.hasNextLine())
                document.insertString(document.getLength(), scan.nextLine() + "\n", null);
        } catch (IOException | BadLocationException ex) {
            throw new RuntimeException(ex.getMessage() + " thrown in TextDocument(), this should not occur.");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    throw new RuntimeException("Failed to close " + in);
                }
            }
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
        FileWriter out = null;
        try {
            out = new FileWriter(file);
            out.write(document.getText(0, document.getLength()));
        } catch (BadLocationException | IOException ex) {
            throw new RuntimeException(ex.getMessage() + " thrown in TextDocument.save(), this should not occur.");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    throw new RuntimeException("Failed to close " + out);
                }
            }

        }
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
            actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
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
