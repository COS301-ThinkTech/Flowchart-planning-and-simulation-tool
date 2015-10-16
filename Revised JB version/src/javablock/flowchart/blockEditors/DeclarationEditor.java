/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javablock.flowchart.blockEditors;

import config.translator;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javablock.flowchart.BlockEditor;
import javablock.flowchart.JBlock;
import javablock.flowchart.blocks.DeclarationBlock;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import widgets.ComboText;

/**
 *
 * @author tshepiso
 */
public class DeclarationEditor extends javax.swing.JPanel implements BlockEditor{

    
    /**
     * Creates new form DeclarationEditor
     */
    public DeclarationEditor() {
        initComponents();
        delIcon=new javax.swing.ImageIcon(getClass().getResource("/icons/16/list-remove.png"));
        makeList();
    }
    private final ImageIcon delIcon;
    DeclarationBlock editing;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	value = new javax.swing.JTextField();
	name = new javax.swing.JTextField();
	addButton = new javax.swing.JButton();
        fieldsScroll = new javax.swing.JScrollPane();
	fieldsPane = new javax.swing.JPanel();
        fieldsScroll.setBorder(javax.swing.BorderFactory.createTitledBorder("Declarations"));
	fieldsPane.setLayout(new java.awt.GridLayout(100, 1));
        fieldsScroll.setViewportView(fieldsPane);
	    
        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/document-new.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("config/lang/lang"); // NOI18N
        addButton.setText(bundle.getString("structEditor.add")); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
	    
	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
	 layout.setHorizontalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(fieldsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
				.addContainerGap())
	);
	layout.setVerticalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(fieldsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
			.addContainerGap())
	);
	    /*
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fieldsScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
                    .addComponent(silent, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
        );*/
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane fieldsScroll;
    // End of variables declaration//GEN-END:variables


    public JBlock getBlock(){
        return editing;
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton addButton;
    private javax.swing.JCheckBox displayName;
    private javax.swing.JLabel error;
    private javax.swing.JPanel fieldsPane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField name;
    private javax.swing.JTextField value;
    private javax.swing.JCheckBox silent;
    // End of variables declaration                   

    List<Field> fields=new ArrayList();

    void makeList(){
        fieldsPane.removeAll();
        //fieldsPane.setPreferredSize(new Dimension(1,1));
        for(Field field:fields)
            fieldsPane.add(field);
        fieldsPane.add(addButton);
        //fieldsScroll.se
        revalidate();
        repaint();
    }

    void removeField(Field f){
        fields.remove(f);
        makeList();
    }

    private void error(boolean e){
        if(e)
            error.setText("error syntax");
        else
            error.setText("");
    }
    @Override
    public void saveBlock() {
        //if(name.getText().indexOf(" ")>=0){ error(true); return;}
        editing.clear();
        for(Field field:fields){
            if(field.name.getText().length()==0) continue;
            editing.addField(field.name.getText(),
                    ((ComboText)field.type.getSelectedItem()).getValue(),field.value.getText());
        }
        editing.shape();
        //editing.flow.update();
    }

    @Override
    public void setEditedBlock(JBlock b) {
 
        if(b==editing) return ;
        if(editing!=null)
            finishEdit();
        editing=(DeclarationBlock)b;
        fields.clear();
        fieldsPane.removeAll();
        String l[][]=editing.getFields();
        for(String f[]:l){
            Field field=new Field(this);
            field.name.setText(f[0]);
            field.value.setText(f[2]);
            for(ComboText c: types){
                if(c.getValue().equals(f[1])){
                    field.type.setSelectedItem(c);
                    break;
                }
            }
            fields.add(field);
        }
        editing.addFieldsToBlock();
        makeList();
    }



    @Override
    public boolean changes() {
        return false;
    }

    @Override
    public void finishEdit() {
        saveBlock();
         editing=null;
    }

    class Field extends JPanel{
        JComboBox type;
        JTextField name;
        JTextField value;
        DeclarationEditor p;
        Field t;
        Field(final DeclarationEditor p){
            this.p=p;
            type=new JComboBox();
            for(ComboText t:types){
                type.addItem(t);
            }
            name=new JTextField();
            value=new JTextField();
            JButton delete=new JButton();
            delete.setIcon(delIcon);
            this.setLayout(new BorderLayout());
            add(name, BorderLayout.CENTER);
            add(value, BorderLayout.SOUTH);
            add(type, BorderLayout.WEST);
            add(delete, BorderLayout.EAST);
            t=this;
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    p.removeField(t);
                }
            });
        }
    }
    public enum DataType{
        INTEGER/*, NUMBER*/, STRING, CHARARRAY, BOOLEAN, ANY
    }
    ComboText types[]={
        /*new ComboText(translator.get("ioEditor.typeNumber"), "NUMBER"),*/
        new ComboText(translator.get("ioEditor.typeInteger"), "INTEGER"),
        new ComboText(translator.get("ioEditor.typeString"), "STRING"),
        //new ComboText(translator.get("ioEditor.typeCharArray"), "CHARARRAY"),
        new ComboText(translator.get("ioEditor.typeBoolean"), "BOOLEAN"),
        new ComboText(translator.get("ioEditor.typeAny"), "ANY")
    };

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {  
        
        Field f=new Field(this);
        System.out.println("Added...");
        fields.add(f);
        makeList();
        
    }  

}
