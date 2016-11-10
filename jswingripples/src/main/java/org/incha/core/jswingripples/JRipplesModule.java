package org.incha.core.jswingripples;

/**
 * JRipples modules implement this interface
 */
public abstract class JRipplesModule {
    /***
     * Runs a {@link JRipplesModule} in a {@link JRipplesModuleRunner} context.
     * MUST CALL {@link JRipplesModuleRunner#moduleFinished()} to notify the module runner after it's done
     * @param moduleRunner the module runner where the JRipplesModule is being run
     */
    public abstract void runModuleWithinRunner(JRipplesModuleRunner moduleRunner);
}
