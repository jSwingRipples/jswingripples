package org.incha.core.texteditor;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.incha.ui.texteditor.TextEditor;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;

/**
 * Created by rodrigo on 05-05-2016.
 */
public class FileOpen {
    private RSyntaxTextArea text;
    private String path;
    private String extension;
    private String content;
    private static HashMap< String , String > extensionMap = new HashMap<>();

    /**
     * Constructor to the FileOpen.
     * @param absolutePath Path of the file.
     */
    public FileOpen( String absolutePath ) {
        path = absolutePath;
        text = new RSyntaxTextArea();
    }

    /**
     * Read the file and copy the content in the RSyntaxTextArea text.
     * @return true if the file was read and false if not.
     */
    public boolean open() {
        try {
            BufferedReader reader = new BufferedReader( new FileReader( path ) );
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while ( line != null ) {
                sb.append( line + "\n" );
                line = reader.readLine();
            }

            // See if we can set its highlighting
            if ( path.indexOf('.') != -1 ) {
                extension = path.substring( path.lastIndexOf('.') + 1 );
                try {
                    setSyntax( getSyntax( extension ) );
                }
                catch( Exception e ) {
                    e.printStackTrace();
                }
            }
            else{
                extension = "";
            }
            content = sb.toString();
            text.setText( content );
            return true;

        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Syntax Option.
     * @param extension the extension of the file.
     * @return the SyntaxConstants of the file.
     */
    private static String getSyntax( String extension ) {
        if( extensionMap.isEmpty() ) {
            extensionMap.put("c", SyntaxConstants.SYNTAX_STYLE_C);
            extensionMap.put("cpp", SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
            extensionMap.put("cs", SyntaxConstants.SYNTAX_STYLE_CSHARP);
            extensionMap.put("html", SyntaxConstants.SYNTAX_STYLE_HTML);
            extensionMap.put("java", SyntaxConstants.SYNTAX_STYLE_JAVA);
            extensionMap.put("js", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
            extensionMap.put("php", SyntaxConstants.SYNTAX_STYLE_PHP);
            extensionMap.put("py", SyntaxConstants.SYNTAX_STYLE_PYTHON);
            extensionMap.put("rb", SyntaxConstants.SYNTAX_STYLE_RUBY);
            extensionMap.put("sql", SyntaxConstants.SYNTAX_STYLE_SQL);
            extensionMap.put("xml", SyntaxConstants.SYNTAX_STYLE_XML);
        }
        return extensionMap.get( extension );
    }

    /**
     * Closer to the File Open.
     * @param frame The TextEditor.
     * @param tab The index of the tab that would be close.
     */
    public void close( TextEditor frame , int tab ) {
        int confirm = JOptionPane.showConfirmDialog( frame , "Would you like to save the changes?" , "Confirm" , JOptionPane.YES_NO_CANCEL_OPTION );
        if(confirm == 0) {
            try {
                save();
                frame.closeTab(tab);
            }
            catch (Exception e){

            }
        }
        else if( confirm == 1 ) {
            frame.closeTab( tab );
        }
    }

    /**
     * Closer to the File Open.
     * @param frame The TextEditor.
     */
    public void close( TextEditor frame ) {
        int confirm = JOptionPane.showConfirmDialog( frame , "Would you like to save the changes?" , "Confirm" , JOptionPane.YES_NO_CANCEL_OPTION );
        if( confirm == 0 ) {
            try {
                save();
                frame.closeSelectedTab();
            }
            catch (Exception e){

            }
        }
        else if( confirm == 1 ){
            frame.closeSelectedTab();
        }
    }

    /**
     * Read all the text and Save the content in the Path of this File.
     */
    public void save() throws IOException {
        BufferedWriter output = null;

            output = new BufferedWriter( new FileWriter( path ) );
            output.write( text.getText() );
       output.close();
    }
    public String getContent() {
        return content;
    }

    public String getExtension() {
        return extension;
    }

    public void setSyntax( String syntax ) {
        text.setSyntaxEditingStyle( syntax );
    }

    public RSyntaxTextArea getText() {
        return text;
    }

    public String getPath(){
        return path;
    }

    public String getFileName(){
        return path.substring( path.lastIndexOf( "/" ) + 1 );
    }
}

