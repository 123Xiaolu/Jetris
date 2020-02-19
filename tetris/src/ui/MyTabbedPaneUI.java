package ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

/**
 * @author lyx1920055799
 * @version 1.0.0
 * @date 2020/2/19 17:50
 */
public class MyTabbedPaneUI extends BasicTabbedPaneUI {

    private boolean tabsOverlapBorder = UIManager.getBoolean("TabbedPane.tabsOverlapBorder");

    private Color unSelectedColor = new Color(240, 240, 240);
    private Color selectedColor = new Color(250, 250, 250);
    private Color borderColor = new Color(200, 200, 200);

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {

    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient;
        switch (tabPlacement) {
            case LEFT:
                if (isSelected) {
                    gradient = new GradientPaint(x + 1, y, selectedColor, x + w, y, Color.WHITE, true);
                } else {
                    gradient = new GradientPaint(x + 1, y, unSelectedColor, x + w, y, Color.WHITE, true);
                }
                g2d.setPaint(gradient);
                g.fillRect(x + 1, y + 1, w - 1, h - 2);
                break;
            case RIGHT:
                if (isSelected) {
                    gradient = new GradientPaint(x + w, y, selectedColor, x + 1, y, Color.WHITE, true);
                } else {
                    gradient = new GradientPaint(x + w, y, unSelectedColor, x + 1, y, Color.WHITE, true);
                }
                g2d.setPaint(gradient);
                g.fillRect(x, y + 1, w - 1, h - 2);
                break;
            case BOTTOM:
                if (isSelected) {
                    gradient = new GradientPaint(x + 1, y + h, selectedColor, x + 1, y, Color.WHITE, true);
                } else {
                    gradient = new GradientPaint(x + 1, y + h, unSelectedColor, x + 1, y, Color.WHITE, true);
                }
                g2d.setPaint(gradient);
                g.fillRect(x + 1, y, w - 2, h - 1);
                break;
            case TOP:
            default:
                if (isSelected) {
                    gradient = new GradientPaint(x + 1, y, selectedColor, x + 1, y + h, Color.WHITE, true);
                } else {
                    gradient = new GradientPaint(x + 1, y, unSelectedColor, x + 1, y + h, Color.WHITE, true);
                }
                g2d.setPaint(gradient);
                g2d.fillRect(x + 1, y + 1, w - 2, h - 1);
        }
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        int width = tabPane.getWidth();
        int height = tabPane.getHeight();
        Insets insets = tabPane.getInsets();
        Insets tabAreaInsets = getTabAreaInsets(tabPlacement);

        int x = insets.left;
        int y = insets.top;
        int w = width - insets.right - insets.left;
        int h = height - insets.top - insets.bottom;

        switch (tabPlacement) {
            case LEFT:
                x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                if (tabsOverlapBorder) {
                    x -= tabAreaInsets.right;
                }
                w -= (x - insets.left);
                break;
            case RIGHT:
                w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                if (tabsOverlapBorder) {
                    w += tabAreaInsets.left;
                }
                break;
            case BOTTOM:
                h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                if (tabsOverlapBorder) {
                    h += tabAreaInsets.top;
                }
                break;
            case TOP:
            default:
                y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                if (tabsOverlapBorder) {
                    y -= tabAreaInsets.bottom;
                }
                h -= (y - insets.top);
        }
        paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
        paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
        paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
        paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
    }

    @Override
    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement,
                                             int selectedIndex,
                                             int x, int y, int w, int h) {
        Rectangle selRect = selectedIndex < 0 ? null :
                getTabBounds(selectedIndex, calcRect);

        g.setColor(borderColor);

        // Draw unbroken line if tabs are not on TOP, OR
        // selected tab is not in run adjacent to content, OR
        // selected tab is not visible (SCROLL_TAB_LAYOUT)
        //
        if (tabPlacement != TOP || selectedIndex < 0 ||
                (selRect.y + selRect.height + 1 < y) ||
                (selRect.x < x || selRect.x > x + w)) {
            g.drawLine(x, y, x + w - 2, y);
        } else {
            // Break line to show visual connection to selected tab
            g.drawLine(x, y, selRect.x - 1, y);
            if (selRect.x + selRect.width < x + w - 2) {
                g.drawLine(selRect.x + selRect.width, y,
                        x + w - 2, y);
            } else {
                g.setColor(borderColor);
                g.drawLine(x + w - 2, y, x + w - 2, y);
            }
        }
    }

    @Override
    protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {

    }

    @Override
    protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {

    }

    @Override
    protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {

    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {

    }
}