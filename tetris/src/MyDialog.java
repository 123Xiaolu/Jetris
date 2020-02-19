import ui.MyTabbedPaneUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lyx1920055799
 * @version 1.0.0
 * @date 2020/2/19 17:50
 */
public class MyDialog extends JDialog implements ActionListener, ItemListener {

    private JTabbedPane tabbedPane;
    private JCheckBox bgm, se;
    private JRadioButton radioButton1, radioButton2;
    private static boolean m = true;
    private static boolean s = false;
    private TetrisFrame tetrisFrame;
    private JButton button;

    public MyDialog(TetrisFrame tetrisFrame) {
        this.tetrisFrame = tetrisFrame;
        setTitle("Help");
        setIconImage(TetrisFrame.help);
        int width = 260;
        int height = 330;
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        Point point = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setBounds(point.x - width / 2, point.y - height / 2, width, height);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        tetrisFrame.playfield.pause();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
        initView();
        setVisible(true);
    }

    public void initView() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(null);
        JPanel settings = new JPanel();
        JPanel guide = new JPanel();
        JPanel about = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        settings.setLayout(gridBagLayout);
        bgm = new JCheckBox("BGM");
        se = new JCheckBox("Sound effect");
        bgm.setSelected(tetrisFrame.playfield.tetrisMusic.isFlag_bgm());
        bgm.addItemListener(this);
        se.setSelected(tetrisFrame.playfield.tetrisMusic.isFlag_se());
        se.addItemListener(this);
        settings.add(bgm);
        settings.add(se);
        ButtonGroup buttonGroup = new ButtonGroup();
        radioButton1 = new JRadioButton("Default");
        radioButton2 = new JRadioButton("Local");
        radioButton1.setSelected(m);
        radioButton2.setSelected(s);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        radioButton1.addItemListener(this);
        radioButton2.addItemListener(this);
        settings.add(radioButton1);
        settings.add(radioButton2);
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagLayout.setConstraints(bgm, gridBagConstraints);
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagLayout.setConstraints(se, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 16;
        gridBagLayout.setConstraints(radioButton1, gridBagConstraints);
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 16;
        gridBagLayout.setConstraints(radioButton2, gridBagConstraints);
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
        button = new JButton("Github");
        button.addActionListener(this);
        about.add(button);
        tabbedPane.addTab("Settings", settings);
        tabbedPane.addTab("Guide", guide);
        tabbedPane.addTab("About", about);
        getContentPane().add(tabbedPane);
        setUI();
    }

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

    @Override
    public void itemStateChanged(ItemEvent e) {
        boolean b;
        if (e.getSource().equals(bgm)) {
            b = ((JCheckBox) e.getSource()).isSelected();
            tetrisFrame.playfield.tetrisMusic.backgroundMusic(b, tetrisFrame.playfield.isRunning(), tetrisFrame.playfield.isPausing());
        } else if (e.getSource().equals(se)) {
            b = ((JCheckBox) e.getSource()).isSelected();
            tetrisFrame.playfield.tetrisMusic.soundEffects(b);
        } else if (e.getSource().equals(radioButton1)) {
            b = ((JRadioButton) e.getSource()).isSelected();
            if (b) {
                setCrossPlatformStyle();
                SwingUtilities.updateComponentTreeUI(tetrisFrame);
                SwingUtilities.updateComponentTreeUI(MyDialog.this);
                setUI();
                m = true;
            } else {
                m = false;
            }
        } else if (e.getSource().equals(radioButton2)) {
            b = ((JRadioButton) e.getSource()).isSelected();
            if (b) {
                setLocalStyle();
                SwingUtilities.updateComponentTreeUI(tetrisFrame);
                SwingUtilities.updateComponentTreeUI(MyDialog.this);
                s = true;
            } else {
                s = false;
            }
        }
    }

    private void setUI() {
        tabbedPane.setUI(new MyTabbedPaneUI());
        button.setUI(new BasicButtonUI());
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
