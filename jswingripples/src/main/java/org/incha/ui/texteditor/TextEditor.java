package org.incha.ui.texteditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.incha.core.texteditor.FileOpen;
import org.incha.ui.JSwingRipplesApplication;

public class TextEditor extends JFrame {

    private ArrayList<FileOpen> openFiles;
    private static TextEditor instance;
    private int tab;
    private JTabbedPane jTabbedPane;

    private JMenu fileMenu = new JMenu( "File" );
    private JMenuItem fileSave = new JMenuItem( "Save" );
    private JMenu syntax = new JMenu( "Syntax" );

    private Map<String,String> extensionMap = new HashMap<String,String>();

    /**
     * Set up the menus in the TextEditor.
     */
    private void setUpJMenuBar(){
        //put the syntax in the extensionMap.
        extensionMap.put( "C", SyntaxConstants.SYNTAX_STYLE_C );
        extensionMap.put( "C++", SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS );
        extensionMap.put( "C#", SyntaxConstants.SYNTAX_STYLE_CSHARP );
        extensionMap.put( "HTML", SyntaxConstants.SYNTAX_STYLE_HTML );
        extensionMap.put( "JAVA", SyntaxConstants.SYNTAX_STYLE_JAVA );
        extensionMap.put( "JAVASCRIPT", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT );
        extensionMap.put( "PHP", SyntaxConstants.SYNTAX_STYLE_PHP );
        extensionMap.put( "PYTHON", SyntaxConstants.SYNTAX_STYLE_PYTHON );
        extensionMap.put( "RUBY", SyntaxConstants.SYNTAX_STYLE_RUBY );
        extensionMap.put( "SQL", SyntaxConstants.SYNTAX_STYLE_SQL );
        extensionMap.put( "XML", SyntaxConstants.SYNTAX_STYLE_XML );
        jTabbedPane = new JTabbedPane();
        jTabbedPane.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2){
                    openFiles.get(jTabbedPane.indexAtLocation(e.getX(),e.getY())).close(instance,jTabbedPane.indexAtLocation(e.getX(),e.getY()));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        openFiles = new ArrayList<FileOpen>();
        //build Menu.
        JMenuBar menubar = new JMenuBar();
        fileMenu.add( fileSave );
        menubar.add( fileMenu );
        menubar.add( syntax );
        add( menubar, BorderLayout.NORTH );
        fileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFiles.get( jTabbedPane.getSelectedIndex() ).save();
                JOptionPane.showMessageDialog( instance, "Saved" );
            }
        });
        //add listener to the syntax menu.
        for (final String string : extensionMap.keySet()){
            JMenuItem extension = new JMenuItem( string );
            syntax.add(extension);
            extension.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openFiles.get(jTabbedPane.getSelectedIndex()).setSyntax(extensionMap.get(string));
                }
            });
        }
    }

    /**
     * Close a Tab in the JTabbedPane.
     * @param tab the index of the Tab that be close.
     */
    public void closeTab(int tab){
        jTabbedPane.remove(tab);
    }

    /**
     * Close the Current tab in the View.
     */
    public void closeSelectedTab(){
        jTabbedPane.remove(jTabbedPane.getSelectedIndex());
    }

    public TextEditor (JSwingRipplesApplication jSwingRipplesApplication){
        super( "Text Editor" );
        instance=this;

        setUpJMenuBar();
        getContentPane().add(jTabbedPane);

        // Show the window
        setSize( 1024, 768 );
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( d.width/2 - 512, d.height/2 - 384 );
        setVisible( true );

        //close option to don't close the program.
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        //save files when close the program.
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(instance,"Would you like to save the changes?","Confirm",JOptionPane.YES_NO_OPTION);
                if(confirm ==0){
                    for (int i=0;i<jTabbedPane.getTabCount();i++){
                        try{
                            openFiles.get((i)).save();
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                    }
                    JOptionPane.showMessageDialog(instance,"Saved");
                }
                super.windowClosing(e);
                instance=null;
            }
        });
    }

    /**
     * open a File in the Text Editor and add a new Tab to the window with
     * the file.
     * @param filename String with the path of the File.
     */
    public void openFile ( String filename ){
        FileOpen file = new FileOpen(filename);
        if(file.open()){
            openFiles.add(file);
            JScrollPane jScrollPane = new JScrollPane(file.getText());
            jTabbedPane.addTab(file.getFileName(), jScrollPane);
        }

    }

    /**
     * Instance the TextEditor.
     * @return the only instance to the TextEditor.
     */
    public static TextEditor getInstance(){
        if(instance==null){
            instance=new TextEditor(JSwingRipplesApplication.getInstance());
        }
        return instance;
    }


}
