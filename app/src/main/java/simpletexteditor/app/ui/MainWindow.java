package simpletexteditor.app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
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
    private final JMenu fileMenu;
    /**
     * Menu with help options
     */
    private final JMenu helpMenu;
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
        JMenuItem menuItem;
        fileMenu = new JMenu("File");
        fileMenu.getAccessibleContext().setAccessibleDescription("File menu");
        menuItem = new JMenuItem("New");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputPane.setText("");
            }
        });
        fileMenu.add(menuItem);
        fileMenu.addSeparator();
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exit();
            }
        });
        fileMenu.add(menuItem);
        menuBar.add(fileMenu);
        helpMenu = new JMenu("Help");
        helpMenu.getAccessibleContext().setAccessibleDescription("Help menu");
        menuItem = new JMenuItem("About");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AboutDialog about = new AboutDialog(frame);
                about.setVisible(true);
            }
        });
        helpMenu.add(menuItem);
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
}
