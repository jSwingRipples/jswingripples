package org.incha.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.apache.commons.logging.LogFactory;
import org.incha.core.JavaProject;
import org.incha.core.JavaProjectsModel;
import org.incha.core.StatisticsManager;

import org.incha.ui.stats.*;
import org.incha.ui.search.NewSearchMenu;

public class JSwingRipplesApplication extends JFrame {
    private static final long serialVersionUID = 6142679404175274529L;

    /**
     * The view area
     */
    private final JDesktopPane viewArea = new JDesktopPane();
    private final ProjectsView projectsView;
    private static JSwingRipplesApplication instance;
    private final ProgressMonitorImpl progressMonitor = new ProgressMonitorImpl();

	

    /**
     * Default constructor.
     */
    public JSwingRipplesApplication() {
        super("JSwingRipples");
        instance = this;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final JPanel contentPane = new JPanel(new BorderLayout(0, 5));
        setContentPane(contentPane);
        contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));

        setJMenuBar(createMenuBar());

        projectsView = new ProjectsView(JavaProjectsModel.getInstance());
        projectsView.addProjectsViewMouseListener(new ProjectsViewMouseListener() {
            @Override
            public void handle(final ProjectsViewMouseEvent e) {
                handleProjectsViewMouseEvent(e);
            }
        });

        final JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, projectsView, viewArea);
        getContentPane().add(pane, BorderLayout.CENTER);

        //add progress monitor.
        getContentPane().add(progressMonitor, BorderLayout.SOUTH);

        //init liteners
        final DefaultController controller = new DefaultController();
        StatisticsManager.getInstance().addStatisticsChangeListener(controller);


        //create model saver, this class will watch for model
        //and save it when model state changed
        new ModelSaver(JavaProjectsModel.getInstance(), JavaProjectsModel.getModelFile());
    }

    /**
     * @param e
     */
    protected void handleProjectsViewMouseEvent(final ProjectsViewMouseEvent e) {
        if (e.getType() != ProjectsViewMouseEvent.LEFT_MOUSE_PRESSED) {
            return;
        }

        final Object[] path = e.getPath();
        if (path[path.length -1] instanceof JavaProject) {
            final JavaProject project = (JavaProject) path[path.length -1];
            final JPopupMenu menu = new JPopupMenu();

            //delete project menu item
            final JMenuItem delete = new JMenuItem("Delete");
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    JavaProjectsModel.getInstance().deleteProject(project);
                }
            });
            menu.add(delete);

            //project preferences menu item
            final JMenuItem prefs = new JMenuItem("Preferences");
            prefs.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    showProjectSettinsEditor(project);
                }
            });
            menu.add(prefs);

            //start analisics
            final JMenuItem startAnalysis = new JMenuItem("Start analysis");
            startAnalysis.addActionListener(new StartAnalysisAction());
            menu.add(startAnalysis);

            menu.show(projectsView, e.getX(), e.getY());
        }
    }
    /**
     * @param project
     */
    protected void showProjectSettinsEditor(final JavaProject project) {
        final JFrame f = new JFrame("Project Settings");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.getContentPane().setLayout(new BorderLayout(0, 5));

        final ProjectSettingsEditor view = new ProjectSettingsEditor(project);
        f.getContentPane().add(view, BorderLayout.CENTER);

        //add ok button
        final JPanel south = new JPanel(new FlowLayout());
        final JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                f.dispose();
            }
        });
        south.add(ok);
        f.getContentPane().add(south, BorderLayout.SOUTH);

        //set frame location
        final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(size.width / 2, size.height / 2);
        f.setLocationRelativeTo(this);

        //show frame
        f.setVisible(true);
    }

    /**
     * @return
     */
    private JMenuBar createMenuBar() {    	
        final JMenuBar bar = new JMenuBar();       
        
        //file menu
        final JMenu file = new JMenu("File");
        bar.add(file);

        final JMenuItem newProject = new JMenuItem("New Project");
        newProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                createNewProject();
            }
        });
        file.add(newProject);

        //Import Project option.
        //Imports a project into the workspace
        final JMenuItem importProject = new JMenuItem("Import Project");
        importProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                importProject();
            }
        });
        file.add(importProject);

        //JRipples menu
        final JMenu jRipples = new JMenu("JRipples");
        bar.add(jRipples);

        final JMenuItem startAnalysis = new JMenuItem("Start analysis");
        StartAnalysisAction act = new StartAnalysisAction();
        startAnalysis.addActionListener(act);
        jRipples.add(startAnalysis);

        jRipples.add(new JSeparator(JSeparator.HORIZONTAL));
        final JMenuItem currentState = new JMenuItem("Current state - statistics");
        currentState.addActionListener(new ShowCurrentStateAction());
        jRipples.add(currentState);

        final JMenuItem currentGraph = new JMenuItem("Current Graph");
        
        currentGraph.addActionListener(new GraphVisualizationAction());
        jRipples.add(currentGraph);

        final JMenuItem impactGraph = new JMenuItem("Impact Set Graph");
        impactGraph.addActionListener(new ImpactGraphVisualizationAction());
        jRipples.add(impactGraph);
        
        final JMenu Graph_style = new JMenu("Graph Style");
        bar.add(Graph_style);
        
        final JMenuItem nodesize = new JMenuItem("Toggle - Node size by rank");
        nodesize.addActionListener(new Node_size_changer());
        Graph_style.add(nodesize);
        nodesize.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        
        final JMenuItem bigger_nodes = new JMenuItem("Bigger Nodes");
        bigger_nodes.addActionListener(new Bigger_node_changer());
        Graph_style.add(bigger_nodes);
        bigger_nodes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
        
        final JMenuItem smaller_nodes = new JMenuItem("Smaller Nodes");
        smaller_nodes.addActionListener(new Smaller_node_changer());
        Graph_style.add(smaller_nodes);
        smaller_nodes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        
        final JMenuItem zoomed_text = new JMenuItem("Toggle - Zoomed text");
        zoomed_text.addActionListener(new Zoomed_text_changer());
        Graph_style.add(zoomed_text);
        zoomed_text.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.ALT_MASK));
        
        JMenu submenu1 = new JMenu("Colors");
        
        JMenuItem c0 = new JMenuItem("White");
        c0.addActionListener(new Node_color_changer("white"));
        JMenuItem c1 = new JMenuItem("SandyBrown");
        c1.addActionListener(new Node_color_changer("sandybrown"));
        JMenuItem c2 = new JMenuItem("Sienna");
        c2.addActionListener(new Node_color_changer("sienna"));
        JMenuItem c3 = new JMenuItem("Salmon");
        c3.addActionListener(new Node_color_changer("salmon"));
        JMenuItem c4 = new JMenuItem("MediumOrchid");
        c4.addActionListener(new Node_color_changer("mediumorchid"));
        JMenuItem c5 = new JMenuItem("NavyBlue");
        c5.addActionListener(new Node_color_changer("navyblue"));
        JMenuItem c6 = new JMenuItem("SkyBlue");
        c6.addActionListener(new Node_color_changer("skyblue"));
        JMenuItem c7 = new JMenuItem("Aquamarine");
        c7.addActionListener(new Node_color_changer("aquamarine"));
        JMenuItem c8 = new JMenuItem("LimeGreen");
        c8.addActionListener(new Node_color_changer("limegreen"));
        JMenuItem c9 = new JMenuItem("Black");
        c9.addActionListener(new Node_color_changer("black"));
        
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
        
        JMenu submenu2 = new JMenu("Themes");
        
        JMenuItem t0 = new JMenuItem("Plain");
        t0.addActionListener(new Theme_changer(0));
        
        JMenuItem t1 = new JMenuItem("Stars");
        t1.addActionListener(new Theme_changer(1));
        

        
        Graph_style.add(submenu2);
        submenu2.add(t0);
        submenu2.add(t1);
        
        
        
        
        
       
//        final JMenuItem manageStates = new JMenuItem("Manage Statess");
//        jRipples.add(manageStates);
//        final JMenuItem saveState = new JMenuItem("Save State");
//        jRipples.add(saveState);
//        final JMenuItem loadState = new JMenuItem("Load State");
//        jRipples.add(loadState);        
        
        bar.add(new NewSearchMenu().getSearchPanel());  //Se agrega el menu de búsqueda
        
        return bar;
    }

    /**
     * Creates new project.
     */
    protected void createNewProject() {
        final JavaProject project = NewProjectWizard.showDialog(this);
        if (project != null) {
            JavaProjectsModel.getInstance().addProject(project);
        }
    }

    /**
     * Import a project from a path.
     */
    protected void importProject(){
        final JavaProject project = NewProjectWizard.showDialog(this);
        if (project != null) {
            if(JavaProjectsModel.getInstance().addProject(project)) {
                new ImportSource(project);
            }
        }


    }

    /**
     * @return the application home folder.
     */
    public static File getHome() {
        return new File(System.getProperty("user.home") + File.separator + ".jswingripples");
    }

    public static void main(final String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        //init logging
        getHome().mkdirs();

        //Properties
        Properties prop = new Properties();
        try
        {
            InputStream in = JSwingRipplesApplication.class.getClassLoader().getResourceAsStream("project.properties");
            prop.load(in);
        } catch (IOException e) {
            LogFactory.getLog(JSwingRipplesApplication.class).error("Missing properties file!");
            System.exit(1);
        }
        final JFrame f = new JSwingRipplesApplication();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set frame location
        final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(size.width / 2, size.height / 2);
        f.setLocationByPlatform(true);
        //LogFactory.getLog(JSwingRipplesApplication.class).debug("Prueba uno");
        String info = prop.getProperty("project_name") + " version " + prop.getProperty("project_version");
        LogFactory.getLog(JSwingRipplesApplication.class).info(info);
        
        // If called with the protocol args
        processArgs(args);

        //show frame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                f.setVisible(true);
            }
        });
    }
    
    private static void processArgs(final String[] args) {
    	if (args.length != 0) {
    		JavaProjectsModel javaProjectsModel = JavaProjectsModel.getInstance();
    		JavaProject project = javaProjectsModel.getProjectByName(args[0]);
    		if (project == null) {
    			project = new JavaProject(args[0]);
    			javaProjectsModel.addProject(project);
    		}
    		for (int argNumber = 1; argNumber < args.length; argNumber++) {
    			project.getBuildPath().addSource(new File(args[argNumber]));
    		}
    	}
    }

    /**
     * Get function using singleton.
     * @return shared application window.
     */
    public static JSwingRipplesApplication getInstance() {
    	if(instance==null){
    		instance=new JSwingRipplesApplication();
    	}
        return instance;
    }

    /**
     * @return progress monitor.
     */
    public TaskProgressMonitor getProgressMonitor() {
        return this.progressMonitor;
    }
    /**
     * @return the viewArea
     */
    public JDesktopPane getViewArea() {
        return viewArea;
    }
}
