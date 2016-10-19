package org.incha.ui.stats;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import org.incha.core.JavaProject;
import org.incha.core.JavaProjectsModel;
import org.incha.core.ModuleConfiguration;
import org.incha.core.StatisticsManager;
import org.incha.core.jswingripples.GraphBuilder;
import org.incha.core.jswingripples.JRipplesModuleInterface;
import org.incha.core.jswingripples.JRipplesModuleRunner;
import org.incha.core.jswingripples.NodeSearchBuilder;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.search.Indexer;
import org.incha.ui.JSwingRipplesApplication;
import org.incha.ui.jripples.JRipplesDefaultModulesConstants;

public class StartAnalysisAction implements ActionListener {
    /**
     * Default constructor.
     */
	
    public StartAnalysisAction() {
        super();
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        final JSwingRipplesApplication app = JSwingRipplesApplication.getInstance();
        final StartAnalysisDialog dialog = new StartAnalysisDialog(app, this);
        dialog.pack();
        dialog.setLocationRelativeTo(app);
        dialog.setVisible(true);
    }

    public void startAnalysis(StartAnalysisDialog dialog) {
        final String projectName = (String) dialog.projects.getSelectedItem();
        if (projectName == null) {
            return;
        }

        final JavaProject project = JavaProjectsModel.getInstance().getProject(projectName);
        final JSwingRipplesEIG eig = new JSwingRipplesEIG(project);

        eig.setMainClass(dialog.getMainClass());

        final ModuleConfiguration config = new ModuleConfiguration();
        //module dependency builder
        String module = (String) dialog.dependencyGraph.getSelectedItem();
        if (JRipplesDefaultModulesConstants.MODULE_DEPENDENCY_BUILDER.equals(module)) {
            config.setDependencyGraphModule(ModuleConfiguration.MODULE_DEPENDENCY_BUILDER);
        } else {
            config.setDependencyGraphModule(ModuleConfiguration.MODULE_DEPENDENCY_BUILDER_WITH_POLYMORPHIC);
        }

        //Incremental change
        module = (String) dialog.incrementalChange.getSelectedItem();
        if (JRipplesDefaultModulesConstants.MODULE_IMPACT_ANALYSIS_TITLE.equals(module)) {
            config.setIncrementalChange(ModuleConfiguration.MODULE_IMPACT_ANALYSIS);
        } else if (JRipplesDefaultModulesConstants.MODULE_IMPACT_ANALYSIS_RELAXED_TITLE.equals(module)) {
            config.setIncrementalChange(ModuleConfiguration.MODULE_IMPACT_ANALYSIS_RELAXED);
        } else if (JRipplesDefaultModulesConstants.MODULE_CHANGE_PROPAGATION_RELAXED_TITLE.equals(module)) {
            config.setIncrementalChange(ModuleConfiguration.MODULE_CHANGE_PROPAGATION_RELAXED);
        } else if (JRipplesDefaultModulesConstants.MODULE_CHANGE_PROPAGATION_TITLE.equals(module)) {
            config.setIncrementalChange(ModuleConfiguration.MODULE_CHANGE_PROPAGATION);
        } else if (JRipplesDefaultModulesConstants.MODULE_CONCEPT_LOCATION_TITLE.equals(module)) {
            config.setIncrementalChange(ModuleConfiguration.MODULE_CONCEPT_LOCATION);
        } else if (JRipplesDefaultModulesConstants.MODULE_CONCEPT_LOCATION_RELAXED_TITLE.equals(module)) {
            config.setIncrementalChange(ModuleConfiguration.MODULE_CONCEPT_LOCATION_RELAXED);
        }

        module = (String) dialog.analysis.getSelectedItem();
        if (JRipplesDefaultModulesConstants.MODULE_IMPACT_ANALYSIS_TITLE.equals(module)) {
            config.setAnalysis(ModuleConfiguration.MODULE_IMPACT_ANALYSIS);
        }

        project.setModuleConfiguration(config);
        final List<JRipplesModuleInterface> m = config.buildModules(eig);

        new JRipplesModuleRunner(new JRipplesModuleRunner.ModuleRunnerListener() {
            @Override
            public void runSuccessful() {
                System.out.printf("run success");
                StatisticsManager.getInstance().addStatistics(config, eig);
            }

            @Override
            public void runFailure() {
                System.out.println("run failure");
            }
        }).runModules(m);

        GraphBuilder.getInstance().addEIG(eig);
        GraphBuilder.getInstance().resetGraphs();
        Thread t = new Thread(new GraphBuild());
        t.start();
        eig.addJRipplesEIGListener(GraphBuilder.getInstance());
        try {
            NodeSearchBuilder.getInstance().addEIG(eig);
        } catch (CloneNotSupportedException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        // Set search indexer current project.
        try {
            Indexer.getInstance().indexEIG(eig);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private class GraphBuild implements Runnable
    {
        @Override
        public void run() {
            GraphBuilder.getInstance().prepareGraphs();
        }
    }
}
