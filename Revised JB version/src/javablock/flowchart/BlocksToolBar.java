/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javablock.flowchart;

import config.translator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.JLabel;


public class BlocksToolBar extends JToolBar{
    public BlocksToolBar(Flowchart flow){
        setFloatable(false);
        setRollover(true);
        setOrientation(JToolBar.VERTICAL);
        JButton b;

        JToolBar blocks = new JToolBar("Blocks");
        blocks.setFloatable(false);
        blocks.setOrientation(JToolBar.HORIZONTAL);
        
        
        {
            JToolBar std = new JToolBar("Standard");
            std.setFloatable(false);
            std.setOrientation(JToolBar.VERTICAL);
            std.addSeparator();
            JLabel label1 = new JLabel("Flowchart Symbols", JLabel.LEFT);
            std.add(label1);
            std.addSeparator();
            for (JBlock.Type T : JBlock.StandardTypes) {
                b = new JButton(); 
                b.setToolTipText(translator.tooltips.getString(T.toString() + ".help"));
                         
                if(T == JBlock.StandardTypes[3])
                {
                    b.setPreferredSize(new Dimension(60, 30));
                    b.setActionCommand("moduleaction/MODULE");
                    b.setName("moduleaction/MODULE");
                }
                else if(T == JBlock.StandardTypes[2]){
                
                    b.setPreferredSize(new Dimension(60, 50));
                    b.setActionCommand("add/" + T.toString());
                    b.setName("add/" + T.toString());
                
                }
                else{
                    b.setPreferredSize(new Dimension(60, 30));
                     b.setActionCommand("add/" + T.toString());
                     b.setName("add/" + T.toString());
                }
                
                b.setIcon(new javax.swing.ImageIcon(JBlock.getIcon(T)));
                std.add(b);                
                //std.add(new JPopupMenu.Separator());
                b.addActionListener(flow);
                b.addMouseMotionListener(flow);
            }
            for (JBlock.Type T : JBlock.HelpingTypes) {
                b = new JButton();
                b.setToolTipText(translator.tooltips.getString(T.toString() + ".help"));
                b.setActionCommand("add/" + T.toString());
                b.setPreferredSize(new Dimension(60, 30));
                b.setIcon(new javax.swing.ImageIcon(JBlock.getIcon(T)));                
                std.add(b);
                //std.add(new JPopupMenu.Separator());
                b.addActionListener(flow);
                b.addMouseMotionListener(flow);
            }
            
             std.addSeparator();
            JLabel label2 = new JLabel("Loop Structures", JLabel.LEFT);
            std.add(label2);
            std.addSeparator();
            
            for (JBlock.Type T : JBlock.LoopTypes) {
                b = new JButton(); 
                b.setToolTipText(translator.tooltips.getString(T.toString() + ".help"));
                if(T == JBlock.LoopTypes[0])
                {
                    b.setActionCommand("foraction/FORLOOP" + T.toString());
                    b.setName("foraction/FORLOOP" + T.toString());
                } 
                else if(T == JBlock.LoopTypes[1]){
                    b.setActionCommand("whileaction/" + T.toString());
                    b.setName("whileaction/" + T.toString());
                }
                else if(T == JBlock.LoopTypes[2]){
                    b.setActionCommand("dowhileaction/" + T.toString());
                    b.setName("dowhileaction/" + T.toString());
                }
                b.setPreferredSize(new Dimension(60, 30));
                b.setIcon(new javax.swing.ImageIcon(JBlock.getIcon(T)));
                std.add(b);                
                //std.add(new JPopupMenu.Separator());
                b.addActionListener(flow);
                b.addMouseMotionListener(flow);
            }
            
            std.addSeparator();
            JLabel label3= new JLabel("Input/Output", JLabel.LEFT);
            std.add(label3);
            std.addSeparator();
            
            
            for (JBlock.Type T : JBlock.IOTypes) {
                b = new JButton();
                b.setToolTipText(translator.tooltips.getString(T.toString() + ".help"));
                b.setActionCommand("add/" + T.toString());
                b.setPreferredSize(new Dimension(60, 30));
                
                //BufferedImage img = JBlock.getIcon(T);
                //img
                b.setIcon(new javax.swing.ImageIcon(JBlock.getIcon(T)));                
                std.add(b);
                //std.add(new JPopupMenu.Separator());
                b.addActionListener(flow);
                b.addMouseMotionListener(flow);
                
                
            }
            
            blocks.add(std);
            add(blocks);
            validate();
            revalidate();        
        }
    }
}
