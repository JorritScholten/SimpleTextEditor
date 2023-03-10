package simpletexteditor.app.ui;

import simpletexteditor.app.ui.dialog.AboutDialog;
import simpletexteditor.app.ui.menu.FileMenu;
import simpletexteditor.app.ui.menu.HelpMenu;

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
     * JToolBar that sits at the bottom of window to show file info
     */
    private final JToolBar bottomToolBar;
    /**
     * JScrollPane to allow editor to scroll
     */
    private final JScrollPane scrollPane;
    /**
     * JPanel to contain the multiple JEditorPanes next to each other
     */
    private final JPanel editorPanel;
    /**
     * JEditorPane where the actual text editing takes place
     */
    private final JEditorPane inputPane;
    /**
     * Menu bar at the top of the window
     */
    private final JMenuBar menuBar;
    /**
     * Menu with file options
     */
    private final FileMenu fileMenu;
    /**
     * Menu with help options
     */
    private final HelpMenu helpMenu;
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

        menuBar = new JMenuBar();
        fileMenu = new FileMenu(this);
        menuBar.add(fileMenu);
        helpMenu = new HelpMenu(this);
        menuBar.add(helpMenu);
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
     * Try to close window, checks for unsaved changes and aborts if necessary.
     */
    private void exit() {
        // TODO: add checks for unsaved changes and such.
        frame.dispose();
    }

    /**
     * Invoked when the UI is interacted with.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == fileMenu.newItem) {
            inputPane.setText("");
        } else if (source == fileMenu.exitItem) {
            exit();
        } else if (source == helpMenu.aboutItem) {
            AboutDialog about = new AboutDialog(frame);
            about.setVisible(true);
        }
    }
}
