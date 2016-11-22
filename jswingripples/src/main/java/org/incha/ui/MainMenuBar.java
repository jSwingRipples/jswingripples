package org.incha.ui;

import org.graphstream.graph.Graph;
import org.incha.core.search.Searcher;
import org.incha.ui.search.SearchMenu;
import org.incha.ui.stats.GraphVisualizationAction;
import org.incha.ui.stats.ImpactGraphVisualizationAction;
import org.incha.ui.stats.ShowCurrentStateAction;
import org.incha.ui.stats.StartAnalysisAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenuBar {
    private final JSwingRipplesApplication context;
    private final JMenuBar bar;
    private SearchMenu searchMenu;

    public static JMenuBar create(JSwingRipplesApplication context) {
        return new MainMenuBar(context).bar;
    }

    private MainMenuBar(final JSwingRipplesApplication context) {
        this.context = context;
        bar = new JMenuBar();
        bar.add(createFileMenu());
        bar.add(createjRipplesMenu());
        bar.add(createHelpMenu());
        bar.add(createSearchPanel());
    }

    private JMenu createFileMenu() {
        final JMenu file = new JMenu("File");
        bar.add(file);

        //File Menu ---> Submenu: Import Project
        final JMenuItem importProject = new JMenuItem("Import Project");
        importProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                context.importProject();
            }
        });
        file.add(importProject);
        return file;
    }

    private JMenu createjRipplesMenu() {
        /* Start: JRipples Menu */
        final JMenu menu = new JMenu("JRipples");
        //JRipples Menu ---> Submenu: Start analysis
        final JMenuItem startAnalysis = new JMenuItem("Start analysis");
        startAnalysis.addActionListener(new StartAnalysisAction());
        menu.add(startAnalysis);

        menu.add(new JSeparator(JSeparator.HORIZONTAL));

        //JRipples Menu ---> Submenu: Current state - statistics
        final JMenuItem currentState = new JMenuItem("Current state - statistics");
        currentState.addActionListener(new ShowCurrentStateAction());
        menu.add(currentState);

        //JRipples Menu ---> Submenu: Current Graph
        final JMenuItem currentGraph = new JMenuItem("Current Graph");
        currentGraph.addActionListener(new GraphVisualizationAction());
        menu.add(currentGraph);

        //JRipples Menu ---> Submenu: Impact Set Graph
        final JMenuItem impactGraph = new JMenuItem("Impact Set Graph");
        impactGraph.addActionListener(new ImpactGraphVisualizationAction());
        menu.add(impactGraph);

        return menu;
    }

    private JMenu createHelpMenu() {
        final JMenu Help = new JMenu("Help");
        //Help Menu ---> Submenu: Help
        final JMenuItem subHelp = new JMenuItem("Help");
        Help.add(subHelp);

        Help.add(new JSeparator(JSeparator.HORIZONTAL));

        //Help Menu ---> Submenu: Help
        final JMenuItem about = new JMenuItem("About");
        Help.add(about);
        return Help;
    }

    private JPanel createSearchPanel() {
        searchMenu = new SearchMenu();
        Searcher.getInstance().setSearchMenu(searchMenu);
        return searchMenu.getSearchPanel();
    }


}
