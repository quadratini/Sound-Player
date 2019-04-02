import javax.swing.*;

public class SoundMain {

    public static void main(String[] args) {
        initFrame();
    }



    public static void initFrame() {
        JFrame frame = new JFrame("Sound Player");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,500);


        JButton playButton = new JButton("Play");
        frame.add(playButton);


        frame.setVisible(true);

    }


}

