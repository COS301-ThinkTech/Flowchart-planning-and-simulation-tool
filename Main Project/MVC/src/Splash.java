/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//package javablock.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;
import javax.swing.*;

public class Splash extends JWindow implements MouseListener {

    private int duration;

    public Splash(int d) {
        duration = d;
    }

    public JPanel content;
    public void createSplash(){
        content = (JPanel)getContentPane();
        content.setBackground(Color.white);
        // Build the splash screen
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("icons/splash.jpg")));
        JLabel copyright = new JLabel
                ("Copyright 2015, University of Pretoria", JLabel.CENTER);
        copyright.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyright, BorderLayout.SOUTH);
    }
    public void showSplash() {
        content = (JPanel)getContentPane();
        /*content=new JPanel(){
            public void paintComponent(Graphics g) {
                Point pos = this.getLocationOnScreen( );
                Point offset = new Point(-pos.x,-pos.y);
                g.drawImage(background,offset.x,offset.y,null);
                }
        };
        updateBackground();
        this.setContentPane(content);*/
        content.setBackground(Color.white);
        //content.setOpaque(true);
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("flowicons/gui/icons/splash.jpg")));
        int width = label.getIcon().getIconWidth();
        int height =label.getIcon().getIconHeight();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x,y,width,height);
        
        
        JLabel copyright = new JLabel("github.com/COS301-ThinkTech");
        //JLabel copyrt = new JLabel
        //        ("<html>Autor: Jakub Konieczny<br/>"
        //        + "Zespół Szkół Zawodowych w Zawadzkiem<br/>"
        //        + "nauczyciel prowadzący: Piotr Wiatrek", JLabel.CENTER);
        //copyrt.setOpaque(true);
        copyright.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        //content.add(copyrt, BorderLayout.SOUTH);
        setVisible(true);
        this.addMouseListener(this);

    }
    /*
    Image background;
    public void updateBackground( ) {
        try {
            Robot rbt = new Robot( );
            Toolkit tk = Toolkit.getDefaultToolkit( );
            Dimension dim = tk.getScreenSize( );
            background = rbt.createScreenCapture(
            new Rectangle(0,0,(int)dim.getWidth( ),
                              (int)dim.getHeight( )));
        } catch (Exception ex) {
            ex.printStackTrace( );
        }
    }*/


    @Override
    public void show(){
        closer c=new closer(this);
        super.show();
    }

    public void mouseClicked(MouseEvent e) {
        setVisible(false);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}


class closer extends TimerTask{
    java.util.Timer timer;
    Splash s=null;
    public closer(Splash s){
        this.s=s;
        timer = new java.util.Timer( );
        timer.schedule(this, 2000, 2000);
    }


    @Override
    public void run() {
        s.hide();
        timer.cancel();
    }
}
