import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class ConsolePanel extends JPanel
{
    private Dimension screenSize;
    private JTextArea textArea;

    public ConsolePanel()
    {
        redirectSystemStreams();

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setLayout(new BorderLayout());

        textArea = new JTextArea();

        setPreferredSize(new Dimension(screenSize.width / 4, screenSize.height / 8));

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void clearTextArea()
    {
        textArea.setText("");
    }

    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textArea.append(text);
            }
        });
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException
            {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
}
