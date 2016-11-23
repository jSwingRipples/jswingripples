package org.incha.ui.dependency;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.incha.core.JavaProject;

import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;
import org.incha.ui.JSwingRipplesApplication;
import org.incha.utils.FileVisitor;
import org.incha.utils.IoUtils;
import java.util.regex.Matcher;

public class OpenWithIDEAction extends AbstractAction {
    private static final long serialVersionUID = 6537463394107510663L;
    /**
     * Node.
     */
    private final JSwingRipplesEIGNode node;

    /**
     * @param node node.
     */
    public OpenWithIDEAction(final JSwingRipplesEIGNode node) {
        super("Open with Editor");
        this.node = node;
        //this.enabled = false;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        try{
            File f = new File(node.getAbsolutePath().replaceAll("/", Matcher.quoteReplacement(File.separator)));
            if (!editFile(f)){
                if (!openFile(f)){
                    JOptionPane.showMessageDialog(null, "It is not possible to open the file with the default editor.");
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    public boolean editFile(final File file) {
        if (!Desktop.isDesktopSupported()) {
          return false;
        }

        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.EDIT)) {
          return false;
        }

        try {
          desktop.edit(file);
        } catch (IOException e) {
          // Log an error
          return false;
        }

        return true;
    }
    
    public boolean openFile(final File file) {
        if (!Desktop.isDesktopSupported()) {
          return false;
        }

        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.OPEN)) {
          return false;
        }

        try {
          desktop.open(file);
        } catch (IOException e) {
          // Log an error
          return false;
        }

        return true;
    }
}
