package org.incha.ui.stats;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class TextEditor extends JFrame implements ActionListener
{
    private RSyntaxTextArea text = new RSyntaxTextArea();

    private JMenu fileMenu = new JMenu( "File" );
    private JMenuItem fileOpen = new JMenuItem( "Save" );
    private JMenu syntax = new JMenu( "Syntax" );

    private Map<String,String> extensionMap = new HashMap<String,String>();

    public TextEditor (  )
    {
        super( "Syntax Text Editor" );

        // Build the extension Map
        extensionMap.put( "java", SyntaxConstants.SYNTAX_STYLE_JAVA );
        extensionMap.put( "xml", SyntaxConstants.SYNTAX_STYLE_XML );
        extensionMap.put( "js", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT );
        extensionMap.put( "html", SyntaxConstants.SYNTAX_STYLE_HTML );
        extensionMap.put( "c", SyntaxConstants.SYNTAX_STYLE_C );
        extensionMap.put( "cpp", SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS );
        extensionMap.put( "groovy", SyntaxConstants.SYNTAX_STYLE_GROOVY );
        extensionMap.put( "jsp", SyntaxConstants.SYNTAX_STYLE_JSP );
        extensionMap.put( "properties", SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE );
        extensionMap.put( "sql", SyntaxConstants.SYNTAX_STYLE_SQL );
        extensionMap.put( "sh", SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL );
        extensionMap.put( "bat", SyntaxConstants.SYNTAX_STYLE_WINDOWS_BATCH );

        // Build a simple menu
        JMenuBar menubar = new JMenuBar();
        fileMenu.add( fileOpen );
        fileMenu.add( syntax );
        menubar.add( fileMenu );
        add( menubar, BorderLayout.NORTH );

        // Add ourself as an action listener (don't do this in a production app,
        // instead use Actions
        fileOpen.addActionListener( this );
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

        // Add our text control to the center of the Frame's border layout
        add( new RTextScrollPane( text ) );

        // Show our window
        setSize( 1024, 768 );
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation( d.width/2 - 512, d.height/2 - 384 );
        setVisible( true );

        // Close our frame when the user closes it
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    }

    public void openFile( String filename )
    {
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader( filename ) );
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while( line != null )
            {
                sb.append( line + "\n" );
                line = reader.readLine();
            }

            // Put the text into the RSyntaxTextArea
            text.setText( sb.toString() );

            // See if we can set its highlighting
            if( filename.indexOf( '.' ) != -1 )
            {
                String extension = filename.substring( filename.lastIndexOf( '.' ) + 1 );
                if( extensionMap.containsKey( extension.toLowerCase() ) )
                {
                    text.setSyntaxEditingStyle( extensionMap.get( extension.toLowerCase() ) );
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if( e.getSource() == fileOpen )
        {
            JFileChooser chooser = new JFileChooser();
            if( chooser.showOpenDialog( this )== JFileChooser.APPROVE_OPTION )
            {
                // The user chose OK, so get the filename and the absolute filename
                openFile( chooser.getSelectedFile().getAbsolutePath() );
            }
        }
        else if(e.getSource() == syntax){
            text.setSyntaxEditingStyle(extensionMap.get("c"));
        }
    }

}
