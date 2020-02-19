import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lyx1920055799
 * @version 1.0.0
 * @date 2020/2/19 18:00
 */
public class TetrisFrame extends JFrame {

    public JLabel score, lines, level;
    public PlayField playfield;
    public int lastX = 0, lastY = 0;

    public static Image Matrix, I, J, L, O, S, T, Z, start, stop, pause, help;

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

    public void initView() {
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
        ActionListener actionListener = e -> {
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
                lastX = e.getX();
                lastY = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xOnScreen = e.getXOnScreen();
                int yOnScreen = e.getYOnScreen();
                int x = xOnScreen - lastX;
                int y = yOnScreen - lastY;
                TetrisFrame.this.setLocation(x, y);
            }
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (!playfield.isPausing) {
                            playfield.rotateLeft();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (!playfield.isPausing) {
                            playfield.softDrop();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!playfield.isPausing) {
                            playfield.moveLeft();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!playfield.isPausing) {
                            playfield.moveRight();
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        if (!playfield.isPausing) {
                            playfield.hardDrop();
                        }
                        break;
                    case KeyEvent.VK_CONTROL:
                        if (!playfield.isPausing) {
                            playfield.rotateRight();
                        }
                        break;
                    case KeyEvent.VK_O:
                        playfield.stop();
                        break;
                    case KeyEvent.VK_P:
                        playfield.pause();
                        break;
                    case KeyEvent.VK_K:
                        playfield.start();
                        break;
                    case KeyEvent.VK_L:
                        help();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;
                    default:
                        break;
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
        new MyDialog(this);
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
        protected TetrisMusic tetrisMusic = new TetrisMusic();

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

        public boolean isPausing() {
            return isPausing;
        }

        public boolean isRunning() {
            return isRunning;
        }

        public void start() {
            tetrisMusic.play();
            if (isPausing) {
                isPausing = false;
            } else {
                init();
                isRunning = true;
                generate();
            }
            speed = dropSpeed(level);
            if (timer == null || timerTask == null) {
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
            if (isRunning) {
                isPausing = true;
                tetrisMusic.pause();
            }
        }

        public void stop() {
            init();
            tetrisMusic.stop();
        }

        public void init() {
            if (timer != null && timerTask != null) {
                timerTask.cancel();
                timer.cancel();
                timerTask = null;
                timer = null;
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
                if (i > level) {
                    level = i;
                    speed = dropSpeed(level);
                    if (timer != null && timerTask != null) {
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
                } else {
                    repaint();
                }
            }
        }

        public void softDrop() {
            if (tetrominos != null) {
                tetrominos.y += 1;
                if (isWall()) {
                    tetrominos.y -= 1;
                    lockDown();
                } else {
                    score++;
                    score();
                    repaint();
                }
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
                } else {
                    repaint();
                    tetrisMusic.roteMusic();
                }
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
                } else {
                    repaint();
                    tetrisMusic.roteMusic();
                }
            }
        }

        public void moveLeft() {
            if (tetrominos != null) {
                tetrominos.x -= 1;
                if (isWall()) {
                    tetrominos.x += 1;
                } else {
                    repaint();
                    tetrisMusic.moveMusic();
                }
            }
        }

        public void moveRight() {
            if (tetrominos != null) {
                tetrominos.x += 1;
                if (isWall()) {
                    tetrominos.x -= 1;
                } else {
                    repaint();
                    tetrisMusic.moveMusic();
                }
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
            tetrisMusic.lockDownMusic();
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
                    tetrisMusic.lockOutMusic();
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
            } else {
                tetrominos = new Tetrominos(chars[random]);
            }
            next = new Tetrominos(chars[random]);
            TetrisFrame.this.repaint();
        }

        public int lineClear() {
            int lines = 0;
            int layer = 21;
            int[][] arr = new int[10][22];
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                System.arraycopy(wall[i], 0, arr[i], 0, 22);
            }
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
                            if (list.size() == 0) {
                                layer = i;
                                list.add(layer);
                            } else {
                                layer--;
                                list.add(layer);
                            }
                            i++;
                        }
                    }
                }
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 22; j++) {
                    int x = wall[i][j];
                    wall[i][j] = arr[i][j];
                    arr[i][j] = x;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                int finalI = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int a = 4, b = 5; a >= 0; a--, b++) {
                            wall[a][list.get(finalI)] = 0;
                            wall[b][list.get(finalI)] = 0;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            repaint();
                        }
                        if (finalI == list.size() - 1) {
                            for (int i = 0; i < 10; i++) {
                                System.arraycopy(arr[i], 0, wall[i], 0, 22);
                            }
                            repaint();
                        }
                    }
                }).start();
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

    public static void main(String[] args) {
        new TetrisFrame();
    }
}
