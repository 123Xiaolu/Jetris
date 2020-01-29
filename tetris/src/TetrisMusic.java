import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * @author lyx1920055799
 * @version beta 1.2.4
 * @date 2020/1/29 19:30
 */
public class TetrisMusic {

    private boolean flag_bgm = true;
    private boolean flag_se = true;

    private static Clip clip, rotate, move, lockDown, lineClear, lockOut;

    static {
        String[] strings = {"bgm.wav", "rotate.wav", "move.wav", "lockDown.wav", "lineClear.wav", "lockOut.wav"};
        final int length = strings.length;
        Clip[] clips = new Clip[length];
        int[] c = {0};
        for (int i = 0; i < length; i++) {
            int j = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = TetrisMusic.class.getResource(strings[j]);
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                        clips[j] = AudioSystem.getClip();
                        clips[j].open(audioInputStream);
                        c[0]++;
                        if (c[0] == length - 1) {
                            clip = clips[0];
                            rotate = clips[1];
                            move = clips[2];
                            lockDown = clips[3];
                            lineClear = clips[4];
                            lockOut = clips[5];
                        }
                    } catch (
                            UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void play() {
        if (flag_bgm) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void pause() {
        clip.stop();
        clip.setFramePosition(clip.getFramePosition());
    }

    public void stop() {
        clip.stop();
        clip.setFramePosition(0);
    }

    public boolean isFlag_bgm() {
        return flag_bgm;
    }

    public boolean isFlag_se() {
        return flag_se;
    }

    public void backgroundMusic(boolean b, boolean isRunning, boolean isPausing) {
        flag_bgm = b;
        if (!flag_bgm) {
            clip.stop();
            clip.setFramePosition(0);
        } else if (isRunning && !isPausing) {
            play();
        }
    }

    public void soundEffects(boolean b) {
        flag_se = b;
    }

    public void roteMusic() {
        if (flag_se) {
            rotate.start();
            rotate.setFramePosition(0);
        }
    }

    public void moveMusic() {
        if (flag_se) {
            move.start();
            move.setFramePosition(0);
        }
    }

    public void lockDownMusic() {
        if (flag_se) {
            lockDown.start();
            lockDown.setFramePosition(0);
        }
    }

    public void lineClearMusic() {
        if (flag_se) {
            lineClear.start();
            lineClear.setFramePosition(0);
        }
    }

    public void lockOutMusic() {
        if (flag_se) {
            lockOut.start();
            lockOut.setFramePosition(0);
        }
    }
}
