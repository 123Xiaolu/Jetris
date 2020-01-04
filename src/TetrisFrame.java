import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

/**
 * @author lyx1920055799
 * @version 1.0
 * @date 2020/1/4 16:33
 */

public class TetrisFrame extends JFrame {

    private int x,y;

    private static Image background,I,J,L,O,S,T,Z,start,stop,pause;

    static {
        try {
            background = ImageIO.read(TetrisFrame.class.getResource("background.png"));
            I = ImageIO.read(TetrisFrame.class.getResource("I.png"));
            J = ImageIO.read(TetrisFrame.class.getResource("J.png"));
            L = ImageIO.read(TetrisFrame.class.getResource("L.png"));
            O = ImageIO.read(TetrisFrame.class.getResource("O.png"));
            S = ImageIO.read(TetrisFrame.class.getResource("S.png"));
            T = ImageIO.read(TetrisFrame.class.getResource("T.png"));
            Z = ImageIO.read(TetrisFrame.class.getResource("Z.png"));
            start = ImageIO.read(TetrisFrame.class.getResource("start.png"));
            pause = ImageIO.read(TetrisFrame.class.getResource("pause.png"));
            stop = ImageIO.read(TetrisFrame.class.getResource("stop.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void func(){
        JLabel label1 = new JLabel("NEXT",JLabel.CENTER);
        JLabel label2 = new JLabel("SCORE",JLabel.CENTER);
        JLabel label3 = new JLabel("LEVEL",JLabel.CENTER);
        JLabel label4 = new JLabel("LINES",JLabel.CENTER);
        JLabel label5 = new JLabel("0",JLabel.CENTER);
        JLabel label6 = new JLabel("0",JLabel.CENTER);
        JLabel label7 = new JLabel("0",JLabel.CENTER);
        label1.setBounds(304,40,70,30);
        label2.setBounds(304,200,70,30);
        label3.setBounds(304,280,70,30);
        label4.setBounds(304,360,70,30);
        label5.setBounds(304,240,70,30);
        label6.setBounds(304,320,70,30);
        label7.setBounds(304,400,70,30);
        label1.setBorder(BorderFactory.createLineBorder(Color.MAGENTA,2));
        label2.setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
        label3.setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
        label4.setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
        label5.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.MAGENTA));
        label6.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.MAGENTA));
        label7.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.MAGENTA));
        add(label1);
        add(label2);
        add(label3);
        add(label4);
        add(label5);
        add(label6);
        add(label7);
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        button1.setIcon(new ImageIcon(start));
        button2.setIcon(new ImageIcon(pause));
        button3.setIcon(new ImageIcon(stop));
        button1.setBounds(295,450,24,24);
        button2.setBounds(329,450,24,24);
        button3.setBounds(363,450,24,24);
        add(button1);
        add(button2);
        add(button3);
    }

    public TetrisFrame(){
        setSize(419,519);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        PlayField playfield = new PlayField();
        add(playfield);
        func();
        setVisible(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TetrisFrame.this.x = e.getX();
                TetrisFrame.this.y = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen();
                int yOnScreen = e.getYOnScreen();
                int x = xOnScreen - TetrisFrame.this.x;
                int y = yOnScreen - TetrisFrame.this.y;
                TetrisFrame.this.setLocation(x, y);
            }
        });
    }

    static class PlayField extends JPanel {

        public PlayField(){
            setBounds(0,0,background.getWidth(this),background.getHeight(this));
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, background.getWidth(this), background.getHeight(this), this);
        }

    }

    /*static class Tetrominos{

    }*/

    public static void main(String[] args){
        new TetrisFrame();
    }
}
