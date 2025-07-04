package pallete;

import java.awt.*;
import javax.swing.JPanel;

public class PanelRounded extends JPanel {

    private int roundedCorner = 20;
    private Color fillColor = new Color(255, 255, 255);

    public PanelRounded() {
        setOpaque(false);
    }

    public int getRoundedCorner() {
        return roundedCorner;
    }

    public void setRoundedCorner(int roundedCorner) {
        this.roundedCorner = roundedCorner;
        repaint();
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(fillColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), roundedCorner, roundedCorner);
        
        g2d.dispose();
    }
}
