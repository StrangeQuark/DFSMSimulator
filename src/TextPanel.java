import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class TextPanel extends JPanel
{
    private Dimension screenSize;
    private JTextArea textArea;
    private JMenuBar menuBar;
    private JButton openButton;
    private JButton saveAsButton;
    private JButton saveButton;
    private JLabel DFSMTextAreaLabel = new JLabel("DFSM text file");
    private JLabel testStringTextAreaLabel = new JLabel("Test string text file");
    private JFileChooser fileChooser;
    private File file = null;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Path root = Paths.get(System.getProperty("user.dir")).getParent();

    public TextPanel(int i)
    {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(new BorderLayout());
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(root + "/textFiles"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text", "txt"));
        textArea = new JTextArea();
        menuBar = new JMenuBar();
        openButton = new JButton("Open");
        openButton.addActionListener(e -> {
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                file = fileChooser.getSelectedFile();
                try
                {
                    bufferedReader = new BufferedReader(new FileReader(file));
                    textArea.read(bufferedReader, null);
                    bufferedReader.close();
                    textArea.requestFocus();
                    saveButton.setEnabled(true);
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        saveAsButton = new JButton("Save As");
        saveAsButton.addActionListener(e -> {
            if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                file = new File(fileChooser.getSelectedFile() + ".txt");
                saveFile();
                saveButton.setEnabled(true);
            }
        });
        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> {
            if(file != null)
            {
                saveFile();
            }
            else
            {
                System.out.println("Save file is null\n");
            }
        });
        menuBar.add(openButton);
        menuBar.add(saveButton);
        menuBar.add(saveAsButton);
        if(i == 0)
            menuBar.add(DFSMTextAreaLabel);
        else
            menuBar.add(testStringTextAreaLabel);

        add(menuBar, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        setPreferredSize(new Dimension(screenSize.width / 4, screenSize.height / 2));

        textArea.setEditable(true);
    }

    public File getFile()
    {
        return file;
    }

    public void saveFile()
    {
        try
        {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            textArea.write(bufferedWriter);
            bufferedWriter.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
