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
        bar.add(createGraphStyleMenu());
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

    private JMenu createGraphStyleMenu() {
        /* Start: Graph Style Menu */
        final JMenu Graph_style = new JMenu("Graph Style");


        //Graph Style Menu Menu ---> Submenu: Toggle - Node size by rank
        final JMenuItem nodesize = new JMenuItem("Toggle - Node size by rank");
        nodesize.addActionListener(new NodeSizeChangeAction());
        Graph_style.add(nodesize);
        nodesize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));

        //Graph Style Menu Menu ---> Submenu: Bigger Nodes
        final JMenuItem bigger_nodes = new JMenuItem("Bigger Nodes");
        bigger_nodes.addActionListener(new NodeChangeAction(0));
        Graph_style.add(bigger_nodes);
        bigger_nodes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));

        //Graph Style Menu Menu ---> Submenu: Smaller Nodes
        final JMenuItem smaller_nodes = new JMenuItem("Smaller Nodes");
        smaller_nodes.addActionListener(new NodeChangeAction(1));
        Graph_style.add(smaller_nodes);
        smaller_nodes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));

        //Graph Style Menu Menu ---> Submenu: Toggle - Zoomed text
        final JMenuItem zoomed_text = new JMenuItem("Toggle - Zoomed text");
        zoomed_text.addActionListener(new Zoomed_text_changer());
        Graph_style.add(zoomed_text);
        zoomed_text.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.ALT_MASK));

        //Graph Style Menu Menu ---> Submenu: Bigger text
        final JMenuItem bigger_text = new JMenuItem("Bigger text");
        bigger_text.addActionListener(new Text_size_changer(0));
        Graph_style.add(bigger_text);
        bigger_text.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));

        //Graph Style Menu Menu ---> Submenu: Smaller text
        final JMenuItem smaller_text = new JMenuItem("Smaller text");
        smaller_text.addActionListener(new Text_size_changer(1));
        Graph_style.add(smaller_text);
        smaller_text.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.ALT_MASK));

        //Graph Style Menu Menu ---> Submenu: Colors
        JMenu submenu1 = new JMenu("Colors");

        //Graph Style Menu Menu ---> Submenu: Colors ---> Colors list
        JMenuItem c0 = new JMenuItem("White");
        c0.addActionListener(new NodeColorChangeAction("white"));
        JMenuItem c1 = new JMenuItem("SandyBrown");
        c1.addActionListener(new NodeColorChangeAction("sandybrown"));
        JMenuItem c2 = new JMenuItem("Sienna");
        c2.addActionListener(new NodeColorChangeAction("sienna"));
        JMenuItem c3 = new JMenuItem("Salmon");
        c3.addActionListener(new NodeColorChangeAction("salmon"));
        JMenuItem c4 = new JMenuItem("MediumOrchid");
        c4.addActionListener(new NodeColorChangeAction("mediumorchid"));
        JMenuItem c5 = new JMenuItem("NavyBlue");
        c5.addActionListener(new NodeColorChangeAction("navyblue"));
        JMenuItem c6 = new JMenuItem("SkyBlue");
        c6.addActionListener(new NodeColorChangeAction("skyblue"));
        JMenuItem c7 = new JMenuItem("Aquamarine");
        c7.addActionListener(new NodeColorChangeAction("aquamarine"));
        JMenuItem c8 = new JMenuItem("LimeGreen");
        c8.addActionListener(new NodeColorChangeAction("limegreen"));
        JMenuItem c9 = new JMenuItem("Black");
        c9.addActionListener(new NodeColorChangeAction("black"));

        Graph_style.add(submenu1);
        submenu1.add(c0);
        submenu1.add(c1);
        submenu1.add(c2);
        submenu1.add(c3);
        submenu1.add(c4);
        submenu1.add(c5);
        submenu1.add(c6);
        submenu1.add(c7);
        submenu1.add(c8);
        submenu1.add(c9);

        //Graph Style Menu Menu ---> Submenu: Themes
        JMenu submenu2 = new JMenu("Themes");

        //Graph Style Menu Menu ---> Submenu: Colors ---> Themes list
        JMenuItem t0 = new JMenuItem("Cappuccino");
        t0.addActionListener(new Theme_changer(0));

        JMenuItem t1 = new JMenuItem("Stars");
        t1.addActionListener(new Theme_changer(1));

        JMenuItem t2 = new JMenuItem("Blob");
        t2.addActionListener(new Theme_changer(2));

        JMenuItem t3 = new JMenuItem("Perimeter");
        t3.addActionListener(new Theme_changer(3));

        JMenuItem t4 = new JMenuItem("Glow");
        t4.addActionListener(new Theme_changer(4));

        JMenuItem t5 = new JMenuItem("Bond");
        t5.addActionListener(new Theme_changer(5));

        Graph_style.add(submenu2);
        submenu2.add(t0);
        submenu2.add(t1);
        submenu2.add(t2);
        submenu2.add(t3);
        submenu2.add(t4);
        submenu2.add(t5);
        /* End: Graph Style Menu */
        return Graph_style;
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
