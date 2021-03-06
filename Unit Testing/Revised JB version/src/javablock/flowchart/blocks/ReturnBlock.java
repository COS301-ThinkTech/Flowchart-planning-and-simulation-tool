/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javablock.flowchart.blocks;
import config.Global;
import config.translator;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javablock.flowchart.Flowchart;
import javablock.flowchart.JBlock;
import javablock.flowchart.Flowline;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import org.w3c.dom.Element;


public class ReturnBlock extends JBlock {
    public ReturnBlock(Flowchart parent){
        super(Type.RETURN, parent);
        this.displayComment=true;
        this.comment=translator.misc.getString("End");
    }
    @Override
    public boolean isSwitchable() {
        return false;
    }
    @Override
    public boolean isEditable(){
        return true;
    }

    @Override
    public JBlock executeCode(ScriptEngine script){
        if(this.code.length()==0)
            return null;
        else
            try {
                script.eval(getCode());
            } catch (ScriptException ex) {
                Logger.getLogger(ReturnBlock.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }

    @Override
    public boolean oneOut(){return false;}
    
    @Override
    public void prepareText(){
        displayComment=true;
        if(comment.length()==0)
            this.comment="End";
        super.prepareText();
    }

    @Override
    public String getScriptFragmentForJavaScript(){
        String code="";
        String c=getCode();
        if(c.length()>0)
            if (c.startsWith("return")) {
                code += "\t\t\t" + c + "\n";
            } else {
                code += "\t\t\treturn " + c+ "\n";
            }
        else
            code+="\t\t\treturn 0;\n";
        //code+="break;\n";
        return code;
    }
    

    @Override
    public void shape(){
        prepareText();
        Ellipse2D st1=new Ellipse2D.Double(
            bound.getX()-10,
            bound.getY()-10,
            40,
            bound.getHeight()+20
        );
        Ellipse2D st2=new Ellipse2D.Double(
            bound.getX() + bound.getWidth()-30,
            bound.getY()-10,
            40,
            bound.getHeight()+20
        );
        Rectangle2D r=new Rectangle2D.Double(
            bound.getX()+10,
            bound.getY()-10,
            bound.getWidth()-20,
            bound.getHeight()+20
            );
        Area s=new Area(st1);
        s.add(new Area(st2));
        s.add(new Area(r));
        shape=s;
        afterShape();
    }

    @Override
    public Flowline connectTo(JBlock n){
        return null;
    }

    @Override
    protected void specialXmlSave(Element xml){
  
    }
    @Override
    protected void specialXmlLoad(Element xml, boolean connect){
 
    }
}
