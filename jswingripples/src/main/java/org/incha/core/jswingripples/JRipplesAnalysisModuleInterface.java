/*
 * Created on Oct 20, 2005
 *
 */
package org.incha.core.jswingripples;

/**
 * Interface of JRipples modules that provide various estimations for JRipplesEIG nodes.
 * @author Maksym Petrenko

 */
public abstract class JRipplesAnalysisModuleInterface extends JRipplesModule {

	/**
	 * Called to calculate estimations for all nodes in the JRipple EIG. Typically is called upon EIG initialization.
	 */
	public abstract void AnalyzeProjectWithinModuleRunner(JRipplesModuleRunner moduleRunner);
}

