package pallete;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class tableHeader extends JLabel {
    
    public tableHeader(String text){
       super(text);
       setOpaque(true);
       setBackground(Color.white);
       setFont(new Font("sansserif",1, 12));
       setForeground(new Color(102,102,102));
       setBorder(new EmptyBorder(10,5,10,5));
    } 
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(230,230,230));
        g.drawLine(0, getHeight() - 1,getWidth(), getHeight() - 1);
    }
}
