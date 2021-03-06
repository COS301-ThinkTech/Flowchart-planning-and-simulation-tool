/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package widgets;

import config.Global;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class JTable2 extends JTable {
    public JTable2(TableModel tab){
        super(tab);
    }
    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        revalidate();
        repaint();
    }

    TableModelEx t=null;
    @Override
    public void setModel(TableModel tab){
        super.setModel(tab);
        t=(TableModelEx)tab;
        ch=new char[getModel().getRowCount()+1];
    }
    private char ch[]=new char[10];
    public void restate(){
        if(!Global.markChanges) return;
        if(getModel().getRowCount()!=ch.length)
            ch=new char[getModel().getRowCount()+1];
        for(int i=0; i<ch.length; i++)
            ch[i]=0;
        
    }
    public void low(){
        for(int i=0; i<ch.length; i++)
          ch[i]--;
    }
    @Override
    public void setValueAt(Object v, int row, int col){
        if(Global.markChanges)
        if(col==1){
            if(!v.equals(this.getValueAt(row, col)))
                restate();
            ch[row]=3;
            }
        super.setValueAt(v,row,col);
    }
    @Override
    public Component prepareRenderer(TableCellRenderer renderer,
            int rowIndex, int vColIndex) {
        Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
        if(Global.markChanges){
            if(rowIndex>=ch.length) restate();
            if(ch[rowIndex]>0 && vColIndex==1){
                switch(ch[rowIndex]){
                    case 1: c.setBackground(Color.PINK); break;
                    case 2: c.setBackground(Color.ORANGE); break;
                    case 3: c.setBackground(Color.RED); break;
                }
            }
            else{
                if(this.isCellSelected(rowIndex, vColIndex))
                    c.setBackground(this.getSelectionBackground());
                else
                    c.setBackground(getBackground());
            }
        }
        else{
            if(this.isCellSelected(rowIndex, vColIndex))
                c.setBackground(this.getSelectionBackground());
            else
                c.setBackground(getBackground());
        }
        return c;

    }
}
