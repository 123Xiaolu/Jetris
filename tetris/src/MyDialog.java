import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lyx1920055799
 * @version beta 1.2.2
 * @date 2020/1/28 10:00
 */
public class MyDialog extends JDialog {

    private static boolean m = true;
    private static boolean s = false;

    public MyDialog(TetrisFrame tetrisFrame) {
        setTitle("Help");
        setIconImage(TetrisFrame.help);
        int width = 240;
        int height = 300;
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        Point point = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setBounds(point.x - width / 2, point.y - height / 2, width, height);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel settings = new JPanel();
        JPanel guide = new JPanel();
        JPanel about = new JPanel();
        settings.setLayout(null);
        JCheckBox bgm = new JCheckBox("BGM");
        JCheckBox se = new JCheckBox("Sound effect");
        bgm.setSelected(tetrisFrame.playfield.tetrisMusic.isFlag_bgm());
        bgm.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean b = ((JCheckBox) e.getSource()).isSelected();
                tetrisFrame.playfield.tetrisMusic.backgroundMusic(b, tetrisFrame.playfield.isRunning(), tetrisFrame.playfield.isPausing());
            }
        });
        se.setSelected(tetrisFrame.playfield.tetrisMusic.isFlag_se());
        se.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean b = ((JCheckBox) e.getSource()).isSelected();
                tetrisFrame.playfield.tetrisMusic.soundEffects(b);
            }
        });
        bgm.setBounds(15, 0, 60, 30);
        se.setBounds(15, 30, 120, 30);
        settings.add(bgm);
        settings.add(se);
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton radioButton1 = new JRadioButton("Metal");
        JRadioButton radioButton2 = new JRadioButton("Local");
        radioButton1.setSelected(m);
        radioButton2.setSelected(s);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        radioButton1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean b = ((JRadioButton) e.getSource()).isSelected();
                if (b) {
                    setCrossPlatformStyle();
                    SwingUtilities.updateComponentTreeUI(tetrisFrame);
                    SwingUtilities.updateComponentTreeUI(MyDialog.this);
                    m = true;
                } else {
                    m = false;
                }
            }
        });
        radioButton2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean b = ((JRadioButton) e.getSource()).isSelected();
                if (b) {
                    setLocalStyle();
                    SwingUtilities.updateComponentTreeUI(tetrisFrame);
                    SwingUtilities.updateComponentTreeUI(MyDialog.this);
                    s = true;
                } else {
                    s = false;
                }
            }
        });
        JPanel panel = new JPanel();
        panel.add(radioButton1);
        panel.add(radioButton2);
        panel.setBounds(0, 60, 150, 30);
        settings.add(panel);
        String str = "<html>\n" +
                "<body>\n" +
                "\t<p>Guide:</p>\n" +
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
                "\t<p>ESC&ensp;&ensp;Exit</p>\n" +
                "</body>\n" +
                "</html>";
        JLabel label = new JLabel(str, JLabel.CENTER);
        guide.add(label);
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
        about.add(button);
        tabbedPane.addTab("Settings", settings);
        tabbedPane.addTab("Guide", guide);
        tabbedPane.addTab("About", about);
        getContentPane().add(tabbedPane);
        setVisible(true);
    }

    public static void setLocalStyle() {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        setStyle(lookAndFeel);
    }

    public static void setCrossPlatformStyle() {
        String lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
        setStyle(lookAndFeel);
    }

    private static void setStyle(String lookAndFeel) {
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
