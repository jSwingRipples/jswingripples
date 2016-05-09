package org.incha.core.texteditor;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import java.io.*;

/**
 * Created by rodrigo on 05-05-2016.
 */
public class FileOpen {
    private RSyntaxTextArea text;
    private String path;
    private String extension;
    private String content;

    public FileOpen( String absolutePath ) {
        path = absolutePath;
        text = new RSyntaxTextArea();
    }

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
            if (path.indexOf('.') != -1) {
                extension = path.substring(path.lastIndexOf('.') + 1);
            }
            else{
                extension = "";
            }
            content = sb.toString();
            text.setText(content);
            return true;

        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }
    public void save( ) {
        BufferedWriter output = null;
        try{
            output = new BufferedWriter( new FileWriter( path ) );
            output.write( text.getText() );

        } catch( IOException e ){
            e.printStackTrace();
        } finally {
            try {
                output.close();
            }catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
    public String getContent() {
        return content;
    }

    public String getExtension() {
        return extension;
    }

    public void setSyntax(String syntax) {
        text.setSyntaxEditingStyle(syntax);
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

