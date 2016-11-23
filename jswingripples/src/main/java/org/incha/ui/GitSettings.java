package org.incha.ui;


import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.incha.core.JavaProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

/**
 * Created by constanzafierro on 22-11-16.
 */
public class GitSettings extends JPanel {
    private JTextField url = new JTextField(20);
    private JButton select = new JButton("Select");
    private SourcesEditor sourcesEditor;
    private File selectedFile;
    private String remoteUrl;

    public GitSettings(JavaProject project){
        super(new BorderLayout());
        sourcesEditor = new SourcesEditor(project);
        //url
        final JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel jLabel = new JLabel("URL Project GitHub: ",JLabel.LEFT);
        north.add(jLabel,BorderLayout.WEST);
        north.add(url,BorderLayout.EAST);
        add(north, BorderLayout.NORTH);
        // dir
        final JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel dirSelect = new JLabel("Select Directory: ",JLabel.LEFT);
        final JLabel selected = new JLabel("", JLabel.LEFT);
        center.add(dirSelect,BorderLayout.WEST);
        center.add(selected);
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = sourcesEditor.selectFile();
                if(f!=null) {
                    selected.setText(f.toString());
                    selectedFile = f;
                }
            }
        });
        center.add(select,BorderLayout.EAST);
        add(center, BorderLayout.CENTER);
    }
    void handleOk() throws IOException {
        remoteUrl = url.getText();
        File fileForRepository;
        try {
            fileForRepository = File.createTempFile("GiHubProject", "", selectedFile);
            if (!fileForRepository.delete()) {
                throw new IOException("Could not delete temporary file " + fileForRepository);
            }
        }
        catch (IOException e){
            windowError("Problems creating file, please try again");
            return;
        }
        // then clone
        try{
            Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(fileForRepository)
                    .call();
            sourcesEditor.addFileToProject(fileForRepository);
        }
        catch (org.eclipse.jgit.api.errors.TransportException e){
            windowError("Connection error, please check internet");
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!

    }

    private void windowError(String message) {
        final JDialog dialog = new JDialog();
        JPanel panel = new JPanel(new BorderLayout());
        JLabel error = new JLabel(message, JLabel.CENTER);
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        panel.add(error, BorderLayout.CENTER);
        panel.add(ok, BorderLayout.EAST);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setVisible(true);
    }

}
