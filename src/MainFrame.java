import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    private Dimension screenSize;
    private DFSMPanel viewPanel;
    private TextPanel DFSMtextPanel;
    private TextPanel stringPanel;
    private ConsolePanel consolePanel;
    private JPanel textPanels;
    private JPanel viewPanels;

    public MainFrame()
    {
        setTitle("DFSM Simulator");

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        textPanels = new JPanel();
        DFSMtextPanel = new TextPanel(0);
        stringPanel = new TextPanel(1);

        textPanels.setLayout(new BoxLayout(textPanels, BoxLayout.Y_AXIS));
        textPanels.add(DFSMtextPanel);
        textPanels.add(stringPanel);

        viewPanels = new JPanel();
        consolePanel = new ConsolePanel();
        viewPanel = new DFSMPanel(DFSMtextPanel, stringPanel, consolePanel);

        viewPanels.setLayout(new BoxLayout(viewPanels, BoxLayout.Y_AXIS));
        viewPanels.add(viewPanel);
        viewPanels.add(consolePanel, BorderLayout.SOUTH);

        add(textPanels, BorderLayout.EAST);
        add(viewPanels);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}