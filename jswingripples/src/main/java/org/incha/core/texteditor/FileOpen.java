package org.incha.core.texteditor;

import java.io.*;

/**
 * Created by rodrigo on 05-05-2016.
 */
public class FileOpen {
    private String path;
    private String extension;
    private String content;

    public FileOpen( String absolutePath ) {
        path = absolutePath;
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
            return true;

        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }
    public void save( String text ) {
        BufferedWriter output = null;
        try{
            output = new BufferedWriter( new FileWriter( path ) );
            output.write( text );

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
}

