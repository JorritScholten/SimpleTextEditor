package simpletexteditor.app.ui;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;

/** JScrollPane containing the editor elements */
class EditorPane extends JScrollPane {
    /** JTextPane where the actual text editing takes place */
    public final JTextPane inputPane;
    /** JPanel to contain the multiple JEditorPanes next to each other */
    private final JPanel editorPanel;
    /** LayoutManager for editorPanel */
    private final LayoutManager editorLayout;
    /** View-only pane to render the line numbers (unused for now) */
    private JEditorPane lineNumberPane;

    /** Constructs a JScrollPane containing */
    public EditorPane(ActionListener listener, DefaultStyledDocument document) {
        inputPane = new JTextPane(document);
        editorLayout = new BorderLayout(0, 0);
        editorPanel = new JPanel(editorLayout);
        editorPanel.add(inputPane, BorderLayout.CENTER);
        setViewportView(editorPanel);
    }
}
