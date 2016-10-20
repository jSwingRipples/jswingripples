package org.incha.core.jswingripples;

/**
 * JRipples modules implement this interface
 */
public interface JRipplesModule {
    /***
     * Runs a {@link JRipplesModule} in a {@link JRipplesModuleRunner} context.
     * MUST CALL {@link JRipplesModuleRunner#moduleFinished()} to notify the module runner after it's done
     * @param moduleRunner the module runner where the JRipplesModule is being run
     */
    void runModuleWithinRunner(JRipplesModuleRunner moduleRunner);
}
