
package javablock.flowchart.blockEditors;

import config.translator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javablock.flowchart.blocks.StartBlock;
import javablock.flowchart.*;
import javax.swing.*;
import widgets.ComboText;

public final class StartEditor extends javax.swing.JPanel implements BlockEditor {
    private final ImageIcon delIcon;
    public StartEditor() {
        initComponents();
        delIcon=new javax.swing.ImageIcon(getClass().getResource("/icons/16/list-remove.png"));
        makeList();
        displayName.setVisible(false);
        name.setVisible(false);
        silent.setVisible(false);
    }
    StartBlock editing;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        name = new javax.swing.JTextField();
        silent = new javax.swing.JCheckBox();
        error = new javax.swing.JLabel();
        displayName = new javax.swing.JCheckBox();
        fieldsScroll = new javax.swing.JScrollPane();
        fieldsPane = new javax.swing.JPanel();

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/document-new.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("config/lang/lang"); // NOI18N
        addButton.setText(bundle.getString("structEditor.add")); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        name.setText("start");

        silent.setText(bundle.getString("startEditor.silentMode")); // NOI18N
        silent.setToolTipText("<html>When called as function, <br>\nthe window will not be showed");
        silent.setEnabled(false);

        displayName.setSelected(true);
        displayName.setText(bundle.getString("startEditor.displayName")); // NOI18N
        displayName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayNameActionPerformed(evt);
            }
        });

        fieldsScroll.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("startEditor.arguments"))); // NOI18N

        fieldsPane.setLayout(new java.awt.GridLayout(100, 1));
        fieldsScroll.setViewportView(fieldsPane);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fieldsScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(error)
                            .addComponent(displayName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
                    .addComponent(silent, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(error)
                    .addComponent(displayName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(silent))
        );

        jTabbedPane1.addTab("Properties", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        Field f=new Field(this);
        fields.add(f);
        makeList();
    }//GEN-LAST:event_addButtonActionPerformed

    private void displayNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_displayNameActionPerformed

    public JBlock getBlock(){
        return editing;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JCheckBox displayName;
    private javax.swing.JLabel error;
    private javax.swing.JPanel fieldsPane;
    private javax.swing.JScrollPane fieldsScroll;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField name;
    private javax.swing.JCheckBox silent;
    // End of variables declaration//GEN-END:variables

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
        editing.name=name.getText();
        editing.silent=silent.isSelected();
        editing.displayName=displayName.isSelected();
        for(Field field:fields){
            if(field.name.getText().length()==0) continue;
            editing.addField(field.name.getText(),
                    ((ComboText)field.type.getSelectedItem()).getValue());
        }
        editing.shape();
        editing.flow.update();
        System.out.println("Updating now...");
    }

    @Override
    public void setEditedBlock(JBlock b) {
        if(b==editing) return ;
        if(editing!=null)
            finishEdit();
        editing=(StartBlock)b;
        fields.clear();
        fieldsPane.removeAll();
        name.setText(editing.name);
        silent.setSelected(editing.silent);
        displayName.setSelected(editing.displayName);
        String l[][]=editing.getFields();
        for(String f[]:l){
            Field field=new Field(this);
            field.name.setText(f[0]);
            for(ComboText c: types){
                if(c.getValue().equals(f[1])){
                    field.type.setSelectedItem(c);
                    break;
                }
            }
            fields.add(field);
        }
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
        StartEditor p;
        Field t;
        Field(final StartEditor p){
            this.p=p;
            type=new JComboBox();
            for(ComboText t:types){
                type.addItem(t);
            }
            name=new JTextField();
            JButton delete=new JButton();
            delete.setIcon(delIcon);
            this.setLayout(new BorderLayout());
            add(name, BorderLayout.CENTER);
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
        /*INTEGER*/ NUMBER, STRING, CHARARRAY, BOOLEAN/*, ANY*/
    }
    ComboText types[]={
        new ComboText(translator.get("ioEditor.typeNumber"), "NUMBER"),
        //new ComboText(translator.get("ioEditor.typeInteger"), "INTEGER"),
        new ComboText(translator.get("ioEditor.typeString"), "STRING"),
        //new ComboText(translator.get("ioEditor.typeCharArray"), "CHARARRAY"),
        new ComboText(translator.get("ioEditor.typeBoolean"), "BOOLEAN"),
        //new ComboText(translator.get("ioEditor.typeAny"), "ANY")
    };
}
