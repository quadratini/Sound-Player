package musicplayer;

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

public class MusicPlayerController {
    // Constants
    public static final String ICON_PATH = "Icon/playIcon.png";

    // Private properties
    private MusicPlayer model;
    private MusicPlayerGUI view;

    public MusicPlayerController(MusicPlayer model, MusicPlayerGUI view) {
        this.model = model;
        this.view = view;

        // Set frame icon
        try {
            this.view.getFrame().setIconImage(ImageIO.read(new File(ICON_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up song display table model
        DefaultTableModel tableModel = new DefaultTableModel(0, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.view.getSongTable().setModel(tableModel);
        tableModel.addColumn("#");
        tableModel.addColumn("Name");
        try {
            File file = new File("music/");
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
            File[] musicFiles = file.listFiles();
            if (musicFiles == null) {
                throw new Exception("null file");
            }
            model.setFiles(musicFiles);
            for (int i = 0; i < musicFiles.length; i++) {
                File song = musicFiles[i];
                String songName = song.getName();
                String[] rowData = new String[]{i + 1 + " ", songName};
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Not sure if this belongs in view or controller
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, 1);
                if (row == model.getCurFileIndex()) {
                    cell.setBackground(MusicPlayerGUI.SELECTED_COLOR);
                } else {
                    cell.setBackground(MusicPlayerGUI.UNSELECTED_COLOR);
                }
                return cell;
            }
        };

        this.view.getSongTable().setDefaultRenderer(Object.class, tableCellRenderer);


        JTable songTable = this.view.getSongTable();
        songTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = songTable.rowAtPoint(e.getPoint());
                model.setCurIndexTo(row);
                songTable.repaint();
            }
        });

        //BUTTONS:
        JButton nextButton = view.getNextButton();
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.stopSound();
                model.nextSong();
                SoundPlayer.playSound(model.getCurSong());
                songTable.repaint();
            }
        });

        JButton previousButton = view.getPreviousButton();
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.stopSound();
                model.prevSong();
                SoundPlayer.playSound(model.getCurSong());
                songTable.repaint();
            }
        });


        JButton playButton = view.getPlayButton();
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playButton.getText() == "Play") {
                    SoundPlayer.stopSound();
                    SoundPlayer.playSound(model.getCurSong());
                    playButton.setText("Pause");

                    Clip clip = SoundPlayer.getClip();
                    clip.addLineListener(new LineListener() {
                        public void update(LineEvent e) {
                            if (e.getType() == LineEvent.Type.STOP) {
                                if (clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
                                    SoundPlayer.stopSound();
                                    playButton.setText("Play");
                                }
                            }
                        }
                    });
                } else {
                    SoundPlayer.pauseSound();
                    playButton.setText("Play");
                }
            }
        });

        // Schedule auto updater to update song elapsed time display
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        JLabel songElapsedLabel = this.view.getSongElapsedLabel();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Clip clip = SoundPlayer.getClip();
                long one = SoundPlayer.getTime();
                long two = 0;
                if (clip != null) {
                    one = clip.getMicrosecondPosition();
                    two = clip.getMicrosecondLength();
                }
                songElapsedLabel.setText(microsecondsToMinutes(one) + "/" + microsecondsToMinutes(two));
                songElapsedLabel.repaint();
            }
        }, 0, 5, TimeUnit.MILLISECONDS);
    }

    public static String microsecondsToMinutes(long microseconds) {
        long second = microseconds / 1000000;
        long minute = second / 60;
        second %= 60;
        return  (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
    }
}
