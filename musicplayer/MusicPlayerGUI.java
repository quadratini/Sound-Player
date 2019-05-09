package musicplayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// Music player view class, contains all the visual elements, but no business logic or model data
public class MusicPlayerGUI {
    // Constants
    public static final Color BACKGROUND_COLOR = new Color(42, 45, 50);
    public static final Color SELECTED_COLOR = new Color(165,219,241);
    public static final Color UNSELECTED_COLOR = Color.white;

    // Private properties
    private JFrame frame;
    private JPanel playControlsPanel;
    private JButton playButton;
    private JButton nextButton;
    private JButton previousButton;
    private JTable songTable;
    private JLabel songElapsedLabel;
    private JScrollPane songScrollPane;

    // Getters (generated with IDEA)
    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPlayControlsPanel() {
        return playControlsPanel;
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getPreviousButton() {
        return previousButton;
    }

    public JTable getSongTable() {
        return songTable;
    }

    public JLabel getSongElapsedLabel() {
        return songElapsedLabel;
    }

    public JScrollPane getSongScrollPane() {
        return songScrollPane;
    }

    public MusicPlayerGUI() {
        //Frame:
        frame = new JFrame("Music Player");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 600);
        frame.setMinimumSize(new Dimension(frame.getSize()));
        frame.getContentPane().setBackground(BACKGROUND_COLOR);


        //Time Label:
        songElapsedLabel = new JLabel(":");

        //Table:
        songTable = new JTable();
        songTable.setSize(200,400);
        songTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //Scroll:
        songScrollPane = new JScrollPane(songTable);
        songScrollPane.setPreferredSize(songTable.getSize());

        // create and add buttons to a panel for play controls
        playControlsPanel = new JPanel(new FlowLayout());
        playButton = new JButton("Play");
        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");
        playControlsPanel.add(previousButton);
        playControlsPanel.add(playButton);
        playControlsPanel.add(nextButton);


        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        frame.add(songScrollPane, c); //TAKE THIS SCROLL

        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        songElapsedLabel.setForeground(Color.white);
        frame.add(songElapsedLabel, c);

        c.gridy = 3;
        frame.add(playControlsPanel, c);
        frame.setVisible(true);
    }
}
