import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class SoundMain {

    public static void main(String[] args) {
        initFrame();
    }

    private static void initFrame() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel flow = new JPanel(new FlowLayout());
        JLabel songName = new JLabel("Test");

        panel.setBackground(Color.RED);
        flow.setBackground(Color.DARK_GRAY);

        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 0;

        JFrame frame = new JFrame("Sound Player");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        JButton playButton = new JButton("Play");
        JButton nextButton = new JButton("Next");
        JButton previousButton = new JButton("Previous");

        JTextPane textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(200,400));
        textPane.setEditable(false);

        c.gridwidth = 2;
        c.gridheight = 2;
        panel.add(textPane, c);
        textPane.add(songName);

        flow.add(previousButton);
        flow.add(playButton);
        flow.add(nextButton);

        frame.add(panel);
        c.gridy = 1;
        frame.add(flow, c);
        frame.setVisible(true);

    }


}

