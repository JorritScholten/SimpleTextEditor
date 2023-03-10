package simpletexteditor.app.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    /**
     * JFrame containing the top-level window
     */
    private JFrame frame;
    /**
     * JPanel used as top-level container of UI components
     */
    private JPanel rootPanel;
    /**
     * JToolBar that sits at the bottom of window to show file info
     */
    private JToolBar bottomToolBar;
    /**
     * JScrollPane to allow editor to scroll
     */
    private JScrollPane scrollPane;
    /**
     * JPanel to contain the multiple JEditorPanes next to each other
     */
    private JPanel editorPanel;
    /**
     * View-only pane to render the line numbers
     */
    private JEditorPane lineNumberPane;
    /**
     * JEditorPane where the actual text editing takes place
     */
    private JEditorPane inputPane;

    public MainWindow() {
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setPreferredSize(new Dimension(400, 400));
        rootPanel.setMinimumSize(new Dimension(200, 200));

        bottomToolBar = new JToolBar("Document information", SwingConstants.HORIZONTAL);
        bottomToolBar.setFloatable(false);
        JLabel testLabel = new JLabel("No document open");
        bottomToolBar.add(testLabel);

        inputPane = new JEditorPane("text/plain", null);
        LayoutManager editorLayout = new BorderLayout(0, 0);
        editorPanel = new JPanel(editorLayout);
        editorPanel.add(inputPane, BorderLayout.CENTER);
        scrollPane = new JScrollPane(editorPanel);

        rootPanel.add(scrollPane, BorderLayout.CENTER);
        rootPanel.add(bottomToolBar, BorderLayout.PAGE_END);
    }

    /**
     * Create frame and start UI
     *
     * @param title Window title
     */
    public void run(String title) {
        frame = new JFrame(title);
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
