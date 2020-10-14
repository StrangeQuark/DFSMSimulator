import javax.swing.*;
import java.awt.*;

public class DFSMPanel extends JPanel
{
    private DFSMSimulator dfsmSimulator;
    private Dimension screenSize;
    private JButton executeButton;
    private JMenuBar menuBar;
    private DrawCanvas canvasPanel;

    private int numberOfStates;
    private int[] alphabets;
    private int[] states;
    private int[][] functions;
    private int numberOfFunctions;
    private int currentState;

    public DFSMPanel(TextPanel d, TextPanel s, ConsolePanel c)
    {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        canvasPanel = new DrawCanvas();

        canvasPanel.setPreferredSize(new Dimension(3 * (screenSize.width / 4), 3 * (screenSize.height / 4)));

        executeButton = new JButton("Execute");
        executeButton.addActionListener(e -> {
            if (d.getFile() != null && s.getFile() != null)
            {
                try
                {
                    c.clearTextArea();
                    dfsmSimulator = new DFSMSimulator(d.getFile(), s.getFile());
                    numberOfStates = dfsmSimulator.getNumberOfStates();
                    alphabets = dfsmSimulator.getAlphabets();
                    states = dfsmSimulator.getStates();
                    functions = dfsmSimulator.getFunctions();
                    numberOfFunctions = dfsmSimulator.getNumberOfFunctions();
                    currentState = dfsmSimulator.getCurrentState();
                    canvasPanel.paintComponent(canvasPanel.getGraphics());
                    canvasPanel.revalidate();
                } catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            } else
                System.out.println("*******ERROR*******\n" +
                        "DFSM file is null: " + (d.getFile() == null) +
                        "\nString file is null: " + (s.getFile() == null) + "\n*******ERROR*******\n");
        });

        menuBar = new JMenuBar();
        menuBar.add(executeButton);

        add(menuBar, BorderLayout.NORTH);
        add(canvasPanel);
    }

    private class DrawCanvas extends JPanel
    {
        int[][] functionsCounter = new int[10][10];

        @Override
        public void paintComponent(Graphics g)
        {
            String statesString;
            super.paintComponent(g);
            setBackground(Color.WHITE);

            if(numberOfStates > 0)
            {
                g.setColor(new Color(0, 180, 0));
                g.fillOval((150 * (currentState + 1)), 450, 80, 80);
            }
            g.setColor(Color.BLACK);
            for (int i = 0; i < numberOfStates; i++)
            {
                statesString = "q" + i;
                g.drawString(statesString, (150 * (i + 1)) + 34, 495);
                if (states[i] == 0)
                    g.drawOval(150 * (i + 1), 450, 80, 80);
                else
                {
                    g.drawOval(150 * (i + 1), 450, 80, 80);
                    g.drawOval((150 * (i + 1)) + 10, 460, 60, 60);
                }
            }

            g.setColor(Color.BLACK);
            for(int i = 0; i < numberOfFunctions; i++)
            {
                if(functions[i][0] < functions[i][2])
                {
                    g.drawArc((150 * (functions[i][0] + 1)) + 80, 415, (70 * (functions[i][2] - functions[i][0])) + (80 * (functions[i][2] - functions[i][0] - 1)), 150, 0, 180);
                    g.drawString(Integer.toString(functions[i][1]), (150 * (functions[i][0] + 1)) + 80 + (((70 * (functions[i][2] - functions[i][0])) + (80 * (functions[i][2] - functions[i][0] - 1))) / 2) + (10 * functionsCounter[functions[i][0]][functions[i][2]]), 405);
                }
                if(functions[i][0] > functions[i][2])
                {
                    g.drawArc((150 * (functions[i][2] + 1)) + 80, 415, (70 * (functions[i][0] - functions[i][2])) + (80 * (functions[i][0] - functions[i][2] - 1)), 150, 180, 180);
                    g.drawString(Integer.toString(functions[i][1]), (150 * (functions[i][2] + 1)) + 80 + (((70 * (functions[i][0] - functions[i][2])) + (80 * (functions[i][0] - functions[i][2] - 1))) / 2) + (10 * functionsCounter[functions[i][0]][functions[i][2]]), 580);
                }
                if(functions[i][0] == functions[i][2])
                {
                    g.drawArc((150 * (functions[i][0] + 1)) + 33, 352, 20, 200, 0, 179);
                    g.drawString(Integer.toString(functions[i][1]), (150 * (functions[i][2] + 1)) + 80 + (((70 * (functions[i][0] - functions[i][2])) + (80 * (functions[i][0] - functions[i][2] - 1))) / 2) + (10 * functionsCounter[functions[i][0]][functions[i][2]]), 340);
                }
                functionsCounter[functions[i][0]][functions[i][2]]++;
            }
            functionsCounter = new int[10][10];
        }
    }
}
