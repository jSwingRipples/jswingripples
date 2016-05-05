package org.incha.ui.texteditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.incha.core.texteditor.FileOpen;
import org.incha.ui.JSwingRipplesApplication;

public class TextEditor extends JFrame {
    private RSyntaxTextArea text;
    private ArrayList<FileOpen> openFiles;
    private static TextEditor instance;
    private int tab;

    private JMenu fileMenu = new JMenu( "File" );
    private JMenuItem fileSave = new JMenuItem( "Save" );
    private JMenu syntax = new JMenu( "Syntax" );

    private Map<String,String> extensionMap = new HashMap<String,String>();

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

        //build Menu.
        JMenuBar menubar = new JMenuBar();
        fileMenu.add( fileSave );
        fileMenu.add( syntax );
        menubar.add( fileMenu );
        add( menubar, BorderLayout.NORTH );
        fileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFiles.get(tab).save(text.getText());
            }
        });
        //add listener to the syntax menu.
        for (final String s : extensionMap.keySet()){
            JMenuItem extension = new JMenuItem(s);
            syntax.add(extension);
            extension.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    text.setSyntaxEditingStyle(extensionMap.get(s));
                }
            });
        }
    }
    public TextEditor (JSwingRipplesApplication jSwingRipplesApplication)
    {
        super( "Text Editor" );
        instance=this;
        setUpJMenuBar();

        add( new RTextScrollPane( text ) );

        // Show the window
        setSize( 1024, 768 );
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( d.width/2 - 512, d.height/2 - 384 );
        setVisible( true );

        //close option to don't close the program.
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    }

    public void openFile ( String filename )
    {
        FileOpen file = new FileOpen(filename);

    }

    public TextEditor newInstance(){
        if(instance==null){
            instance=new TextEditor(JSwingRipplesApplication.getInstance());
        }
        return instance;
    }

}
