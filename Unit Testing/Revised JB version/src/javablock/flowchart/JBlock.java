package javablock.flowchart;

import addons.AnimatedVariable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javablock.flowchart.blocks.*;
import config.Global;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import javablock.FlowchartManager;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.w3c.dom.*;

public abstract class JBlock implements FlowElement{
    public enum Type { START, RETURN,
        IO, CPU, DECISION, LINK,
        JUMP, NULL, GROUP, 
        CUSTOM, MODULE, FORLOOP,COMMENT,WHILELOOP,DOWHILELOOP
    };
    static final ArrayList<Class<JBlock>> customTypes=new ArrayList<Class<JBlock>>();
    static HashMap<Class, String> customTypesNames=new HashMap<Class, String>();
    public static ArrayList<Class<JBlock>> getCustomTypes(){
        return customTypes;
    }
    public static void addCustomType(Class newType, String name){
        try {
            Object o=newType.getConstructor().newInstance();
            if(o instanceof JBlock){
                customTypes.add(newType.asSubclass(JBlock.class));
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Type getTypeFromString(String type){
        if(type.equals("CLIP")) type="BRACE";
        try{
            return Type.valueOf(type);
        }catch(Exception e){
        }
        return Type.CUSTOM;
    }
    public static Type StandardTypes[]={
        Type.CPU/*, Type.IOin, Type.IOout*/, Type.DECISION, Type.RETURN, Type.MODULE, Type.COMMENT,Type.FORLOOP, Type.WHILELOOP,Type.DOWHILELOOP
    };
    public static Type HelpingTypes[]={
        Type.IO, Type.JUMP
    };
   
    
    public static String leftLineTypes=
        "CPU IOin IOout DECISION RETURN MODULE FORLOOP WHILELOOP";
    public static String rightLineTypes=
        "IO JUMP LINK BRACE STRUCT SCRIPT";
    public static String[] getLeftLine(){
        return leftLineTypes.split(" ");
    }
    public static String[] getRightLine(){
        return rightLineTypes.split(" ");
    }
    
    public static BufferedImage icons[]=new BufferedImage[20];
    public JBlock linkTo;
    public Type type;
    public boolean codeBased=true;
    public float posX=0, posY=0;
    public Shape shape=new Rectangle(0,0,0,0);
    public String code, comment;
    public boolean displayComment=false;
    public boolean commentIsHTML=false;
    public Object extra=null;

    public Color color, borderColor, textColor;
    protected Paint gradient;
    //public boolean alignCenter;

    public List<Flowline> connects=new ArrayList();
    public List<Flowline> connectsIn=new ArrayList();
    public List<BlockGroup> groups=new ArrayList();
    public int ID;

    public Flowchart flow;
    protected BasicStroke border=Global.strokeNormal;
    protected Font font;

    public JBlock(Type type, Flowchart parent){
        this.flow=parent;
        this.type=type;
        font=Global.monoFontBold;
        color=Color.LIGHT_GRAY;
        borderColor=Color.BLACK;
        textColor=Color.BLACK;
        gradient=new GradientPaint(0,0, color, 0,20, color.darker());
        code="";
        comment="";
        switch(type){
            case START: code=""; break;
            case NULL: code="-"; break;
        }
        if(Global.prerender){
            prerender=new BufferedImage(1,1, BufferedImage.TYPE_4BYTE_ABGR);
        }
    }
    String typeInString;
    public String getType(){
        if(type!=Type.CUSTOM)
            return type.toString();
        return typeInString;
    }
    
    public static JBlock make(String t, boolean codeBased, Flowchart parent){
        Type type=getTypeFromString(t);
        if(type!=Type.CUSTOM)
            return make(type, codeBased, parent);
        return makeCustom(t, parent);
    }
    static JBlock makeCustom(String type, Flowchart parent){
        for(Class<JBlock> c:customTypes){
            try {
                if(c.getMethod("getName").invoke(null).equals(type)){
                    return c.getConstructor(Flowchart.class).newInstance(parent);
                }
            } catch (IllegalAccessException ex) {
                Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return make(Type.CUSTOM, true, parent);
    }
    public static JBlock make(Type type, boolean codeBased, Flowchart parent){
        JBlock b;
        switch(type){            
            case IO: b= new IOBlock(parent); break;
            case CPU: b= new CPUBlock(parent); break;
            case START: b= new StartBlock(parent); break;
            case RETURN: b= new ReturnBlock(parent); break;
            case DECISION: b= new DecisionBlock(parent); break;
            case MODULE: b= new ModuleBlock(parent);  break;
            case FORLOOP: b= new ForLoopBlock(parent);  break; 
            case WHILELOOP: b= new WhileLoopBlock(parent);  break; 
            case DOWHILELOOP: b=new DoWhileLoopBlock(parent); break;
            case LINK: b= new LinkBlock(parent); break;
            case JUMP: b= new JumpBlock(parent); break;
            case GROUP: b= new BlockGroup(parent); break;
            case COMMENT: b=new CommentBlock(parent); break;
            default: b= new CPUBlock(parent);b.type=Type.CUSTOM; break;
        }
        
           b.nonCodeBased(!codeBased);
        return b;
    }
    public static JBlock make(Type type, Flowchart parent){
        return make(type, true, parent);
    }
    public static JBlock make(String t, Flowchart parent){
        return make(t, true, parent);
    }
    /**
     * calls when block is added to scene
     */
    public void addedToScene(){}
    protected void nonCodeBased(boolean ncb){}
    public FlowchartManager getManager(){
        return flow.action;
    }
    @Override
    public boolean isEditable(){
        return true;
    }
    public boolean isSwitchable(){
        return true;
    }
    public boolean canBeConnected(JBlock b){
        return true;
    }
    public boolean oneOut(){return true;}
    public boolean popUpEditor(){return true;}
    public boolean isDefinitionBlock(){return false;}
    @Override
    public BlockEditor getEditor(){
        return (BlockEditor) javablock.flowchart.blockEditors.Editor.standard;
    }
    public boolean moveWhenAdded(){
        return true;
    }

    public Flowline connectTo(JBlock n){
        if(n==this || n==null) return null;
        for(Flowline c: connects)
            c.n.removeConnectFrom(this);
        connects.clear();
        Flowline c=new Flowline(this, n);
        flow.historyAdd();
        connects.add(c);
        n.addInConnect(c);
        return c;
    }
    public void reverseValues(){
        for(int i=0; i<connects.size(); i++){
            if(connects.get(i).value.equals("true"))
                connects.get(i).setValue("false");
            else
                connects.get(i).setValue("true");
        }
    }

    public void removeInConnects(){
        for(Flowline c:connectsIn){
            c.f.removeConnectTo(this);
        }
        connectsIn.clear();
    }
    public void removeOutConnects(){
        for(Flowline c:connects){
            c.f.removeConnectFrom(this);
        }
        connects.clear();
    }

    public void addInConnect(Flowline c){
        connectsIn.add(c);
    }
    public void removeConnectFrom(JBlock b){
        for(int i=0; i<connectsIn.size(); i++)
            if(b==connectsIn.get(i).f)
                connectsIn.remove(i);
    }
    public void removeConnectTo(JBlock b){
        for(int i=0; i<connects.size(); i++)
            if(b==connects.get(i).n)
                connects.remove(i);
    }
    public void removeConnectWith(JBlock b){
        removeConnectTo(b);
        removeConnectFrom(b);
    }

    public boolean deleted=false;
    @Override
    public void delete(){
        deleted=true;
        if(connectsIn.size()==1 && connects.size()==1)
            connectsIn.get(0).f.connectTo(connects.get(0).n);
        for(int i=0; i<connects.size(); i++)
            connects.get(i).n.removeConnectWith(this);
        for(int i=0; i<connectsIn.size(); i++)
            connectsIn.get(i).f.removeConnectWith(this);
        connects.clear();
        connectsIn.clear();
        prerender=null;
        gradient=null;
        txtList.clear();
        if(flow!=null)
            while(flow.blocks.remove(this)){};
        flow=null;
    }

    @Override
    public void finalize(){
        try {
            super.finalize();
            code=null;
            comment=null;
        } catch (Throwable ex) {
            Logger.getLogger(JBlock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setColor(Color c){
        color=c;
        needUpdate=true;
    }
    public void setBorderColor(Color c){
        borderColor=c;
        needUpdate=true;
    }
    public void setTextColor(Color c){
        textColor=c;
        needUpdate=true;
    }
    public void setPos(float x, float y){
        posX=x;
        posY=y;
        needUpdate=true;
        for(int i=0; i<connects.size(); i++)
            connects.get(i).needUpdate=true;
        for(int i=0; i<connectsIn.size(); i++)
            connectsIn.get(i).needUpdate=true;
    }
    public void setPos(double x, double y){
        posX=(float)x;
        posY=(float)y;
        needUpdate=true;
        for(int i=0; i<connects.size(); i++)
            connects.get(i).needUpdate=true;
        for(int i=0; i<connectsIn.size(); i++)
            connectsIn.get(i).needUpdate=true;
    }

    protected Rectangle2D bound=new Rectangle2D.Double();
    public boolean needUpdate=true;
    public boolean nowExecute=false;

    public void prepareToExe(){
        nowExecute = !flow.blockEdit;
        makeGradient();
    }
    public void releaseFromExe(){
        nowExecute=false;
        makeGradient();
    }
    
    public JBlock nextExe(){
        JBlock n=this;
        return n;
    }

    public JBlock nextBlock(){
        if(connects.size()==1)
            return connects.get(0).n;
        return null;
    }

    public Shape getRound(){
        return new Ellipse2D.Float(posX-5, posY-5, 10, 10);
    }

    private boolean rep=false;
    public String getCode(){
        if(getManager().scriptEngine.equals("JavaScript"))
            return getCodeForJavaScript();
        return "";
    }

    protected boolean isReadyCode(){
        return getEditor() != javablock.flowchart.blockEditors.Editor.standard;
    }
    private String parseCode(char s){
        String c="";
        String code=this.code.replaceAll("\"", "\" ");
        // <editor-fold defaultstate="collapsed" desc="Pascal mode">
        if (Global.pascalMode && !isReadyCode()) {
            String l[] = code.split("\"");
            int i = 0;
            for (String part : l) {
                if (i % 2 == 1) {
                    c += part;
                    if (i < l.length - 1)
                        c += "\"";
                    i++;
                    continue;
                }
                part = part.replaceAll("([^=!<>:])=([^=!<>])", "$1==$2")
                    .replaceAll("([^=!<>:])<>([^=!<>])", "$1!=$2")
                    .replaceAll(
                            "([a-zA-Z0-9]*):=([a-zA-Z0-9]*)",
                            "$1=$2")
                    .replaceAll("([^\\+\\-\\*\\/\\n\\^&|;=<>]*) (div|DIV) ([^\\+\\-\\*\\/\\n\\^&|;=<>]*)",
                            "floor($1/$3)")
                   .replaceAll("([^\\+\\-\\*\\/\\n\\^&|;=<>]*) (mod|MOD) ([^\\+\\-\\*\\/\\n\\^&|;=<>]*)",
                            "($1%$3)")
                    .replaceAll(" AND ", " && ")
                    .replaceAll(" OR ", " || ")
                    .replaceAll(" XOR ", " ^^ ")
                    .replaceAll(" NOT ", " ! ");
                c += part;
                if (i < l.length - 1)
                    c += "\"";
                i++;
            }
            System.out.println(c);
            if(code.endsWith("\""))
                c+="\"";
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Normal mode">
        else if(Global.scriptReplace){
            String l[] = code.split("\"");
            int i = 0;
            for (String part : l) {
                if (i % 2 == 1) {
                    c += part;
                    if (i < l.length - 1) {
                        c += "\"";
                    }
                    i++;
                    continue;
                }
                part = part.replaceAll("([^\\+\\-\\*\\/\\n\\^&|;=<>]*) (div|DIV) ([^\\+\\-\\*\\/\\n\\^&|;=<>]*)",
                        "toInt(floor($1/$3))")
                    .replaceAll("([^\\+\\-\\*\\/\\n\\^&|;=<>]*) (mod|MOD) ([^\\+\\-\\*\\/\\n\\^&|;=<>]*)",
                        "($1%$3)");
                c += part;
                if (i < l.length - 1)
                    c += "\"";
                i++;
            }
            if(code.endsWith("\""))
                c+="\"";
        }// </editor-fold>
        //else
        if(c.length()==0)
            c=code;
        // <editor-fold defaultstate="collapsed" desc="IO">
        if(Global.scriptReplace)
        if (type == Type.IO) {
            String l[] = c.split("\n");
            c = "";
            int i = 0;
            for (String part : l) {
                part = part.replaceAll("read\\s([a-zA-Z_][a-zA-Z0-9_\\[\\]]*)",
                        "$1=Read(\"$1=\")");
                part = part.replaceAll("readNumber\\s([a-zA-Z_][a-zA-Z0-9_\\[\\]]*)",
                        "$1=ReadNumber(\"Number $1=\")");
                part = part.replaceAll("write\\s([^\n|$]*)","Write($1)");
                part = part.replaceAll("writeln\\s([^\n|$]*)","Writeln($1)");
                c += part;
                i++;
                if(i<l.length)
                    c+="\n";
            }
        }// </editor-fold>
        if(c.indexOf("#^")>=0){
            String l[]=c.split("\n");
            c="";
            for (String l1 : l) {
                if (l1.startsWith("#^")) {
                    if (l1.charAt(2) == s) {
                        c += l1.substring(4) + "\n";
                    }
                } else {
                    c += l1 + "\n";
                }
            }
        }
        return c;
    }

    public int getId(){
        return ID;
    }
    public void setId(int id){
        this.ID=id;
    }
    private String jsRecoded="", jsBeforeRecoded="";
    private String getCodeForJavaScript(){
        //if(rep!=Global.scriptReplace)
        //    jsBeforeRecoded="";
        rep=Global.scriptReplace;
        if(code.equals(jsBeforeRecoded)) return jsRecoded;
        String c=parseCode('j');
        jsRecoded=c.replaceAll("\" ", "\"");
        //if(Global.scriptReplace)
            jsBeforeRecoded=this.code;
        return jsRecoded;
    }

    
    
    public String getScriptFragmentForJavaScript(){
        String code="";
        for(String line: (getCodeForJavaScript().split("\\n")) ){
            code+="\t\t\t"+line+"\n";
        }
        if(connects.size()==1)
            code+="\t\t\t"+flow.getName()+"_block="+connects.get(0).n.nextExe().ID+"\n";
        else
            code+="\t\t\treturn 0;\n";
        code+="\t\t\tbreak;\n";
        return code;
    }

    
    public JBlock executeCode(ScriptEngine script){
        if(script==null) return null;
        if(type==Type.NULL){
            if(connects.size()==1)
                return connects.get(0).n.nextExe();
        }
        else {
            try {
                //System.err.println(getCode());
                script.eval(getCode());
                nowExecute=false;
                makeGradient();
                if(connects.size()==1)
                    return connects.get(0).n.nextExe();
            } catch (ScriptException ex) {
                JOptionPane.showMessageDialog(null,"Script error:\n"+getCode()+"\n\n"
                        + ""+ex.getMessage());
                return null;
            }
        }
        return null;
    }
    public JBlock execute(ScriptEngine script){
        return execute(script, true);
    }
    public JBlock execute(ScriptEngine script, boolean highlight){
        if(script==null) return null;
        JBlock r= executeCode(script);
        releaseFromExe();
        if(highlight && r!=null){
                r.prepareToExe();
            flow.update();
        }
        return r;
    }

    protected List<TextLayout> txtList=new ArrayList();
    protected List<List<TextLayout>> txtLines=new ArrayList();
    protected float height=0;

    public BufferedImage prerender=null;
    protected boolean prerendered=false;
    protected float prerenderedInScale=0;
    @Override
    public void shape(){
        prepareText();
        afterShape();
        prerender=null;
        prerendered=false;
    }
    public void update(){
        if(needUpdate) shape();
    }
    
    public FontRenderContext frc;
    public TextLayout txtLay;
    JLabel label;
    protected String labelStyle(){
        return "";
    }
    protected String makeHTML(String txt){
        Color stringCol=new Color((
                0xffffff-color.getRGB()
                )&0x00ffffff);
        return txt
                    .replaceAll(" ", "&nbsp;")
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\n", "<br/>")
                    
                    .replaceAll("\\\"(.*?)\\\"",
                        "<i color=\""+Integer.toHexString( stringCol.getRGB() & 0x00ffffff )+"\">$1</i>")
                    .replaceAll("var&nbsp;",
                        "<b>var&nbsp;</b>")
                    //.replaceAll("\\((.*?)\\)", "(<small>$1</small>)")
                    ;
    }
    protected void prepareText(){
        String txt;
        if(this.displayComment || code.length()==0)
            txt=this.comment;
        else{
            txt=code;
            if(Global.flowMarks){
                if(!Global.pascalMode)
                    txt=code.replaceAll("([^=+\\-\\*/<>!])=([^=\\*/<>])", "$1←$2")
                            .replaceAll("([^=+\\-\\*/<>])<>([^=\\*/<>])", "$1≠$2");
                else
                    txt=code.replaceAll("([^=+\\-\\*/<>!]):=([^=\\*/<>])", "$1←$2");
                txt=txt.replaceAll("<=", "≤")
                        .replaceAll(">=", "≥")
                        .replaceAll("==", "=")
                        .replaceAll("!=", "≠")
                        ;
            }
        }
        String lines[];
        if(txt.length() < 1){
            txt=" ";}
        lines=txt.split("\n");
        float width=0;
        if(txt.length() < 1){
            lines[0]=" ";}
        if(txt.length() < 1){
            txt="0";}
        txtList.clear();

        if(flow!=null){
            if(frc==null)
                frc=flow.frc;
            if(frc==null)
                frc=Global.frc;
            if(flow.txtLay!=null)
                txtLay=flow.txtLay;
        }
        if(Global.useJLabels){
            if(label==null){
                label=new JLabel();
            }
            label.setFont(Global.monoFont);
            if(displayComment && commentIsHTML){
            }
            else{
                txt=makeHTML(txt);
            }
            
            label.setText("<html><body "+labelStyle()+">"+txt+"</body></html>");
            label.setSize(label.getPreferredSize());
            bound.setRect(-label.getPreferredSize().width/2,
                        -label.getPreferredSize().height/2,
                        label.getPreferredSize().width,
                        label.getPreferredSize().height);
            
        }
        else {
            if(txtLay==null){
                if(frc==null)
                    frc=Global.frc;
                txtLay=new TextLayout(".YTyj",
                        Global.monoFont,
                        frc);
            }
            height=txtLay.getAscent();
            for(int i=0; i<lines.length; i++){
                if(lines[i].length()==0)
                    lines[i]=" ";
                if(lines[i].startsWith("#^")){
                    if(lines[i].charAt(2)==(getManager().scriptEngine.equals("JavaScript")?'j':'p'))
                        lines[i]=lines[i].substring(4);
                    else continue;
                }
                TextLayout t=new TextLayout(lines[i], Global.monoFont, frc);
                if(width<t.getBounds().getWidth())
                    width=(float) t.getBounds().getWidth();
                txtList.add(t);
            }
            bound=txtLay.getPixelBounds(frc, 0, 0);

            bound.setRect(
                    -(width+1)/2,
                    -(height+2)*txtList.size()/2,
                    width+1,
                    (height+2)*txtList.size()
                    );
        }
        prerender=null;
        prerendered=false;
        for(JBlock g:groups){
            g.needUpdate=true;
        }
    }
    
    public void drawText(Graphics2D g2d){
        g2d.setStroke(Global.strokeNormal);
        g2d.translate(bound.getX(), bound.getY()+height-1);
        g2d.setColor(textColor);
        if(Global.useJLabels){
            label.setForeground(textColor);
            label.setSize(label.getPreferredSize().width+15,
                          label.getPreferredSize().height);
            label.paint(g2d);
        }
        else{
            for(int i = 0; i < txtList.size(); i++) {
                txtList.get(i).draw(g2d, 0, (height + 2) * i + 1);
            }
        }
        g2d.translate(-bound.getX(), -bound.getY()-height+1);
    }

    protected void afterShape(){
        makeGradient();
        needUpdate=false;
        for(int i=0; i<connects.size(); i++)
            connects.get(i).needUpdate=true;
        for(int i=0; i<connectsIn.size(); i++)
            connectsIn.get(i).needUpdate=true;
        prerender=null;
        prerendered=false;
    }
    public void makeGradient(){
        prerendered=false;
        prerender=null;
        if(gradient!=null)
            gradient=null;
        if(nowExecute){
            gradient=new GradientPaint(0,shape.getBounds().y, color, 0,
                (float) shape.getBounds().y+shape.getBounds().height, color.brighter());
        }
        else{
            Color col;
                col=new Color(
                    (int)((color.getRed()  *2+borderColor.getRed())  /3),
                    (int)((color.getGreen()*2+borderColor.getGreen())/3),
                    (int)((color.getBlue() *2+borderColor.getBlue()) /3)
                    );
            if(color.getRed()+color.getGreen()+color.getBlue()>
                    col.getRed()+col.getGreen()+col.getBlue())
                gradient=new GradientPaint(0,shape.getBounds().y, color, 0,
                    (float) shape.getBounds().y+shape.getBounds().height, 
                        col
                        );
            else
                gradient=new GradientPaint(0,shape.getBounds().y, col, 0,
                    (float) shape.getBounds().y+shape.getBounds().height, 
                        color
                        );
        }
    }

    public void setCode(String c){
        code=c;
        needUpdate=true;
    }
    public void setComment(String c){
        comment=c;
        needUpdate=true;
    }

    public void requestRepaint(){
        if(flow==null) return;
        flow.update();
    }

    ///public BufferedImage icon=null;
    public BufferedImage drawIcon(){
        BufferedImage ico=new BufferedImage(
                        1, 1,
                        BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) ico.getGraphics();
        frc = g.getFontRenderContext();
        shape();
        float scale=(float) 0.5;
        ico=new BufferedImage(
                        (int) ((shape.getBounds().width + 4)*scale) + 2,
                        (int) ((shape.getBounds().height + 4)*scale),
                        BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) ico.getGraphics();
        g.scale(scale, scale);
        g.translate(-shape.getBounds().x + 2, -shape.getBounds().y + 2);
        if (Global.antialiasing) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        }
        else
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setPaint(gradient);
        g.fill(shape);
        g.setColor(borderColor);
        g.setStroke(border);
        g.draw(shape);
        g.setStroke(Global.strokeNormal);
        g.translate(bound.getX(), bound.getY()+height);
        if(Global.useJLabels){
            label.paint(g);
        }
        else{
            g.setColor(textColor);
            for (int i = 0; i < txtList.size(); i++) {
                txtList.get(i).draw(g, 0, (height + 2) * i + 1);
            }
        }
        if(!this.codeBased){
            //g.drawString("E", 10, 10);
        }
        //g.draw(bound);
        prerendered = true;

        return ico;
    }
    public static BufferedImage getIcon(Type t){
        System.out.println("Type " + t);
        if(icons[t.ordinal()]!=null)
            return icons[t.ordinal()];
        JBlock b=make(t,null);
        icons[t.ordinal()]=b.drawIcon();
        return icons[t.ordinal()];
    }
      

    float scale=1;
    float highlight=0;
    @Override
    public void draw(Graphics2D g2d){draw(g2d, false);}
    @Override
    public void draw(Graphics2D g2d, boolean drawFull){
        if(flow==null) return;
        if (Global.prerender) {
            if (prerenderedInScale != flow.scale() || !prerendered || prerender == null) {
                prerender = null;
                prerendered = false;
                prerenderedInScale = (float) flow.scale();
            }
            if (prerender == null) {
                prerender = new BufferedImage(
                            (int) ((shape.getBounds().width + 4) * prerenderedInScale) + 2,
                            (int) ((shape.getBounds().height + 4) * prerenderedInScale),
                            BufferedImage.TYPE_INT_ARGB);
            }
            if (!prerendered && prerender!=null) {
                Graphics2D g;
                g=(Graphics2D) prerender.getGraphics();
                g.scale(prerenderedInScale, prerenderedInScale);
                g.translate(-shape.getBounds().x + 2, -shape.getBounds().y + 2);
                if (Global.antialiasing) {
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                }
                if(Global.gradients)
                    g.setPaint(gradient);
                else
                    g.setPaint(color);
                g.fill(shape);
                g.setColor(borderColor);
                g.setStroke(border);
                g.draw(shape);
                drawText(g);
                prerendered = true;
            }
        }
        if(g2d==null) return;
        g2d.translate(posX, posY);
        g2d.scale(scale, scale);
        if (Global.prerender && !drawFull) {
            g2d.translate(shape.getBounds2D().getX() - 2,
                    shape.getBounds2D().getY() - 2);
            g2d.scale(1 / prerenderedInScale, 1 / prerenderedInScale);
            g2d.drawImage(prerender, 0, 0, flow);
            g2d.scale(prerenderedInScale, prerenderedInScale);
            g2d.translate((-shape.getBounds2D().getX()) + 2,
                    (-shape.getBounds2D().getY()) + 3);
        }
        else {
            g2d.setPaint(gradient);
            g2d.fill(shape);
            g2d.setColor(borderColor);
            g2d.setStroke(border);
            g2d.draw(shape);
            drawText(g2d);
            g2d.translate(-1, 1);
        }
        if(nowExecute && Global.bolderBorder){
            g2d.setStroke(Global.strokeBold6);
            g2d.setColor(borderColor);
            g2d.draw(shape);
            g2d.setStroke(Global.strokeBold);
            g2d.setColor(color);
            g2d.draw(shape);
            g2d.setStroke(Global.strokeNormal);
        }
        if(Global.debugMode){
            g2d.drawString("ID: "+this.ID, 
                shape.getBounds().width/2,
                shape.getBounds().height/2);
            g2d.drawString("IN: "+this.connectsIn.size(), 
                shape.getBounds().width/2,
                shape.getBounds().height/2+15);
            g2d.drawString("OUT: "+this.connects.size(), 
                shape.getBounds().width/2,
                shape.getBounds().height/2+15*2);
        }
    }
    @Override
    public void drawSelection(Graphics2D g2d){
        g2d.translate(posX, posY);
        g2d.setStroke(Global.strokeSelection);
        g2d.setColor(Color.BLACK);
        g2d.translate(0.5, 0);
        g2d.draw(new Rectangle2D.Float(shape.getBounds().x - 2.5f,
                shape.getBounds().y - 2.5f,
                shape.getBounds().width + 5.5f,
                shape.getBounds().height + 5.5f));
        g2d.setStroke(Global.strokeNormal);
    }
    static Color hlight=new Color(255,255,255,140);
    AnimatedVariable highlightLevel=new AnimatedVariable();
    public void setHighligted(boolean set){
        if(set)
            highlightLevel.set(1, 200, AnimatedVariable.METHOD_SINE);
        else
            highlightLevel.set(0, 500, AnimatedVariable.METHOD_COS);
    }
    public boolean isHighlighted(){
        return highlightLevel.get()!=0;
    }
    @Override
    public boolean highLight(Graphics2D g2d){
        if(flow==null)return false;
        if(Global.animations)
            highlight=highlightLevel.get();
        else
            highlight=1;
        if(highlight>0)
            drawHighlight(g2d);
        return highlight>0 || highlightLevel.isAnimating();
    }
    void drawHighlight(Graphics2D g2d){
        switch(Global.hlight){
            case NONE: break;
            case AUTO:{
                if(flow.scale()<1){
                    scale=(float)(1+((1f/flow.scale())*(highlight))/2);
                    shape();
                    draw(g2d, true);
                    scale=1f;
                }
                else{
                    g2d.translate(posX, posY);
                    if(Global.animations)
                        g2d.setColor(new Color(255,255,255,(int)(150*highlight)));
                    else
                        g2d.setColor(hlight);
                    g2d.fill(shape);
                }
                //g2d.setTransform(af);
                break;
            }
            case SCALE:{
                if(flow.movingSelected) break;
                //AffineTransform af = g2d.getTransform();
                scale=(float)(1+((1f/flow.scale())*(highlight))/2);
                if(flow.scale()<1)
                    scale*=1f/flow.scale();
                draw(g2d, true);
                scale=1f;
                //g2d.setTransform(af);
                break;
            }
            case HIGHLIGHT:{
                //AffineTransform af = g2d.getTransform();
                g2d.translate(posX, posY);
                if (Global.animations) {
                    g2d.setColor(new Color(255, 255, 255, (int) (150 * highlight)));
                } else {
                    g2d.setColor(hlight);
                }
                g2d.fill(shape);
                //g2d.setTransform(af);
                break;
            }
        }
    }
    protected static Color shadowColor=new Color(0,0,0,100);
    @Override
    public void drawShadow(Graphics2D g2d){
        AffineTransform af = g2d.getTransform();
        g2d.translate(posX, posY);
        //g2d.scale(1.05, 1.05);
        g2d.setColor(shadowColor);
        g2d.translate(2,2);
        //g2d.setStroke(Global.strokeNormal);
        g2d.fill(shape);
        g2d.setTransform(af);
    }


    public void drawConnections(Graphics2D g2d){
        for (int i = 0; i < connects.size(); i++)
            connects.get(i).draw(g2d);
    }
    public void translate(float x, float y){
        posX+=x;
        posY+=y;
        int i;
        for(i=0; i<connects.size(); i++)
            connects.get(i).needUpdate=true;
        for(i=0; i<connectsIn.size(); i++)
            connectsIn.get(i).needUpdate=true;
        for(i=0; i<groups.size(); i++){
            BlockGroup g=groups.get(i);
            if(g.blocks.contains(this))
                g.needUpdate=true;
            else{
                groups.remove(g);
                i--;
            }
        }
        translatedFloat=true;
    }
    float posXT=0, posYT=0;
    public void resetT(){
        posXT=0; posYT=0;
    }
    public void setT(){
        posX/=10;posY/=10;
        posX=Math.round(posX)*10;
        posY=Math.round(posY)*10;
        translatedFloat=false;
        resetT();
    }
    boolean translateTUpdate=false;
    boolean translatedFloat=true;
    public void translateT(float x, float y){
        if(translatedFloat)
            setT();
        posXT+=x;
        posYT+=y;
        while(posXT>=10){
            posX+=10;
            posXT-=10;
            translateTUpdate=true;
        }
        while(posYT>=10){
            posY+=10;
            posYT-=10;
            translateTUpdate=true;
        }
        while(posXT<=-10){
            posX-=10;
            posXT+=10;
            translateTUpdate=true;
        }
        while(posYT<=-10){
            posY-=10;
            posYT+=10;
            translateTUpdate=true;
        }
        if(translateTUpdate){
            translateTUpdate=false;
            int i;
            for(i=0; i<connects.size(); i++)
                connects.get(i).needUpdate=true;
            for(i=0; i<connectsIn.size(); i++)
                connectsIn.get(i).needUpdate=true;
            for(i=0; i<groups.size(); i++){
                BlockGroup g=groups.get(i);
                if(g.blocks.contains(this))
                    g.needUpdate=true;
                else{
                    groups.remove(g);
                    i--;
                }
            }
            translatedFloat=false;
        }
    }

    @Override
    public Rectangle2D bound2D(){
        Rectangle2D rect=shape.getBounds2D();
        rect.setFrame(rect.getX() + posX,
                rect.getY() + posY,
                rect.getWidth(),
                rect.getHeight());
        //rect.translate( (posX-bound.getWidth()/2+0.5),
        //        (posY-bound.getHeight()/2+0.5));
        return rect;
    }
    @Override
    public Rectangle bound(){
        Rectangle rect=shape.getBounds();
        //rect.translate( (int)(posX-bound.getWidth()/2+0.5),
        //        (int)(posY-bound.getHeight()/2+0.5));
        rect.translate((int)posX, (int)posY);
        return rect;
    }

    @Override
    public boolean contains(double x, double y){
        return shape.contains(x-posX,
                y-posY);
    }
    public boolean contains(double x, double y, double prec){
        if(shape.contains(x-posX,
                          y-posY))
            return true;
        if(shape.contains(x-posX+prec,
                          y-posY+prec/0.7))
            return true;
        if(shape.contains(x-posX+prec,
                          y-posY-prec/0.7))
            return true;
        if(shape.contains(x-posX-prec,
                          y-posY+prec/0.7))
            return true;
        return shape.contains(x-posX-prec,
                y-posY-prec/0.7);
    }
    @Override
    public boolean intersects(Shape s){
        return shape.intersects(s.getBounds2D());
    }

    public Point2D connectPoint(float angle){
        Point2D c=new Point2D.Float();
             if(angle >=-Math.PI*0.25 && angle<=Math.PI*0.25)
            c.setLocation(posX, posY-shape.getBounds2D().getHeight()/2.0 );
        else if(angle >= Math.PI * 0.75 || angle <= -Math.PI * 0.75)
            c.setLocation(posX, posY+shape.getBounds2D().getHeight()/2.0);
        else if(angle >= Math.PI * 0.25 && angle <= Math.PI * 0.75)
            c.setLocation(posX-shape.getBounds2D().getWidth()/2.0, posY);
        else if(angle <= -Math.PI * 0.25 && angle >= -Math.PI * 0.75)
            c.setLocation(posX+shape.getBounds2D().getWidth()/2.0, posY);
        //c.setLocation(c.getX()+0.5, c.getY()+0.5);
        return c;
    }

    public Point2D connectPointBezier(float angle, Point2D s, float mult){
        Point2D c=new Point2D.Float();
        float xp1=Math.abs(posX-(float)s.getX())*0.8f;
        float yp1=Math.abs(posY-(float)s.getY())*0.8f;
        //float xp=Math.min(xp1, xp1*xp1/yp1),
        //      yp=Math.min(yp1, yp1*yp1/xp1);
        float xp=Math.min(xp1, yp1)*mult, yp=xp;
             if(angle >=-Math.PI*0.25 && angle<=Math.PI*0.25)
            c.setLocation(posX, posY-shape.getBounds2D().getHeight()/2.0-yp);
        else if(angle >= Math.PI * 0.75 || angle <= -Math.PI * 0.75)
            c.setLocation(posX, posY+shape.getBounds2D().getHeight()/2.0+yp);
        else if(angle >= Math.PI * 0.25 && angle <= Math.PI * 0.75)
            c.setLocation(posX-shape.getBounds2D().getWidth()/2.0-xp, posY);
        else if(angle <= -Math.PI * 0.25 && angle >= -Math.PI * 0.75)
            c.setLocation(posX+shape.getBounds2D().getWidth()/2.0+xp, posY);
        return c;
    }

    public boolean drawArrow(){return true;}
    public boolean drawArrow(Flowline c){return true;}

    protected void parseXml(Element b, boolean connect){
         if(connect==false){
            type=getTypeFromString(b.getAttribute("type"));
            typeInString=b.getAttribute("type");
            ID=Integer.parseInt(b.getAttribute("id"));
            NodeList options=b.getElementsByTagName("options");
            for(int i=0; i<options.getLength(); i++){
                Element option=(Element)options.item(i);
                if(option.hasAttribute("displayComment"))
                    displayComment=Boolean.parseBoolean(option.getAttribute("displayComment"));
                if(option.hasAttribute("HTMLComment"))
                    commentIsHTML=Boolean.parseBoolean(option.getAttribute("HTMLComment"));
            }
            Element visual=(Element)b.getElementsByTagName("visual").item(0);
                posX=Float.parseFloat(visual.getAttribute("posX"));
                posY=Float.parseFloat(visual.getAttribute("posY"));
                String c[]=visual.getAttribute("color").split(" ");
                color=new Color(Integer.parseInt(c[0]), Integer.parseInt(c[1]), Integer.parseInt(c[2]));
                if(visual.hasAttribute("borderColor")){
                    c=visual.getAttribute("borderColor").split(" ");
                    borderColor=new Color(Integer.parseInt(c[0]), Integer.parseInt(c[1]), Integer.parseInt(c[2]));
                }
                if(visual.hasAttribute("textColor")){
                    c=visual.getAttribute("textColor").split(" ");
                    textColor=new Color(Integer.parseInt(c[0]), Integer.parseInt(c[1]), Integer.parseInt(c[2]));
                }
            NodeList lines=b.getElementsByTagName("content");
            code="";
            for(int i=0; i<lines.getLength(); i++){
                Element content=(Element)lines.item(i);
                String co=content.getTextContent();
                co=co.replaceAll(";\t", "\n");
                co=co.replaceAll(";\t\t", ";\t");
                code+=co;
                if(i!=lines.getLength()-1)
                    code+="\n";
            }
            lines=b.getElementsByTagName("comment");
            comment="";
            for(int i=0; i<lines.getLength(); i++){
                Element content=(Element)lines.item(i);
                String co=content.getTextContent();
                co=co.replaceAll(";\t", "\n");
                co=co.replaceAll(";\t\t", ";\t");
                comment+=co;
                if(i!=lines.getLength()-1)
                    comment+="\n";
            }

        }
        else{
            NodeList con=b.getElementsByTagName("connect");
            for(int i=0; i<con.getLength(); i++){
                Element to=(Element)con.item(i);
                Flowline c=connectTo(
                        flow.getBlockById(Integer.parseInt(to.getAttribute("ID")))
                        );
                if(to.hasAttribute("value"))
                    c.setValue(to.getAttribute("value"));
            }
        }
        specialXmlLoad(b, connect);
    }
    public void loadXml(Element b, boolean connect){
       parseXml(b, connect);
        //shape();
    }

    public Element makeXml(Element root){
        return makeXml(root, true);
    }
    public Element makeXml(Element root, boolean connect){
        return makeXml(root, connect, 1);
    }
    public String xmlTagName="block";

    protected void specialXmlSave(Element xml){
    }
    protected void specialXmlLoad(Element xml, boolean connect){
    }

    public Element makeXml(Element root, boolean connect, int IDs){
        Document doc=root.getOwnerDocument();
        Element block=doc.createElement(xmlTagName);
        {
            if(type!=Type.CUSTOM)
                block.setAttribute("type", type.toString());
            else
                block.setAttribute("type", getType());
            block.setAttribute("id", ID+"");
            //block.appendChild(doc.createElement("showComment"));
            Element options=doc.createElement("options");
            options.setAttribute("displayComment", displayComment+"");
            if(commentIsHTML)
                options.setAttribute("HTMLComment", commentIsHTML+"");
            block.appendChild(options);
            Element visual=doc.createElement("visual");
                visual.setAttribute("posX", Float.toString(posX));
                visual.setAttribute("posY", Float.toString(posY));
                visual.setAttribute("color",
                        color.getRed()+" "+color.getGreen()+" "+color.getBlue());
                if(!borderColor.equals(Color.BLACK))
                    visual.setAttribute("borderColor",
                        borderColor.getRed()+" "+borderColor.getGreen()+" "+borderColor.getBlue());
                if(!textColor.equals(Color.BLACK))
                    visual.setAttribute("textColor",
                        textColor.getRed()+" "+textColor.getGreen()+" "+textColor.getBlue());
                block.appendChild(visual);
            //String lines[]=code.split("\n");
            /*for(int i=0; i<lines.length; i++){
                Element text=doc.createElement("content");
                Text Content = doc.createTextNode(lines[i]);
                text.appendChild(Content);
                block.appendChild(text);
            }*/
            if(code.length()>0){
                Element text=doc.createElement("content");
                String c=code.replaceAll(";\t", ";\t\t");
                c=c.replaceAll("\n", ";\t");
                Text Content = doc.createTextNode(c);
                text.appendChild(Content);
                block.appendChild(text);
            }
            if(comment.length()>0){
                Element text=doc.createElement("comment");
                String c=comment.replaceAll(";\t", ";\t\t");
                c=c.replaceAll("\n", ";\t");
                Text Content = doc.createTextNode(c);
                text.appendChild(Content);
                block.appendChild(text);
            }
            if(connect)
                for(int i=0; i<connects.size(); i++){
                    if(Math.signum(connects.get(i).n.ID)!=IDs)
                        continue;
                    Element con=doc.createElement("connect");
                    con.setAttribute("ID", connects.get(i).n.ID+"");
                    if(connects.get(i).value.length()>0)
                        con.setAttribute("value", connects.get(i).value);
                    block.appendChild(con);
                }
        }
        specialXmlSave(block);
        return block;
    }
    public void saveXml(Element root){
        Element block=makeXml(root);
        root.appendChild(block);
    }

}
