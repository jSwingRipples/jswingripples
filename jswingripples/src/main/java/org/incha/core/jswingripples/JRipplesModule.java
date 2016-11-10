package org.incha.core.jswingripples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static org.incha.core.jswingripples.JRipplesModule.Priority.LOW;

/**
 * JRipples modules implement this interface
 */
public abstract class JRipplesModule {
    public enum Priority {
        LOW,
        HIGH
    }

    protected Priority priority = LOW; // default priority value is LOW

    /**
     *
     * @param modules
     * @return a collection of modules with those with higher priority grouped at the front.
     */
    public static LinkedList<Collection<JRipplesModule>> sortByPriority(Collection<JRipplesModule> modules) {
        LinkedList<Collection<JRipplesModule>> sortedModules = new LinkedList<>();
        sortedModules.add(filterModulesWithPriority(modules, Priority.HIGH));
        sortedModules.add(filterModulesWithPriority(modules, Priority.LOW));
        return sortedModules;
    }

    private static Collection<JRipplesModule> filterModulesWithPriority(
            Collection<JRipplesModule> modules, Priority priority ) {
        Collection<JRipplesModule> filteredModules = new ArrayList<>();
        for (JRipplesModule module : modules) {
            if (module.priority == priority) {
                filteredModules.add(module);
            }
        }
        return filteredModules;
    }

    public JRipplesModule withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    /***
     * Runs a {@link JRipplesModule} in a {@link JRipplesModuleRunner} context.
     * MUST CALL {@link JRipplesModuleRunner#moduleFinished()} to notify the module runner after it's done
     * @param moduleRunner the module runner where the JRipplesModule is being run
     */
    public abstract void runModuleWithinRunner(JRipplesModuleRunner moduleRunner);
}
