package pallete;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class JPassField extends JPasswordField  {

        public String getLabelText() {
            return labelText;
        }

        
        public void setLabelText(String labelText) {
            this.labelText = labelText;
        }

        
        public Color getLineColor() {
            return lineColor;
        }

       
        public void setLineColor(Color lineColor) {
            this.lineColor = lineColor;
        }
        
        public int getLabelOffsetY() {
        return labelOffsetY;
        }

        public void setLabelOffsetY(int labelOffsetY) {
        this.labelOffsetY = labelOffsetY;
        repaint();
        }


    private final Animator animator;
    private boolean animateHintText = true;
    private float location;
    private boolean show;
    private boolean mouseOver = false;
    private String labelText="label";
    private Color lineColor = new Color(169,225,144);
    private int labelOffsetY = -6; // Default posisi label agak ke atas

        
    public JPassField() {
        setBorder(new EmptyBorder(20,3,10,3));
        setSelectionColor(new Color(169,225,144));
        addMouseListener(new MouseAdapter(){
        @Override
        public void mouseEntered(MouseEvent me){
            mouseOver = true;
            repaint();
        }
        
        @Override
        public void mouseExited(MouseEvent me){
            mouseOver = false;
            repaint();
        }        
    });
    addFocusListener (new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent me){
            showing(false);
        }
        
        @Override
        public void focusLost(FocusEvent me){
            showing(true);
        }
    });        
    TimingTarget target = new TimingTargetAdapter() {    
    @Override
    public void begin() {
        animateHintText = String.valueOf(getPassword()).equals("");
    }
    
    @Override
    public void timingEvent(float fraction) {
        location = fraction;
        repaint();
        }
    
    };
    animator = new Animator(300, target);
    animator.setResolution(0);
    animator.setAcceleration(0.5f);
    animator.setDeceleration(0.5f);
    }
    
    private void showing(boolean action){
              if(animator.isRunning()){
                 animator.stop();
                } else {
                  location = 1;
                }
              animator.setStartFraction(1f - location);
              show = action;
              location = 1f - location;
              animator.start();
    }
            
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        int width = getWidth();
        int height = getHeight();
        if(mouseOver) {
            g2.setColor(lineColor);
        } else {
            g2.setColor (new Color(150, 150, 150));            
        }
        g2.fillRect(2, height - 1, width - 4, 1);
        createHintText(g2);
        g2.dispose();
    }
    
       private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        g2.setColor(new Color(150, 150, 150));
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(labelText, g2);
        double height = getHeight() - in.top - in.bottom;
        double textY = (height - r2.getHeight()) / 2 + labelOffsetY;
        double size;
        if (animateHintText){
            if (show){
                size = 18 * (1 - location);
            }else{
                size = 18 * location;
            }
        }else {
            size = 18;
        }
        g2.drawString(labelText, in.right, (int) (in.top + textY + ft.getAscent() - size));
    }
    private void createLineStyle(Graphics2D g2){
    if (isFocusOwner()) {
        double width=getWidth()-4;
        int height=getHeight();
        g2.setColor(getLineColor());
        double size;
        if(show){
            size = width * (1 - location);
        } else {
            size = width * location;
        }
        double x=(width - size)/2;
        g2.fillRect((int) (x*2), height-2, (int) size, 2);
    }
    }
    @Override
    public void setText(String string) {
        if (String.valueOf(getPassword()).equals(string)) {
            showing(string.equals(""));
        }
        super.setText(string);
    }
}