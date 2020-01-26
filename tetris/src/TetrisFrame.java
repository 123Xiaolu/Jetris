import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lyx1920055799
 * @version 1.2
 * @date 2020/1/26 14:48
 */

public class TetrisFrame extends JFrame {

    public JLabel score, lines, level;
    private PlayField playfield;
    private int X = 0, Y = 0;
    public MyMusic myMusic = new MyMusic();

    private static Image Matrix, I, J, L, O, S, T, Z, start, stop, pause, help;

    static {
        try {
            Matrix = ImageIO.read(TetrisFrame.class.getResource("Matrix.png"));
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
            help = ImageIO.read(TetrisFrame.class.getResource("help.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initView() {
        JLabel label1 = new JLabel("NEXT", JLabel.CENTER);
        JLabel label2 = new JLabel("SCORE", JLabel.CENTER);
        JLabel label3 = new JLabel("LINES", JLabel.CENTER);
        JLabel label4 = new JLabel("LEVEL", JLabel.CENTER);
        score = new JLabel();
        lines = new JLabel();
        level = new JLabel();
        score.setHorizontalAlignment(JLabel.CENTER);
        lines.setHorizontalAlignment(JLabel.CENTER);
        level.setHorizontalAlignment(JLabel.CENTER);
        label1.setBounds(304, 40, 70, 30);
        label2.setBounds(304, 200, 70, 30);
        label3.setBounds(304, 280, 70, 30);
        label4.setBounds(304, 360, 70, 30);
        score.setBounds(304, 240, 70, 30);
        lines.setBounds(304, 320, 70, 30);
        level.setBounds(304, 400, 70, 30);
        label1.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
        label2.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        label3.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        label4.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        score.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.MAGENTA));
        lines.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.MAGENTA));
        level.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.MAGENTA));
        add(label1);
        add(label2);
        add(label3);
        add(label4);
        add(score);
        add(lines);
        add(level);
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JButton button4 = new JButton();
        button1.setIcon(new ImageIcon(start.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        button2.setIcon(new ImageIcon(pause));
        button3.setIcon(new ImageIcon(stop));
        button4.setIcon(new ImageIcon(help.getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        button1.setBounds(278, 450, 24, 24);
        button2.setBounds(312, 450, 24, 24);
        button3.setBounds(346, 450, 24, 24);
        button4.setBounds(380, 450, 24, 24);
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(button1)) {
                    playfield.start();
                } else if (e.getSource().equals(button2)) {
                    playfield.pause();
                } else if (e.getSource().equals(button3)) {
                    playfield.stop();
                } else if (e.getSource().equals(button4)) {
                    help();
                }
                requestFocus();
            }
        };
        button1.addActionListener(actionListener);
        button2.addActionListener(actionListener);
        button3.addActionListener(actionListener);
        button4.addActionListener(actionListener);
    }

    public TetrisFrame() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                X = e.getX();
                Y = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen();
                int yOnScreen = e.getYOnScreen();
                int x = xOnScreen - X;
                int y = yOnScreen - Y;
                TetrisFrame.this.setLocation(x, y);
            }
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    playfield.rotateLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    playfield.softDrop();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    playfield.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    playfield.moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    playfield.hardDrop();
                } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    playfield.rotateRight();
                } else if (e.getKeyCode() == KeyEvent.VK_O) {
                    playfield.stop();
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    playfield.pause();
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    playfield.start();
                } else if (e.getKeyCode() == KeyEvent.VK_L) {
                    help();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        enableInputMethods(false);
        setSize(419, 519);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 1, 1, new Color(140, 211, 236)));
        getContentPane().setBackground(Color.WHITE);
        playfield = new PlayField();
        add(playfield);
        initView();
        setVisible(true);
        requestFocus();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        playfield.paintNext(g);
    }

    public void score() {
        score.setText(String.valueOf(playfield.score));
    }

    public void lines() {
        lines.setText(String.valueOf(playfield.lines));
    }

    public void level() {
        level.setText(String.valueOf(playfield.level));
    }

    public void help() {
        new MyDialog();
    }


    class PlayField extends JPanel {

        private Tetrominos tetrominos;
        private Tetrominos next;
        private int[][] wall = new int[10][22];
        private Timer timer;
        private TimerTask timerTask;
        private int speed;
        private boolean isRunning = false;
        private boolean isPausing = false;
        private int score = 0;
        private int level = 1;
        private int lines = 0;

        public PlayField() {
            setBounds(0, 0, Matrix.getWidth(this), Matrix.getHeight(this));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(Matrix, 0, 0, Matrix.getWidth(this), Matrix.getHeight(this), this);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            paintTetrominos(g);
            paintWall(g);
        }

        public void start() {
            init();
            myMusic.play();
            isRunning = true;
            if (isPausing) {
                isPausing = false;
            } else {
                generate();
            }
            boolean isUp = levelUp();
            if (!isUp) {
                timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (!isPausing) {
                            drop();
                        }
                    }
                };
                timer.schedule(timerTask, speed, speed);
            }
        }

        public void pause() {
            isPausing = true;
            myMusic.pause();
        }

        public void stop() {
            init();
            myMusic.stop();
        }

        public void init() {
            if (timer != null) {
                timerTask.cancel();
                timer.cancel();
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 22; j++) {
                    wall[i][j] = 0;
                }
            }
            isRunning = false;
            isPausing = false;
            tetrominos = null;
            next = null;
            score = 0;
            lines = 0;
            level = 1;
            score();
            lines();
            level();
            repaint();
            TetrisFrame.this.repaint();
        }

        public boolean levelUp() {
            if (level <= 15) {
                int i = lines / 10 + 1;
                speed = dropSpeed(level);
                if (i != level) {
                    level = i;
                    speed = dropSpeed(level);
                    if (timer != null) {
                        timerTask.cancel();
                        timer.cancel();
                    }
                    timer = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            if (!isPausing) {
                                drop();
                            }
                        }
                    };
                    timer.schedule(timerTask, speed, speed);
                    return true;
                }
            }
            return false;
        }

        public int dropSpeed(int level) {
            return (int) (Math.pow(0.8 - ((level - 1) * 0.007), level - 1) * 1000);
        }

        public void drop() {
            if (tetrominos != null) {
                tetrominos.y += 1;
                if (isWall()) {
                    tetrominos.y -= 1;
                    lockDown();
                }
                repaint();
            }
        }

        public void softDrop() {
            if (tetrominos != null) {
                tetrominos.y += 1;
                score++;
                if (isWall()) {
                    tetrominos.y -= 1;
                    lockDown();
                    if (score >= 1) {
                        score--;
                    }
                }
                score();
                repaint();
            }
        }

        public void hardDrop() {
            if (tetrominos != null) {
                while (true) {
                    tetrominos.y += 1;
                    score += 2;
                    if (isWall()) {
                        tetrominos.y -= 1;
                        lockDown();
                        if (score >= 2) {
                            score -= 2;
                        }
                        score();
                        repaint();
                        break;
                    }
                }
            }
        }

        public void rotateLeft() {
            if (tetrominos != null) {
                tetrominos.state += 1;
                if (tetrominos.state == 4) {
                    tetrominos.state = 0;
                }
                if (isWall()) {
                    tetrominos.state -= 1;
                    if (tetrominos.state == -1) {
                        tetrominos.state = 3;
                    }
                }
                repaint();
            }
        }

        public void rotateRight() {
            if (tetrominos != null) {
                tetrominos.state -= 1;
                if (tetrominos.state == -1) {
                    tetrominos.state = 3;
                }
                if (isWall()) {
                    tetrominos.state += 1;
                    if (tetrominos.state == 4) {
                        tetrominos.state = 0;
                    }
                }
                repaint();
            }
        }

        public void moveLeft() {
            if (tetrominos != null) {
                tetrominos.x -= 1;
                if (isWall()) {
                    tetrominos.x += 1;
                }
                repaint();
            }
        }

        public void moveRight() {
            if (tetrominos != null) {
                tetrominos.x += 1;
                if (isWall()) {
                    tetrominos.x -= 1;
                }
                repaint();
            }
        }

        public boolean isWall() {
            for (int i = 0; i < 4; i++) {
                int x = tetrominos.o[tetrominos.state][i][0] + tetrominos.x;
                int y = tetrominos.o[tetrominos.state][i][1] + tetrominos.y;
                if (x < 0 || x > 9 || y < 0 || y > 21 || wall[x][y] != 0) {
                    return true;
                }
            }
            return false;
        }

        public void lockDown() {
            for (int i = 0; i < 4; i++) {
                int x = (tetrominos.o[tetrominos.state][i][0] + tetrominos.x);
                int y = (tetrominos.o[tetrominos.state][i][1] + tetrominos.y);
                wall[x][y] = tetrominos.type;
            }
            int c = lineClear();
            if (c == 1) {
                score += 100 * c;
            } else if (c == 2) {
                score += 300 * c;
            } else if (c == 3) {
                score += 500 * c;
            } else if (c == 4) {
                score += 800 * c;
            }
            lines += c;
            levelUp();
            TetrisFrame.this.score();
            TetrisFrame.this.level();
            TetrisFrame.this.lines();
            generate();
            lockOut();
        }

        public void lockOut() {
            for (int i = 0; i < 10; i++) {
                if (wall[i][1] != 0) {
                    pause();
                    JOptionPane.showMessageDialog(null, "SCORE: " + score, "Game over", JOptionPane.INFORMATION_MESSAGE);
                    stop();
                }
            }
        }

        public void generate() {
            int random = (int) (Math.random() * 7);
            char[] chars = {'O', 'I', 'T', 'L', 'J', 'S', 'Z'};
            if (next != null) {
                tetrominos = next;
                next = new Tetrominos(chars[random]);
            } else {
                tetrominos = new Tetrominos(chars[random]);
                next = new Tetrominos(chars[random]);
            }
            TetrisFrame.this.repaint();
        }

        public int lineClear() {
            int lines = 0;
            for (int i = 21; i >= 0; i--) {
                int count = 0;
                for (int j = 9; j >= 0; j--) {
                    if (wall[j][i] != 0) {
                        count++;
                        if (count == 10) {
                            lines++;
                            for (int k = 0; k < 10; k++) {
                                wall[k][i] = 0;
                            }
                            for (int m = i; m >= 1; m--) {
                                for (int n = 9; n >= 0; n--) {
                                    int o = wall[n][m - 1];
                                    wall[n][m] = o;
                                }
                            }
                            i++;
                        }
                    }
                }
            }
            return lines;
        }

        public void paintWall(Graphics g) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 22; j++) {
                    int x = 0;
                    int y = -52;
                    switch (wall[i][j]) {
                        case 'O':
                            x += i * 26;
                            y += j * 26;
                            g.drawImage(O, x, y, O.getWidth(this), O.getHeight(this), this);
                            break;
                        case 'I':
                            x += i * 26;
                            y += j * 26;
                            g.drawImage(I, x, y, I.getWidth(this), I.getHeight(this), this);
                            break;
                        case 'T':
                            x += i * 26;
                            y += j * 26;
                            g.drawImage(T, x, y, T.getWidth(this), T.getHeight(this), this);
                            break;
                        case 'L':
                            x += i * 26;
                            y += j * 26;
                            g.drawImage(L, x, y, L.getWidth(this), L.getHeight(this), this);
                            break;
                        case 'J':
                            x += i * 26;
                            y += j * 26;
                            g.drawImage(J, x, y, J.getWidth(this), J.getHeight(this), this);
                            break;
                        case 'S':
                            x += i * 26;
                            y += j * 26;
                            g.drawImage(S, x, y, S.getWidth(this), S.getHeight(this), this);
                            break;
                        case 'Z':
                            x += i * 26;
                            y += j * 26;
                            g.drawImage(Z, x, y, Z.getWidth(this), Z.getHeight(this), this);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        public void paintTetrominos(Graphics g) {
            if (tetrominos != null) {
                for (int i = 0; i < 4; i++) {
                    int x = 0;
                    int y = -52;
                    x += (tetrominos.o[tetrominos.state][i][0] + tetrominos.x) * 26;
                    y += (tetrominos.o[tetrominos.state][i][1] + tetrominos.y) * 26;
                    switch (tetrominos.type) {
                        case 'O':
                            g.drawImage(O, x, y, O.getWidth(this), O.getHeight(this), this);
                            break;
                        case 'I':
                            g.drawImage(I, x, y, I.getWidth(this), I.getHeight(this), this);
                            break;
                        case 'T':
                            g.drawImage(T, x, y, T.getWidth(this), T.getHeight(this), this);
                            break;
                        case 'L':
                            g.drawImage(L, x, y, L.getWidth(this), L.getHeight(this), this);
                            break;
                        case 'J':
                            g.drawImage(J, x, y, J.getWidth(this), J.getHeight(this), this);
                            break;
                        case 'S':
                            g.drawImage(S, x, y, S.getWidth(this), S.getHeight(this), this);
                            break;
                        case 'Z':
                            g.drawImage(Z, x, y, Z.getWidth(this), Z.getHeight(this), this);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        public void paintNext(Graphics g) {
            if (next != null) {
                for (int i = 0; i < 4; i++) {
                    int x = 0;
                    int y = 0;
                    x += (next.o[next.state][i][0] + next.x - 4) * 26;
                    y += (next.o[next.state][i][1] + next.y) * 26;
                    switch (next.type) {
                        case 'O':
                            x += 314;
                            y += 110;
                            g.drawImage(O, x, y, O.getWidth(this), O.getHeight(this), this);
                            break;
                        case 'I':
                            x += 314;
                            y += 97;
                            g.drawImage(I, x, y, I.getWidth(this), I.getHeight(this), this);
                            break;
                        case 'T':
                            x += 326;
                            y += 110;
                            g.drawImage(T, x, y, T.getWidth(this), T.getHeight(this), this);
                            break;
                        case 'L':
                            x += 326;
                            y += 110;
                            g.drawImage(L, x, y, L.getWidth(this), L.getHeight(this), this);
                            break;
                        case 'J':
                            x += 326;
                            y += 110;
                            g.drawImage(J, x, y, J.getWidth(this), J.getHeight(this), this);
                            break;
                        case 'S':
                            x += 326;
                            y += 110;
                            g.drawImage(S, x, y, S.getWidth(this), S.getHeight(this), this);
                            break;
                        case 'Z':
                            x += 326;
                            y += 110;
                            g.drawImage(Z, x, y, Z.getWidth(this), Z.getHeight(this), this);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    static class Tetrominos {

        public char type;
        public int state = 0;
        public int[][][] o;
        public int x, y;

        public Tetrominos(char type) {
            this.type = type;
            mino();
        }

        public void mino() {
            x = 4;
            y = 1;
            if (type == 'T') {
                o = new int[][][]{
                        {
                                {0, 0}, {-1, 0}, {0, -1}, {1, 0}
                        },
                        {
                                {0, 0}, {-1, 0}, {0, -1}, {0, 1}
                        },
                        {
                                {0, 0}, {-1, 0}, {0, 1}, {1, 0}
                        },
                        {
                                {0, 0}, {0, -1}, {0, 1}, {1, 0}
                        }
                };
            } else if (type == 'O') {
                o = new int[][][]{
                        {
                                {0, 0}, {0, -1}, {1, -1}, {1, 0}
                        },
                        {
                                {0, 0}, {0, -1}, {1, -1}, {1, 0}
                        },
                        {
                                {0, 0}, {0, -1}, {1, -1}, {1, 0}
                        },
                        {
                                {0, 0}, {0, -1}, {1, -1}, {1, 0}
                        }
                };
            } else if (type == 'I') {
                o = new int[][][]{
                        {
                                {0, 0}, {-1, 0}, {1, 0}, {2, 0}
                        },
                        {
                                {0, 0}, {0, -1}, {0, 1}, {0, 2}
                        },
                        {
                                {0, 1}, {-1, 1}, {1, 1}, {2, 1}
                        },
                        {
                                {1, 0}, {1, -1}, {1, 1}, {1, 2}
                        }
                };
            } else if (type == 'L') {
                o = new int[][][]{
                        {
                                {0, 0}, {-1, 0}, {1, -1}, {1, 0}
                        },
                        {
                                {0, 0}, {-1, -1}, {0, -1}, {0, 1}
                        },
                        {
                                {0, 0}, {-1, 0}, {-1, 1}, {1, 0}
                        },
                        {
                                {0, 0}, {0, -1}, {0, 1}, {1, 1}
                        }
                };
            } else if (type == 'J') {
                o = new int[][][]{
                        {
                                {0, 0}, {-1, -1}, {-1, 0}, {1, 0}
                        },
                        {
                                {0, 0}, {-1, 1}, {0, -1}, {0, 1}
                        },
                        {
                                {0, 0}, {-1, 0}, {1, 0}, {1, 1}
                        },
                        {
                                {0, 0}, {0, -1}, {0, 1}, {1, -1}
                        }
                };
            } else if (type == 'S') {
                o = new int[][][]{
                        {
                                {0, 0}, {-1, 0}, {0, -1}, {1, -1}
                        },
                        {
                                {0, 0}, {-1, -1}, {-1, 0}, {0, 1}
                        },
                        {
                                {0, 0}, {-1, 1}, {0, 1}, {1, 0}
                        },
                        {
                                {0, 0}, {0, -1}, {1, 0}, {1, 1}
                        }
                };
            } else if (type == 'Z') {
                o = new int[][][]{
                        {
                                {0, 0}, {-1, -1}, {0, -1}, {1, 0}
                        },
                        {
                                {0, 0}, {-1, 0}, {-1, 1}, {0, -1}
                        },
                        {
                                {0, 0}, {-1, -1}, {0, -1}, {1, 0}
                        },
                        {
                                {0, 0}, {0, 1}, {1, -1}, {1, 0}
                        }
                };
            }
        }
    }

    class MyDialog extends JDialog {

        public MyDialog() {
            int width = 260;
            int height = 330;
            setModalityType(ModalityType.APPLICATION_MODAL);
            Point point = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
            setBounds(point.x - width / 2, point.y - height / 2, width, height);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setTitle("Help");
            setIconImage(help);
            String str = "<html>\n" +
                    "<body>\n" +
                    "\t<p>Control</p>\n" +
                    "\t<br>\n" +
                    "\t<p>&uarr;&ensp;&ensp;Rotate Counter-clockwise</p>\n" +
                    "\t<p>&larr;&ensp;&ensp;Move Left</p>\n" +
                    "\t<p>&rarr;&ensp;&ensp;Move Right</p>\n" +
                    "\t<p>&darr;&ensp;&ensp;Soft drop</p>\n" +
                    "\t<p>Ctrl&ensp;&ensp;Rotate Clockwise</p>\n" +
                    "\t<p>Space&ensp;&ensp;Hard drop</p>\n" +
                    "\t<p>K&ensp;&ensp;Start</p>\n" +
                    "\t<p>P&ensp;&ensp;Pause</p>\n" +
                    "\t<p>O&ensp;&ensp;Stop</p>\n" +
                    "\t<p>L&ensp;&ensp;Help</p>\n" +
                    "\t<p>Author&ensp;&ensp;lyx1920055799</p>\n" +
                    "\t<br>\n" +
                    "</html>";
            JLabel label = new JLabel(str, JLabel.CENTER);
            JCheckBox music = new JCheckBox("music");
            music.setSelected(myMusic.isFlag());
            music.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    boolean b = ((JCheckBox) e.getSource()).isSelected();
                    myMusic.mute(b);
                }
            });
            JButton button = new JButton("Github");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI("https://github.com/lyx1920055799/tetris"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            JPanel panel = new JPanel();
            panel.add(music);
            panel.add(button);
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, label);
            splitPane.setBorder(null);
            JScrollPane scrollPane = new JScrollPane(splitPane);
            scrollPane.setBorder(null);
            Container container = getContentPane();
            container.add(scrollPane);
            setVisible(true);
        }
    }

    class MyMusic {

        private Clip clip;
        private boolean flag = true;

        public MyMusic() {
            try {
                URL url = this.getClass().getResource("bgm.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            } catch (
                    UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        public void play() {
            if (flag) {
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

        public boolean isFlag() {
            return flag;
        }

        public void mute(boolean b) {
            flag = b;
            if (!flag) {
                clip.stop();
                clip.setFramePosition(0);
            } else if (playfield.isRunning && !playfield.isPausing) {
                play();
            }
        }
    }

    public static void main(String[] args) {
        new TetrisFrame();
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
