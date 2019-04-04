import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SoundMain {
    private static File[] music = null;
    private static int curFileIndex = 0;
    private static File cur = null;

    public static void main(String[] args) {
        initFrame();
    }

    private static void initFrame() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel playboySex = new JPanel(new FlowLayout());

        //Buttons:
        JButton playButton = new JButton("Play");
        JButton nextButton = new JButton("Next");
        JButton previousButton = new JButton("Previous");

        //DISCORD:
        Color discord = new Color(42,45,50);

        //Frame:
        JFrame frame = new JFrame("Sound Player");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 500);
        frame.setMinimumSize(new Dimension(frame.getSize()));
        //Frame Icon:
        try {
            frame.setIconImage(ImageIO.read(new File("Icon/playIcon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Table:
        DefaultTableModel model = new DefaultTableModel(0, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setSize(200,400);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //Scroll:
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(table.getSize());

        model.addColumn("#");
        model.addColumn("Name");

        try {
            File file = new File("music/sex");
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            music = file.listFiles();
            if (music == null) {
                throw new Exception("null file");
            }
            for (int i = 0; i < music.length; i++) {
                File song = music[i];
                String songName = song.getName();
                String[] rowData = new String[] {i + 1 + " ", songName};
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //BUTTONS:
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (curFileIndex == music.length - 1) {
                    curFileIndex = -1;
                }
                curFileIndex++;
                cur = music[curFileIndex];
                System.out.println(cur);
                SoundManager.playSound(cur);
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (curFileIndex == 0) {
                    curFileIndex = music.length;
                }
                curFileIndex--;
                cur = music[curFileIndex];
                System.out.println(cur);
                SoundManager.playSound(cur);
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cur = music[curFileIndex];
                System.out.println(cur);
                SoundManager.playSound(cur);
            }
        });

        //scroll.getViewport().setBackground(Color.PINK);
        //table.setGridColor(Color.BLUE);
        //playboySex.setBackground(discord);
        //scroll.setBackground(discord);
        //playButton.setBackground(Color.RED);
        //table.setBackground(Color.GRAY);
        frame.getContentPane().setBackground(discord);

        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;

        // "hey ronny, u need to remove anywhere that adds text area because
        // it's now added to scroll pane."
        //panel.add(scroll, c);
        //panel.add(table, c);
        //frame.add(table, c);

        playboySex.add(previousButton);
        playboySex.add(playButton);
        playboySex.add(nextButton);

        frame.add(scroll, c); //TAKE THIS SCROLL
        c.gridy = 2;
        frame.add(playboySex, c);
        frame.setVisible(true);
    }

}
