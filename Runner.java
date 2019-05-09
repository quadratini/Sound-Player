import musicplayer.MusicPlayer;
import musicplayer.MusicPlayerController;
import musicplayer.MusicPlayerGUI;

public class Runner {
    public static void main(String[] args) {
        MusicPlayerController controller;
        MusicPlayer model;
        MusicPlayerGUI view;

        model = new MusicPlayer();
        view = new MusicPlayerGUI();
        controller = new MusicPlayerController(model, view);
    }
}
