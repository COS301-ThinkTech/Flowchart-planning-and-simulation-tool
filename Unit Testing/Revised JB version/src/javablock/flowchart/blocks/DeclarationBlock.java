/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javablock.flowchart.blocks;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javablock.flowchart.Flowchart;
import javablock.flowchart.JBlock;

/**
 *
 * @author tshepiso
 */
public class DeclarationBlock extends JBlock{

    public DeclarationBlock(Type type, Flowchart parent) {
        super(type, parent);
    }

    //public DeclarationBlock(Flowchart parent) {
        //super(Type.DECLARATION, parent);
    //}

    @Override
    public void shape(){
        prepareText();
        Rectangle2D cpu=new Rectangle.Double(
                        bound.getX()-10,
                        bound.getY()-10,
                        bound.getWidth()+20,
                        bound.getHeight()+20
                        );
                shape=cpu;
        afterShape();
    }
}
