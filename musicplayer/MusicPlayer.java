package musicplayer;

import java.io.File;

// Music player model class, holds the state of the music player
public class MusicPlayer {
    private File[] musicFiles;
    private int curFileIndex;
    private File cur;

    public MusicPlayer() {
        musicFiles = null;
        curFileIndex = 0;
        cur = null;
    }

    public void prevSong() {
        if (curFileIndex == 0) {
            curFileIndex = musicFiles.length;
        }
        setCurIndexTo(--curFileIndex);
    }

    public void nextSong() {
        if (curFileIndex == musicFiles.length - 1) {
            curFileIndex = musicFiles.length;
        }
        setCurIndexTo(++curFileIndex);
    }

    public File[] getAllSongs() {
        return musicFiles;
    }

    public void unsetCurFileIndex() {
        curFileIndex = -1;
    }

    public File getCurSong() {
        return cur;
    }

    public int getCurFileIndex() {
        return curFileIndex;
    }

    public void setFiles(File[] musicFiles) {
        this.musicFiles = musicFiles;
        setCurIndexTo(curFileIndex);
    }

    public void setCurIndexTo(int index) {
        curFileIndex = index;
        cur = musicFiles[curFileIndex];
    }
}
