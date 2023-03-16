package simpletexteditor.app.ui.dialog;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class FileChooser extends JFileChooser {
    /** Constructs a JFileChooser pointing to the user's default directory. */
    public FileChooser() {
        super();
    }

    /**
     * Constructs a JFileChooser using the given File as the path.
     * @param currentDirectory a File object specifying the path to a file or directory
     */
    public FileChooser(File currentDirectory) {
        super(currentDirectory);
    }

    /**
     * Constructs a JFileChooser using the given FileSystemView.
     * @param fsv a FileSystemView
     */
    public FileChooser(FileSystemView fsv) {
        super(fsv);
    }

    /**
     * Constructs a JFileChooser using the given current directory and FileSystemView.
     * @param currentDirectory a File object specifying the path to a file or directory
     * @param fsv              a FileSystemView
     */
    public FileChooser(File currentDirectory, FileSystemView fsv) {
        super(currentDirectory, fsv);
    }

    @Override
    protected JDialog createDialog(Component parent) throws HeadlessException {
        JDialog dialog = super.createDialog(parent);
        dialog.pack();
        // prevent file chooser window from getting too small
        dialog.setMinimumSize(dialog.getSize());
        return dialog;
    }
}
