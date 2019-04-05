import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        frame.setSize(300, 600);
        frame.setMinimumSize(new Dimension(frame.getSize()));
        //Frame Icon:
        try {
            frame.setIconImage(ImageIO.read(new File("Icon/playIcon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Time Label:
        JLabel sex = new JLabel(":");

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

        DefaultTableCellRenderer dong = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {

                Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, 1);
                if (row == curFileIndex) {
                    cell.setBackground(new Color(165,219,241));
                } else {
                    cell.setBackground(Color.white);
                }
                return cell;
            }
        };
        table.setDefaultRenderer(Object.class, dong);

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

        //NUTTONS
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                setCurIndexTo(row);
                SoundManager.openFile(cur);
                table.repaint();
            }
        });

        //BUTTONS:
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (curFileIndex == music.length - 1) {
                    curFileIndex = -1;
                }
                setCurIndexTo(++curFileIndex);
                SoundManager.openFile(cur);
                //SoundManager.playSound(cur);

                table.repaint();
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (curFileIndex == 0) {
                    curFileIndex = music.length;
                }
                setCurIndexTo(--curFileIndex);
                SoundManager.openFile(cur);
                //SoundManager.playSound(cur);

                table.repaint();
            }
        });

        ScheduledExecutorService sexy = Executors.newScheduledThreadPool(1);
        sexy.scheduleAtFixedRate (new Runnable() {
            @Override
            public void run() {
                Clip clit = SoundManager.getClip();
                long one = SoundManager.getTime();
                long two = 0;
                if (clit != null) {
                    one = clit.getMicrosecondPosition();
                    two = clit.getMicrosecondLength();
                }
                sex.setText(microToMacro(one) + "/" + microToMacro(two));
                sex.repaint();
            }
        }, 0, 5, TimeUnit.MILLISECONDS);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playButton.getText() == "Play") {
                    SoundManager.playSound(cur);
                    playButton.setText("Pause");

                    Clip clit = SoundManager.getClip();
                    clit.addLineListener(new LineListener() {
                        public void update(LineEvent e) {

                            if (e.getType() == LineEvent.Type.STOP) {
                                if (clit.getMicrosecondPosition() >= clit.getMicrosecondLength()) {
                                    SoundManager.stopSound();
                                    playButton.setText("Play");
                                }
                            }
                        }
                    });
                } else {
                    SoundManager.pauseSound();
                    playButton.setText("Play");
                }
            }
        });

        frame.getContentPane().setBackground(discord);

        playboySex.add(previousButton);
        playboySex.add(playButton);
        playboySex.add(nextButton);

        // "hey ronny, u need to remove anywhere that adds text area because
        // it's now added to scroll pane."
        //panel.add(scroll, c);

        setCurIndexTo(curFileIndex);

        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        frame.add(scroll, c); //TAKE THIS SCROLL

        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        sex.setForeground(Color.white);
        frame.add(sex, c);

        c.gridy = 3;
        frame.add(playboySex, c);
        frame.setVisible(true);
    }

    public static String microToMacro(long micropenis) {
        long second = micropenis / 1000000;
        long minute = second / 60;
        second %= 60;
        return  (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
    }

    public static void setCurIndexTo(int index) {
        curFileIndex = index;
        cur = music[curFileIndex];

    }
}
