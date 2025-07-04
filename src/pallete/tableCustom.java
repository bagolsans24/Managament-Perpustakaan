/*package pallete;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import com.raven.swing.TableHeader;
import javax.swing.JLabel;


public class tableCustom extends JTable{
    
    
    public tableCustom() {
        setShowHorizontalLines(true);
        setGridColor(new Color(230,230,230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer(){
        @Override
        public Component getTableCellRendererComponent (JTable jtable, Object o, boolean bln, boolean blnl, int i, int ii){
            TableHeader header = new TableHeader(o+"");
            if(ii!==4){
                header.setHorizontalAlignment(JLabel.CENTER);    
            }
            return header;
        } 
   });
        
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent (JTable jtable, Object o, boolean bln, boolean blnl, int i, int ii){
                if(ii!=4){
                    Component com = super.getTableCellRendererComponent(jtable, o, selected, blnl, i, ii);
                    com.setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    if(selected){
                        com.setForeground(new Color(15,89,140));
                    }else{
                        com.setForeground(new Color(102,102,102));
                    }
                return com;
                } else {
                    StatusType type = (StatusType) o;
                    CellStatus cell = new CellStatus(type);
                    return cell;
                }
            }
        });
     }
            
     public void addRow(Object[] row){
         DefaultTableModel model = (DefaultTableModel) getModel();
         model.addRow(row);
     }
     }*/



