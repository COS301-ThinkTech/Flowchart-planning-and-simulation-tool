package javablock.gui;

import config.Global;
import config.misc;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import javablock.FlowchartManager;
import javablock.flowchart.JBlock;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public final class MainWindow extends javax.swing.JFrame 
        implements ActionListener, ComponentListener{
    public FlowchartManager Manager;
    /** Creates new form MainWindow */
    boolean inited=false;
    public MainWindow() {
        addComponentListener(this);
        Global.Window=this;
        initComponents();
        if(Global.GUI){
            try {
                this.setIconImage(ImageIO.read(getClass().getResource("/javablock/gui/icon.png")));
            } catch (IOException ex) {}
        }
        inited=true;

        boolean isPython=false;
        for(String e:misc.getScriptEngines()){
        }
        New();
        updateConfig(Manager);
        addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(final WindowEvent e){
                    if(Manager.saveExit()<0)
                        SwingUtilities.invokeLater(new Runnable(){
                    @Override
                            public void run(){
                                e.getWindow().setVisible(true);
                            }
                        });
                    else{
                        Global.conf.saveConfig();
                        System.exit(0);
                    }
                }
            }
        );
        if(!Global.applet)
            setBounds(Global.WindowSize);
        init();
        if(Global.loadLast)
            Manager.loadLast();
        Global.setGlobalManager(Manager);
        Global.ready=true;
    }

    void init(){
        //engine.addActionListener(this);
        menuSavePython.setVisible(false);
    }

    void New(){
        if(Manager!=null) {
            if(Manager.saveClose()<0) return ;
            Manager.close();
        }
        Manager=new FlowchartManager(this);
        Manager.makeUI(false);
        Global.setGlobalManager(Manager);
        FLOW.removeAll();
        FLOW.add(Manager);
        System.gc();
        FLOW.validate();
        updateConfig(Manager);
    }
    void Open(boolean imp){
        if(Manager!=null && Manager.saveClose()<0)
                return ;
        FlowchartManager oldManager=Manager;
                Manager=new FlowchartManager(this);
        int ok=Manager.loadFile(imp);
        if(ok==1){ //OK
            if (oldManager != null) 
                oldManager.close();
            Manager.makeUI(false);
            Global.setGlobalManager(Manager);
            FLOW.removeAll();
            FLOW.add(Manager);
            System.gc();
            FLOW.validate();
            updateConfig(Manager);
        }
        if(ok==-1){ //ERROR
            Manager.close();
            Manager=oldManager;
            //Global.setGlobalManager(oldManager);
            //Global.setGlobalManager(Manager);
            JOptionPane.showMessageDialog(this, "Error while reading",
                    "Load error",
                    JOptionPane.ERROR_MESSAGE);
        }
        if(ok==0){ 
            Manager.close();
            Manager=oldManager;
           
        }
    }

    public void saveOut(String url){
    }

    public void undoAvaiable(boolean is){
        toolUndo.setEnabled(is);
        menuUndo.setEnabled(is);
    }
    public void redoAvaiable(boolean is){
        toolRedo.setEnabled(is);
        menuRedo.setEnabled(is);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FLOW = new javax.swing.JPanel();
        toolBar = new javax.swing.JToolBar();
        toolNew = new javax.swing.JButton();
        toolOpen = new javax.swing.JButton();
        toolSave = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        toolUndo = new javax.swing.JButton();
        toolRedo = new javax.swing.JButton();
        scriptTools = new javax.swing.JToolBar();
        scriptStop = new javax.swing.JButton();
        scriptStart = new javax.swing.JButton();
        scriptStep = new javax.swing.JButton();
        scriptRun = new javax.swing.JButton();
        scriptInterval = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuNew = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuOpen = new javax.swing.JMenuItem();
        menuSave = new javax.swing.JMenuItem();
        menuSaveAs = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuImportXml = new javax.swing.JMenuItem();
        menuExportXml = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        menuExportImage = new javax.swing.JMenuItem();
        menuSavePython = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuUndo = new javax.swing.JMenuItem();
        menuRedo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuCut = new javax.swing.JMenuItem();
        menuCopy = new javax.swing.JMenuItem();
        menuPaste = new javax.swing.JMenuItem();
        sep = new javax.swing.JPopupMenu.Separator();
        menuDelete = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenu6 = new javax.swing.JMenu();
        menuFlowchartsAdd = new javax.swing.JMenuItem();
        menuFlowchartsRemove = new javax.swing.JMenuItem();
        menuFlowchartsRename = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        menuScript = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        menuGridBool = new javax.swing.JCheckBoxMenuItem();
        menuAABool = new javax.swing.JCheckBoxMenuItem();
        menuFullConnectorsValues = new javax.swing.JCheckBoxMenuItem();
        menuPascalMode = new javax.swing.JCheckBoxMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuSettings = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuHelp = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JavaBlock");
        setMinimumSize(new java.awt.Dimension(900, 550));

        FLOW.setLayout(new java.awt.BorderLayout());

        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        toolNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24/document-new.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("config/lang/lang"); // NOI18N
        toolNew.setToolTipText(bundle.getString("main.new")); // NOI18N
        toolNew.setActionCommand("manage/new");
        toolNew.setFocusable(false);
        toolNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolNewActionPerformed(evt);
            }
        });
        toolBar.add(toolNew);

        toolOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24/document-open.png"))); // NOI18N
        toolOpen.setToolTipText(bundle.getString("main.open")); // NOI18N
        toolOpen.setActionCommand("manage/open/false");
        toolOpen.setFocusable(false);
        toolOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolOpenActionPerformed(evt);
            }
        });
        toolBar.add(toolOpen);
        toolOpen.addActionListener(this);

        toolSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24/document-save.png"))); // NOI18N
        toolSave.setToolTipText(bundle.getString("main.save")); // NOI18N
        toolSave.setActionCommand("manage/save/false");
        toolSave.setFocusable(false);
        toolSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(toolSave);
        toolSave.addActionListener(this);
        toolBar.add(jSeparator5);

        toolUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24/edit-undo.png"))); // NOI18N
        toolUndo.setToolTipText(bundle.getString("main.undo")); // NOI18N
        toolUndo.setActionCommand("history/undo");
        toolUndo.setEnabled(false);
        toolUndo.setFocusable(false);
        toolUndo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolUndo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(toolUndo);
        toolUndo.addActionListener(this);

        toolRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/24/edit-redo.png"))); // NOI18N
        toolRedo.setToolTipText(bundle.getString("main.redo")); // NOI18N
        toolRedo.setActionCommand("history/redo");
        toolRedo.setEnabled(false);
        toolRedo.setFocusable(false);
        toolRedo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        toolRedo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(toolRedo);
        toolRedo.addActionListener(this);

        scriptTools.setRollover(true);
        scriptTools.setMaximumSize(new java.awt.Dimension(350, 33));
        scriptTools.setMinimumSize(new java.awt.Dimension(350, 33));

        scriptStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/media-playback-stop.png"))); // NOI18N
        scriptStop.setFocusable(false);
        scriptStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        scriptStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        scriptStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scriptStopActionPerformed(evt);
            }
        });
        scriptTools.add(scriptStop);

        scriptStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/media-playback-start.png"))); // NOI18N
        scriptStart.setFocusable(false);
        scriptStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        scriptStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        scriptStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scriptStartActionPerformed(evt);
            }
        });
        scriptTools.add(scriptStart);

        scriptStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/step.png"))); // NOI18N
        scriptStep.setFocusable(false);
        scriptStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        scriptStep.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        scriptStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scriptStepActionPerformed(evt);
            }
        });
        scriptTools.add(scriptStep);

        scriptRun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/system-run.png"))); // NOI18N
        scriptRun.setFocusable(false);
        scriptRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        scriptRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        scriptRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scriptRunActionPerformed(evt);
            }
        });
        scriptTools.add(scriptRun);

        scriptInterval.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5000, 50));
        scriptInterval.setMaximumSize(new java.awt.Dimension(63, 20));
        scriptTools.add(scriptInterval);

        jLabel2.setText("run speed");
        scriptTools.add(jLabel2);

        toolBar.add(scriptTools);

        jMenu1.setText(bundle.getString("main.file")); // NOI18N

        menuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/document-new.png"))); // NOI18N
        menuNew.setText(bundle.getString("main.new")); // NOI18N
        menuNew.setActionCommand("manage/new");
        menuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewActionPerformed(evt);
            }
        });
        jMenu1.add(menuNew);
        jMenu1.add(jSeparator1);

        menuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/document-open.png"))); // NOI18N
        menuOpen.setText(bundle.getString("main.open")); // NOI18N
        menuOpen.setActionCommand("manage/open/false");
        menuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenActionPerformed(evt);
            }
        });
        jMenu1.add(menuOpen);
        menuOpen.addActionListener(this);

        menuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/document-save.png"))); // NOI18N
        menuSave.setText(bundle.getString("main.save")); // NOI18N
        menuSave.setActionCommand("manage/save/false");
        jMenu1.add(menuSave);
        menuSave.addActionListener(this);

        menuSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/document-save-as.png"))); // NOI18N
        menuSaveAs.setText(bundle.getString("main.saveAs")); // NOI18N
        menuSaveAs.setActionCommand("manage/saveas");
        jMenu1.add(menuSaveAs);
        menuSaveAs.addActionListener(this);
        jMenu1.add(jSeparator4);

        menuImportXml.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/mail-inbox.png"))); // NOI18N
        menuImportXml.setText(bundle.getString("main.importXml")); // NOI18N
        menuImportXml.setActionCommand("manage/open/true");
        menuImportXml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuImportXmlActionPerformed(evt);
            }
        });
        jMenu1.add(menuImportXml);
        menuImportXml.addActionListener(this);

        menuExportXml.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/mail-outbox.png"))); // NOI18N
        menuExportXml.setText(bundle.getString("main.exportXml")); // NOI18N
        menuExportXml.setActionCommand("manage/save/true");
        jMenu1.add(menuExportXml);
        menuExportXml.addActionListener(this);
        jMenu1.add(jSeparator8);
        jMenu1.add(jSeparator7);

        menuExportImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/image-png.png"))); // NOI18N
        menuExportImage.setText(bundle.getString("main.importImage")); // NOI18N
        menuExportImage.setActionCommand("manage/saveImage");
        jMenu1.add(menuExportImage);
        menuExportImage.addActionListener(this);

        menuSavePython.setText(bundle.getString("main.savePythonScript")); // NOI18N
        menuSavePython.setActionCommand("manage/savePython");
        jMenu1.add(menuSavePython);
        menuSavePython.addActionListener(this);

        jMenu1.add(jSeparator2);

        menuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/window-close.png"))); // NOI18N
        menuExit.setText(bundle.getString("main.exit")); // NOI18N
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        menu.add(jMenu1);

        jMenu2.setText(bundle.getString("main.edit")); // NOI18N

        menuUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        menuUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/edit-undo.png"))); // NOI18N
        menuUndo.setText(bundle.getString("main.undo")); // NOI18N
        menuUndo.setActionCommand("history/undo");
        jMenu2.add(menuUndo);
        menuUndo.addActionListener(this);

        menuRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        menuRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/edit-redo.png"))); // NOI18N
        menuRedo.setText(bundle.getString("main.redo")); // NOI18N
        menuRedo.setActionCommand("history/redo");
        jMenu2.add(menuRedo);
        menuRedo.addActionListener(this);
        jMenu2.add(jSeparator3);

        menuCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        menuCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/edit-cut.png"))); // NOI18N
        menuCut.setText(bundle.getString("main.cut")); // NOI18N
        menuCut.setActionCommand("clp/cut");
        jMenu2.add(menuCut);
        menuCut.addActionListener(this);

        menuCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/edit-copy.png"))); // NOI18N
        menuCopy.setText(bundle.getString("main.copy")); // NOI18N
        menuCopy.setActionCommand("clp/copy");
        jMenu2.add(menuCopy);
        menuCopy.addActionListener(this);

        menuPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        menuPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/edit-paste.png"))); // NOI18N
        menuPaste.setText(bundle.getString("main.paste")); // NOI18N
        menuPaste.setActionCommand("clp/paste");
        jMenu2.add(menuPaste);
        menuPaste.addActionListener(this);
        jMenu2.add(sep);

        menuDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        menuDelete.setText(bundle.getString("main.delete")); // NOI18N
        menuDelete.setActionCommand("delete");
        jMenu2.add(menuDelete);
        menuDelete.addActionListener(this);

        jMenu2.add(jSeparator12);

        menu.add(jMenu2);

        jMenu6.setText(bundle.getString("main.flowcharts")); // NOI18N

        menuFlowchartsAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/folder-new.png"))); // NOI18N
        menuFlowchartsAdd.setText(bundle.getString("main.flowcharts.add")); // NOI18N
        menuFlowchartsAdd.setActionCommand("flowcharts/add");
        menuFlowchartsAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFlowchartsAddActionPerformed(evt);
            }
        });
        jMenu6.add(menuFlowchartsAdd);
        menuFlowchartsAdd.addActionListener(this);

        menuFlowchartsRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/list-remove.png"))); // NOI18N
        menuFlowchartsRemove.setText(bundle.getString("main.flowcharts.remove")); // NOI18N
        menuFlowchartsRemove.setActionCommand("flowcharts/remove");
        menuFlowchartsRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFlowchartsRemoveActionPerformed(evt);
            }
        });
        jMenu6.add(menuFlowchartsRemove);
        menuFlowchartsRemove.addActionListener(this);

        menuFlowchartsRename.setText(bundle.getString("main.flowcharts.rename")); // NOI18N
        menuFlowchartsRename.setActionCommand("flowcharts/rename");
        jMenu6.add(menuFlowchartsRename);
        menuFlowchartsRename.addActionListener(this);

        jMenu6.add(jSeparator11);

        menuScript.setText(bundle.getString("main.flowcharts.showScripts")); // NOI18N
        menuScript.setActionCommand("flowcharts/script");
        jMenu6.add(menuScript);
        menuScript.addActionListener(this);

        menu.add(jMenu6);

        jMenu7.setText("Components");

        jMenuItem4.setIcon(new ImageIcon(JBlock.getIcon(JBlock.Type.IO)));
        jMenuItem4.setText("I/O");
        jMenuItem4.setToolTipText("I/O");
        jMenuItem4.setActionCommand("add/IO");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem4);
        jMenuItem4.addActionListener(this);

        jMenuItem5.setIcon(new ImageIcon(JBlock.getIcon(JBlock.Type.CPU)));
        jMenuItem5.setText("CPU");
        jMenuItem5.setToolTipText("CPU");
        jMenuItem5.setActionCommand("add/CPU");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem5);
        jMenuItem5.addActionListener(this);

        jMenuItem6.setIcon(new ImageIcon(JBlock.getIcon(JBlock.Type.DECISION)));
        jMenuItem6.setText("Decision");
        jMenuItem6.setToolTipText("Decision");
        jMenuItem6.setActionCommand("add/DECISION");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem6);
        jMenuItem6.addActionListener(this);

        jMenuItem7.setIcon(new ImageIcon(JBlock.getIcon(JBlock.Type.RETURN)));
        jMenuItem7.setText("End");
        jMenuItem7.setToolTipText("End");
        jMenuItem7.setActionCommand("add/RETURN");
        jMenu7.add(jMenuItem7);
        jMenuItem7.addActionListener(this);

        jMenuItem8.setIcon(new ImageIcon(JBlock.getIcon(JBlock.Type.LINK)));
        jMenuItem8.setText("Link");
        jMenuItem8.setToolTipText("Link");
        jMenuItem8.setActionCommand("add/LINK");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem8);
        jMenuItem8.addActionListener(this);

        jMenuItem9.setIcon(new ImageIcon(JBlock.getIcon(JBlock.Type.JUMP)));
        jMenuItem9.setText("Jump");
        jMenuItem9.setToolTipText("Jump");
        jMenuItem9.setActionCommand("add/JUMP");
        jMenu7.add(jMenuItem9);
        jMenuItem9.addActionListener(this);

        jMenuItem10.setIcon(new ImageIcon(JBlock.getIcon(JBlock.Type.FORLOOP)));
        jMenuItem10.setText("For Loop");
        jMenuItem10.setToolTipText("For Loop");
        jMenuItem10.setActionCommand("foraction/FORLOOP");
        jMenu7.add(jMenuItem10);
        jMenuItem10.addActionListener(this);

        menu.add(jMenu7);

        jMenu3.setText(bundle.getString("main.settings")); // NOI18N

        jMenu5.setText(bundle.getString("main.draw")); // NOI18N

        menuGridBool.setText(bundle.getString("main.drawGrid")); // NOI18N
        menuGridBool.setActionCommand("view/grid");
        menuGridBool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGridBoolActionPerformed1(evt);
            }
        });
        jMenu5.add(menuGridBool);

        menuAABool.setText(bundle.getString("main.antiAliasing")); // NOI18N
        menuAABool.setActionCommand("view/aa");
        menuAABool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAABoolActionPerformed(evt);
            }
        });
        jMenu5.add(menuAABool);

        menuFullConnectorsValues.setSelected(true);
        menuFullConnectorsValues.setText(bundle.getString("main.drawFullConnectorsValue")); // NOI18N
        menuFullConnectorsValues.setActionCommand("view/fullValues");
        menuFullConnectorsValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFullConnectorsValuesActionPerformed(evt);
            }
        });
        jMenu5.add(menuFullConnectorsValues);

        jMenu3.add(jMenu5);

        menuPascalMode.setText(bundle.getString("main.pascalMode")); // NOI18N
        menuPascalMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPascalModeActionPerformed(evt);
            }
        });
        jMenu3.add(menuPascalMode);

        jMenuItem1.setText(bundle.getString("main.fullscreen")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        menuSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/gconf-editor.png"))); // NOI18N
        menuSettings.setText(bundle.getString("main.settings")); // NOI18N
        menuSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSettingsActionPerformed(evt);
            }
        });
        jMenu3.add(menuSettings);

        jMenuItem11.setText("Console");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        menu.add(jMenu3);

        jMenu4.setText(bundle.getString("main.help")); // NOI18N

        menuHelp.setText(bundle.getString("main.help")); // NOI18N
        menuHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpActionPerformed(evt);
            }
        });
        jMenu4.add(menuHelp);
        menuHelp.addActionListener(Manager);

        jMenuItem12.setText(bundle.getString("main.about")); // NOI18N
        jMenuItem12.setEnabled(false);
        jMenu4.add(jMenuItem12);

        menu.add(jMenu4);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE)
            .addComponent(FLOW, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FLOW, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void scriptStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scriptStopActionPerformed
        Manager.flow.I.reset();
    }//GEN-LAST:event_scriptStopActionPerformed

    private void scriptStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scriptStartActionPerformed
        Manager.flow.I.start();
    }//GEN-LAST:event_scriptStartActionPerformed

    private void scriptStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scriptStepActionPerformed
        Manager.flow.I.step();
    }//GEN-LAST:event_scriptStepActionPerformed

    private void scriptRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scriptRunActionPerformed
        Manager.flow.I.run();
    }//GEN-LAST:event_scriptRunActionPerformed

    private void menuFlowchartsAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFlowchartsAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuFlowchartsAddActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void menuFlowchartsRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFlowchartsRemoveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuFlowchartsRemoveActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
         if(Manager.flow.split.getBottomComponent().getBounds().height<50){
            Manager.flow.split.setDividerLocation(
                    Manager.flow.getSize().height-220);
        }
        else
            Manager.flow.split.setDividerLocation(2000);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void menuSaveAsActionPerformed(java.awt.event.ActionEvent evt) {                                           
        Manager.saveFileAs();
    }                                          

    private void menuGridBoolActionPerformed(java.awt.event.ActionEvent evt) {                                            
        Global.grid=menuGridBool.getState();
        Manager.flow.update();
    }                                        

    private void menuAABoolActionPerformed(java.awt.event.ActionEvent evt) {                                           
        Global.antialiasing=menuAABool.getState();
        Manager.flow.update();
    }                                                                            

    private void menuPascalModeActionPerformed(java.awt.event.ActionEvent evt) {                                               
        Global.pascalMode=menuPascalMode.getState();
    }                                              

    private void menuGridBoolActionPerformed1(java.awt.event.ActionEvent evt) {                                              
        Global.grid=menuGridBool.getState();
        Manager.flow.update();
    }                                             

    private void menuFullConnectorsValuesActionPerformed(java.awt.event.ActionEvent evt) {                                                         
        Global.fullConnectorValue=menuFullConnectorsValues.getState();
        Manager.flow.update();
    }                                                        

    private void menuSettingsActionPerformed(java.awt.event.ActionEvent evt) {                                             
        Global.conf.show();
    }                                           
                                  

    private void menuHelpActionPerformed(java.awt.event.ActionEvent evt) {                                         
        widgets.help.showHelp();
    }                                

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Manager.saveExit();
        Global.conf.saveConfig();
        System.exit(0);
    }                                        
    
                                         

                                        


    //*******************************NEW**************************************//
    private void menuNewActionPerformed(java.awt.event.ActionEvent evt) {                                        
        New();
    }                                       

    private void toolNewActionPerformed(java.awt.event.ActionEvent evt) {                                        
        New();
    }                                       

    
    //**********************************OPEN**********************************//
    private void toolOpenActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Open(false);
    }                                        

    private void menuOpenActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Open(false);
    }                                        

    private void menuImportXmlActionPerformed(java.awt.event.ActionEvent evt) {                                              
        Open(true);
    }                                             
boolean fullscreen=false;
Rectangle windowSize;
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        fullscreen^=true;
        if(fullscreen){
            windowSize=this.getBounds();
            this.setVisible(false);
            this.dispose();
            this.setUndecorated(true);
            Toolkit tk = Toolkit.getDefaultToolkit();
            this.setBounds(0,0,tk.getScreenSize().width, tk.getScreenSize().height);
            this.setVisible(true);
        }
        else{
            this.setVisible(false);
            this.dispose();
            this.setUndecorated(false);
            setBounds(windowSize);
            this.setVisible(true);
        }
    }                                          



                                            

                                          

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FLOW;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    public javax.swing.JMenuBar menu;
    private javax.swing.JCheckBoxMenuItem menuAABool;
    private javax.swing.JMenuItem menuCopy;
    private javax.swing.JMenuItem menuCut;
    private javax.swing.JMenuItem menuDelete;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenuItem menuExportImage;
    private javax.swing.JMenuItem menuExportXml;
    private javax.swing.JMenuItem menuFlowchartsAdd;
    private javax.swing.JMenuItem menuFlowchartsRemove;
    private javax.swing.JMenuItem menuFlowchartsRename;
    private javax.swing.JCheckBoxMenuItem menuFullConnectorsValues;
    private javax.swing.JCheckBoxMenuItem menuGridBool;
    private javax.swing.JMenuItem menuHelp;
    private javax.swing.JMenuItem menuImportXml;
    private javax.swing.JMenuItem menuNew;
    private javax.swing.JMenuItem menuOpen;
    private javax.swing.JCheckBoxMenuItem menuPascalMode;
    private javax.swing.JMenuItem menuPaste;
    private javax.swing.JMenuItem menuRedo;
    private javax.swing.JMenuItem menuSave;
    private javax.swing.JMenuItem menuSaveAs;
    private javax.swing.JMenuItem menuSavePython;
    private javax.swing.JMenuItem menuScript;
    private javax.swing.JMenuItem menuSettings;
    private javax.swing.JMenuItem menuUndo;
    public javax.swing.JSpinner scriptInterval;
    public javax.swing.JButton scriptRun;
    public javax.swing.JButton scriptStart;
    public javax.swing.JButton scriptStep;
    public javax.swing.JButton scriptStop;
    private javax.swing.JToolBar scriptTools;
    private javax.swing.JPopupMenu.Separator sep;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JButton toolNew;
    private javax.swing.JButton toolOpen;
    private javax.swing.JButton toolRedo;
    private javax.swing.JButton toolSave;
    private javax.swing.JButton toolUndo;
    // End of variables declaration//GEN-END:variables
    
    public void updateConfig(FlowchartManager men) {
        if(!inited) return ;
            if (!this.inited) return;
        if (this.Manager != null) {
          this.scriptInterval.setValue(Integer.valueOf(this.Manager.flow.interval));
        }
        this.menuAABool.setState(Global.antialiasing);
        this.menuGridBool.setState(Global.grid);
        this.menuPascalMode.setState(Global.pascalMode);
        this.menuFullConnectorsValues.setState(Global.fullConnectorValue);
        //this.engine.setSelectedItem(men.scriptEngine);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().contains("history"))
            if(this.getFocusOwner() instanceof javax.swing.text.JTextComponent)
                return ;
        Manager.actionPerformed(e);
        //if(e.getSource()==engine){
            Manager.scriptEngine="JavaScript";
        //}
    }
    
    public void updateConfig() {
        updateConfig(Manager);
    }

    @Override
    public void componentResized(ComponentEvent ce) {
    }

    @Override
    public void componentMoved(ComponentEvent ce) {
    }

    @Override
    public void componentShown(ComponentEvent ce) {
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
        Global.getManager().close();
    }

}