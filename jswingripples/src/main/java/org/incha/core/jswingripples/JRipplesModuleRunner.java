package org.incha.core.jswingripples;

import java.util.Collection;

/**
 * Created by stefano on 19-10-16.
 */
public class JRipplesModuleRunner {
    public interface ModuleRunnerListener {
        void runSuccessful();
        void runFailure();
    }

    private ModuleRunnerListener listener;
    private int modulesFinished;
    private int targetModulesRunning;

    public JRipplesModuleRunner(ModuleRunnerListener listener) {
        this.listener = listener;
    }

    public void moduleFinished() {
        // this method is called by each running module
        if (++modulesFinished == targetModulesRunning) {
            // a module runner run is successful only if all
            // modules were successful.
            listener.runSuccessful();
        }
    }

    public void runModules(Collection<JRipplesModuleInterface> modules) {
        modulesFinished = 0;
        targetModulesRunning = modules.size();
        System.out.println("Running modules. Target = " + targetModulesRunning);
        for (JRipplesModuleInterface module : modules) {
            try {
                module.runModuleWithinRunner(this);
            } catch (Exception e) {
                listener.runFailure();
            }
        }
    }
}
